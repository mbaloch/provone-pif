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

import org.kit.scufl.embl.ScuflType;

public class EmblMain {
	
	public ScuflType parseEmbl(String filePath){


		File file = new File(filePath);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ScuflType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			FileInputStream inputStream = new FileInputStream(file);
			Source source = new StreamSource(inputStream);
			JAXBElement<ScuflType> root = jaxbUnmarshaller.unmarshal(source, ScuflType.class);
			return root.getValue();
			
		} catch (JAXBException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
