package it.silco.shiptrack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.silco.shiptrack.data.TrackingResultData;

public class TrackingResultTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final Logger logger = LogManager.getLogger(this.getClass());

	private String[] columnNames;
	private final Class<?>[] columnClass;

	private List<TrackingResultData> data = new ArrayList<TrackingResultData>();

	public TrackingResultTableModel(List<TrackingResultData> data, Properties l) {
		this.data = data;
		this.columnNames = new String[] { l.getProperty("track_id"), l.getProperty("company"), l.getProperty("from_city"), l.getProperty("to_city"),
				l.getProperty("arrival_date") };
		this.columnClass = new Class<?>[] { String.class, String.class, String.class, String.class, String.class };

		logger.debug("Tracking result table model created.");
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(final int rowIndex, int columnIndex) {
		Object value = null;
		TrackingResultData row = data.get(rowIndex);
		if (0 == columnIndex) {
			value = row.getTrackId();
		} else if (1 == columnIndex) {
			value = row.getCompany();
		} else if (2 == columnIndex) {
			value = row.getFromCity();
		} else if (3 == columnIndex) {
			value = row.getToCity();
		} else if (4 == columnIndex) {
			value = row.getArrivalDate();
		}
		return value;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClass[columnIndex];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		TrackingResultData row = data.get(rowIndex);
		if (0 == columnIndex) {
			row.setTrackId((String) aValue);
			data.get(rowIndex).setTrackId((String) aValue);
		} else if (1 == columnIndex) {
			row.setCompany((String) aValue);
			data.get(rowIndex).setCompany((String) aValue);
		} else if (2 == columnIndex) {
			row.setFromCity((String) aValue);
			data.get(rowIndex).setFromCity((String) aValue);
		} else if (3 == columnIndex) {
			row.setToCity((String) aValue);
			data.get(rowIndex).setToCity((String) aValue);
		} else if (4 == columnIndex) {
			row.setArrivalDate((String) aValue);
			data.get(rowIndex).setArrivalDate((String) aValue);
		}
	}
}
