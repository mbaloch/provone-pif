package kit.edu.util;

import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;

import kit.edu.core.ProcessRun;
import kit.edu.core.RDF;
import kit.edu.core.WorkflowRun;

/**
 * This 
 * @author Vaibhav
 *
 */
public class Utilities {

	RDFUtility rdfUtil;
	private String about;

	public Utilities() {
	}

	public Utilities(RDFUtility rdfUtil2) {
		rdfUtil = rdfUtil2;
	}

	/**
	 * this function is used to get the about information from the string.
	 * @param rdfabout
	 * @return
	 */
	public String getID(String rdfabout) {
		about = rdfabout;
		String[] about = rdfabout.split("/");
		String id = about[about.length - 1];
		return id;
	}

	/**
	 * This function is used to calculate the actual path from the string.
	 * 
	 * @param resource
	 * @return
	 */
	public String getPath(String resource) {
		String workflowid = getWorkFlowID(resource);
		String[] splitString = resource.split(workflowid);
		String finalPath = splitString[1].replaceAll("/workflow/", "");
		if(finalPath.endsWith("/")){
			finalPath = finalPath.substring(0, finalPath.length()-1);
		}
		return finalPath;
	}

	/**
	 * this function is used to calculate the ID from the String
	 * @param describedByWorkflowID
	 * @return
	 */
	public String getWorkFlowID(String describedByWorkflowID) {
		String[] about = describedByWorkflowID.split("/");
		for (String part : about) {
			if (part.contains("-")) {
				return part;
			}
		}
		return null;
	}

	/**
	 * This function is used to convert the TTL file to RDF/XML representations
	 * @param ttlFilePath
	 * @return
	 */
	public String transformTTl2RdfModel(String ttlFilePath) {
		Model model = RDFDataMgr.loadModel(ttlFilePath);
		StringWriter rdfXmlWriter = new StringWriter();
		model.write(rdfXmlWriter, "RDF/XML-ABBREV");
		return rdfXmlWriter.toString();
	}

	/**
	 * this method is used to transform the XML to java pojo objects.
	 * 
	 * @param ttlFilePath
	 * @return
	 * @throws JAXBException
	 */
	public RDF parseRdfModel(String ttlFilePath) throws JAXBException {
		Source source = new StreamSource(new
				StringReader(transformTTl2RdfModel(ttlFilePath)));
//		Source source = new StreamSource(new File("C://Users/Vaibhav/Desktop/halfAbcd_rdf.xml"));
		JAXBContext jaxbContext = JAXBContext.newInstance(RDF.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<RDF> root = jaxbUnmarshaller.unmarshal(source, RDF.class);
		RDF rdfObj = root.getValue();
		return rdfObj;
	}

	/**
	 * This method is used to create the Process resource.
	 * 
	 * @param processRun
	 * @return
	 */
	public Resource createProcessResource(ProcessRun processRun) {
		String proceesorID = getID(processRun.getAbout());
		String processName;
		if (null == processRun.getDescribedByProcess().getResource()) {
			processName = getPath(processRun.getDescribedByProcess().getPlan().getAbout());
		} else {
			processName = getPath(processRun.getDescribedByProcess().getResource());
		}
		Resource processResource = rdfUtil.createProcessExec(proceesorID, processName, proceesorID,
				processRun.getStartedAtTime(), processRun.getEndedAtTime(), "completed", null);

		return processResource;
	}

	// for creating the resource for workflow node 
	public Resource createWorkResource(WorkflowRun workflowRun) {

		String workflowID = getID(workflowRun.getAbout());
		String workFlowName = workflowRun.getLabel().replaceAll("Workflow run of ", "");
		String describedByWorkflowID = "";

		if (null != workflowRun.getDescribedByWorkflow()) {
			if (null != workflowRun.getDescribedByWorkflow().getResource()) {
				workFlowName = getPath(workflowRun.getDescribedByWorkflow().getResource());
				describedByWorkflowID = getWorkFlowID(workflowRun.getDescribedByWorkflow().getResource());
			} else if (null != workflowRun.getDescribedByWorkflow().getPlan()) {
//				workFlowName = getPath(workflowRun.getDescribedByWorkflow().getPlan().getAbout());
				describedByWorkflowID = getWorkFlowID(workflowRun.getDescribedByWorkflow().getPlan().getAbout());
			}
		} else if (null != workflowRun.getDescribedByProcess()) {
			workFlowName = getPath(workflowRun.getDescribedByProcess().getResource());
		}

		Resource workFlowResource = rdfUtil.createProcessExec(workflowID, workFlowName, workflowID,
				workflowRun.getStartedAtTime(), workflowRun.getEndedAtTime(), "completed", describedByWorkflowID);
		return workFlowResource;
	}

	public Resource createSubWrkFlwProcessResource(WorkflowRun workflowRun) {
		String workflowID = getID(workflowRun.getAbout());
		
		String workFlowName = workflowRun.getLabel().replaceAll("Processor execution ", "");
		String describedByProcess = null;
		if(null != workflowRun.getDescribedByProcess().getResource()){
			describedByProcess = getPath(workflowRun.getDescribedByProcess().getResource());
		}else if(null != workflowRun.getDescribedByProcess().getPlan()){
			describedByProcess = getPath(workflowRun.getDescribedByProcess().getPlan().getAbout());
		}
		Resource subworkFlowResource = rdfUtil.createProcessExec(workflowID, describedByProcess, workflowID,
				workflowRun.getStartedAtTime(), workflowRun.getEndedAtTime(), "completed", null);
		return subworkFlowResource;
	}

}
