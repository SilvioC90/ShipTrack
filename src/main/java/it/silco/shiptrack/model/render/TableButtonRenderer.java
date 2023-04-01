package it.silco.shiptrack.model.render;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableButtonRenderer implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JButton button = (JButton) value;
		button.setFocusable(false);
//		button.setFocusPainted(false);
//		button.setRolloverEnabled(false);
//		button.setOpaque(true);
//		button.setSelected(false);
		return button;
	}
}
