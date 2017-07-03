package edu.kit.scufl.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Dictionary {

	@XmlAttribute(name = "about", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String about;

	@XmlElement(name = "wasOutputFrom", namespace = "http://purl.org/wf4ever/wfprov#")
	private WasOutputFrom wasOutputFrom;

	@XmlElement(name = "hadMember", namespace = "http://www.w3.org/ns/prov#")
	private List<HadMember> hadMember;

	@XmlElement(name = "hadDictionaryMember", namespace = "http://www.w3.org/ns/prov#")
	private List<HadDictionaryMember> hadDictionaryMember;

	@XmlAnyElement(lax = true)
	public List<Object> anyDict;

	public List<Object> getAnyDict() {
		return anyDict;
	}

	public void setAnyDict(List<Object> anyDict) {
		this.anyDict = anyDict;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public WasOutputFrom getWasOutputFrom() {
		return wasOutputFrom;
	}

	public void setWasOutputFrom(WasOutputFrom wasOutputFrom) {
		this.wasOutputFrom = wasOutputFrom;
	}

	public List<HadMember> getHadMember() {
		return hadMember;
	}

	public void setHadMember(List<HadMember> hadMember) {
		this.hadMember = hadMember;
	}

	public List<HadDictionaryMember> getHadDictionaryMember() {
		return hadDictionaryMember;
	}

	public void setHadDictionaryMember(List<HadDictionaryMember> hadDictionaryMember) {
		this.hadDictionaryMember = hadDictionaryMember;
	}

	@Override
	public String toString() {
		return "Dictionary [about=" + about + ", wasOutputFrom=" + wasOutputFrom + ", hadMember=" + hadMember
				+ ", hadDictionaryMember=" + hadDictionaryMember + ", anyDict=" + anyDict + "]";
	}

}
