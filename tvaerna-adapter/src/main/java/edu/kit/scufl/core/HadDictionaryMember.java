package edu.kit.scufl.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HadDictionaryMember {

	@XmlElement(name = "KeyEntityPair", namespace = "http://www.w3.org/ns/prov#")
	private KeyEntityPair keyEntityPair;

	@XmlAnyElement(lax = true)
	public List<Object> any;

	public KeyEntityPair getKeyEntityPair() {
		return keyEntityPair;
	}

	public void setKeyEntityPair(KeyEntityPair keyEntityPair) {
		this.keyEntityPair = keyEntityPair;
	}

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

}
