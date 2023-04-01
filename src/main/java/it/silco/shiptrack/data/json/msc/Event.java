package it.silco.shiptrack.data.json.msc;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
	@JsonProperty("Order")
	public int order;
	@JsonProperty("Date")
	public String date;
	@JsonProperty("Location")
	public String location;
	@JsonProperty("Description")
	public String description;
	@JsonProperty("Detail")
	public ArrayList<String> detail;
	@JsonProperty("EquipmentHandling")
	public EquipmentHandling equipmentHandling;
	@JsonProperty("IntermediaryPortCalls")
	public IntermediaryPortCalls intermediaryPortCalls;

	public IntermediaryPortCalls getIntermediaryPortCalls() {
		return intermediaryPortCalls;
	}

	public void setIntermediaryPortCalls(IntermediaryPortCalls intermediaryPortCalls) {
		this.intermediaryPortCalls = intermediaryPortCalls;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getDetail() {
		return detail;
	}

	public void setDetail(ArrayList<String> detail) {
		this.detail = detail;
	}

	public EquipmentHandling getEquipmentHandling() {
		return equipmentHandling;
	}

	public void setEquipmentHandling(EquipmentHandling equipmentHandling) {
		this.equipmentHandling = equipmentHandling;
	}
}
