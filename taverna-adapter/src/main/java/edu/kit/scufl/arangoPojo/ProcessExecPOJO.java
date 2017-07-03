package edu.kit.scufl.arangoPojo;

public class ProcessExecPOJO {

	private String processID;
	private String name;
	private String graphName;
	private String startTime;
	private String endTime;

	public ProcessExecPOJO(String processId, String processName, String startedAtTime, String endedAtTime) {
		this.processID = processId;
		this.name = processName;
		this.graphName = processName;
		this.startTime = startedAtTime;
		this.endTime = endedAtTime;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "ProcessExecPOJO [processID=" + processID + ", name=" + name + ", graphName=" + graphName
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
