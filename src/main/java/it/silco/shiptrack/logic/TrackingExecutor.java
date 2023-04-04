package it.silco.shiptrack.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.silco.shiptrack.data.InputData;
import it.silco.shiptrack.data.TrackingResultData;
import it.silco.shiptrack.data.json.maersk.MaerskJson;
import it.silco.shiptrack.data.json.msc.GeneralTrackingInfo;
import it.silco.shiptrack.data.json.msc.MscJson;
import it.silco.shiptrack.utils.MiscUtils;

public class TrackingExecutor {
	private static Logger logger = LogManager.getLogger(TrackingExecutor.class);

	private final static String MAERSK_URL = "https://api.maersk.com/track/";
	private final static String MSC_URL = "https://www.msc.com/api/feature/tools/TrackingInfo";

	public static List<TrackingResultData> track(List<InputData> data) {
		List<TrackingResultData> result = new ArrayList<TrackingResultData>();

		for (InputData entry : data) {
			if (entry.getCompany().equalsIgnoreCase("MAERSK")) {
				result.add(maerskTrack(entry));
			} else if (entry.getCompany().equalsIgnoreCase("MSC")) {
				result.add(mscTrack(entry));
			} else {
				logger.warn("Compagnia non valida: " + entry.getCompany());
			}
		}

		return result;
	}

	private static TrackingResultData maerskTrack(InputData data) {
		TrackingResultData result = new TrackingResultData();

		String trackId = data.getTrackId();

		result.setCompany("MAERSK");
		result.setTrackId(trackId);
		result.setClient(data.getClient());
		result.setProgNum(data.getProgNum());

		String fromCity = "-", toCity = "-";
		Date arrivalDate = new Date(0);

		try {
			URL url = new URL(MAERSK_URL + trackId + "?operator=MAEU");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();

			logger.info("Content: " + content);

			ObjectMapper mapper = new ObjectMapper();

			MaerskJson maerskJson = mapper.readValue(content.toString(), MaerskJson.class);

			if (maerskJson.getOrigin() != null) {
				fromCity = MiscUtils.escapeCommas(maerskJson.getOrigin().getCity());
				if (fromCity == null || fromCity.isBlank()) {
					fromCity = "-";
				}
			}
			if (maerskJson.getDestination() != null) {
				toCity = MiscUtils.escapeCommas(maerskJson.getDestination().getCity());
				if (toCity == null || toCity.isBlank()) {
					toCity = "-";
				}
			}
			if (maerskJson.getContainers().get(0) != null) {
				arrivalDate = maerskJson.getContainers().get(0).getEta_final_delivery();
				if (arrivalDate == null) {
					arrivalDate = new Date(0);
				}
			}

		} catch (FileNotFoundException e) {
			logger.warn("TrackID non trovato: " + trackId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		result.setFromCity(fromCity);
		result.setToCity(toCity);
		result.setArrivalDate(arrivalDate);
		return result;
	}

	private static TrackingResultData mscTrack(InputData data) {
		TrackingResultData result = new TrackingResultData();

		String trackId = data.getTrackId();

		result.setCompany("MSC");
		result.setTrackId(trackId);
		result.setClient(trackId);
		result.setClient(data.getClient());
		result.setProgNum(data.getProgNum());

		String fromCity = "-", toCity = "-";
		Date arrivalDate = new Date(0);
		String dateAsString = "";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
			URL url = new URL(MSC_URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.setRequestProperty("x-requested-with", "XMLHttpRequest");

			// Example: {"trackingNumber":"MEDUIQ737278","trackingMode":"0"}
			String jsonString = "{\"trackingNumber\":\"" + trackId + "\",\"trackingMode\":\"0\"}";

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				logger.info("Content: " + response);

				StringTokenizer st = new StringTokenizer(response.toString(), ",");
				if (st.hasMoreTokens()) {
					String successString = st.nextToken();
					if (successString.contains("true")) {
						ObjectMapper mapper = new ObjectMapper();

						MscJson mscJson = mapper.readValue(response.toString(), MscJson.class);
						GeneralTrackingInfo gti = mscJson.getData().getBillOfLadings().get(0).getGeneralTrackingInfo();

						if (gti != null) {
							fromCity = MiscUtils.escapeCommas(gti.getShippedFrom());
							if (fromCity == null || fromCity.isBlank()) {
								fromCity = "-";
							}

							toCity = MiscUtils.escapeCommas(gti.getShippedTo());
							if (toCity == null || toCity.isBlank()) {
								toCity = "-";
							}

							dateAsString = gti.getFinalPodEtaDate();
							arrivalDate = sdf.parse(dateAsString);
							if (arrivalDate == null) {
								arrivalDate = new Date(0);
							}
						}
					} else {
						logger.warn("Track ID non trovato: " + trackId);
					}
				} else {
					logger.warn("JSON di risposta malformato o corrotto.");
				}
			}
		} catch (FileNotFoundException e) {
			logger.warn("TrackID non trovato: " + trackId);
		} catch (ParseException e) {
			logger.error("Formato della data non valido: " + dateAsString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setFromCity(fromCity);
		result.setToCity(toCity);
		result.setArrivalDate(arrivalDate);

		return result;
	}
}
