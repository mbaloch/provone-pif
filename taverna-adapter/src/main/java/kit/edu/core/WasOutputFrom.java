package kit.edu.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class WasOutputFrom {

	@XmlAttribute(name = "resource", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", required= false)
	private String resource;

	@XmlElement( name = "WorkflowRun", namespace = "http://purl.org/wf4ever/wfprov#")
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
