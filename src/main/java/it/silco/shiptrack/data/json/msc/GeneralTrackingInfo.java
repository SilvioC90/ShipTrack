package it.silco.shiptrack.data.json.msc;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralTrackingInfo {
	@JsonProperty("ShippedFrom")
	public String shippedFrom;
	@JsonProperty("ShippedTo")
	public String shippedTo;
	@JsonProperty("PortOfLoad")
	public String portOfLoad;
	@JsonProperty("PortOfDischarge")
	public String portOfDischarge;
	@JsonProperty("Transshipments")
	public ArrayList<String> transshipments;
	@JsonProperty("PriceCalculationDate")
	public String priceCalculationDate;
	@JsonProperty("FinalPodEtaDate")
	public String finalPodEtaDate;

	public String getShippedFrom() {
		return shippedFrom;
	}

	public void setShippedFrom(String shippedFrom) {
		this.shippedFrom = shippedFrom;
	}

	public String getShippedTo() {
		return shippedTo;
	}

	public void setShippedTo(String shippedTo) {
		this.shippedTo = shippedTo;
	}

	public String getPortOfLoad() {
		return portOfLoad;
	}

	public void setPortOfLoad(String portOfLoad) {
		this.portOfLoad = portOfLoad;
	}

	public String getPortOfDischarge() {
		return portOfDischarge;
	}

	public void setPortOfDischarge(String portOfDischarge) {
		this.portOfDischarge = portOfDischarge;
	}

	public ArrayList<String> getTransshipments() {
		return transshipments;
	}

	public void setTransshipments(ArrayList<String> transshipments) {
		this.transshipments = transshipments;
	}

	public String getPriceCalculationDate() {
		return priceCalculationDate;
	}

	public void setPriceCalculationDate(String priceCalculationDate) {
		this.priceCalculationDate = priceCalculationDate;
	}

	public String getFinalPodEtaDate() {
		return finalPodEtaDate;
	}

	public void setFinalPodEtaDate(String finalPodEtaDate) {
		this.finalPodEtaDate = finalPodEtaDate;
	}
}
