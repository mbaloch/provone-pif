package edu.kit.scufl.mainapp;

import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
//import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
//import edu.kit.scufl.api.RetorspectiveMapper;
import edu.kit.scufl.api.RetorspectiveMapper;
import edu.kit.scufl.core.RDF;

public class SCUFLRetro2ProvONE {

	public void attachPros2Retro(Model retrospectiveRDFModel){

	//	retrospectiveRDFModel
	}
	public void generateRetro(String ttlFilePath){
	//	Model retrospectiveRDFModel = getRetrospectiveRDFModel(ttlFilePath);
	//	attachPros2Retro(retrospectiveRDFModel);
	}

	public Model getRetrospectiveRDFModel(String retroTTLFilePath) throws Exception {
		Model model = ModelFactory.createDefaultModel();
		Model retrospectiveModel=null;
		try {
			model.read(new FileInputStream(retroTTLFilePath), null, "TTL");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		StringWriter rdfXmlWriter = new StringWriter();
		 //model.write(System.out, "RDF/XML");
		model.write(rdfXmlWriter, "RDF/XML-ABBREV");
		try {
			//Source source = new StreamSource(new StringReader(rdfXmlWriter.toString()));
			Source source = new StreamSource(
					new File("/home/mukhtar/IdeaProjects/provone-pif/provone-provenance/src/main/resources/scufttlRDF.rdf"));
			JAXBContext jaxbContext = JAXBContext.newInstance(RDF.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<RDF> root = jaxbUnmarshaller.unmarshal(source, RDF.class);
			RDF rdfObj = root.getValue();
			RetorspectiveMapper retorspectiveMapper = new RetorspectiveMapper(rdfObj);
			retrospectiveModel = retorspectiveMapper.startRetrospective();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return retrospectiveModel;
	}

	/*protected static Source prettyLogXml(String xml){
		try {
			Transformer transformer=TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			Source xmlInput=new StreamSource(new StringReader(xml));
			StreamResult xmlOutput=new StreamResult(new StringWriter());
			transformer.transform(xmlInput,xmlOutput);
			return xmlInput;
		}
		catch (  Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}