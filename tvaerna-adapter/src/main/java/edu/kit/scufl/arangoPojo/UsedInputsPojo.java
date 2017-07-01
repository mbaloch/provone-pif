package edu.kit.scufl.arangoPojo;

public class UsedInputsPojo {

	private String dataEntity;
	private String processExeNodeId;

	public String getDataEntity() {
		return dataEntity;
	}

	public void setDataEntity(String dataEntity) {
		this.dataEntity = dataEntity;
	}

	public String getProcessExeNodeId() {
		return processExeNodeId;
	}

	public void setProcessExeNodeId(String processExeNodeId) {
		this.processExeNodeId = processExeNodeId;
	}

	public UsedInputsPojo(String dataEntity, String processExeNodeId) {
		super();
		this.dataEntity = dataEntity;
		this.processExeNodeId = processExeNodeId;
	}

	@Override
	public String toString() {
		return "UsedInputsPojo [dataEntity=" + dataEntity + ", processExeNodeId=" + processExeNodeId + "]";
	}
	
}
