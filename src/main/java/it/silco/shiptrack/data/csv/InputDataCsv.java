package it.silco.shiptrack.data.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.silco.shiptrack.data.InputData;
import it.silco.shiptrack.model.InputDataTableModel;

public class InputDataCsv {
	private Logger logger = LogManager.getLogger(this.getClass());

	private List<InputData> readedCsvData;
	private final static String DEFAULT_HEADER = "track_id,company";
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
					int j = 0;
					for (String element : lineElements) {
						if (0 == j) {
							currentElement.setId(element);
							ids.add(Integer.parseInt(element));
						} else if (1 == j) {
							currentElement.setTrackId(element);
						} else if (2 == j) {
							currentElement.setCompany(element);
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
		return readedCsvData;
	}

	public boolean writeCSVfile(InputDataTableModel tableModel, List<int[]> changes) {
		boolean result = false;
		InputData currentElement;
		for (int[] change : changes) {
			currentElement = readedCsvData.get(change[0]);
			if (0 == change[1]) {
				currentElement.setId((String) tableModel.getValueAt(change[0], change[1]));
			} else if (1 == change[1]) {
				currentElement.setTrackId((String) tableModel.getValueAt(change[0], change[1]));
			} else if (2 == change[1]) {
				currentElement.setCompany((String) tableModel.getValueAt(change[0], change[1]));
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(header + "\n");
		for (InputData elem : readedCsvData) {
			sb.append(elem.getId());
			sb.append(",");
			sb.append(elem.getTrackId());
			sb.append(",");
			sb.append(elem.getCompany());
			sb.append("\n");
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

	public static void addId(int id) {
		ids.add(id);
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