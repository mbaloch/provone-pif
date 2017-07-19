package kit.edu.xpathmapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kit.edu.core.Artifact;
import kit.edu.core.Content;
import kit.edu.core.DescribedByParameter;
import kit.edu.core.Entity;
import kit.edu.core.HadMember;
import kit.edu.core.ProcessRun;
import kit.edu.core.Used;
import kit.edu.core.UsedInput;
import kit.edu.core.WasGeneratedBy;
import kit.edu.core.WasOutputFrom;
import kit.edu.core.WorkflowRun;
import kit.edu.util.RDFUtility;
import kit.edu.util.Utilities;
import kit.edu.util.XpathUtilities;

public class RetrospectiveXpathMapper {

	private XpathUtilities xpathUtil;
	private RDFUtility rdfUtil;
	private Utilities util;
	GenerateProcessExe generate;

	private Map<String, Resource> processExeMap = new HashMap<String, Resource>();
	private Map<String, Resource> dataMap = new HashMap<String, Resource>();
	private Set<String> wasGeneretedBySet = new HashSet<String>();
	private Set<String> usedBySet = new HashSet<String>();
	private Map<String, WorkflowRun> workFlowRunMap = new HashMap<String, WorkflowRun>();
	private Map<String, ProcessRun> processRunMap = new HashMap<String, ProcessRun>();

	public RetrospectiveXpathMapper() {
		xpathUtil = new XpathUtilities();
		rdfUtil = new RDFUtility();
		util = new Utilities(rdfUtil);
	}

	public Model xpathParser(String filePath, RetrospectiveXpathMapper retroclassobj) {
		generate = new GenerateProcessExe(retroclassobj);
		String ttlToRdf = util.transformTTl2RdfModel(filePath);
		// String ttlToRdf = util.parseRdfModel(filePath);
		xpathUtil.setXmlString(ttlToRdf);
		NodeList workflowRunNodeList = xpathUtil.getExperessionNodes("//*[local-name()='WorkflowRun']");
		NodeList processRunNodeList = xpathUtil.getExperessionNodes("//*[local-name()='ProcessRun']");
		NodeList hadMemberList = xpathUtil.getExperessionNodes("//*[local-name()='hadMember']");
		Resource WorkflowRunResource = parseAllWorkFlows(workflowRunNodeList);
		parseProcessRun(processRunNodeList, WorkflowRunResource);

		// generating Data node from Xpath experesion <tavernaprov:content>
		NodeList tavernaContent = xpathUtil.getExperessionNodes("//*[local-name()='content']");
		createDataNode(tavernaContent, hadMemberList);

		// creating connections of remaining nodes who dont have taverna content
		// for creating relations
		executeRemainingAssociations();
		return rdfUtil.getModel();
	}

