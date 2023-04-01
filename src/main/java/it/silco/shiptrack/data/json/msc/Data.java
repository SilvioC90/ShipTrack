package it.silco.shiptrack.data.json.msc;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
	@JsonProperty("TrackingType")
	public String trackingType;
	@JsonProperty("TrackingTitle")
	public String trackingTitle;
	@JsonProperty("TrackingNumber")
	public String trackingNumber;
	@JsonProperty("CurrentDate")
	public String currentDate;
	@JsonProperty("PriceCalculationLabel")
	public String priceCalculationLabel;
	@JsonProperty("TrackingResultsLabel")
	public String trackingResultsLabel;
	@JsonProperty("BillOfLadings")
	public ArrayList<BillOfLading> billOfLadings;

	public String getTrackingType() {
		return trackingType;
	}

	public void setTrackingType(String trackingType) {
		this.trackingType = trackingType;
	}

	public String getTrackingTitle() {
		return trackingTitle;
	}

	public void setTrackingTitle(String trackingTitle) {
		this.trackingTitle = trackingTitle;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getPriceCalculationLabel() {
		return priceCalculationLabel;
	}

	public void setPriceCalculationLabel(String priceCalculationLabel) {
		this.priceCalculationLabel = priceCalculationLabel;
	}

	public String getTrackingResultsLabel() {
		return trackingResultsLabel;
	}

	public void setTrackingResultsLabel(String trackingResultsLabel) {
		this.trackingResultsLabel = trackingResultsLabel;
	}

	public ArrayList<BillOfLading> getBillOfLadings() {
		return billOfLadings;
	}

	public void setBillOfLadings(ArrayList<BillOfLading> billOfLadings) {
		this.billOfLadings = billOfLadings;
	}
}
