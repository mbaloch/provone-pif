package edu.kit.provoneprovenance;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by mukhtar on 26.06.17.
 */
public class MyResourceTest {
    @Test
    public void scuflProspective() throws Exception {
        final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
        final WebTarget target = client.target("http://localhost:8585/provone-provenance/workflow/scufl");
        final Response response = target.request().get();
    }

    @Test
    public void scuflRetro() throws Exception {
    }

    private HttpServer server;
    private WebTarget target;
    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --

        //c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());
        //c.getConfiguration().
        c.register(MultiPartFeature.class);

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getIt() throws Exception {
//        final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
//        final WebTarget target = client.target("http://localhost:8585/provone-provenance/workflow/scufl");
//        final Response response = target.request().get();


//        final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
//
//        final FileDataBodyPart filePart = new FileDataBodyPart("file", new File("/home/mukhtar/Desktop/final3/provonepif/provone-provenance/src/main/resources/bpelAdd.bpel"));
//        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
//        final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.field("foo", "bar").bodyPart(filePart);
//
//        final WebTarget target = client.target("http://localhost:8585/provone-provenance/workflow/bpelworkflow");
//        final Response response = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
//
//        //Use response object to verify upload success
//
//        formDataMultiPart.close();
//        multipart.close();
    }

    @Test
    public void uploadPdfFile() throws Exception {
/*
        final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

        final FileDataBodyPart filePart = new FileDataBodyPart("file", new File("/home/mukhtar/Desktop/final3/provonepif/provone-provenance/src/main/resources/bpelAdd.bpel"));
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.field("foo", "bar").bodyPart(filePart);

        final WebTarget target = client.target("http://localhost:8585/provone-provenance/workflow/bpelworkflow");
        final Response response = target.request().post(Entity.entity(multipart, multipart.getMediaType()));

        //Use response object to verify upload success

        formDataMultiPart.close();
        multipart.close();
*/
    }

}