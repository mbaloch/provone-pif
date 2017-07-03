package edu.kit.scufl.arangoPojo;

import java.util.List;

import edu.kit.scufl.core.WasGeneratedBy;
import edu.kit.scufl.core.WasOutputFrom;

public class DataPOJO {

	private String taverna_Content;
	private String graphName;
	private List<String> wasDerivedFrom;
	private List<WasGeneratedBy> wasGeneratedBy;
	private List<WasOutputFrom> wasOutPutFrom;
	private String dataNodeID;

	public DataPOJO(String taverna_Content, List<String> wasDerivedFrom, List<WasGeneratedBy> wasGeneratedBy,
			List<WasOutputFrom> wasOutPutFrom, String dataNodeID) {
		super();
		this.taverna_Content = taverna_Content;
		this.wasDerivedFrom = wasDerivedFrom;
		this.wasGeneratedBy = wasGeneratedBy;
		this.wasOutPutFrom = wasOutPutFrom;
		this.dataNodeID = dataNodeID;
		graphName = taverna_Content;
	}

	public DataPOJO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DataPOJO [taverna_Content=" + taverna_Content + ", graphName=" + graphName + ", wasDerivedFrom="
				+ wasDerivedFrom + ", wasGeneratedBy=" + wasGeneratedBy + ", wasOutPutFrom=" + wasOutPutFrom
				+ ", dataNodeID=" + dataNodeID + "]";
	}

	public String getTaverna_Content() {
		return taverna_Content;
	}

	public void setTaverna_Content(String taverna_Content) {
		this.taverna_Content = taverna_Content;
		graphName = taverna_Content;
	}

	public List<String> getWasDerivedFrom() {
		return wasDerivedFrom;
	}

	public void setWasDerivedFrom(List<String> wasDerivedFrom) {
		this.wasDerivedFrom = wasDerivedFrom;
	}

	public List<WasGeneratedBy> getWasGeneratedBy() {
		return wasGeneratedBy;
	}

	public void setWasGeneratedBy(List<WasGeneratedBy> wasGeneratedBy) {
		this.wasGeneratedBy = wasGeneratedBy;
	}

	public List<WasOutputFrom> getWasOutPutFrom() {
		return wasOutPutFrom;
	}

	public void setWasOutPutFrom(List<WasOutputFrom> list) {
		this.wasOutPutFrom = list;
	}

	public String getDataNodeID() {
		return dataNodeID;
	}

	public void setDataNodeID(String dataNodeID) {
		this.dataNodeID = dataNodeID;
	}

}
