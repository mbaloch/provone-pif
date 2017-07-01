package edu.kit.provone.bpel;

public class WorkflowRootNode {

	private String workflowName;
	public WorkflowRootNode(String workflowName, String workflowID,
			 String workflowAuthor) {
		super();
		this.workflowName = workflowName;
		this.workflowID = workflowID;
		//this.executionDate = executionDate;
		this.workflowAuthor = workflowAuthor;
	}
	private String workflowID;
	private String executionDate;
	private String workflowAuthor;
	public String getWorkflowName() {
		return workflowName;
	}
	public String getWorkflowID() {
		return workflowID;
	}
	public String getExecutionDate() {
		return executionDate;
	}
	public String getWorkflowAuthor() {
		return workflowAuthor;
	}
	
}
