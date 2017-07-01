package edu.kit.scufl.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PairEntity {

	@XmlElement(name = "Entity", namespace = "http://www.w3.org/ns/prov#")
	private Entity entity;

	@XmlAnyElement(lax = true)
	public List<Object> any;

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
