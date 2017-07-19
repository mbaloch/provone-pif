package kit.edu.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DescribedByWorkflow {
	
	@XmlAttribute(name="resource",namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String resource;

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	@XmlElement(name = "Plan", namespace = "http://www.w3.org/ns/prov#", required = false)
	private Plan plan;

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
