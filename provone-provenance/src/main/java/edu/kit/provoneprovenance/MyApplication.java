package edu.kit.provoneprovenance;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.Map;

/**
 * Created by mukhtar on 29.06.17.
 */
@ApplicationPath("webapi")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        //packages("org.foo.rest;org.bar.rest");
        packages("edu.kit.provoneprovenance");
        packages("org.glassfish.jersey.examples.multipart");
        register(MultiPartFeature.class);
    }
}