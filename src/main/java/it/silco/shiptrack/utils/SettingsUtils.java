package it.silco.shiptrack.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JFileChooser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SettingsUtils {
	private static Logger logger = LogManager.getLogger(SettingsUtils.class);
	private static Properties settings;
	private static SettingsUtils instance;

	private SettingsUtils() {
		try {
			SettingsUtils.settings = readSettings();
		} catch (IOException e) {
			SettingsUtils.settings = MiscUtils.readProperties("settings.properties");
		}
	}

	public static SettingsUtils getInstance() {
		if (instance == null) {
			instance = new SettingsUtils();
		}
		return instance;
	}

	private Properties readSettings() throws IOException {
		logger.info("Reading saved settings...");

		Properties props = new Properties();
		InputStream input = new FileInputStream(MiscUtils.getSettingsFilePath());
		props.load(input);

		return props;
	}

	public void saveSettings() {
		logger.info("Saving settings...");
		try {
			String settingsPath = MiscUtils.getSettingsFilePath();
			OutputStream fos = new FileOutputStream(settingsPath);
			settings.store(fos, null);

			fos.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public String getSetting(SettingsKeys key) {
		return settings.getProperty(key.getKey());
	}

	public void addSetting(SettingsKeys key, String value) {
		logger.info("Adding settings...");
		settings.setProperty(key.getKey(), value);
	}

	public String workFolderSelection() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showSaveDialog(null);

		String path = "";

		try {
			path = fileChooser.getSelectedFile().getAbsolutePath();
			this.addSetting(SettingsKeys.WORK_DIRECTORY, path);
			this.saveSettings();

			logger.info("Selected path: " + path);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return path;
	}

	public Properties getSettings() {
		return settings;
	}
}
