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

import org.kit.xscufl.api.ScuflType;

public class XScuflParser {

	public ScuflType parseXScufl(String filePath) throws JAXBException, FileNotFoundException {

		File file = new File(filePath);
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(ScuflType.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		FileInputStream inputStream = new FileInputStream(file);
		Source source = new StreamSource(inputStream);
		JAXBElement<ScuflType> root = jaxbUnmarshaller.unmarshal(source, ScuflType.class);
		return root.getValue();
	}

}
