package it.silco.shiptrack.data;

public class InputData {
	private Boolean isSelected;
	private String id;
	private String trackId;
	private String company;
	private String client;
	private String progNum;

	public InputData() {
	}

	public InputData(int id) {
		this.id = String.format("%04d", id);
		this.trackId = "";
		this.company = "";
		this.isSelected = false;
	}

	public InputData(String id, String trackId, String company) {
		this.id = id;
		this.trackId = trackId;
		this.company = company;
		this.isSelected = false;
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

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProgNum() {
		return progNum;
	}

	public void setProgNum(String progNum) {
		this.progNum = progNum;
	}
}
