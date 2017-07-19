package kit.edu.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Activity {

	@XmlElement(name = "WorkflowRun", namespace = "http://purl.org/wf4ever/wfprov#", required = false)
	private WorkflowRun workflowRun;

	@XmlAnyElement(lax = true)
	public List<Object> any;

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
