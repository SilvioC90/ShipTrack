package it.silco.shiptrack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.silco.shiptrack.data.InputData;

public class InputDataTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final Logger logger = LogManager.getLogger(this.getClass());

	private String[] columnNames;
	private final Class<?>[] columnClass;

	private List<InputData> data = new ArrayList<InputData>();

	private List<int[]> changes = new ArrayList<int[]>();

	public InputDataTableModel(List<InputData> data, Properties l) {
		this.data = data;
		this.columnNames = new String[] { l.getProperty("id"), l.getProperty("track_id"), l.getProperty("company") };
		this.columnClass = new Class<?>[] { String.class, String.class, String.class };

		logger.debug("Input data table model created.");
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(final int rowIndex, int columnIndex) {
		Object value = null;
		InputData row = data.get(rowIndex);
		if (0 == columnIndex) {
			value = row.getId();
		} else if (1 == columnIndex) {
			value = row.getCompany();
		} else if (2 == columnIndex) {
			value = row.getTrackId();
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
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean isEditable = true;
		return isEditable;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		changes.add(new int[] { rowIndex, columnIndex });
		InputData row = data.get(rowIndex);
		if (0 == columnIndex) {
			row.setId((String) aValue);
			data.get(rowIndex).setId((String) aValue);
		} else if (1 == columnIndex) {
			row.setCompany((String) aValue);
			data.get(rowIndex).setCompany((String) aValue);
		} else if (2 == columnIndex) {
			row.setTrackId((String) aValue);
			data.get(rowIndex).setTrackId((String) aValue);
		}
	}

	public List<int[]> getChanges() {
		return changes;
	}

	public void addRow(InputData newRow) {
		data.add(newRow);
		fireTableDataChanged();
	}
}
