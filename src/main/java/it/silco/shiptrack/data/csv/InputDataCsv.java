package it.silco.shiptrack.data.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.silco.shiptrack.data.InputData;
import it.silco.shiptrack.model.InputDataTableModel;

public class InputDataCsv {
	private Logger logger = LogManager.getLogger(this.getClass());

	private List<InputData> readedCsvData;
	private final static String DEFAULT_HEADER = "id,track_id,company,client,progNum";
	private String header;
	private String filePath;
	private static List<Integer> ids;

	private static InputDataCsv instance;

	private InputDataCsv() {
		readedCsvData = new ArrayList<InputData>();
		ids = new ArrayList<Integer>();
	}

	public List<InputData> readCSVfile(File dataFile) {
		filePath = dataFile.getAbsolutePath();
		try {
			BufferedReader brd = new BufferedReader(new FileReader(dataFile));
			String line = null;
			String[] lineElements;
			InputData currentElement;
			int i = 0;

			while ((line = brd.readLine()) != null) {
				if (i == 0) {
					// first line in header
					header = line;
				} else {
					currentElement = new InputData();
					lineElements = line.split(",");
					int j = 1;
					for (String element : lineElements) {
						if (1 == j) {
							currentElement.setId(Integer.parseInt(element));
							ids.add(Integer.parseInt(element));
						} else if (2 == j) {
							currentElement.setTrackId((String) element);
						} else if (3 == j) {
							currentElement.setCompany((String) element);
						} else if (4 == j) {
							currentElement.setClient((String) element);
						} else if (5 == j) {
							currentElement.setProgNum((String) element);
						}
						j++;
					}
					readedCsvData.add(currentElement);
				}
				i++;
			}

			brd.close();
		} catch (Exception e) {
			logger.error("File not found:" + e.getMessage());
		}

		Collections.sort(readedCsvData, new Comparator<InputData>() {
			public int compare(InputData o1, InputData o2) {
				return o1.getId() - o2.getId();
			}
		});

		return readedCsvData;
	}

	public boolean writeCSVfile(InputDataTableModel tableModel, List<int[]> changes, List<Integer> deletedRows) {
		boolean result = false;
		StringBuilder sb = new StringBuilder();
		sb.append(header + "\n");

		if (deletedRows == null) {
			deletedRows = new ArrayList<Integer>();
		}

		for (InputData elem : readedCsvData) {
			if (deletedRows.contains(elem.getId())) {
				// non scrivo la riga (cancellata)
			} else {
				sb.append(elem.getId());
				sb.append(",");
				sb.append(elem.getTrackId());
				sb.append(",");
				sb.append(elem.getCompany());
				sb.append(",");
				sb.append(elem.getClient());
				sb.append(",");
				sb.append(elem.getProgNum());
				sb.append("\n");
			}
		}

		try {
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			fos.write(sb.toString().getBytes());

			result = true;
			fos.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return result;
	}

	public static List<Integer> getIds() {
		return ids;
	}

	public static void addId(Integer id) {
		ids.add(id);
	}

	public static void removeId(Integer id) {
		ids.remove(id);
	}

	public static void removeIds(List<Integer> ids) {
		InputDataCsv.ids.removeAll(ids);
	}

	public static InputDataCsv getInstance() {
		if (instance != null) {
			// return existing instance
		} else {
			instance = new InputDataCsv();
		}
		return instance;
	}

	public static String getDefaultHeader() {
		return DEFAULT_HEADER;
	}
}