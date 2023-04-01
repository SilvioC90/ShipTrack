package it.silco.shiptrack.data;

public class InputData {
	private String id;
	private String trackId;
	private String company;

	public InputData() {
	}

	public InputData(int id) {
		this.id = String.format("%04d", id);
		this.trackId = "";
		this.company = "";
	}

	public InputData(String id, String trackId, String company) {
		this.id = id;
		this.trackId = trackId;
		this.company = company;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
