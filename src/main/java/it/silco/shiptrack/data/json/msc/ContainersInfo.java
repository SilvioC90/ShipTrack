package it.silco.shiptrack.data.json.msc;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContainersInfo {
	@JsonProperty("ContainerNumber")
	public String containerNumber;
	@JsonProperty("PodEtaDate")
	public String podEtaDate;
	@JsonProperty("ContainerType")
	public String containerType;
	@JsonProperty("LatestMove")
	public String latestMove;
	@JsonProperty("Events")
	public ArrayList<Event> events;

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getPodEtaDate() {
		return podEtaDate;
	}

	public void setPodEtaDate(String podEtaDate) {
		this.podEtaDate = podEtaDate;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getLatestMove() {
		return latestMove;
	}

	public void setLatestMove(String latestMove) {
		this.latestMove = latestMove;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}
}
