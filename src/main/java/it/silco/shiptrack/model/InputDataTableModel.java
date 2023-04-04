package it.silco.shiptrack.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
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
	private final List<Integer> notEditableIndexes;
	private List<int[]> changes = new ArrayList<int[]>();
	private List<Integer> selectedRows = new ArrayList<Integer>();

	public InputDataTableModel(List<InputData> data, Properties l) {
		this.data = data;
		this.columnNames = new String[] { " ", l.getProperty("id"), l.getProperty("track_id"), l.getProperty("company"), l.getProperty("client"), l.getProperty("progNum") };
		this.columnClass = new Class<?>[] { Boolean.class, Integer.class, String.class, String.class, String.class, String.class };
		this.notEditableIndexes = Arrays.asList(1);

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
			value = row.getIsSelected();
		} else if (1 == columnIndex) {
			value = row.getId();
		} else if (2 == columnIndex) {
			value = row.getCompany();
		} else if (3 == columnIndex) {
			value = row.getTrackId();
		} else if (4 == columnIndex) {
			value = row.getClient();
		} else if (5 == columnIndex) {
			value = row.getProgNum();
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
		changes.add(new int[] { rowIndex, columnIndex });
		InputData row = data.get(rowIndex);
		if (0 == columnIndex) {
			row.setIsSelected((Boolean) aValue);
			data.get(rowIndex).setIsSelected((Boolean) aValue);
			if ((Boolean) aValue) {
				selectedRows.add(row.getId());
			} else {
				if (selectedRows.contains(row.getId())) {
					selectedRows.remove(row.getId());
				}
			}
		} else if (1 == columnIndex) {
			row.setId((Integer) aValue);
			data.get(rowIndex).setId((Integer) aValue);
		} else if (2 == columnIndex) {
			row.setCompany((String) aValue);
			data.get(rowIndex).setCompany((String) aValue);
		} else if (3 == columnIndex) {
			row.setTrackId((String) aValue);
			data.get(rowIndex).setTrackId((String) aValue);
		} else if (4 == columnIndex) {
			row.setClient((String) aValue);
			data.get(rowIndex).setClient((String) aValue);
		} else if (5 == columnIndex) {
			row.setProgNum((String) aValue);
			data.get(rowIndex).setProgNum((String) aValue);
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean isEditable = true;
		if (notEditableIndexes.contains(columnIndex)) {
			isEditable = false;
		}
		return isEditable;
	}

	public void removeRows(List<Integer> idsToRemove) {
		int i = 0;
		ListIterator<InputData> li = data.listIterator();
		while (li.hasNext()) {
			InputData id = li.next();
			if (idsToRemove.contains(id.getId())) {
				li.remove();
				selectedRows.remove(id.getId());
				fireTableRowsDeleted(i, i);
				i++;
			}
		}
	}

	public List<Integer> getSelectedRows() {
		return selectedRows;
	}

	public List<int[]> getChanges() {
		return changes;
	}

	public void addRow(InputData newRow) {
		data.add(newRow);
		fireTableDataChanged();
	}
}
