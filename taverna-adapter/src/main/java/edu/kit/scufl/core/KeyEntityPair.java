package edu.kit.scufl.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class KeyEntityPair {

	@XmlElement(name = "pairKey", namespace = "http://www.w3.org/ns/prov#")
	private String pairKey;

	@XmlElement(name = "pairEntity", namespace = "http://www.w3.org/ns/prov#")
	private PairEntity pairEntity;

	public String getPairKey() {
		return pairKey;
	}

	public void setPairKey(String pairKey) {
		this.pairKey = pairKey;
	}

	public PairEntity getPairEntity() {
		return pairEntity;
	}

	public void setPairEntity(PairEntity pairEntity) {
		this.pairEntity = pairEntity;
	}

}
