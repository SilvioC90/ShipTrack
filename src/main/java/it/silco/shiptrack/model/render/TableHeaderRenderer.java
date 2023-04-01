package it.silco.shiptrack.model.render;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.table.TableCellRenderer;

import it.silco.shiptrack.utils.MiscUtils;

public class TableHeaderRenderer extends JTextArea implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public TableHeaderRenderer(boolean isMultiLine) {
		setEditable(false);
		setLineWrap(isMultiLine);
		setOpaque(false);
		setFocusable(false);
		setWrapStyleWord(true);
		LookAndFeel.installBorder(this, "TableHeader.cellBorder");
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setFont(MiscUtils.FONT_BOLD);
		String str = (value == null) ? "" : value.toString();
		setText(str);
		int nRows = str.length() / getColumnWidth();
		setRows(nRows);
		return this;
	}
}