	// this is the final stage for building the remaining relations which are
	// not formed during earlier phase
	// as in earlier execution we use taverna:content tag to build the relations
	// which is does not provide all the relations
	private void executeRemainingAssociations() {

		for (String workflowKey : workFlowRunMap.keySet()) {
			WorkflowRun workflow = workFlowRunMap.get(workflowKey);
			if (null != workflow.getUsed()) {
				for (Used used : workflow.getUsed()) {
					if (null != used.getResource()) {
						if (!usedBySet.contains(workflowKey + util.getID(used.getResource()))) {
							rdfUtil.used(processExeMap.get(workflowKey), dataMap.get(util.getID(used.getResource())));
							usedBySet.add(workflowKey + dataMap.get(util.getID(used.getResource())));
						}
					} else if (null != used.getEntity()) {
						if (!usedBySet.contains(workflowKey + util.getID(used.getEntity().getAbout()))) {
							rdfUtil.used(processExeMap.get(workflowKey),
									dataMap.get(util.getID(used.getEntity().getAbout())));
							usedBySet.add(workflowKey + util.getID(used.getEntity().getAbout()));
						}
					}
				}
			}
			if (null != workflow.getUsedInput()) {
				for (UsedInput usedInput : workflow.getUsedInput()) {
					if (null != usedInput.getResource()) {
						if (!usedBySet.contains(workflowKey + util.getID(usedInput.getResource()))) {
							rdfUtil.used(processExeMap.get(workflowKey),
									dataMap.get(util.getID(usedInput.getResource())));
							usedBySet.add(workflowKey + dataMap.get(util.getID(usedInput.getResource())));
						}
					} else {
						if (!usedBySet.contains(workflowKey + util.getID(usedInput.getEntity().getAbout()))) {
							rdfUtil.used(processExeMap.get(workflowKey),
									dataMap.get(util.getID(usedInput.getEntity().getAbout())));
							usedBySet.add(workflowKey + dataMap.get(util.getID(usedInput.getEntity().getAbout())));
						}
					}
				}
			}

		}

		// now for processRun
		for (String processKey : processRunMap.keySet()) {
			ProcessRun processRun = processRunMap.get(processKey);
			if (null != processRun.getUsed()) {
				for (Used used : processRun.getUsed()) {
					if (null != used.getResource()) {
						if (!usedBySet.contains(processKey + util.getID(used.getResource()))) {
							rdfUtil.used(processExeMap.get(processKey), dataMap.get(util.getID(used.getResource())));
							usedBySet.add(processKey + dataMap.get(util.getID(used.getResource())));
						}
					} else {
						if (!usedBySet.contains(processKey + util.getID(used.getEntity().getAbout()))) {
							rdfUtil.used(processExeMap.get(processKey),
									dataMap.get(util.getID(used.getEntity().getAbout())));
							usedBySet.add(processKey + dataMap.get(util.getID(used.getEntity().getAbout())));
						}
					}
				}
			}

			if (null != processRun.getUsedInput()) {
				for (UsedInput usedInput : processRun.getUsedInput()) {
					if (null != usedInput.getResource()) {
						if (!usedBySet.contains(processKey + util.getID(usedInput.getResource()))) {
							rdfUtil.used(processExeMap.get(processKey),
									dataMap.get(util.getID(usedInput.getResource())));
							usedBySet.add(processKey + dataMap.get(util.getID(usedInput.getResource())));
						}
					} else {
						if (!usedBySet.contains(processKey + util.getID(usedInput.getEntity().getAbout()))) {
							rdfUtil.used(processExeMap.get(processKey),
									dataMap.get(util.getID(usedInput.getEntity().getAbout())));
							usedBySet.add(processKey + dataMap.get(util.getID(usedInput.getEntity().getAbout())));
						}
					}
				}
			}
		}

	}

