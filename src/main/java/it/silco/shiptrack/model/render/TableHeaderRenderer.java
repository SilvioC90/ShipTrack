package it.silco.shiptrack.model.render;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import it.silco.shiptrack.utils.MiscUtils;

public class TableHeaderRenderer implements TableCellRenderer {

	private DefaultTableCellRenderer renderer;

	public TableHeaderRenderer(JTable table) {
		renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		table.getTableHeader().setFont(MiscUtils.FONT_BOLD);

		value = (value == null) ? "" : value.toString();

		JLabel c = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		c.setBackground(new Color(200, 200, 200));
		c.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));

		return c;
	}
}