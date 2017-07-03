package edu.kit.scufl.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
@XmlAccessorType(XmlAccessType.FIELD)
public class RDF {

	@XmlElement(namespace = "http://www.w3.org/ns/prov#", name = "Dictionary")
	public Dictionary dictionary;

	@XmlElement(name = "Generation", namespace = "http://www.w3.org/ns/prov#")
	private List<Generation> generation;

	@XmlElement(name = "Entity", namespace = "http://www.w3.org/ns/prov#")
	private List<Entity> entity;

	@XmlElement(name = "Bundle", namespace = "http://www.w3.org/ns/prov#")
	private Bundle bundle;

	@XmlElement(name = "Artifact", namespace = "http://purl.org/wf4ever/wfprov#")
	private List<Artifact> artifact;

	@XmlAnyElement(lax = true)
	public List<Object> any;

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

	public List<Generation> getGeneration() {
		return generation;
	}

	public void setGeneration(List<Generation> generation) {
		this.generation = generation;
	}

	public List<Entity> getEntity() {
		return entity;
	}

	public void setEntity(List<Entity> entity) {
		this.entity = entity;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public List<Artifact> getArtifact() {
		return artifact;
	}

	public void setArtifact(List<Artifact> artifact) {
		this.artifact = artifact;
	}

}
