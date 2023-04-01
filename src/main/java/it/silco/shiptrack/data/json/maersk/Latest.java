package it.silco.shiptrack.data.json.maersk;

import java.util.Date;

public class Latest {
	public Date actual_time;
	public String activity;
	public boolean stempty;
	public String actfor;
	public String geo_site;
	public String city;
	public String state;
	public String country;
	public String country_code;

	public Date getActual_time() {
		return actual_time;
	}

	public void setActual_time(Date actual_time) {
		this.actual_time = actual_time;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public boolean isStempty() {
		return stempty;
	}

	public void setStempty(boolean stempty) {
		this.stempty = stempty;
	}

	public String getActfor() {
		return actfor;
	}

	public void setActfor(String actfor) {
		this.actfor = actfor;
	}

	public String getGeo_site() {
		return geo_site;
	}

	public void setGeo_site(String geo_site) {
		this.geo_site = geo_site;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
}
