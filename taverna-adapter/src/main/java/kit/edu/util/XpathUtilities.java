package kit.edu.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kit.edu.core.Artifact;
import kit.edu.core.Content;
import kit.edu.core.Entity;
import kit.edu.core.HadMember;
import kit.edu.core.ProcessRun;
import kit.edu.core.WorkflowRun;

public class XpathUtilities {

	// private String filePath = "C://Users/Vaibhav/Desktop/halfAbcd_rdf.xml";
	// private String filePath = "C://Users/Vaibhav/Desktop/fullAbcd_rdf.xml";
	private String xmlString;

	public NodeList getExperessionNodes(String expression) {
		try {
			// File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dbFactory.setNamespaceAware(true);

			dBuilder = dbFactory.newDocumentBuilder();
			// Document doc = dBuilder.parse(inputFile);
			Document doc = dBuilder.parse(new InputSource(new StringReader(xmlString)));
			doc.getDocumentElement().normalize();

			XPath xPath = XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI,
					"net.sf.saxon.xpath.XPathFactoryImpl",
					ClassLoader.getSystemClassLoader()).newXPath();

			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			System.out.println("Total Size :" + nodeList.getLength());
			return nodeList;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (XPathFactoryConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public WorkflowRun transformNodeToWrkFlw(Node node) {
		Source source = new DOMSource(node);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(WorkflowRun.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<WorkflowRun> root = jaxbUnmarshaller.unmarshal(source, WorkflowRun.class);
			WorkflowRun rdfObj = root.getValue();
			return rdfObj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String nodeToString(Node node) {
		try {
			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProcessRun transformNodeToProcessRun(Node node) {
		Source source = new DOMSource(node);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ProcessRun.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<ProcessRun> root = jaxbUnmarshaller.unmarshal(source, ProcessRun.class);
			ProcessRun rdfObj = root.getValue();
			return rdfObj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Content transformNodeTavernaContent(Node node) {
		Source source = new DOMSource(node);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Content.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<Content> root = jaxbUnmarshaller.unmarshal(source, Content.class);
			Content rdfObj = root.getValue();
			return rdfObj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Entity transformNodeParentEntity(Node node) {
		Source source = new DOMSource(node);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Entity.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<Entity> root = jaxbUnmarshaller.unmarshal(source, Entity.class);
			Entity rdfObj = root.getValue();
			return rdfObj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HadMember transformHadMemberList(Node node) {
		Source source = new DOMSource(node);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(HadMember.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<HadMember> root = jaxbUnmarshaller.unmarshal(source, HadMember.class);
			HadMember rdfObj = root.getValue();
			return rdfObj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setXmlString(String ttlToRdf) {
		xmlString = ttlToRdf;
	}

	public Artifact transformArtifact(Node node) {
		Source source = new DOMSource(node);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Artifact.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<Artifact> root = jaxbUnmarshaller.unmarshal(source, Artifact.class);
			Artifact rdfObj = root.getValue();
			return rdfObj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
