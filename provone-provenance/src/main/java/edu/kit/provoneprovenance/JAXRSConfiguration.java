package edu.kit.provoneprovenance;

//import org.glassfish.jersey.media.multipart.MultiPartFeature;
//import org.glassfish.jersey.server.ResourceConfig;

import edu.kit.provoneprovenance.helper.AppConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Override
    public Map<String, Object> getProperties() {
        System.out.println(">>>>>>>>>>>>>>>> get properties");
        Map<String, Object> props = new HashMap<>();
        String filePath = System.getProperty("jboss.server.data.dir") + "/workflows";
        System.getProperties().list(System.out);
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