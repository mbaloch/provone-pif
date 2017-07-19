package kit.edu.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
@XmlAccessorType(XmlAccessType.FIELD)
public class RDF {

	@XmlElement( name = "Dictionary",namespace = "http://www.w3.org/ns/prov#")
	public List<Dictionary> dictionary;

	@XmlElement(name = "Generation", namespace = "http://www.w3.org/ns/prov#")
	private List<Generation> generation;
//
//	@XmlElement(name = "Entity", namespace = "http://www.w3.org/ns/prov#")
//	private List<Entity> entity;
//
//	@XmlElement(name = "Bundle", namespace = "http://www.w3.org/ns/prov#")
//	private Bundle bundle;
//
//	@XmlElement(name = "Artifact", namespace = "http://purl.org/wf4ever/wfprov#")
//	private List<Artifact> artifact;
//
	@XmlAnyElement(lax = true)
	public List<Object> any;

	public List<Dictionary> getDictionary() {
		return dictionary;
	}

	public void setDictionary(List<Dictionary> dictionary) {
		this.dictionary = dictionary;
	}

	public List<Generation> getGeneration() {
		return generation;
	}

	public void setGeneration(List<Generation> generation) {
		this.generation = generation;
	}
	
	

}
