package edu.kit.scufl.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HasPart {

	@XmlElement(name = "ProcessRun", namespace = "http://purl.org/wf4ever/wfprov#")
	private ProcessRun processRun;

	@XmlElement(name = "WorkflowRun", namespace = "http://purl.org/wf4ever/wfprov#")
	private WorkflowRun workflowRun;

	@XmlAnyElement(lax = true)
	private List<Object> any;

	public ProcessRun getProcessRun() {
		return processRun;
	}

	public void setProcessRun(ProcessRun processRun) {
		this.processRun = processRun;
	}

	public WorkflowRun getWorkflowRun() {
		return workflowRun;
	}

	public void setWorkflowRun(WorkflowRun workflowRun) {
		this.workflowRun = workflowRun;
	}

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

}
