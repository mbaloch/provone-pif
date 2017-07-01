package org.kit.pojo.arangodb;

import java.util.HashMap;

public class ProcessorInOutMapArango {

	private String processorID;
	private HashMap<String, String> inputPortMap = new HashMap<String, String>();
	private HashMap<String, String> outputPortMap = new HashMap<String, String>();

	public String getProcessorID() {
		return processorID;
	}

	public void setProcessorID(String processorID) {
		this.processorID = processorID;
	}

	public void addInputPort(String name, String id) {
		inputPortMap.put(name, id);
	}

	public HashMap<String, String> getInputPortMap() {
		return inputPortMap;
	}

	public void setInputPortMap(HashMap<String, String> inputPortMap) {
		this.inputPortMap = inputPortMap;
	}

	public void addOutputPort(String name, String id) {
		outputPortMap.put(name, id);
	}

	public HashMap<String, String> getOutputPortMap() {
		return outputPortMap;
	}

	public void setOutputPortMap(HashMap<String, String> outputPortMap) {
		this.outputPortMap = outputPortMap;
	}

}
