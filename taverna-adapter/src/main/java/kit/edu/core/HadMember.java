package kit.edu.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HadMember {

	@XmlAttribute(name = "resource", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String resource;

	@XmlElement(name = "Entity", namespace = "http://www.w3.org/ns/prov#")
	private Entity entity;

	@XmlAnyElement(lax = true)
	private Object any;

	public Object getAny() {
		return any;
	}

	public void setAny(Object any) {
		this.any = any;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return "HadMember [resource=" + resource + ", entity=" + entity + ", any=" + any + "]";
	}

}
