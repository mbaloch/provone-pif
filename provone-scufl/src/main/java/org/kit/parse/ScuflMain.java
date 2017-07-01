package org.kit.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.kit.scufl.api.Workflow;

public class ScuflMain {

	public Workflow parseScufl(String filePath) {

		try {
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Workflow.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			FileInputStream inputStream = new FileInputStream(file);
			Source source = new StreamSource(inputStream);
			JAXBElement<Workflow> root = jaxbUnmarshaller.unmarshal(source, Workflow.class);
			
			return (Workflow) root.getValue();

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
