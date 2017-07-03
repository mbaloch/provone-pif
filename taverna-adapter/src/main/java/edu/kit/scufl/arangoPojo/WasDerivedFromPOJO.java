package edu.kit.scufl.arangoPojo;

import java.util.ArrayList;
import java.util.List;

public class WasDerivedFromPOJO {

	private List<String> wasGeneratedBy;
	private List<String> usedBy;

	public WasDerivedFromPOJO() {
		wasGeneratedBy = new ArrayList<String>();
		usedBy = new ArrayList<String>();
	}

	public List<String> getWasGeneratedBy() {
		return wasGeneratedBy;
	}

	public void setWasGeneratedBy(String wasGeneratedBy) {
		this.wasGeneratedBy.add(wasGeneratedBy);
	}

	public List<String> getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(String usedBy) {
		this.usedBy.add(usedBy);
	}

	@Override
	public String toString() {
		return "WasDerivedFromPOJO [wasGeneratedBy=" + wasGeneratedBy + ", usedBy=" + usedBy + "]";
	}

	public WasDerivedFromPOJO(List<String> wasGeneratedBy, List<String> usedBy) {
		super();
		this.wasGeneratedBy = wasGeneratedBy;
		this.usedBy = usedBy;
	}

}
