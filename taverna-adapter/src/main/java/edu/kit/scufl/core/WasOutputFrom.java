package edu.kit.scufl.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class WasOutputFrom {

	@XmlAttribute(name = "resource", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String resource;

	@XmlElement(namespace = "http://purl.org/wf4ever/wfprov#", name = "WorkflowRun")
	public WorkflowRun workflowRun;

	public WorkflowRun getWorkflowRun() {
		return workflowRun;
	}

	public void setWorkflowRun(WorkflowRun workflowRun) {
		this.workflowRun = workflowRun;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
