package edu.kit.provoneprovenance.rest;

import edu.kit.provone.queries.SparqlQueries;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;

import javax.inject.Inject;
import javax.ws.rs.*;
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
    @Path("{id}/prospective")
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
    @Path("{id}/retrospective")
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

    @GET
    @Produces("application/xml")
    @Path("{id}/retrospective/filter")
    public Response getAllFailedRetrospectiveProvenance(
            @PathParam("id") String provenanceId,
            @QueryParam("status") String status) {
        final String SPARQL_DATASET_NAME = (String) configuration.getProperty("SPARQL_DATASET_NAME");
        final String SPARQL_ENDPOINTURL = (String) configuration.getProperty("SPARQL_ENDPOINTURL");
        final String SPARQL_DATASET_ENDPOINTURL = SPARQL_ENDPOINTURL + "/" + SPARQL_DATASET_NAME;
        String graphUri = "http://kit.edu/bpel/" + provenanceId;
        boolean statusValue = false;
        if (status.equalsIgnoreCase("fail")) {
            statusValue = false;
        } else if (status.equalsIgnoreCase("success")) {
            statusValue = true;
        }
        ResultSet outputPortsOfProcessHavingValue = SparqlQueries.getRetrospectiveByStatus(statusValue, graphUri, SPARQL_DATASET_ENDPOINTURL);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsXML(byteArrayOutputStream, outputPortsOfProcessHavingValue);
        return Response.ok(byteArrayOutputStream.toString()).build();
    }

    @GET
    @Produces("application/xml")
    @Path("{id}/allports")
    public Response getAllInputAndOutputPorts(@PathParam("id") String provenanceId) {
        final String SPARQL_DATASET_NAME = (String) configuration.getProperty("SPARQL_DATASET_NAME");
        final String SPARQL_ENDPOINTURL = (String) configuration.getProperty("SPARQL_ENDPOINTURL");
        final String SPARQL_DATASET_ENDPOINTURL = SPARQL_ENDPOINTURL + "/" + SPARQL_DATASET_NAME;
        String graphUri = "http://kit.edu/bpel/" + provenanceId;
        Model prospectiveModel = SparqlQueries.getInputAndOutputPorts(graphUri, SPARQL_DATASET_ENDPOINTURL);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        prospectiveModel.write(byteArrayOutputStream);
        return Response.ok(byteArrayOutputStream.toString()).build();
    }

    @GET
    @Produces("application/xml")
    @Path("{id}/{process}/allports")
    public Response getAllInputAndOutputPortsOfGivenProcess(@PathParam("id") String provenanceId, @PathParam("process") String processName) {
        final String SPARQL_DATASET_NAME = (String) configuration.getProperty("SPARQL_DATASET_NAME");
        final String SPARQL_ENDPOINTURL = (String) configuration.getProperty("SPARQL_ENDPOINTURL");
        final String SPARQL_DATASET_ENDPOINTURL = SPARQL_ENDPOINTURL + "/" + SPARQL_DATASET_NAME;
        String graphUri = "http://kit.edu/bpel/" + provenanceId;
        Model prospectiveModel = SparqlQueries.getInputAndOutputPortsOfGivenProcess(processName, graphUri, SPARQL_DATASET_ENDPOINTURL);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        prospectiveModel.write(byteArrayOutputStream);
        return Response.ok(byteArrayOutputStream.toString()).build();
    }

    @GET
    @Produces("application/xml")
    @Path("{id}/outputports")
    public Response getOutputPortsOfProcessHavingValue(
            @PathParam("id") String provenanceId,
            @QueryParam("value") String value) {
        final String SPARQL_DATASET_NAME = (String) configuration.getProperty("SPARQL_DATASET_NAME");
        final String SPARQL_ENDPOINTURL = (String) configuration.getProperty("SPARQL_ENDPOINTURL");
        final String SPARQL_DATASET_ENDPOINTURL = SPARQL_ENDPOINTURL + "/" + SPARQL_DATASET_NAME;
        String graphUri = "http://kit.edu/bpel/" + provenanceId;
        ResultSet outputPortsOfProcessHavingValue = SparqlQueries.getOutputPortsOfProcessHavingValue(value, graphUri, SPARQL_DATASET_ENDPOINTURL);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsXML(byteArrayOutputStream, outputPortsOfProcessHavingValue);
        return Response.ok(byteArrayOutputStream.toString()).build();
    }
}
