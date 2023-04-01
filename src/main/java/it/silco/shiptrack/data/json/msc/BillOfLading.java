package it.silco.shiptrack.data.json.msc;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BillOfLading {
	@JsonProperty("BillOfLadingNumber")
	public String billOfLadingNumber;
	@JsonProperty("NumberOfContainers")
	public int numberOfContainers;
	@JsonProperty("GeneralTrackingInfo")
	public GeneralTrackingInfo generalTrackingInfo;
	@JsonProperty("ContainersInfo")
	public ArrayList<ContainersInfo> containersInfo;

	public String getBillOfLadingNumber() {
		return billOfLadingNumber;
	}

	public void setBillOfLadingNumber(String billOfLadingNumber) {
		this.billOfLadingNumber = billOfLadingNumber;
	}

	public int getNumberOfContainers() {
		return numberOfContainers;
	}

	public void setNumberOfContainers(int numberOfContainers) {
		this.numberOfContainers = numberOfContainers;
	}

	public GeneralTrackingInfo getGeneralTrackingInfo() {
		return generalTrackingInfo;
	}

	public void setGeneralTrackingInfo(GeneralTrackingInfo generalTrackingInfo) {
		this.generalTrackingInfo = generalTrackingInfo;
	}

	public ArrayList<ContainersInfo> getContainersInfo() {
		return containersInfo;
	}

	public void setContainersInfo(ArrayList<ContainersInfo> containersInfo) {
		this.containersInfo = containersInfo;
	}
}
