package it.silco.shiptrack.data.json.maersk;

import java.util.ArrayList;
import java.util.Date;

public class Container {
	public String container_num;
	public String container_size;
	public String container_type;
	public String iso_code;
	public String operator;
	public ArrayList<Location> locations;
	public Date eta_final_delivery;
	public Latest latest;
	public String status;

	public String getContainer_num() {
		return container_num;
	}

	public void setContainer_num(String container_num) {
		this.container_num = container_num;
	}

	public String getContainer_size() {
		return container_size;
	}

	public void setContainer_size(String container_size) {
		this.container_size = container_size;
	}

	public String getContainer_type() {
		return container_type;
	}

	public void setContainer_type(String container_type) {
		this.container_type = container_type;
	}

	public String getIso_code() {
		return iso_code;
	}

	public void setIso_code(String iso_code) {
		this.iso_code = iso_code;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public Date getEta_final_delivery() {
		return eta_final_delivery;
	}

	public void setEta_final_delivery(Date eta_final_delivery) {
		this.eta_final_delivery = eta_final_delivery;
	}

	public Latest getLatest() {
		return latest;
	}

	public void setLatest(Latest latest) {
		this.latest = latest;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
