package org.kit.pojo;

import java.util.HashMap;

import org.apache.jena.rdf.model.Resource;

public class ProcessorInOutMap {

	private HashMap<String, Resource> inportMap = new HashMap<String, Resource> ();
	private HashMap<String, Resource> outportMap = new HashMap<String, Resource> ();
	private Resource processDoc;

	public HashMap<String, Resource> getInportMap() {
		return inportMap;
	}

	public void setInportMap(HashMap<String, Resource> inportMap) {
		this.inportMap = inportMap;
	}
	

	public HashMap<String, Resource> getOutportMap() {
		return outportMap;
	}

	public void setOutportMap(HashMap<String, Resource> outportMap) {
		this.outportMap = outportMap;
	}

	public Resource getProcessDoc() {
		return processDoc;
	}

	public void setProcessDoc(Resource processDoc) {
		this.processDoc = processDoc;
	}

	public void addInportMap(String name, Resource inport) {
		inportMap.put(name, inport);
	}

	public void addOutportMap(String name, Resource outport) {
		outportMap.put(name, outport);
	}

}
