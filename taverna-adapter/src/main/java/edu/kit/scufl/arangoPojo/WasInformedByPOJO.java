package edu.kit.scufl.arangoPojo;

import java.util.ArrayList;
import java.util.List;

public class WasInformedByPOJO {

	private List<String> usedBy;
	private List<String> wasGeneratedBy;

	public WasInformedByPOJO(List<String> usedBy, List<String> wasGeneratedBy) {
		super();
		this.usedBy = usedBy;
		this.wasGeneratedBy = wasGeneratedBy;
	}

	public WasInformedByPOJO() {
		usedBy = new ArrayList<String>();
		wasGeneratedBy = new ArrayList<String>();
	}

	@Override
	public String toString() {
		return "WasInformedByPOJO [usedBy=" + usedBy + ", wasGeneratedBy=" + wasGeneratedBy + "]";
	}

	public List<String> getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(List<String> usedBy) {
		this.usedBy = usedBy;
	}

	public List<String> getWasGeneratedBy() {
		return wasGeneratedBy;
	}

	public void setWasGeneratedBy(List<String> wasGeneratedBy) {
		this.wasGeneratedBy = wasGeneratedBy;
	}

	public void setUsedBy(String usedBy) {

		this.usedBy.add(usedBy);
	}

	public void setWasGeneratedBy(String wasGeneratedBy) {
		this.wasGeneratedBy.add(wasGeneratedBy);
	}

}
