package org.kit.mainapp;

public class MomlToProv2 {
//
//	private ArangoDBUtil arangoDB;
//	private RDFUtility rdfUtil;
//
//	private HashMap<String, ProcessorInOutMap> processInOutMap = new HashMap<String, ProcessorInOutMap>();
//	private HashMap<String, ProcessorInOutMapArango> processInOutMap2 = new HashMap<String, ProcessorInOutMapArango>();
//	// private Set<String> noInputPorts = new HashSet<String>();
//
//	private Stack<String> processorStack = new Stack<String>();
//
//	public void parseCreateGraph(String filePath) {
//
//		MoMLParser parser = new MoMLParser();
//		try {
//			Entity momlEntity = parser.parseMoML(filePath);
//
//			arangoDB = new ArangoDBUtil("MomlProv", "TrillsDeocder");
//			arangoDB.createCollections();
//			rdfUtil = new RDFUtility();
//
//			Prov2ONEMoML(momlEntity);
//
//			/*
//			 * File file = new File("C:/Users/VB/Desktop/sample2.rdf");
//			 * rdfUtil.getModel().write(new FileWriter(file));
//			 */
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void Prov2ONEMoML(Entity momlEntity) {
//
//		processorStack.push(momlEntity.getName());
//
//		SegrigaterUtility utility = new SegrigaterUtility();
//		utility.segrigate(momlEntity);
//
//		ATTACHPORTS(momlEntity, utility, processInOutMap, processInOutMap2);
//		CONNECT(momlEntity, processInOutMap, processInOutMap2);
//
//		for (Entity entity : utility.getW()) {
//			if (entity.getClazz().equals("ptolemy.actor.TypedCompositeActor")) {
//				Prov2ONEMoML(entity);
//			} else {
//				ATTACHPORTS(entity, utility, processInOutMap, processInOutMap2);
//			}
//		}
//
//		for (Relation relation : utility.getR()) {
//			ArrayList<String> allRelations = new ArrayList<String>();
//			for (Link link : utility.getL()) {
//				if (relation.getName().equals(link.getRelation())) {
//					allRelations.add(link.getPort());
//				}
//			}
//			GenerateProvONE(allRelations);
//		}
//		processorStack.pop();
//	}
//
//	// This Method is used to create all the data links and Sequance control
//	// link
//	private void GenerateProvONE(ArrayList<String> allRelations) {
//		ArrayList<String> nestedP = new ArrayList<String>();
//		ArrayList<String> inputP = new ArrayList<String>();
//		ArrayList<String> outputP = new ArrayList<String>();
//		ArrayList<String> noP = new ArrayList<String>();
//
//		// categoriesing all the ports with respective to their entities or
//		// process
//		// Step 1 all the relations as to identify all the relations between
//		// several ports
//		for (String relationStr : allRelations) {
//			if (!relationStr.contains(".")) {
//				nestedP.add(relationStr);
//			} else {
//				String[] reltArry = relationStr.split("\\.");
//				switch (reltArry[1].trim().toLowerCase()) {
//				case "input":
//					inputP.add(relationStr);
//					break;
//				case "output":
//					outputP.add(relationStr);
//					break;
//				default:
//					String[] temp = relationStr.split("\\.");
//					if (processInOutMap.get(temp[0]).getInportMap().containsKey(temp[1])) {
//						inputP.add(relationStr);
//					} else if (processInOutMap.get(temp[0]).getOutportMap().containsKey(temp[1])) {
//						outputP.add(relationStr);
//					} else {
//						noP.add(relationStr);
//					}
//				}
//			}
//		}
//
//		// logic for connecting all ports to respective entities
//		if (nestedP.size() > 0) {
//			for (String nested : nestedP) {
//				if (processInOutMap.get(processorStack.peek()).getInportMap().containsKey(nested)) {
//					for (String ipP : inputP) {
//						connectInToIn(ipP, nested);
//					}
//					for (String noneP : noP) {
//						connectInToNone(noneP, nested);
//					}
//				} else if (processInOutMap.get(processorStack.peek()).getOutportMap().containsKey(nested)) {
//
//				}
//			}
//		} else {
//
//		}
//
//	}
//
//	private void connectInToNone(String nonePort, String nested) {
//
//	}
//
//	private void connectInToIn(String inputpPort, String nested) {
//
//		String[] nameSplit = inputpPort.split("\\.");
//
//		String inputID;
//		Resource localInPort;
//		Resource wrkFlwInPort = processInOutMap.get(processorStack.peek()).getInportMap().get(nested);
//		String wrkFlwInPortA = processInOutMap2.get(processorStack.peek()).getInputPortMap().get(nested);
//
//		if (processInOutMap.get(nameSplit[0]).getInportMap().containsKey(nameSplit[1])) {
//			localInPort = processInOutMap.get(nameSplit[0]).getInportMap().get(nameSplit[1]);
//			inputID = processInOutMap2.get(nameSplit[0]).getInputPortMap().get(nameSplit[1]);
//		} else {
//			inputID = UUID.randomUUID().toString();
//			localInPort = rdfUtil.createInputPort(inputID, nameSplit[1]);
//			rdfUtil.hasInPort(processInOutMap.get(nameSplit[0]).getProcessDoc(), localInPort);
//
//			ProcessorDoc wrkPojo = new ProcessorDoc();
//			wrkPojo.setGraphName("IP:" + nameSplit[1]);
//			wrkPojo.setName(nameSplit[1]);
//			arangoDB.createNode(wrkPojo, inputID, "MomlCollections");
//			arangoDB.createEdge("hasInPort", processInOutMap2.get(nameSplit[0]).getProcessorID(), inputID, "MomlCollections", "MomlCollections");
//
//			processInOutMap.get(nameSplit[0]).addInportMap(nameSplit[1], localInPort);
//			processInOutMap2.get(nameSplit[0]).addInputPort(nameSplit[1], inputID);
//		}
//
//		// code to conect to datalink with ports for RDF
//		Resource dataLinkResource = rdfUtil.createDataLink("DL:" + processorStack.peek());
//		rdfUtil.inPortToDL(wrkFlwInPort, dataLinkResource);
//		rdfUtil.DLToInPort(dataLinkResource, localInPort);
//
//		// Code to connect to datalink with ports for aranogdb
//		String datalinkID = UUID.randomUUID().toString();
//		ProcessorDoc wrkPojo = new ProcessorDoc();
//		wrkPojo.setGraphName("DL:" + processorStack.peek());
//		wrkPojo.setName(processorStack.peek());
//		arangoDB.createNode(wrkPojo, datalinkID, "MomlCollections");
//
//		arangoDB.createEdge("inPortToDl", wrkFlwInPortA, datalinkID, "MomlCollections", "MomlCollections");
//		arangoDB.createEdge("DLToInPort", datalinkID, inputID, "MomlCollections", "MomlCollections");
//
//	}
//
//	private void CONNECT(Entity momlEntity, HashMap<String, ProcessorInOutMap> processInOutMap, HashMap<String, ProcessorInOutMapArango> processInOutMap2) {
//
//		UUID uid = UUID.randomUUID();
//
//		// code for RDF Graphs
//		Resource workFlow = rdfUtil.createProcess("" + uid, "WorkFlow");
//		Resource processor = processInOutMap.get(momlEntity.getName()).getProcessDoc();
//		rdfUtil.wasDerivedFrom(workFlow, processor);
//
//		// Code for ArangoDB
//		String processorID = processInOutMap2.get(momlEntity.getName()).getProcessorID();
//		WorkFlowDoc wrkPojo = new WorkFlowDoc();
//		wrkPojo.setGraphName("WF:" + momlEntity.getName());
//		wrkPojo.setName(momlEntity.getName());
//		arangoDB.createNode(wrkPojo, "" + uid, "MomlCollections");
//		arangoDB.createEdge("wasDerivedFrom", "" + uid, processorID, "MomlCollections", "MomlCollections");
//	}
//
//	private void ATTACHPORTS(Entity momlEntity, SegrigaterUtility utility, HashMap<String, ProcessorInOutMap> processInOutMap, HashMap<String, ProcessorInOutMapArango> processInOutMap2) {
//
//		UUID uid = UUID.randomUUID();
//		// Code For Creating Process Node in RDF Graph
//		ProcessorInOutMap inOutMap = new ProcessorInOutMap();
//		Resource process = rdfUtil.createProcess("" + uid, momlEntity.getName());
//		inOutMap.setProcessDoc(process);
//
//		// Code For Creating Process Node in ArangoDB
//		ProcessorDoc processorPojo = new ProcessorDoc();
//		processorPojo.setGraphName("P:" + momlEntity.getName());
//		processorPojo.setName(momlEntity.getName());
//		arangoDB.createNode(processorPojo, "" + uid, "MomlCollections");
//
//		ProcessorInOutMapArango inOutMap2 = new ProcessorInOutMapArango();
//		inOutMap2.setProcessorID("" + uid);
//
//		for (Port port : utility.segrigatePorts(momlEntity)) {
//			Property property = (Property) port.getConfigureOrDocOrProperty().get(0);
//
//			UUID portId = UUID.randomUUID();
//
//			if (property.getName().equalsIgnoreCase("input")) {
//				// Code for ports in RDF
//				Resource inport = rdfUtil.createInputPort("" + portId, port.getName());
//				rdfUtil.hasInPort(process, inport);
//				inOutMap.addInportMap(port.getName(), inport);
//
//				// code for ports in ArangoDB
//				ProcessorDoc inportDoc = new ProcessorDoc();
//				inportDoc.setGraphName("IP:" + port.getName());
//				inportDoc.setName(port.getName());
//				arangoDB.createNode(inportDoc, "" + portId, "MomlCollections");
//				arangoDB.createEdge("hasInPort", "" + uid, "" + portId, "MomlCollections", "MomlCollections");
//				inOutMap2.addInputPort(port.getName(), "" + portId);
//
//			} else if (property.getName().equalsIgnoreCase("output")) {
//				// Code for ports in RDF
//				Resource outport = rdfUtil.createOutputPort("" + portId, "" + port.getName());
//				rdfUtil.hasOutPort(process, outport);
//				inOutMap.addOutportMap(port.getName(), outport);
//
//				// code for ports in ArangoDB
//				ProcessorDoc inportDoc = new ProcessorDoc();
//				inportDoc.setGraphName("OP:" + port.getName());
//				inportDoc.setName(port.getName());
//				arangoDB.createNode(inportDoc, "" + portId, "MomlCollections");
//				arangoDB.createEdge("hasOutPort", "" + uid, "" + portId, "MomlCollections", "MomlCollections");
//				inOutMap2.addOutputPort(port.getName(), "" + portId);
//			}
//		}
//
//		processInOutMap.put(momlEntity.getName(), inOutMap);
//		processInOutMap2.put(momlEntity.getName(), inOutMap2);
//	}
}
