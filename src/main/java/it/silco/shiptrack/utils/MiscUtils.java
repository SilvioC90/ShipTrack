package it.silco.shiptrack.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.silco.shiptrack.data.csv.InputDataCsv;
import it.silco.shiptrack.model.render.CellRenderer;
import it.silco.shiptrack.model.render.TableHeaderRenderer;

public class MiscUtils {
	private static Logger logger = LogManager.getLogger(MiscUtils.class);

	private final static String APP_NAME = "ShipTrack";
	public final static String SOURCE_FILENAME = "input.csv";
	private final static String CONFIG_FILE_NAME = "settings.properties";

	public final static Font FONT = new Font("Arial", Font.PLAIN, 16);
	public final static Font FONT_BOLD = new Font("Arial", Font.BOLD, 16);

	public final static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");

	public static String getWorkDir() {
		return System.getProperty("user.home") + File.separator + "." + APP_NAME + File.separator;
	}

	public static String getSettingsFilePath() {
		return getWorkDir() + CONFIG_FILE_NAME;
	}

	public static String getInputFilePath() {
		return getWorkDir() + SOURCE_FILENAME;
	}

	public static String getMainFolderName() {
		return File.separator + "." + APP_NAME + File.separator;
	}

	public static void resizeColumnWidth(JTable table, int minWidth) {
		final TableColumnModel columnModel = table.getColumnModel();
		Class<?> currentClass;
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = minWidth;
			currentClass = table.getModel().getColumnClass(column);
			// Se la colonna è di tipo boolean non deve essere ridimensionata
			if (currentClass.equals(Boolean.class)) {
				for (int row = 0; row < table.getRowCount(); row++) {
					TableCellRenderer renderer = table.getCellRenderer(row, column);
					Component comp = table.prepareRenderer(renderer, row, column);
					width = Math.max(comp.getPreferredSize().width + 1, 70);
				}
			} else {
				for (int row = 0; row < table.getRowCount(); row++) {
					TableCellRenderer renderer = table.getCellRenderer(row, column);
					Component comp = table.prepareRenderer(renderer, row, column);
					width = Math.max(comp.getPreferredSize().width + 1, width);
				}
			}
			if (width > 300) {
				width = 300;
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	public static int nextValidId(List<Integer> ids) {
		Collections.sort(ids);
		boolean nextIdUpdated = false;
		int nextId = 0;
		int prevId = 0;

		for (int id : ids) {
			if (id - prevId > 0) {
				nextId = prevId;
				nextIdUpdated = true;
			} else {
				prevId++;
			}
		}
		if (!nextIdUpdated) {
			nextId = ids.size();
		}
		logger.info("Next ID: " + nextId);

		return nextId;
	}

	public static Properties readProperties(String propFileName) {
		logger.info("Reading property from: " + propFileName);

		Properties props = new Properties();

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			InputStream resourceStream = loader.getResourceAsStream(propFileName);
			props.load(resourceStream);
		} catch (IOException e) {
			logger.error("Unable to find the file: " + propFileName);
		}
		return props;
	}

	public static JTable buildTable() {
		JTable table = new JTable();
		table.setFillsViewportHeight(true);
		table.setRowHeight(20);
		table.setFont(MiscUtils.FONT);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);

		// header
		JTableHeader header = table.getTableHeader();
		header.setDefaultRenderer(new TableHeaderRenderer(table));

		// cell selection hightlight renderer
		table.setDefaultRenderer(String.class, new CellRenderer());
		table.setDefaultRenderer(Float.class, new CellRenderer());
		table.setDefaultRenderer(Integer.class, new CellRenderer());
		table.setDefaultRenderer(Date.class, new CellRenderer());

		return table;
	}

	public static JButton buildButton(String label, ActionListener listener) {
		JButton button = new JButton(label);
		button.setFont(MiscUtils.FONT_BOLD);
		button.setSize(170, 40);
		button.setPreferredSize(new Dimension(170, 40));
		button.addActionListener(listener);

		return button;
	}

	public static Properties install(SettingsUtils settingsUtils) {
		logger.info("Checking installation...");

		Properties props;

		File dir = new File(getWorkDir());
		boolean check = fileExists(dir, CONFIG_FILE_NAME);

		if (check) {
			// software already installed
			logger.info("Software already installed");

			props = settingsUtils.getSettings();
		} else {
			props = settingsUtils.getSettings();
			logger.info("Perfoming installation...");

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.showSaveDialog(null);

			String selectedPath = "";
			try {
				selectedPath = fileChooser.getSelectedFile().getAbsolutePath() + getMainFolderName();
				logger.info("Selected path: " + selectedPath);

				File selectedDir = new File(selectedPath);
				if (!selectedDir.exists()) {
					selectedDir.mkdir();
				}

				logger.info("Saving default settings in installation directory: " + selectedPath);
				settingsUtils.addSetting(SettingsKeys.WORK_DIRECTORY, selectedPath);
				settingsUtils.saveSettings();

				logger.info("Creating empty source files...");

				File currentFile = new File(selectedPath + File.separator + SOURCE_FILENAME);
				if (currentFile.createNewFile()) {
					try {
						FileWriter fileWriter = new FileWriter(currentFile);
						fileWriter.write(InputDataCsv.getDefaultHeader());
						fileWriter.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				} else {
					logger.error("Error creating file: " + SOURCE_FILENAME + ". Check working folder!");
				}

				logger.info("Installation complete!");
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
		}

		logger.info("Installation complete!");

		return props;
	}

	public static boolean fileExists(File dir, String fileName) {
		return new File(dir, fileName).exists();
	}

	public static boolean dirExists(File dir) {
		return dir.exists();
	}

	public static String escapeCommas(String in) {
		return in.replaceAll(", ", " - ");
	}
}
