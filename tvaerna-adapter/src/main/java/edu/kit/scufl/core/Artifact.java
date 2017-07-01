package edu.kit.scufl.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Artifact {

	@XmlAttribute(name = "about", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String about;

	@XmlElement(name = "hadMember", namespace = "http://www.w3.org/ns/prov#")
	private List<HadMember> hadMembers;

	@XmlElement(name = "wasGeneratedBy", namespace = "http://www.w3.org/ns/prov#")
	private List<WasGeneratedBy> wasGeneratedBy;

	@XmlAnyElement(lax = true)
	private Object any;

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<HadMember> getHadMembers() {
		return hadMembers;
	}

	public void setHadMembers(List<HadMember> hadMembers) {
		this.hadMembers = hadMembers;
	}

	public List<WasGeneratedBy> getWasGeneratedBy() {
		return wasGeneratedBy;
	}

	public void setWasGeneratedBy(List<WasGeneratedBy> wasGeneratedBy) {
		this.wasGeneratedBy = wasGeneratedBy;
	}

	public Object getAny() {
		return any;
	}

	public void setAny(Object any) {
		this.any = any;
	}

}
