package it.silco.shiptrack.data.json.msc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MscJson {
	@JsonProperty("IsSuccess")
	public boolean isSuccess;
	@JsonProperty("Data")
	public Data data;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