	private void createDataNode(NodeList tavernaContent, NodeList hadMemberList) {

		Set<String> uniqueRelation = new HashSet<String>();

		for (int i = 0; i < tavernaContent.getLength(); i++) {
			Node node = tavernaContent.item(i);
			Content content = xpathUtil.transformNodeTavernaContent(node);
			Entity parentEntity = xpathUtil.transformNodeParentEntity(node.getParentNode());

			if (null != content.getContent().getChars()) {

				Resource dataNode = rdfUtil.createData(util.getID(parentEntity.getAbout()), "Data",
						"" + content.getContent().getChars(), content.getContent().getChars());

				dataMap.put(util.getID(parentEntity.getAbout()), dataNode);

				// was DescribedBy Parameter
				if (null != parentEntity.getDescribedByParameter()) {
					for (DescribedByParameter describedByParam : parentEntity.getDescribedByParameter()) {
						if (null != describedByParam.getRole()) {
							if (util.getPath(describedByParam.getRole().getAbout()).contains("/out/"))
								rdfUtil.addProperty(dataNode, util.getPath(describedByParam.getRole().getAbout()));
						} else if (null != describedByParam.getResource()) {
							if (util.getPath(describedByParam.getResource()).contains("/out/"))
								rdfUtil.addProperty(dataNode, util.getPath(describedByParam.getResource()));
						}
					}
				}

				// was generatedby linked by wasoutputfrom
				if (null != parentEntity.getWasOutputFrom()) {
					for (WasOutputFrom wasOpFrom : parentEntity.getWasOutputFrom()) {
						if (!uniqueRelation.contains(util.getID(wasOpFrom.getResource()))) {
							rdfUtil.wasGeneratedBy(dataNode, processExeMap.get(util.getID(wasOpFrom.getResource())));
							uniqueRelation
									.add(util.getID(wasOpFrom.getResource()) + util.getID(parentEntity.getAbout()));
							wasGeneretedBySet
									.add(util.getID(wasOpFrom.getResource()) + util.getID(parentEntity.getAbout()));

						}
					}
				}
				if (null != parentEntity.getWasGeneratedBy()) {
					for (WasGeneratedBy wasGenby : parentEntity.getWasGeneratedBy()) {
						if (null != wasGenby.getResource()) {
							if (!uniqueRelation.contains(util.getID(wasGenby.getResource()))) {
								rdfUtil.wasGeneratedBy(dataNode, processExeMap.get(util.getID(wasGenby.getResource())));
								uniqueRelation
										.add(util.getID(wasGenby.getResource()) + util.getID(parentEntity.getAbout()));
								wasGeneretedBySet
										.add(util.getID(wasGenby.getResource()) + util.getID(parentEntity.getAbout()));
							}
						} else if (null != wasGenby.getProcessRun()) {
							String processId = util.getID(wasGenby.getProcessRun().getAbout());
							if (!uniqueRelation.contains(processId)) {
								rdfUtil.wasGeneratedBy(dataNode, processExeMap.get(processId));
								uniqueRelation.add(processId + util.getID(parentEntity.getAbout()));
								wasGeneretedBySet.add(processId + util.getID(parentEntity.getAbout()));
							}
						}
					}
				}

				if (node.getParentNode().getParentNode().getNodeName().toString().equalsIgnoreCase("prov:used")
						|| node.getParentNode().getParentNode().getNodeName().toString().equalsIgnoreCase("prov:entity")
						|| node.getParentNode().getParentNode().getNodeName().toString()
								.equalsIgnoreCase("wfprov:usedInput")) {
					String processMapId = getProcessId(node);
					rdfUtil.used(processExeMap.get(processMapId), dataNode);
					usedBySet.add(processMapId + util.getID(parentEntity.getAbout()));
				} else if (node.getParentNode().getParentNode().getNodeName().toString()
						.equalsIgnoreCase("prov:pairEntity")) {

					if (getParetNode("prov:hadDictionaryMember", node.getParentNode().getParentNode())) {
						String processMapId = util.getWorkFlowID(parentEntity.getAbout());
						rdfUtil.used(processExeMap.get(processMapId), dataNode);
						usedBySet.add(processMapId + util.getID(parentEntity.getAbout()));
					}

				} else if (node.getParentNode().getParentNode().getNodeName().toString().equalsIgnoreCase("rdf:RDF")) {
					System.out.println("ParentEntity:" + parentEntity.getAbout() + ">>rdf:RDF");
				} else if (node.getParentNode().getParentNode().getNodeName().toString()
						.equalsIgnoreCase("prov:hadMember")) {

					if (getParetNode("wfprov:Artifact", node.getParentNode().getParentNode())) {
						String processMapId = util.getWorkFlowID(parentEntity.getAbout());
						rdfUtil.used(processExeMap.get(processMapId), dataNode);
						usedBySet.add(processMapId + util.getID(parentEntity.getAbout()));
					}

				} else {
					System.out.println("missing Statement at RetrospectiveXpathMapper.java>>"
							+ node.getParentNode().getParentNode().getNodeName().toString());
				}

			}
		}

		for (int i = 0; i < hadMemberList.getLength(); i++) {

			Node hadMemberNode = hadMemberList.item(i);
			if (hadMemberNode.getParentNode().getParentNode().getNodeName().toString().equalsIgnoreCase("rdf:RDF")
					&& hadMemberNode.getParentNode().getNodeName().toString().equalsIgnoreCase("prov:Entity")) {
				HadMember hadMember = xpathUtil.transformHadMemberList(hadMemberNode);
				if (null != hadMember.getResource()) {
					Entity entity = xpathUtil.transformNodeParentEntity(hadMemberNode.getParentNode());
					String processorId = util.getID(entity.getWasOutputFrom().get(0).getResource());
					String hadmemberId = util.getID(hadMember.getResource());
					rdfUtil.wasGeneratedBy(dataMap.get(hadmemberId), processExeMap.get(processorId));
				}
			}
		}
	}

	private Node getRequiredNode(String nodeToSearch, Node parentNode) {
		if (parentNode.getParentNode().getNodeName().equals(nodeToSearch)) {
			return parentNode.getParentNode();
		} else {
			return getRequiredNode(nodeToSearch, parentNode.getParentNode());
		}
	}

	private boolean getParetNode(String nodeToSearch, Node parentNode) {
		if (null != parentNode.getParentNode()) {
			if (parentNode.getParentNode().getNodeName().equals(nodeToSearch)) {
				return true;
			} else {
				return getParetNode(nodeToSearch, parentNode.getParentNode());
			}
		} else {
			return false;
		}

	}

