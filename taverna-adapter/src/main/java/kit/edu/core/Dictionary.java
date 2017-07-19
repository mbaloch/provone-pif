package kit.edu.core;

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

	@XmlElement(name = "wasGeneratedBy", namespace = "http://www.w3.org/ns/prov#")
	private WasGeneratedBy wasGeneratedBy;

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

	public WasGeneratedBy getWasGeneratedBy() {
		return wasGeneratedBy;
	}

	public void setWasGeneratedBy(WasGeneratedBy wasGeneratedBy) {
		this.wasGeneratedBy = wasGeneratedBy;
	}

}
