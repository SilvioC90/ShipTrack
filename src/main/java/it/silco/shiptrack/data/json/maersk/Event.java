package it.silco.shiptrack.data.json.maersk;

import java.util.Date;

public class Event {
	public String activity;
	public boolean stempty;
	public String actfor;
	public String vessel_name;
	public String voyage_num;
	public String vessel_num;
	public Date expected_time;
	public Date actual_time;
	public String rkem_move;
	public boolean is_cancelled;
	public boolean is_current;

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

	public String getVessel_name() {
		return vessel_name;
	}

	public void setVessel_name(String vessel_name) {
		this.vessel_name = vessel_name;
	}

	public String getVoyage_num() {
		return voyage_num;
	}

	public void setVoyage_num(String voyage_num) {
		this.voyage_num = voyage_num;
	}

	public String getVessel_num() {
		return vessel_num;
	}

	public void setVessel_num(String vessel_num) {
		this.vessel_num = vessel_num;
	}

	public Date getExpected_time() {
		return expected_time;
	}

	public void setExpected_time(Date expected_time) {
		this.expected_time = expected_time;
	}

	public Date getActual_time() {
		return actual_time;
	}

	public void setActual_time(Date actual_time) {
		this.actual_time = actual_time;
	}

	public String getRkem_move() {
		return rkem_move;
	}

	public void setRkem_move(String rkem_move) {
		this.rkem_move = rkem_move;
	}

	public boolean isIs_cancelled() {
		return is_cancelled;
	}

	public void setIs_cancelled(boolean is_cancelled) {
		this.is_cancelled = is_cancelled;
	}

	public boolean isIs_current() {
		return is_current;
	}

	public void setIs_current(boolean is_current) {
		this.is_current = is_current;
	}
}
