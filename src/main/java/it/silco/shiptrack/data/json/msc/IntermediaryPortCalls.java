package it.silco.shiptrack.data.json.msc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntermediaryPortCalls {
	@JsonProperty("FromPort")
	public String fromPort;
	@JsonProperty("ToPort")
	public String toPort;
	@JsonProperty("Vessel")
	public String vessel;
	@JsonProperty("FromDate")
	public String fromDate;
	@JsonProperty("ToDate")
	public String toDate;
	@JsonProperty("Voyage")
	public String voyage;

	public String getFromPort() {
		return fromPort;
	}

	public void setFromPort(String fromPort) {
		this.fromPort = fromPort;
	}

	public String getToPort() {
		return toPort;
	}

	public void setToPort(String toPort) {
		this.toPort = toPort;
	}

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}
}
