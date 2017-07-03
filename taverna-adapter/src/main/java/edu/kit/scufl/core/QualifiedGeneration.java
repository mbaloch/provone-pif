package edu.kit.scufl.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class QualifiedGeneration {

	@XmlElement(name = "Generation", namespace = "http://www.w3.org/ns/prov#")
	private Generation Generation;

	@XmlAnyElement(lax = true)
	public List<Object> any;

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

	public Generation getGeneration() {
		return Generation;
	}

	public void setGeneration(Generation generation) {
		Generation = generation;
	}

}
