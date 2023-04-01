package it.silco.shiptrack.data.json.msc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EquipmentHandling {
	@JsonProperty("Bic")
	public String bic;
	@JsonProperty("Firm")
	public String firm;
	@JsonProperty("Name")
	public String name;
	@JsonProperty("Smdg")
	public String smdg;

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSmdg() {
		return smdg;
	}

	public void setSmdg(String smdg) {
		this.smdg = smdg;
	}
}