	private String getProcessId(Node node) {
		if (node.getNodeName().toString().equalsIgnoreCase("wfprov:WorkflowRun")
				|| node.getNodeName().toString().equalsIgnoreCase("wfprov:ProcessRun")) {
			return util.getID(node.getAttributes().getNamedItem("rdf:about").toString().replaceAll("rdf:about=\"", "")
					.replaceAll("\"", ""));
		} else {
			return getProcessId(node.getParentNode());
		}
	}

	private void parseProcessRun(NodeList processRunNodeList, Resource workflowRunResource) {

		for (int i = 0; i < processRunNodeList.getLength(); i++) {
			Node node = processRunNodeList.item(i);
			ProcessRun processRun = xpathUtil.transformNodeToProcessRun(node);

			String processId = util.getID(processRun.getAbout());
			if (!processExeMap.containsKey(processId)) {
				Resource processResource = util.createProcessResource(processRun);
				rdfUtil.isPartOf(processResource, workflowRunResource);
				processExeMap.put(processResource.getProperty(generate.getActivityIdProperty()).getObject().toString(),
						processResource);

				processRunMap.put(processId, processRun);
			}
		}
	}

	private Resource parseAllWorkFlows(NodeList workflowRunNodeList) {
		Resource mainWorkflowResource = null;
		Resource subWorkflowResource = null;
		// this loop is only for executing the mail workflow and its has part
		// not others
		for (int i = 0; i < workflowRunNodeList.getLength(); i++) {
			Node node = workflowRunNodeList.item(i);
			WorkflowRun wrkFlwRun = xpathUtil.transformNodeToWrkFlw(node);
			if (wrkFlwRun.getLabel().contains("Workflow run")) {
				mainWorkflowResource = generate.workflowProcExec(wrkFlwRun);
				workFlowRunMap.put(util.getWorkFlowID(wrkFlwRun.getAbout()), wrkFlwRun);
			}
		}

		// ifAny sub workflow is left to parse in that case this method will
		// handle those
		for (int i = 0; i < workflowRunNodeList.getLength(); i++) {
			Node node = workflowRunNodeList.item(i);
			WorkflowRun workflowRun = xpathUtil.transformNodeToWrkFlw(node);
			if (workflowRun.getLabel().contains("Processor execution")) {
				String workflowID = util.getID(workflowRun.getAbout());
				if (!processExeMap.containsKey(workflowID)) {
					subWorkflowResource = generate.createSubWorkFlow(workflowRun);
					rdfUtil.isPartOf(subWorkflowResource, mainWorkflowResource);
					processExeMap.put(workflowID, subWorkflowResource);
					workFlowRunMap.put(workflowID, workflowRun);
				}
			}
		}

		return mainWorkflowResource;
	}

	public XpathUtilities getXpathUtil() {
		return xpathUtil;
	}

	public void setXpathUtil(XpathUtilities xpathUtil) {
		this.xpathUtil = xpathUtil;
	}

	public RDFUtility getRdfUtil() {
		return rdfUtil;
	}

	public void setRdfUtil(RDFUtility rdfUtil) {
		this.rdfUtil = rdfUtil;
	}

	public Utilities getUtil() {
		return util;
	}

	public void setUtil(Utilities util) {
		this.util = util;
	}

	public Map<String, Resource> getProcessExeMap() {
		return processExeMap;
	}

	public void setProcessExeMap(Map<String, Resource> processExeMap) {
		this.processExeMap = processExeMap;
	}

	public void setProcessExeMap(String processid, Resource processExe) {
		this.processExeMap.put(processid, processExe);
	}

	public Map<String, WorkflowRun> getWorkFlowRunMap() {
		return workFlowRunMap;
	}

	public void setWorkFlowRunMap(Map<String, WorkflowRun> workFlowRunMap) {
		this.workFlowRunMap = workFlowRunMap;
	}

	public void setWorkFlowRunMap(String workflowId, WorkflowRun workFlowRunMap) {
		this.workFlowRunMap.put(workflowId, workFlowRunMap);
	}

	public Map<String, ProcessRun> getProcessRunMap() {
		return processRunMap;
	}

	public void setProcessRunMap(Map<String, ProcessRun> processRunMap) {
		this.processRunMap = processRunMap;
	}

	public void setProcessRunMap(String processId, ProcessRun processRunMap) {
		this.processRunMap.put(processId, processRunMap);
	}

}
