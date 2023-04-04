package it.silco.shiptrack.data.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.silco.shiptrack.model.TrackingResultTableModel;
import it.silco.shiptrack.utils.MiscUtils;

public class TrackingResultCsv {
	private Logger logger = LogManager.getLogger(this.getClass());

	private final static String DEFAULT_HEADER = "track_id,company,from_city,to_city,arrival_date,client,progNum";

	private DateFormat formatter = MiscUtils.DATE_FORMAT;

	private static TrackingResultCsv instance;

	private TrackingResultCsv() {
	}

	public boolean writeCSVfile(TrackingResultTableModel tableModel, File savePath) {
		logger.info("Writing export file to: " + savePath);
		boolean result = false;
		StringBuilder sb = new StringBuilder();
		sb.append(DEFAULT_HEADER + "\n");

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			for (int j = 0; j < tableModel.getColumnCount(); j++) {
				if (j == 4) {
					if (formatter == null) {
						formatter = DateFormat.getDateInstance();
					}

					sb.append(formatter.format(tableModel.getValueAt(i, j)));
				} else {
					sb.append(tableModel.getValueAt(i, j));
				}
				sb.append(",");
			}
			sb = sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}

		try {
			FileOutputStream fos = new FileOutputStream(savePath + File.separator + "export.csv");
			fos.write(sb.toString().getBytes());

			result = true;
			fos.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return result;
	}

	public static TrackingResultCsv getInstance() {
		if (instance != null) {
			// return existing instance
		} else {
			instance = new TrackingResultCsv();
		}
		return instance;
	}

	public static String getDefaultHeader() {
		return DEFAULT_HEADER;
	}
}