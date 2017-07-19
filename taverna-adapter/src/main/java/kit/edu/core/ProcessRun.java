package kit.edu.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessRun {

	@XmlAttribute(name = "about", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String about;

	@XmlElement(name = "endedAtTime", namespace = "http://www.w3.org/ns/prov#")
	private String endedAtTime;

	@XmlElement(name = "startedAtTime", namespace = "http://www.w3.org/ns/prov#")
	private String startedAtTime;

	@XmlElement(name = "describedByProcess", namespace = "http://purl.org/wf4ever/wfprov#")
	private DescribedByProcess describedByProcess;

	@XmlElement(name = "qualifiedAssociation", namespace = "http://www.w3.org/ns/prov#")
	private QualifiedAssociation qualifiedAssociation;

	@XmlElement(name = "label", namespace = "http://www.w3.org/2000/01/rdf-schema#")
	private String label;

	@XmlElement(name = "wasPartOfWorkflowRun", namespace = "http://purl.org/wf4ever/wfprov#")
	private WasPartOfWorkflowRun wasPartOfWorkflowRun;
	
	@XmlElement(name = "used", namespace = "http://www.w3.org/ns/prov#", required = false)
	private List<Used> used;
	
	@XmlElement(name = "usedInput", namespace = "http://purl.org/wf4ever/wfprov#", required = false)
	private List<UsedInput> usedInput;

	@XmlAnyElement(lax = true)
	private List<Object> any;

	public String getEndedAtTime() {
		return endedAtTime;
	}

	public void setEndedAtTime(String endedAtTime) {
		this.endedAtTime = endedAtTime;
	}

	public String getStartedAtTime() {
		return startedAtTime;
	}

	public void setStartedAtTime(String startedAtTime) {
		this.startedAtTime = startedAtTime;
	}

	public DescribedByProcess getDescribedByProcess() {
		return describedByProcess;
	}

	public void setDescribedByProcess(DescribedByProcess describedByProcess) {
		this.describedByProcess = describedByProcess;
	}

	public QualifiedAssociation getQualifiedAssociation() {
		return qualifiedAssociation;
	}

	public void setQualifiedAssociation(QualifiedAssociation qualifiedAssociation) {
		this.qualifiedAssociation = qualifiedAssociation;
	}

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public WasPartOfWorkflowRun getWasPartOfWorkflowRun() {
		return wasPartOfWorkflowRun;
	}

	public void setWasPartOfWorkflowRun(WasPartOfWorkflowRun wasPartOfWorkflowRun) {
		this.wasPartOfWorkflowRun = wasPartOfWorkflowRun;
	}

	public List<Used> getUsed() {
		return used;
	}

	public void setUsed(List<Used> used) {
		this.used = used;
	}

	public List<UsedInput> getUsedInput() {
		return usedInput;
	}

	public void setUsedInput(List<UsedInput> usedInput) {
		this.usedInput = usedInput;
	}

}
