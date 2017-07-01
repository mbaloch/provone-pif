package org.kit.mainapp;

public class MomlToProv {
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
//
//			rdfUtil = new RDFUtility();
//
//			Prov2ONEMoML(momlEntity);
//
//			File file = new File("C:/Users/VB/Desktop/sample2.rdf");
//			rdfUtil.getModel().write(new FileWriter(file));
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
//
//			GenerateProvONE(relation, utility.getL(), processInOutMap, processInOutMap2);
//		}
//		processorStack.pop();
//	}
//
//	private void GenerateProvONE(Relation relation, ArrayList<Link> links,
//			HashMap<String, ProcessorInOutMap> processInOutMap,
//			HashMap<String, ProcessorInOutMapArango> processInOutMap2) {
//
//		ArrayList<String> nestedP = new ArrayList<String>();
//		ArrayList<String> inputP = new ArrayList<String>();
//		ArrayList<String> outputP = new ArrayList<String>();
//		ArrayList<String> noP = new ArrayList<String>();
//
//		for (Link link : links) {
//			if (relation.getName().toString().equals(link.getRelation().toString())) {
//
//				if (!link.getPort().contains(".")) {
//					nestedP.add(link.getPort());
//				} else {
//					String[] reltArry = link.getPort().split("\\.");
//					switch (reltArry[1].trim().toLowerCase()) {
//					case "input":
//						inputP.add(link.getPort());
//						break;
//					case "output":
//						outputP.add(link.getPort());
//						break;
//					default:
//						String[] temp = link.getPort().split("\\.");
//						if (processInOutMap.get(temp[0]).getInportMap().containsKey(temp[1])) {
//							inputP.add(link.getPort());
//						} else if (processInOutMap.get(temp[0]).getOutportMap().containsKey(temp[1])) {
//							outputP.add(link.getPort());
//						} else {
//							noP.add(link.getPort());
//						}
//					}
//				}
//			}
//		}
//
//		if (nestedP.size() != 0) {
//			for (String nested : nestedP) {
//				if (processInOutMap.get(processorStack.peek()).getInportMap().containsKey(nested)) {
//
//					for (String ipP : inputP) {
//						connectNestedInputToProcessInput(ipP, processInOutMap, processInOutMap2, nested);
//					}
//					for (String noneP : noP) {
//						connectNestedInputToProcessInput(noneP, processInOutMap, processInOutMap2, nested);
//					}
//				} else if (processInOutMap.get(processorStack.peek()).getOutportMap().containsKey(nested)) {
//					for (String outPort : outputP) {
//						String[] nameSplit = outPort.split("\\.");
//						String outputID;
//						Resource localOutPort;
//
//						if (processInOutMap.get(nameSplit[0]).getOutportMap().containsKey(nameSplit[1])) {
//							outputID = processInOutMap2.get(nameSplit[0]).getOutputPortMap().get(nameSplit[1]);
//							localOutPort = processInOutMap.get(nameSplit[0]).getOutportMap().get(nameSplit[1]);
//						} else {
//							// Create new port and add to the list
//							outputID = UUID.randomUUID().toString();
//							localOutPort = rdfUtil.createOutputPort(outputID, nameSplit[1]);
//							rdfUtil.hasOutPort(processInOutMap.get(nameSplit[0]).getProcessDoc(), localOutPort);
//
//							ProcessorDoc wrkPojo = new ProcessorDoc();
//							wrkPojo.setGraphName("OP:" + nameSplit[1]);
//							wrkPojo.setName(nameSplit[1]);
//							arangoDB.createNode(wrkPojo, outputID, "MomlCollections");
//							arangoDB.createEdge("hasOutPort", processInOutMap2.get(nameSplit[0]).getProcessorID(),
//									outputID, "MomlCollections", "MomlCollections");
//						}
//
//						for (String nestedPort : nestedP) {
//							String nestedInputA = processInOutMap2.get(processorStack.peek()).getOutputPortMap()
//									.get(nestedPort);
//							Resource nestedInput = processInOutMap.get(processorStack.peek()).getOutportMap()
//									.get(nestedPort);
//
//							String datalinkId = UUID.randomUUID().toString();
//							Resource datalink = rdfUtil.createDataLink("DL:" + nameSplit[1]);
//
//							ProcessorDoc wrkPojo1 = new ProcessorDoc();
//							wrkPojo1.setGraphName("DL:" + nameSplit[0]);
//							wrkPojo1.setName(nameSplit[0]);
//							arangoDB.createNode(wrkPojo1, datalinkId, "MomlCollections");
//
//							rdfUtil.outPortToDL(localOutPort, datalink);
//							rdfUtil.DLToOutPort(datalink, nestedInput);
//
//							arangoDB.createEdge("outPortToDl", outputID, datalinkId, "MomlCollections",
//									"MomlCollections");
//							arangoDB.createEdge("DLToOutPort", datalinkId, nestedInputA, "MomlCollections",
//									"MomlCollections");
//
//						}
//
//						for (String noneP : noP) {
//							String[] noneNameSplit = noneP.split("\\.");
//
//							String inputPortA;
//							Resource inputPort;
//
//							if (processInOutMap.get(noneNameSplit[0]).getInportMap().containsKey(noneNameSplit[1])) {
//								inputPort = processInOutMap.get(noneNameSplit[0]).getInportMap().get(noneNameSplit[1]);
//								inputPortA = processInOutMap2.get(noneNameSplit[0]).getInputPortMap()
//										.get(noneNameSplit[1]);
//							} else {
//
//								inputPortA = UUID.randomUUID().toString();
//								inputPort = rdfUtil.createInputPort(inputPortA, noneNameSplit[1]);
//								rdfUtil.hasInPort(processInOutMap.get(noneNameSplit[0]).getProcessDoc(), inputPort);
//
//								ProcessorDoc wrkPojo = new ProcessorDoc();
//								wrkPojo.setGraphName("IP:" + noneNameSplit[1]);
//								wrkPojo.setName(noneNameSplit[1]);
//								arangoDB.createNode(wrkPojo, inputPortA, "MomlCollections");
//								arangoDB.createEdge("hasInPort",
//										processInOutMap2.get(noneNameSplit[0]).getProcessorID(), inputPortA,
//										"MomlCollections", "MomlCollections");
//
//							}
//
//							String datalinkId = UUID.randomUUID().toString();
//							Resource datalink = rdfUtil.createDataLink("DL:" + nameSplit[0]);
//
//							ProcessorDoc wrkPojo1 = new ProcessorDoc();
//							wrkPojo1.setGraphName("DL:" + nameSplit[0]);
//							wrkPojo1.setName(nameSplit[0]);
//							arangoDB.createNode(wrkPojo1, datalinkId, "MomlCollections");
//
//							rdfUtil.outPortToDL(localOutPort, datalink);
//							rdfUtil.DLToInPort(datalink, inputPort);
//
//							arangoDB.createEdge("outPortToDl", outputID, datalinkId, "MomlCollections",
//									"MomlCollections");
//							arangoDB.createEdge("DLToInPort", datalinkId, inputPortA, "MomlCollections",
//									"MomlCollections");
//						}
//					}
//				}
//			}
//
//		} else {
//
//			if (inputP.size() > 0 && outputP.size() > 0 && noP.size() == 0) {
//				for (String outPort : outputP) {
//
//					String[] nameSplit = outPort.split("\\.");
//					String outputID;
//					Resource localOutPort;
//
//					if (processInOutMap.get(nameSplit[0]).getOutportMap().containsKey(nameSplit[1])) {
//						outputID = processInOutMap2.get(nameSplit[0]).getOutputPortMap().get(nameSplit[1]);
//						localOutPort = processInOutMap.get(nameSplit[0]).getOutportMap().get(nameSplit[1]);
//					} else {
//						// Create new port and add to the list
//						outputID = UUID.randomUUID().toString();
//						localOutPort = rdfUtil.createOutputPort(outputID, nameSplit[1]);
//						rdfUtil.hasOutPort(processInOutMap.get(nameSplit[0]).getProcessDoc(), localOutPort);
//
//						ProcessorDoc wrkPojo = new ProcessorDoc();
//						wrkPojo.setGraphName("OP:" + nameSplit[1]);
//						wrkPojo.setName(nameSplit[1]);
//						arangoDB.createNode(wrkPojo, outputID, "MomlCollections");
//						arangoDB.createEdge("hasOutPort", processInOutMap2.get(nameSplit[0]).getProcessorID(), outputID,
//								"MomlCollections", "MomlCollections");
//
//						processInOutMap.get(nameSplit[0]).addInportMap(nameSplit[1], localOutPort);
//						processInOutMap2.get(nameSplit[0]).addInputPort(nameSplit[1], outputID);
//
//					}
//					for (String inPort : inputP) {
//
//						String[] nameSplitInput = inPort.split("\\.");
//
//						String inportID;
//						Resource localInPort;
//
//						if (processInOutMap.get(nameSplitInput[0]).getInportMap().containsKey(nameSplitInput[1])) {
//							localInPort = processInOutMap.get(nameSplitInput[0]).getInportMap().get(nameSplitInput[1]);
//							inportID = processInOutMap2.get(nameSplitInput[0]).getInputPortMap().get(nameSplitInput[1]);
//						} else {
//							inportID = UUID.randomUUID().toString();
//							localInPort = rdfUtil.createInputPort(inportID, nameSplitInput[1]);
//							rdfUtil.hasInPort(processInOutMap.get(nameSplitInput[0]).getProcessDoc(), localInPort);
//
//							ProcessorDoc wrkPojo = new ProcessorDoc();
//							wrkPojo.setGraphName("IP:" + nameSplitInput[1]);
//							wrkPojo.setName(nameSplitInput[1]);
//							arangoDB.createNode(wrkPojo, inportID, "MomlCollections");
//							arangoDB.createEdge("hasInPort", processInOutMap2.get(nameSplitInput[0]).getProcessorID(),
//									inportID, "MomlCollections", "MomlCollections");
//
//							processInOutMap.get(nameSplitInput[0]).addInportMap(nameSplitInput[1], localInPort);
//							processInOutMap2.get(nameSplitInput[0]).addInputPort(nameSplitInput[1], inportID);
//						}
//
//						String datalinkId = UUID.randomUUID().toString();
//						Resource datalink = rdfUtil.createDataLink("DL:" + nameSplit[0]);
//
//						ProcessorDoc wrkPojo1 = new ProcessorDoc();
//						wrkPojo1.setGraphName("DL:" + nameSplit[0]);
//						wrkPojo1.setName(nameSplit[0]);
//						arangoDB.createNode(wrkPojo1, datalinkId, "MomlCollections");
//
//						rdfUtil.outPortToDL(localOutPort, datalink);
//						rdfUtil.DLToInPort(datalink, localInPort);
//
//						arangoDB.createEdge("outPortToDl", outputID, datalinkId, "MomlCollections", "MomlCollections");
//						arangoDB.createEdge("DLToInPort", datalinkId, inportID, "MomlCollections", "MomlCollections");
//					}
//				}
//			} else if (outputP.size() > 0 && noP.size() > 0 && inputP.size() == 0) {
//				for (String outPort : outputP) {
//					String[] nameSplit = outPort.split("\\.");
//					String outputID;
//					Resource localOutPort;
//
//					if (processInOutMap.get(nameSplit[0]).getOutportMap().containsKey(nameSplit[1])) {
//						outputID = processInOutMap2.get(nameSplit[0]).getOutputPortMap().get(nameSplit[1]);
//						localOutPort = processInOutMap.get(nameSplit[0]).getOutportMap().get(nameSplit[1]);
//					} else {
//						// Create new port and add to the list
//						outputID = UUID.randomUUID().toString();
//						localOutPort = rdfUtil.createOutputPort(outputID, nameSplit[1]);
//						rdfUtil.hasOutPort(processInOutMap.get(nameSplit[0]).getProcessDoc(), localOutPort);
//
//						ProcessorDoc wrkPojo = new ProcessorDoc();
//						wrkPojo.setGraphName("OP:" + nameSplit[1]);
//						wrkPojo.setName(nameSplit[1]);
//						arangoDB.createNode(wrkPojo, outputID, "MomlCollections");
//						arangoDB.createEdge("hasOutPort", processInOutMap2.get(nameSplit[0]).getProcessorID(), outputID,
//								"MomlCollections", "MomlCollections");
//
//						processInOutMap.get(nameSplit[0]).addInportMap(nameSplit[1], localOutPort);
//						processInOutMap2.get(nameSplit[0]).addInputPort(nameSplit[1], outputID);
//
//					}
//					for (String noneP : noP) {
//						String[] noneNameSplit = noneP.split("\\.");
//
//						String inputPortA;
//						Resource inputPort;
//
//						if (processInOutMap.get(noneNameSplit[0]).getInportMap().containsKey(noneNameSplit[1])) {
//							inputPort = processInOutMap.get(noneNameSplit[0]).getInportMap().get(noneNameSplit[1]);
//							inputPortA = processInOutMap2.get(noneNameSplit[0]).getInputPortMap().get(noneNameSplit[1]);
//						} else {
//
//							inputPortA = UUID.randomUUID().toString();
//							inputPort = rdfUtil.createInputPort(inputPortA, noneNameSplit[1]);
//							rdfUtil.hasInPort(processInOutMap.get(noneNameSplit[0]).getProcessDoc(), inputPort);
//
//							ProcessorDoc wrkPojo = new ProcessorDoc();
//							wrkPojo.setGraphName("IP:" + noneNameSplit[1]);
//							wrkPojo.setName(noneNameSplit[1]);
//							arangoDB.createNode(wrkPojo, inputPortA, "MomlCollections");
//							arangoDB.createEdge("hasInPort", processInOutMap2.get(noneNameSplit[0]).getProcessorID(),
//									inputPortA, "MomlCollections", "MomlCollections");
//
//						}
//
//						String datalinkId = UUID.randomUUID().toString();
//						Resource datalink = rdfUtil.createDataLink("DL:" + nameSplit[0]);
//
//						ProcessorDoc wrkPojo1 = new ProcessorDoc();
//						wrkPojo1.setGraphName("DL:" + nameSplit[0]);
//						wrkPojo1.setName(nameSplit[0]);
//						arangoDB.createNode(wrkPojo1, datalinkId, "MomlCollections");
//
//						rdfUtil.outPortToDL(localOutPort, datalink);
//						rdfUtil.DLToInPort(datalink, inputPort);
//
//						arangoDB.createEdge("outPortToDl", outputID, datalinkId, "MomlCollections", "MomlCollections");
//						arangoDB.createEdge("DLToInPort", datalinkId, inputPortA, "MomlCollections", "MomlCollections");
//					}
//				}
//
//			} else if (inputP.size() > 0 && noP.size() > 0 && outputP.size() == 0) {
//
//			}
//		}
//
//	}
//
//	private void connectNestedInputToProcessInput(String noneP, HashMap<String, ProcessorInOutMap> processInOutMap,
//			HashMap<String, ProcessorInOutMapArango> processInOutMap2, String nested) {
//
//		String[] nameSplit = noneP.split("\\.");
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
//			arangoDB.createEdge("hasInPort", processInOutMap2.get(nameSplit[0]).getProcessorID(), inputID,
//					"MomlCollections", "MomlCollections");
//
//			processInOutMap.get(nameSplit[0]).addInportMap(nameSplit[1], localInPort);
//			processInOutMap2.get(nameSplit[0]).addInputPort(nameSplit[1], inputID);
//		}
//		connectNestedInputToInput(wrkFlwInPort, localInPort, processorStack.peek());
//		connectNestedInputToInputA(wrkFlwInPortA, inputID, processorStack.peek());
//
//	}
//
//	private void connectNestedInputToInputA(String wrkFlwInPortA, String inputID, String peek) {
//		String datalinkID = UUID.randomUUID().toString();
//		ProcessorDoc wrkPojo = new ProcessorDoc();
//		wrkPojo.setGraphName("DL:" + peek);
//		wrkPojo.setName(peek);
//		arangoDB.createNode(wrkPojo, datalinkID, "MomlCollections");
//
//		arangoDB.createEdge("inPortToDl", wrkFlwInPortA, datalinkID, "MomlCollections", "MomlCollections");
//		arangoDB.createEdge("DLToInPort", datalinkID, inputID, "MomlCollections", "MomlCollections");
//
//	}
//
//	private void connectNestedInputToInput(Resource wrkFlwInPort, Resource localInPort, String sourceP) {
//		Resource dataLinkResource = rdfUtil.createDataLink("DL:" + sourceP);
//		rdfUtil.inPortToDL(wrkFlwInPort, dataLinkResource);
//		rdfUtil.DLToInPort(dataLinkResource, localInPort);
//	}
//
//	private void CONNECT(Entity momlEntity, HashMap<String, ProcessorInOutMap> processInOutMap,
//			HashMap<String, ProcessorInOutMapArango> processInOutMap2) {
//
//		UUID uid = UUID.randomUUID();
//
//		// code for RDF Graphs
////		Resource workFlow = rdfUtil.createProcess("" + uid, "WorkFlow");
//		Resource processor = processInOutMap.get(momlEntity.getName()).getProcessDoc();
////		rdfUtil.wasDerivedFrom(workFlow, processor);
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
//	private void ATTACHPORTS(Entity momlEntity, SegrigaterUtility utility,
//			HashMap<String, ProcessorInOutMap> processInOutMap,
//			HashMap<String, ProcessorInOutMapArango> processInOutMap2) {
//
//		UUID uid = UUID.randomUUID();
//		// Code For Creating Process Node in RDF Graph
//		ProcessorInOutMap inOutMap = new ProcessorInOutMap();
////		Resource process = rdfUtil.createProcess("" + uid, momlEntity.getName());
////		inOutMap.setProcessDoc(process);
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
