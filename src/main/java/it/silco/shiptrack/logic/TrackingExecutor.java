package it.silco.shiptrack.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
				result.add(maerskTrack(entry.getTrackId()));
			} else if (entry.getCompany().equalsIgnoreCase("MSC")) {
				result.add(mscTrack(entry.getTrackId()));
			} else {
				logger.warn("Compagnia non valida: " + entry.getCompany());
			}
		}

		return result;
	}

	private static TrackingResultData maerskTrack(String trackId) {
		TrackingResultData result = new TrackingResultData();
		result.setCompany("MAERSK");
		result.setTrackId(trackId);
		String fromCity = "-", toCity = "-", arrivalDate = "-";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

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
			}
			if (maerskJson.getDestination() != null) {
				toCity = MiscUtils.escapeCommas(maerskJson.getDestination().getCity());
			}
			if (maerskJson.getContainers().get(0) != null) {
				arrivalDate = sdf.format(maerskJson.getContainers().get(0).getEta_final_delivery());
			}

			result.setFromCity(fromCity);
			result.setToCity(toCity);
			result.setArrivalDate(arrivalDate);
		} catch (FileNotFoundException e) {
			logger.warn("TrackID non trovato: " + trackId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static TrackingResultData mscTrack(String trackId) {
		TrackingResultData result = new TrackingResultData();
		result.setCompany("MSC");
		result.setTrackId(trackId);
		String fromCity = "-", toCity = "-", arrivalDate = "-";

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

						fromCity = MiscUtils.escapeCommas(gti.getShippedFrom());
						toCity = MiscUtils.escapeCommas(gti.getShippedTo());
						arrivalDate = gti.getFinalPodEtaDate();
					} else {
						logger.warn("Track ID non trovato: " + trackId);
					}
				} else {
					logger.warn("JSON di risposta malformato o corrotto.");
				}
			}

			result.setFromCity(fromCity);
			result.setToCity(toCity);
			result.setArrivalDate(arrivalDate);
		} catch (FileNotFoundException e) {
			logger.warn("TrackID non trovato: " + trackId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
