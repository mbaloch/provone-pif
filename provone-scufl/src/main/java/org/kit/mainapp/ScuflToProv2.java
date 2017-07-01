package org.kit.mainapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.xerces.dom.ElementNSImpl;
import org.kit.parse.ScuflMain;
import org.kit.pojo.ProcessorInOutMap;
import org.kit.rdf.RDFUtility;
import org.kit.scufl.api.Activity;
import org.kit.scufl.api.AnnotatedGranularDepthPort;
import org.kit.scufl.api.AnnotatedPort;
import org.kit.scufl.api.DataLink;
import org.kit.scufl.api.Dataflow;
import org.kit.scufl.api.DepthPort;
import org.kit.scufl.api.GranularDepthPort;
import org.kit.scufl.api.Link;
import org.kit.scufl.api.Processor;
import org.kit.scufl.api.Workflow;

public class ScuflToProv2 {
//
//	// private HashMap<String, Resource> inportMap = new HashMap<String,
//	// Resource>();
//	private HashMap<String, Dataflow> dataFlwRefMap = new HashMap<String, Dataflow>();
//	private HashMap<String, ProcessorInOutMap> processInOutMap = new HashMap<String, ProcessorInOutMap>();
//	private RDFUtility rdfUtility = new RDFUtility();
//	private String currentProceess = "";
//	private HashSet<String> seqLinkSet = new HashSet<String>();
//
//	public void parseCreateGraph(String filePath) {
//		ScuflMain scuflMain = new ScuflMain();
//		Workflow wrkFlw = scuflMain.parseScufl(filePath);
//
//		for (Dataflow datflw : wrkFlw.getDataflow()) {
//			dataFlwRefMap.put(datflw.getId(), datflw);
//		}
//		
//
//		for (Dataflow dtflw : wrkFlw.getDataflow()) {
//			if (dtflw.getRole().toString().equalsIgnoreCase("top")) {
//				boolean nestedFlag = false;
//				Prov2ONE_SCUFL(dtflw, nestedFlag);
//			}
//		}
//		File file = new File("C:/Users/VB/Desktop/sample.rdf");
//		try {
//			rdfUtility.getModel().write(new FileWriter(file));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void Prov2ONE_SCUFL(Dataflow dtflw, boolean nestedFlag) {
//
//		Resource processDoc = null;
//
//		if (nestedFlag) {
//
//			processDoc = processInOutMap.get(currentProceess).getProcessDoc();
//			Resource workflow = rdfUtility.createWorkflow("" + UUID.randomUUID(), "WorkFlow");
//			rdfUtility.wasDerivedFrom(workflow, processDoc);
//
//		} else {
//			Resource workflow = rdfUtility.createWorkflow("" + UUID.randomUUID(), "WorkFlow");
//			processDoc = rdfUtility.createProcess(dtflw.getId(), dtflw.getName());
//			rdfUtility.wasDerivedFrom(workflow, processDoc);
//			ATTACHPORTS(dtflw, processDoc, nestedFlag);
//		}
//
//		for (Processor processor : dtflw.getProcessors().getProcessor()) {
//			Resource processorDoc = rdfUtility.createProcess("" + UUID.randomUUID(), processor.getName());
//			ATTACHPORTS(processor, processorDoc);
//
//			for (Activity activity : processor.getActivities().getActivity()) {
//				if (null != activity.getConfigBean() && activity.getConfigBean().getEncoding().equals("dataflow")) {
//					ElementNSImpl config = (ElementNSImpl) activity.getConfigBean().getAny();
//
//					currentProceess = processor.getName();
//					Prov2ONE_SCUFL(dataFlwRefMap.get(config.getAttribute("ref")), true);
//					currentProceess = dtflw.getName();
//				}
//			}
//		}
//
//		for (DataLink dtlinks : dtflw.getDatalinks().getDatalink()) {
//
//			Resource dataLinkDoc = null;
//			Link sinkObj = dtlinks.getSink();
//			Link sourceObj = dtlinks.getSource();
//
//			ProcessorInOutMap processorInOutSourceObj = null;
//			ProcessorInOutMap processorInOutSinkObj = null;
//			String sourcePName = null;
//			String destinationPName = null;
//			boolean sourcePOP = false;
//			boolean destinationPOP = false;
//
//			if (sourceObj.getType().toString().equalsIgnoreCase("processor")) {
//				processorInOutSourceObj = processInOutMap.get(sourceObj.getProcessor());
//				dataLinkDoc = rdfUtility.createDataLink("" + UUID.randomUUID());
//				sourcePName = sourceObj.getProcessor();
//				if (processorInOutSourceObj.getInportMap().containsKey(sourceObj.getPort())) {
//					Resource inport = processorInOutSourceObj.getInportMap().get(sourceObj.getPort());
//					rdfUtility.inPortToDL(inport, dataLinkDoc);
//
//				} else if (processorInOutSourceObj.getOutportMap().containsKey(sourceObj.getPort())) {
//					Resource outport = processorInOutSourceObj.getOutportMap().get(sourceObj.getPort());
//					rdfUtility.outPortToDL(outport, dataLinkDoc);
//					sourcePOP = true;
//				}
//
//			} else if (sourceObj.getType().toString().equalsIgnoreCase("dataflow")) {
//				processorInOutSourceObj = processInOutMap.get(currentProceess);
//				dataLinkDoc = rdfUtility.createDataLink("" + UUID.randomUUID());
//				sourcePName = currentProceess;
//				if (nestedFlag) {
//					if (processorInOutSourceObj.getInportMap().containsKey(sourceObj.getPort())) {
//						Resource inport = processorInOutSourceObj.getInportMap().get(sourceObj.getPort());
//						rdfUtility.inPortToDL(inport, dataLinkDoc);
//					} else if (processorInOutSourceObj.getOutportMap().containsKey(sourceObj.getPort())) {
//						Resource outport = processorInOutSourceObj.getOutportMap().get(sourceObj.getPort());
//						rdfUtility.outPortToDL(outport, dataLinkDoc);
//						sourcePOP = true;
//					}
//				} else {
//					if (processorInOutSourceObj.getInportMap().containsKey(sourceObj.getPort())) {
//						Resource inport = processorInOutSourceObj.getInportMap().get(sourceObj.getPort());
//						rdfUtility.inPortToDL(inport, dataLinkDoc);
//					} else if (processorInOutSourceObj.getOutportMap().containsKey(sourceObj.getPort())) {
//						Resource outport = processorInOutSourceObj.getOutportMap().get(sourceObj.getPort());
//						rdfUtility.outPortToDL(outport, dataLinkDoc);
//						sourcePOP = true;
//					}
//				}
//			}
//
//			if (sinkObj.getType().toString().equalsIgnoreCase("processor")) {
//				processorInOutSinkObj = processInOutMap.get(sinkObj.getProcessor());
//				destinationPName = sinkObj.getProcessor();
//				if (processorInOutSinkObj.getInportMap().containsKey(sinkObj.getPort())) {
//					Resource inputPort = processorInOutSinkObj.getInportMap().get(sinkObj.getPort());
//					rdfUtility.DLToInPort(dataLinkDoc, inputPort);
//				} else if (processorInOutSinkObj.getOutportMap().containsKey(sinkObj.getPort())) {
//					Resource outputPort = processorInOutSinkObj.getOutportMap().get(sinkObj.getPort());
//					rdfUtility.DLToOutPort(dataLinkDoc, outputPort);
//					destinationPOP= true;
//				}
//
//			} else if (sinkObj.getType().toString().equalsIgnoreCase("dataflow")) {
//				processorInOutSinkObj = processInOutMap.get(currentProceess);
//				destinationPName = currentProceess;
//				if (nestedFlag) {
//					if (processorInOutSinkObj.getInportMap().containsKey(sinkObj.getPort())) {
//						Resource inport = processorInOutSinkObj.getInportMap().get(sinkObj.getPort());
//						rdfUtility.DLToInPort(dataLinkDoc, inport);
//
//					} else if (processorInOutSinkObj.getOutportMap().containsKey(sinkObj.getPort())) {
//						Resource outport = processorInOutSinkObj.getOutportMap().get(sinkObj.getPort());
//						rdfUtility.DLToOutPort(dataLinkDoc, outport);
//						destinationPOP= true;
//
//					}
//				} else {
//					if (processorInOutSinkObj.getInportMap().containsKey(sinkObj.getPort())) {
//						Resource inport = processorInOutSinkObj.getInportMap().get(sinkObj.getPort());
//						rdfUtility.DLToInPort(dataLinkDoc, inport);
//					} else if (processorInOutSinkObj.getOutportMap().containsKey(sinkObj.getPort())) {
//						Resource outport = processorInOutSinkObj.getOutportMap().get(sinkObj.getPort());
//						rdfUtility.DLToOutPort(dataLinkDoc, outport);
//						destinationPOP= true;
//					}
//				}
//
//			}
//			// insert map to avoid implementating multiple sequance ctrl link in
//			// graph eg Source.name-target.name as key to avoid the duplicates
//			if (!(sourcePOP && destinationPOP)) {
//				if (!seqLinkSet.contains(sourcePName + "" + destinationPName)) {
//					Resource seqCtrlLink = rdfUtility.createSeqCtrlLink("" + UUID.randomUUID());
//					rdfUtility.sourcePToCL(processorInOutSourceObj.getProcessDoc(), seqCtrlLink);
//					rdfUtility.CLtoDestP(processorInOutSinkObj.getProcessDoc(), seqCtrlLink);
//					System.out.println("++++++++++SEQctrl++++++");
//					System.out.println(processorInOutSourceObj.getProcessDoc().getProperty(DCTerms.title).getObject().toString() + ">>>>>"
//							+ processorInOutSinkObj.getProcessDoc().getProperty(DCTerms.title).getObject().toString() + "\n");
//				}
//			}
//		}
//
//	}
//
//	private void ATTACHPORTS(Processor processor, Resource processorDoc) {
//		System.out.println("+++++++++Processor :" + processor.getName());
//		System.out.println("---------Attaching Ports :NoNested");
//		HashMap<String, Resource> inportMap = new HashMap<String, Resource>();
//		HashMap<String, Resource> outportMap = new HashMap<String, Resource>();
//
//		for (DepthPort inport : processor.getInputPorts().getPort()) {
//			Resource inputPort = rdfUtility.createInputPort("" + UUID.randomUUID(), inport.getName());
//			inportMap.put(inport.getName(), inputPort);
//			System.out.println("****IN :" + inport.getName());
//			rdfUtility.hasInPort(processorDoc, inputPort);
//
//		}
//		for (GranularDepthPort outport : processor.getOutputPorts().getPort()) {
//			Resource outputPort = rdfUtility.createOutputPort("" + UUID.randomUUID(), outport.getName());
//			outportMap.put(outport.getName(), outputPort);
//			System.out.println("****Out:" + outport.getName());
//			rdfUtility.hasOutPort(processorDoc, outputPort);
//		}
//
//		ProcessorInOutMap inOutMap = new ProcessorInOutMap();
//		inOutMap.setProcessDoc(processorDoc);
//		inOutMap.setInportMap(inportMap);
//		inOutMap.setOutportMap(outportMap);
//		processInOutMap.put(processor.getName(), inOutMap);
//	}
//
//	private void ATTACHPORTS(Dataflow dtflw, Resource processDoc, boolean nestedFlag) {
//		System.out.println("+++++++++Processor :" + dtflw.getName());
//		System.out.println("---------Attaching Ports :" + nestedFlag);
//		HashMap<String, Resource> inportMap = new HashMap<String, Resource>();
//		HashMap<String, Resource> outportMap = new HashMap<String, Resource>();
//
//		for (AnnotatedGranularDepthPort inPort : dtflw.getInputPorts().getPort()) {
//			Resource inportDoc = rdfUtility.createInputPort("" + UUID.randomUUID(), inPort.getName());
//			inportMap.put(inPort.getName(), inportDoc);
//			System.out.println("****IN :" + inPort.getName());
//			rdfUtility.hasInPort(processDoc, inportDoc);
//		}
//		for (AnnotatedPort outPort : dtflw.getOutputPorts().getPort()) {
//			Resource outportDoc = rdfUtility.createOutputPort("" + UUID.randomUUID(), outPort.getName());
//			outportMap.put(outPort.getName(), outportDoc);
//			System.out.println("****out:" + outPort.getName());
//			rdfUtility.hasOutPort(processDoc, outportDoc);
//		}
//
//		ProcessorInOutMap inOutMap = new ProcessorInOutMap();
//		inOutMap.setProcessDoc(processDoc);
//		inOutMap.setInportMap(inportMap);
//		inOutMap.setOutportMap(outportMap);
//
//		if (nestedFlag) {
//			processInOutMap.put(currentProceess, inOutMap);
//		} else {
//			processInOutMap.put(dtflw.getName(), inOutMap);
//		}
//	}
//
}
