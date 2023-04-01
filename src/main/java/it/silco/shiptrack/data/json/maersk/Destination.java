package it.silco.shiptrack.data.json.maersk;

public class Destination {
	public String terminal;
	public String geo_site;
	public String city;
	public String state;
	public String country;
	public String country_code;
	public String geoid_city;
	public String site_type;

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
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

	public String getGeoid_city() {
		return geoid_city;
	}

	public void setGeoid_city(String geoid_city) {
		this.geoid_city = geoid_city;
	}

	public String getSite_type() {
		return site_type;
	}

	public void setSite_type(String site_type) {
		this.site_type = site_type;
	}
}
