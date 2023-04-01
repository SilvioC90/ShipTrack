package it.silco.shiptrack.utils;

public enum SettingsKeys {
	WORK_DIRECTORY("workDirectory"), LANGUAGE("language");

	private String key;

	SettingsKeys(String envUrl) {
		this.key = envUrl;
	}

	public String getKey() {
		return key;
	}
}
