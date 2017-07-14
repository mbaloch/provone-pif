package edu.kit.scufl.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class WorkflowRun {

	@XmlElement(name = "hasPart", namespace = "http://purl.org/dc/terms/")
	public List<HasPart> hasPart;

	@XmlElement(name = "startedAtTime", namespace = "http://www.w3.org/ns/prov#")
	public String startedAtTime;

	@XmlElement(name = "endedAtTime", namespace = "http://www.w3.org/ns/prov#")
	public String endedAtTime;

	@XmlAttribute(name = "about", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	public String about;

	@XmlElement(name = "label", namespace = "http://www.w3.org/2000/01/rdf-schema#")
	private String label;

	@XmlElement(name = "usedInput", namespace = "http://purl.org/wf4ever/wfprov#", required = false)
	private List<UsedInput> usedInput;

	@XmlElement(name = "qualifiedUsage", namespace = "http://www.w3.org/ns/prov#", required = false)
	private List<QualifiedUsage> qualifiedUsage;

	@XmlElement(name = "used", namespace = "http://www.w3.org/ns/prov#", required = false)
	private List<Used> used;

	@XmlElement(name="describedByWorkflow",namespace="http://purl.org/wf4ever/wfprov#",required=false)
	private DescribedByWorkflow describedByWorkflow;

	@XmlElement(name = "describedByProcess", namespace = "http://purl.org/wf4ever/wfprov#", required = false)
	private DescribedByProcess describedByProcess;

	@XmlAnyElement(lax = true)
	public List<Object> anyDict;

	public List<Object> getAnyDict() {
		return anyDict;
	}

	public void setAnyDict(List<Object> anyDict) {
		this.anyDict = anyDict;
	}

	public List<HasPart> getHasPart() {
		return hasPart;
	}

	public void setHasPart(List<HasPart> hasPart) {
		this.hasPart = hasPart;
	}

	public String getStartedAtTime() {
		return startedAtTime;
	}

	public void setStartedAtTime(String startedAtTime) {
		this.startedAtTime = startedAtTime;
	}

	public String getEndedAtTime() {
		return endedAtTime;
	}

	public void setEndedAtTime(String endedAtTime) {
		this.endedAtTime = endedAtTime;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<UsedInput> getUsedInput() {
		return usedInput;
	}

	public void setUsedInput(List<UsedInput> usedInput) {
		this.usedInput = usedInput;
	}

	public List<QualifiedUsage> getQualifiedUsage() {
		return qualifiedUsage;
	}

	public void setQualifiedUsage(List<QualifiedUsage> qualifiedUsage) {
		this.qualifiedUsage = qualifiedUsage;
	}

	public List<Used> getUsed() {
		return used;
	}

	public void setUsed(List<Used> used) {
		this.used = used;
	}
	public DescribedByWorkflow getDescribedByWorkflow() {
		return describedByWorkflow;
	}

	public void setDescribedByWorkflow(DescribedByWorkflow describedByWorkflow) {
		this.describedByWorkflow = describedByWorkflow;
	}
	public DescribedByProcess getDescribedByProcess() {
		return describedByProcess;
	}

	public void setDescribedByProcess(DescribedByProcess describedByProcess) {
		this.describedByProcess = describedByProcess;
	}
}
