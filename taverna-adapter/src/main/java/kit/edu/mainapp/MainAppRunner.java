package kit.edu.mainapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.jena.rdf.model.Model;

import kit.edu.xpathmapper.RetrospectiveXpathMapper;

public class MainAppRunner {

	public static void main(String[] args) {

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URL resource = classLoader.getResource("halfHello_abcd.ttl");
		String ttlFilePath = resource.getFile();

//		RetrospectiveMapper retroMapper = new RetrospectiveMapper();
//		Model finalModel = retroMapper.parseTtl("resource/fullHello_abcd.ttl");
//		Model finalModel = retroMapper.parseTtl("resource/halfHello_abcd.ttl");
//		Model finalModel = retroMapper.parseTtl("resource/workflowrun.prov.ttl");
		RetrospectiveXpathMapper retroMapper = new RetrospectiveXpathMapper(); 
		Model finalModel = retroMapper.xpathParser(ttlFilePath);
//		Model finalModel = retroMapper.xpathParser("resource/fullHello_abcd.ttl");
		
//		File file = new File("C://Users/Vaibhav/Desktop/retroOutPut.rdf");
//		File file2 = new File("C://Users/Vaibhav/Desktop/retroOutPut.xml");
//		FileOutputStream out = null;
//		FileOutputStream out2 = null;
//		try {
//			out = new FileOutputStream(file);
//			out2 = new FileOutputStream(file2);
//			finalModel.write(out);
//			finalModel.write(out2);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				out.close();
//				out2.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
	}
}
