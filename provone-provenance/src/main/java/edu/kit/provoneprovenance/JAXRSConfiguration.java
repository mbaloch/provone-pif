package edu.kit.provoneprovenance;

//import org.glassfish.jersey.media.multipart.MultiPartFeature;
//import org.glassfish.jersey.server.ResourceConfig;

import edu.kit.provoneprovenance.helper.AppConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by mukhtar on 29.06.17.
 */
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
    private void loadSetting(Map<String, Object> propertiesHashMap) throws IOException {
        // ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        URL resource = classLoader.getResource("settings.properties");
//        String settingsFilePath = resource.getFile();
//        File file = new File(settingsFilePath);
//        FileInputStream fileInput = new FileInputStream(file);
        Properties properties = new Properties();
        String fileName = System.getProperty("jboss.server.config.dir") + "/provenance.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
        }
        //        properties.load(fileInput);
//        fileInput.close();
        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            propertiesHashMap.put(key, value);
            System.out.println(key + ": " + value);
        }


    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();

        System.out.println(">>>>> read properties from file <<<<<");
        try {
            loadSetting(props);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(">>>>> finished reading properties from file <<<<<");

        System.out.println(">>>>>>>>>>>>>>>> get properties");
        String filePath = System.getProperty("jboss.server.data.dir") + "/workflows";
        java.nio.file.Path dirPath = Paths.get(filePath);
        if (!Files.exists(dirPath)) {
            System.out.println("<<<<<<<<<< workflow directory does not exist>>>>>");
            System.out.println("creating folder");
            try {
                Files.createDirectory(dirPath);
                System.out.println(" directory for workflows created at:" + dirPath);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("<<<<<<<<<< workflow directory exist>>>>>" + dirPath);
        }


        props.put("workflow-dir", filePath);
        return props;
    }

    public JAXRSConfiguration() {

        // loadConfig();
        //packages("org.foo.rest;org.bar.rest");

//        packages("edu.kit.provoneprovenance");
//        packages("org.glassfish.jersey.examples.multipart");
//        register(MultiPartFeature.class);
    }

}