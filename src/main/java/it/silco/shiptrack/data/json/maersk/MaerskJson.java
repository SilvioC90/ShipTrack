package it.silco.shiptrack.data.json.maersk;

import java.util.ArrayList;

public class MaerskJson {
	public String tpdoc_num;
	public boolean isContainerSearch;
	public Origin origin;
	public Destination destination;
	public ArrayList<Container> containers;

	public String getTpdoc_num() {
		return tpdoc_num;
	}

	public void setTpdoc_num(String tpdoc_num) {
		this.tpdoc_num = tpdoc_num;
	}

	public boolean isContainerSearch() {
		return isContainerSearch;
	}

	public void setContainerSearch(boolean isContainerSearch) {
		this.isContainerSearch = isContainerSearch;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public ArrayList<Container> getContainers() {
		return containers;
	}

	public void setContainers(ArrayList<Container> containers) {
		this.containers = containers;
	}
}
