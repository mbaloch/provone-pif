package org.kit.mainapp;

import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.xerces.dom.ElementNSImpl;
import org.kit.parse.ScuflMain;
import org.kit.pojo.ProcessorInOutMap;
import org.kit.pojo.arangodb.ProcessorDoc;
import org.kit.rdf.RDFUtility;
import org.kit.scufl.api.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.UUID;

public class ScuflToProv {

	private Stack<String> currentProceess = new Stack<String>();
	String scuflWorkflowID;
	private HashMap<String, Dataflow> dataFlwRefMap = new HashMap<String, Dataflow>();
	private HashMap<String, ProcessorInOutMap> processInOutMap = new HashMap<String, ProcessorInOutMap>();
	private HashSet<String> seqLinkSet = new HashSet<String>();
	private Stack<String> processNamePathStack = new Stack<String>();

	private RDFUtility rdfUtility = new RDFUtility();

	/**
	 * 
	 * @param filePath
	 *            T@FLOW file path
	 */
	public void parseCreateGraph(String filePath) {

		ScuflMain scuflMain = new ScuflMain();
		Workflow wrkFlw = scuflMain.parseScufl(filePath);

		for (Dataflow datflw : wrkFlw.getDataflow()) {
			dataFlwRefMap.put(datflw.getId(), datflw);
		}

		for (Dataflow dtflw : wrkFlw.getDataflow()) {
			if (dtflw.getRole().toString().equalsIgnoreCase("top")) {
				boolean nestedFlag = false;
				scuflWorkflowID = dtflw.getId();
				currentProceess.push(dtflw.getName());
				Prov2ONE_SCUFL(dtflw, nestedFlag);
			}
		}

//create graph in jena
		try {
			saveRDFtoJenaStore(rdfUtility);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	final String ServiceURI = "http://localhost:3030/kit/";
	//final String workflowContainerID="http://bpelAdd.kit.edu/bpelAdd"; //change workflowContainerID with this.
	final static String provone = "http://purl.org/provone#";
	final static String prov = "http://www.w3.org/ns/prov#";
	final static String ns = "http://kit.edu/pp/";

	private void saveRDFtoJenaStore(RDFUtility rdfUtility) throws Exception {
		String workflowID="http://kit.edu/scufl/"+scuflWorkflowID;
		Model model = rdfUtility.getModel();
		DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(ServiceURI);
		if(!accessor.containsModel(workflowID)){
			Model workflowContainerModel = ModelFactory.createDefaultModel();
			accessor.putModel(workflowID,model);
		}else
		{
			throw new Exception("Prospective graph for the givern workflow id already exists in store");
		}
//		String workflowContainerIDScufl="http://kit.edu/scufl/"+scuflWorkflowID;
//		Model model = rdfUtility.getModel();
//		DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(ServiceURI);
//		if(!accessor.containsModel(workflowContainerIDScufl)){
//
//			Model workflowContainerModel = ModelFactory.createDefaultModel();
//			//create wf container and add new record for the new wf
//			String workflowVersion="1.0";
//			String workflowID=workflowContainerIDScufl +"/"+workflowVersion;
//			Resource workflow = workflowContainerModel.createResource(ns + "wf_" + scuflWorkflowID);
//			workflow.addProperty(RDF.type, workflowContainerModel.createResource(provone + "Workflow"));
//			workflow.addProperty(DCTerms.identifier, scuflWorkflowID);
//			workflow.addProperty(DCTerms.title, workflowID);
//			accessor.add(workflowContainerIDScufl,workflowContainerModel);
//			//create a named graph for wf and add data to it
//			accessor.putModel(workflowID,model);
//			//Property wasAssociatedWithProperty = model.createProperty(prov + "wasAssociatedWith");
//			//workflow.addProperty(wasAssociatedWithProperty, process);
//
//
//		}
//		else{
//			Model workflowContainerModel=accessor.getModel(workflowContainerIDScufl);
//
//			String workflowVersion="3.0";
//			String workflowID=workflowContainerIDScufl +"/"+workflowVersion;
//			Resource workflow = workflowContainerModel.createResource(ns + "wf_" + scuflWorkflowID);
//			workflow.addProperty(RDF.type, workflowContainerModel.createResource(provone + "Workflow"));
//			workflow.addProperty(DCTerms.identifier, scuflWorkflowID);
//			workflow.addProperty(DCTerms.title, workflowID);
//			accessor.add(workflowContainerIDScufl,workflowContainerModel);
//			//create a named graph for wf and add data to it
//			accessor.putModel(workflowID,model);
//
//		}
	}

	/**
	 * 
	 * @param dtflw
	 *            main or Nested workflow called recursively
	 * @param nestedFlag
	 *            to check weather the dataflow is nested or main i.e. Role =
	 *            TOP / Nested
	 */
	private void Prov2ONE_SCUFL(Dataflow dtflw, boolean nestedFlag) {

		Resource processDoc = null;

		if (nestedFlag) {
			processDoc = processInOutMap.get(currentProceess.peek()).getProcessDoc();

		} else {
			processNamePathStack.push(dtflw.getName());
			// RDF code
			processDoc = rdfUtility.createWorkflow(dtflw.getId(), getPathNaMe(), dtflw.getRole().toString());

			ATTACHPORTS(dtflw, processDoc, nestedFlag);
		}

		// below code is for parsing each process of the workflow

		for (Processor processor : dtflw.getProcessors().getProcessor()) {
			boolean normalFlag = true;

			// special condition for checking if it is a nested workflow and if
			// yes then special operations has to be done for the same.
			for (Activity activity : processor.getActivities().getActivity()) {
				if (null != activity.getConfigBean() && activity.getConfigBean().getEncoding().equals("dataflow")) {

					String nestedWrkFlw = UUID.randomUUID().toString();
					ElementNSImpl config = (ElementNSImpl) activity.getConfigBean().getAny();

					// RDF code
					Resource processorDoc = rdfUtility.createWorkflow(nestedWrkFlw, getPathNaMe(processor.getName()), dataFlwRefMap.get(config.getAttribute("ref")).getRole().toString());
					rdfUtility.hasSubProcess(processDoc, processorDoc);
					// ArangoDB code
					// creating workflow node
					// creating processor node
					ProcessorDoc processorPojo = new ProcessorDoc();
					processorPojo.setGraphName("P:" + processor.getName());
					processorPojo.setName(processor.getName());

					ATTACHPORTS(processor, processorDoc, nestedWrkFlw);
					Stack<String> tempStack = new Stack<String>();
					for (String processNames : processNamePathStack) {
						tempStack.push(processNames);
					}
					processNamePathStack.removeAllElements();

					processNamePathStack.push(processor.getName());

					currentProceess.push(processor.getName());
					Prov2ONE_SCUFL(dataFlwRefMap.get(config.getAttribute("ref")), true);
					currentProceess.pop();
					processNamePathStack.pop();
					for (String processName : tempStack) {
						processNamePathStack.push(processName);
					}
					// currentProceess.push(dtflw.getName());

					normalFlag = false;

				}
			}
			if (normalFlag) {

				// common Method for creating all the listed process and
				// relations is of type hasSubProcess
				String processUUID = UUID.randomUUID().toString();
				// code for rdf
				ElementNSImpl config = (ElementNSImpl) processor.getActivities().getActivity().get(0).getConfigBean().getAny();
				String scriptText = null;
				for (int i = 0; i < config.getChildNodes().getLength(); i++) {
					if (config.getChildNodes().item(i).getNodeName().toString().equalsIgnoreCase("script")) {
						scriptText = config.getChildNodes().item(i).getTextContent();
						break;
					}
				}
				Resource processorDoc = rdfUtility.createProcess(processUUID, getPathNaMe(processor.getName()), processor.getActivities().getActivity().get(0).getClazz(), scriptText);
				rdfUtility.hasSubProcess(processDoc, processorDoc);

				// common for RDF and ArangoDb
				ATTACHPORTS(processor, processorDoc, processUUID);
			}

		}
		// datalink logic for each workflow
		for (DataLink dtlinks : dtflw.getDatalinks().getDatalink()) {

			Resource dataLinkDoc = null;
			Link sinkObj = dtlinks.getSink();
			Link sourceObj = dtlinks.getSource();

			ProcessorInOutMap processorInOutSourceObj = null;
			ProcessorInOutMap processorInOutSinkObj = null;


			String sourcePName = null;
			String destinationPName = null;
			boolean sourcePOP = false;
			boolean destinationPOP = false;
			boolean sourceIsProcessor = true;
			boolean sinkIsProcessor = true;

			String datalinkUUID = UUID.randomUUID().toString();
			dataLinkDoc = rdfUtility.createDataLink(datalinkUUID);

			if (sourceObj.getType().toString().equalsIgnoreCase("processor")) {

				// code for RDF
				processorInOutSourceObj = processInOutMap.get(sourceObj.getProcessor());

				// code for ArangoDB
				sourcePName = sourceObj.getProcessor();
				ProcessorDoc wrkPojo = new ProcessorDoc();
				wrkPojo.setGraphName("DL:" + sourcePName);
				wrkPojo.setName("DL:" + sourcePName);

				if (processorInOutSourceObj.getInportMap().containsKey(sourceObj.getPort())) {
					// code for RDF
					Resource inport = processorInOutSourceObj.getInportMap().get(sourceObj.getPort());
					rdfUtility.inPortToDL(inport, dataLinkDoc);

				} else if (processorInOutSourceObj.getOutportMap().containsKey(sourceObj.getPort())) {
					// code for RDF
					Resource outport = processorInOutSourceObj.getOutportMap().get(sourceObj.getPort());
					rdfUtility.addProperty(dataLinkDoc,processNamePathStack.peek()+"/processor/"+sourceObj.getProcessor()+"/out/"+sourceObj.getPort());
					rdfUtility.outPortToDL(outport, dataLinkDoc);

					sourcePOP = true;
				}

			} else if (sourceObj.getType().toString().equalsIgnoreCase("dataflow")) {
				sourceIsProcessor = false;
				// code for RDF
				processorInOutSourceObj = processInOutMap.get(currentProceess.peek());

				if (nestedFlag) {
					if (processorInOutSourceObj.getInportMap().containsKey(sourceObj.getPort())) {
						// code for RDF
						Resource inport = processorInOutSourceObj.getInportMap().get(sourceObj.getPort());
						rdfUtility.inPortToDL(inport, dataLinkDoc);

					} else if (processorInOutSourceObj.getOutportMap().containsKey(sourceObj.getPort())) {
						// code for RDF
						Resource outport = processorInOutSourceObj.getOutportMap().get(sourceObj.getPort());
						rdfUtility.addProperty(dataLinkDoc,processNamePathStack.peek()+"/processor/"+sourceObj.getProcessor()+"/out/"+sourceObj.getPort());
						rdfUtility.outPortToDL(outport, dataLinkDoc);

						sourcePOP = true;
					}
				} else {
					if (processorInOutSourceObj.getInportMap().containsKey(sourceObj.getPort())) {
						// code for RDF
						Resource inport = processorInOutSourceObj.getInportMap().get(sourceObj.getPort());
						rdfUtility.inPortToDL(inport, dataLinkDoc);

					} else if (processorInOutSourceObj.getOutportMap().containsKey(sourceObj.getPort())) {
						// code for RDF
						Resource outport = processorInOutSourceObj.getOutportMap().get(sourceObj.getPort());
						rdfUtility.outPortToDL(outport, dataLinkDoc);

						sourcePOP = true;
					}
				}
			}
			if (sinkObj.getType().toString().equalsIgnoreCase("processor")) {
				destinationPName = sinkObj.getProcessor();
				// code for RDF
				processorInOutSinkObj = processInOutMap.get(sinkObj.getProcessor());

				if (processorInOutSinkObj.getInportMap().containsKey(sinkObj.getPort())) {
					// code for RDF
					Resource inputPort = processorInOutSinkObj.getInportMap().get(sinkObj.getPort());
					rdfUtility.DLToInPort(dataLinkDoc, inputPort);

				} else if (processorInOutSinkObj.getOutportMap().containsKey(sinkObj.getPort())) {
					// code for RDF
					Resource outputPort = processorInOutSinkObj.getOutportMap().get(sinkObj.getPort());
					rdfUtility.DLToOutPort(dataLinkDoc, outputPort);

					destinationPOP = true;
				}
			} else if (sinkObj.getType().toString().equalsIgnoreCase("dataflow")) {
				sinkIsProcessor = false;
				destinationPName = currentProceess.peek();
				processorInOutSinkObj = processInOutMap.get(currentProceess.peek());

				if (nestedFlag) {
					if (processorInOutSinkObj.getInportMap().containsKey(sinkObj.getPort())) {
						// code for RDF
						Resource inport = processorInOutSinkObj.getInportMap().get(sinkObj.getPort());
						rdfUtility.DLToInPort(dataLinkDoc, inport);

					} else if (processorInOutSinkObj.getOutportMap().containsKey(sinkObj.getPort())) {
						Resource outport = processorInOutSinkObj.getOutportMap().get(sinkObj.getPort());
						rdfUtility.DLToOutPort(dataLinkDoc, outport);

						destinationPOP = true;

					}
				} else {
					if (processorInOutSinkObj.getInportMap().containsKey(sinkObj.getPort())) {
						Resource inport = processorInOutSinkObj.getInportMap().get(sinkObj.getPort());
						rdfUtility.DLToInPort(dataLinkDoc, inport);

					} else if (processorInOutSinkObj.getOutportMap().containsKey(sinkObj.getPort())) {
						Resource outport = processorInOutSinkObj.getOutportMap().get(sinkObj.getPort());
						rdfUtility.DLToOutPort(dataLinkDoc, outport);

						destinationPOP = true;
					}
				}
			}

			// insert map to avoid implementating multiple sequance ctrl link in
			// graph eg Source.name-target.name as key to avoid the duplicates
			if (!(sourcePOP && destinationPOP) && sourceIsProcessor && sinkIsProcessor) {
				if (!seqLinkSet.contains(sourcePName + "" + destinationPName)) {
					String seqLinkUUID = UUID.randomUUID().toString();
					// code for RDF
					Resource seqCtrlLink = rdfUtility.createSeqCtrlLink(seqLinkUUID);
					rdfUtility.sourcePToCL(processorInOutSourceObj.getProcessDoc(), seqCtrlLink);
					rdfUtility.CLtoDestP(processorInOutSinkObj.getProcessDoc(), seqCtrlLink);

				}
			}
		}

	}
	private String getPathNaMe(String processorName) {
		StringWriter pathName = new StringWriter();
		for (String processorNames : processNamePathStack) {
			pathName.append(processorNames+"/processor/");
		}

		pathName.append(processorName+"/");
		return pathName.toString();
	}

	private String getPathNaMe() {
		StringWriter pathName = new StringWriter();
		for (String processorNames : processNamePathStack) {
			pathName.append(processorNames+"/");
		}
		return pathName.toString();
	}
	private void ATTACHPORTS(Dataflow dtflw, Resource processDoc, boolean nestedFlag, String processUUID) {

		HashMap<String, Resource> inportMap = new HashMap<String, Resource>();
		HashMap<String, Resource> outportMap = new HashMap<String, Resource>();

		for (AnnotatedGranularDepthPort inPort : dtflw.getInputPorts().getPort()) {
			String uuid = UUID.randomUUID().toString();
			// code for RDF
			Resource inportDoc = rdfUtility.createInputPort(uuid, inPort.getName());
			inportMap.put(inPort.getName(), inportDoc);
			rdfUtility.hasInPort(processDoc, inportDoc);

		}
		for (AnnotatedPort outPort : dtflw.getOutputPorts().getPort()) {
			String uuid = UUID.randomUUID().toString();
			// code for RDF
			Resource outportDoc = rdfUtility.createOutputPort(uuid, outPort.getName());
			outportMap.put(outPort.getName(), outportDoc);
			rdfUtility.hasOutPort(processDoc, outportDoc);

		}

		ProcessorInOutMap inOutMap = new ProcessorInOutMap();
		inOutMap.setProcessDoc(processDoc);
		inOutMap.setInportMap(inportMap);
		inOutMap.setOutportMap(outportMap);

		// if (nestedFlag) {
		// processInOutMap.put(currentProceess.peek(), inOutMap);
		// processInOutMap2.put(currentProceess.peek(), inOutMapA);
		// } else {
		processInOutMap.put(dtflw.getName(), inOutMap);
		// }
	}

	private void ATTACHPORTS(Processor processor, Resource processorDoc, String processUUID) {

		HashMap<String, Resource> inportMap = new HashMap<String, Resource>();
		HashMap<String, Resource> outportMap = new HashMap<String, Resource>();

		for (DepthPort inPort : processor.getInputPorts().getPort()) {
			String uuid = UUID.randomUUID().toString();
			// code for RDF
			Resource inportDoc = rdfUtility.createInputPort(uuid, inPort.getName());
			inportMap.put(inPort.getName(), inportDoc);
			rdfUtility.hasInPort(processorDoc, inportDoc);

		}
		for (GranularDepthPort outPort : processor.getOutputPorts().getPort()) {
			String uuid = UUID.randomUUID().toString();
			// code for RDF
			Resource outportDoc = rdfUtility.createOutputPort(uuid, outPort.getName());
			outportMap.put(outPort.getName(), outportDoc);
			rdfUtility.hasOutPort(processorDoc, outportDoc);

		}

		ProcessorInOutMap inOutMap = new ProcessorInOutMap();
		inOutMap.setProcessDoc(processorDoc);
		inOutMap.setInportMap(inportMap);
		inOutMap.setOutportMap(outportMap);

		processInOutMap.put(processor.getName(), inOutMap);

	}

	private void ATTACHPORTS(Dataflow dtflw, Resource processDoc, boolean nestedFlag) {

		HashMap<String, Resource> inportMap = new HashMap<String, Resource>();
		HashMap<String, Resource> outportMap = new HashMap<String, Resource>();

		for (AnnotatedGranularDepthPort inPort : dtflw.getInputPorts().getPort()) {
			String uuid = UUID.randomUUID().toString();
			// code for RDF
			Resource inportDoc = rdfUtility.createInputPort(uuid, inPort.getName());
			inportMap.put(inPort.getName(), inportDoc);
			rdfUtility.hasInPort(processDoc, inportDoc);

		}
		for (AnnotatedPort outPort : dtflw.getOutputPorts().getPort()) {
			String uuid = UUID.randomUUID().toString();
			// code for RDF
			Resource outportDoc = rdfUtility.createOutputPort(uuid, outPort.getName());
			outportMap.put(outPort.getName(), outportDoc);
			rdfUtility.hasOutPort(processDoc, outportDoc);

		}

		ProcessorInOutMap inOutMap = new ProcessorInOutMap();
		inOutMap.setProcessDoc(processDoc);
		inOutMap.setInportMap(inportMap);
		inOutMap.setOutportMap(outportMap);

		if (nestedFlag) {
			processInOutMap.put(currentProceess.peek(), inOutMap);
		} else {
			processInOutMap.put(dtflw.getName(), inOutMap);
		}
	}

}
