package it.silco.shiptrack.model.render;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class SelectedCellHighlightRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cellRenderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column < table.getModel().getColumnCount()) {
			if (table.isCellSelected(row, column)) {
				if (!table.getModel().isCellEditable(row, column)) {
					selectedBlockedCellStyle(cellRenderer);
				} else {
					selectedCellStyle(cellRenderer);
				}
			} else if (table.isColumnSelected(column)) {
				if (row % 2 == 0) {
					if (!table.getModel().isCellEditable(row, column)) {
						blockedAlternateCellStyle(cellRenderer);
					} else {
						alternateCellStyle(cellRenderer);
					}
				} else {
					if (!table.getModel().isCellEditable(row, column)) {
						blockedDefaultCellStyle(cellRenderer);
					} else {
						defaultCellStyle(cellRenderer);
					}
				}
			} else if (table.isRowSelected(row)) {
				selectedRowCellStyle(cellRenderer);
			} else {
				if (row % 2 == 0) {
					if (!table.getModel().isCellEditable(row, column)) {
						blockedAlternateCellStyle(cellRenderer);
					} else {
						alternateCellStyle(cellRenderer);
					}
				} else {
					if (!table.getModel().isCellEditable(row, column)) {
						blockedDefaultCellStyle(cellRenderer);
					} else {
						defaultCellStyle(cellRenderer);
					}
				}
			}
		}
		return cellRenderer;
	}

	private void defaultCellStyle(Component c) {
		c.setForeground(Color.BLACK);
		c.setBackground(Color.WHITE);
	}

	private void alternateCellStyle(Component c) {
		c.setForeground(Color.BLACK);
		c.setBackground(new Color(220, 240, 255));
	}

	private void blockedDefaultCellStyle(Component c) {
		c.setForeground(Color.BLACK);
		c.setBackground(new Color(255, 255, 255));
	}

	private void blockedAlternateCellStyle(Component c) {
		c.setForeground(Color.BLACK);
		c.setBackground(new Color(220, 240, 255));
	}

	private void selectedCellStyle(Component c) {
		c.setForeground(Color.BLACK);
		c.setBackground(new Color(192, 237, 173));
	}

	private void selectedBlockedCellStyle(Component c) {
		c.setForeground(Color.BLACK);
		c.setBackground(new Color(192, 237, 173));
	}

	private void selectedRowCellStyle(Component c) {
		c.setForeground(Color.BLACK);
		c.setBackground(new Color(157, 194, 141));
	}
}
