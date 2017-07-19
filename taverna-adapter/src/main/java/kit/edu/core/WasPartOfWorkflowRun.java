package kit.edu.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class WasPartOfWorkflowRun {

	@XmlAttribute(name = "resource", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", required = false)
	private String resource;

	@XmlElement(name = "WorkflowRun", namespace = "http://purl.org/wf4ever/wfprov#", required = false)
	private WorkflowRun workFlowRun;

	@XmlAnyElement(lax = true)
	private List<Object> any;

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public WorkflowRun getWorkFlowRun() {
		return workFlowRun;
	}

	public void setWorkFlowRun(WorkflowRun workFlowRun) {
		this.workFlowRun = workFlowRun;
	}

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

}
