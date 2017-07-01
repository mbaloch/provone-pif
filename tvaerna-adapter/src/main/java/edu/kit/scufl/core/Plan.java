package edu.kit.scufl.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Plan {

	@XmlAttribute(name = "about", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String about;

	@XmlElement(name = "type", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String type;

	@XmlElement(name = "label", namespace = "http://www.w3.org/2000/01/rdf-schema#")
	private String label;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

}
