package edu.kit.provoneprovenance.rest;

import edu.kit.provone.queries.SparqlQueries;
import org.apache.jena.rdf.model.Model;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;

@Path("provenance")
public class ProvenanceEndpoint {
    @Context
    private Configuration configuration;
    //@Inject
    // private SparqlQueries sparqlQueries=new SparqlQueries();

    @GET
    @Produces("application/xml")
    @Path("prospectiveprovenance/{id}")
    public Response getProspectiveProvenance(@PathParam("id") String provenanceId) {
        final String SPARQL_DATASET_NAME = (String) configuration.getProperty("SPARQL_DATASET_NAME");
        final String SPARQL_ENDPOINTURL = (String) configuration.getProperty("SPARQL_ENDPOINTURL");
        final String SPARQL_DATASET_ENDPOINTURL = SPARQL_ENDPOINTURL + "/" + SPARQL_DATASET_NAME;
        String graphUri = "http://kit.edu/bpel/" + provenanceId;
        Model prospectiveModel = SparqlQueries.getProspective(graphUri, SPARQL_DATASET_ENDPOINTURL);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        prospectiveModel.write(byteArrayOutputStream);
        return Response.ok(byteArrayOutputStream.toString()).build();
    }

    @GET
    @Produces("application/xml")
    @Path("retrospectiveprovenance/{id}")
    public Response getRetrospectiveProvenance(@PathParam("id") String provenanceId) {
        final String SPARQL_DATASET_NAME = (String) configuration.getProperty("SPARQL_DATASET_NAME");
        final String SPARQL_ENDPOINTURL = (String) configuration.getProperty("SPARQL_ENDPOINTURL");
        final String SPARQL_DATASET_ENDPOINTURL = SPARQL_ENDPOINTURL + "/" + SPARQL_DATASET_NAME;
        String graphUri = "http://kit.edu/bpel/" + provenanceId;
        Model prospectiveModel = SparqlQueries.getRetrospective(graphUri, SPARQL_DATASET_ENDPOINTURL);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        prospectiveModel.write(byteArrayOutputStream);
        return Response.ok(byteArrayOutputStream.toString()).build();
    }
}
