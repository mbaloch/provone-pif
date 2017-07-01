package edu.kit.scufl.mainapp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import edu.kit.scufl.api.RetorspectiveMapper;
import edu.kit.scufl.core.RDF;

public class MainAppRunner {

	public void appRunner(String ttlFilePath){
		Model model = ModelFactory.createDefaultModel();
		try {
			model.read(new FileInputStream(ttlFilePath), null, "TTL");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		StringWriter rdfXmlWriter = new StringWriter();
		// model.write(System.out, "RDF/XML");
		model.write(rdfXmlWriter, "RDF/XML-ABBREV");
//		System.out.println("xmlOutput:-" + rdfXmlWriter.toString() + "\n------------------------\n");
		InputStream stream = new ByteArrayInputStream(rdfXmlWriter.toString().getBytes(StandardCharsets.UTF_8));

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(RDF.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Source source = new StreamSource(new File("/home/mukhtar/Desktop/final3/provonepif/tvaerna-adapter/src/main/resources/tempRDF.xml"));
//			Source source = new StreamSource(stream);
			JAXBElement<RDF> root = jaxbUnmarshaller.unmarshal(source, RDF.class);
			RDF rdfObj = root.getValue();
			System.out.println(">>"+rdfObj.getDictionary().getWasOutputFrom().getWorkflowRun().getLabel());
			RetorspectiveMapper retorspectiveMapper = new RetorspectiveMapper(rdfObj);
			retorspectiveMapper.startRetrospective();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {


		Model model = ModelFactory.createDefaultModel();
		try {
			model.read(new FileInputStream("E:\\KIT\\provenance.ttl"), null, "TTL");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		StringWriter rdfXmlWriter = new StringWriter();
		// model.write(System.out, "RDF/XML");
		model.write(rdfXmlWriter, "RDF/XML-ABBREV");
//		System.out.println("xmlOutput:-" + rdfXmlWriter.toString() + "\n------------------------\n");
		InputStream stream = new ByteArrayInputStream(rdfXmlWriter.toString().getBytes(StandardCharsets.UTF_8));
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(RDF.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Source source = new StreamSource(new File("E:\\KIT\\tempRDF.xml"));
//			Source source = new StreamSource(stream);
			JAXBElement<RDF> root = jaxbUnmarshaller.unmarshal(source, RDF.class);
			RDF rdfObj = root.getValue();
			System.out.println(">>"+rdfObj.getDictionary().getWasOutputFrom().getWorkflowRun().getLabel());
			RetorspectiveMapper retorspectiveMapper = new RetorspectiveMapper(rdfObj);
			retorspectiveMapper.startRetrospective();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
