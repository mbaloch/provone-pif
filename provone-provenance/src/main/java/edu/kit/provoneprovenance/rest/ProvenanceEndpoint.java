package edu.kit.provoneprovenance.rest;

import edu.kit.provone.queries.SparqlQueries;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api("provenance")
public class ProvenanceEndpoint {
    @Context
    private Configuration configuration;
    //@Inject
    // private SparqlQueries sparqlQueries=new SparqlQueries();

    @GET
    @Produces("application/xml")
    @Path("{id}/prospective")
    @ApiOperation(value = "given a workflow id, returns the prospective provenance of the workflow")
    @ApiResponses({
            @ApiResponse(code = 200, message = "prospective provenance found for the given workflow"),
            @ApiResponse(code = 404, message = "provenance for the given workflow id does not exists")
    })
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
    @ApiOperation(value = "given a workflow id, returns the retrospective provenance of the workflow")
    @ApiResponses({
            @ApiResponse(code = 200, message = "retrospective provenance found for the given workflow"),
            @ApiResponse(code = 404, message = "retrospective provenance for the given workflow id does not exists")
    })
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
    @ApiOperation(value = "Returns the filtered list of retrospective provenance given a workflow id and filter criteria of either \"fail\" or \"succes\"")
    @ApiResponses({
            @ApiResponse(code = 200, message = "retrospective provenance found for the given workflow"),
            @ApiResponse(code = 404, message = "retrospective provenance for the given workflow id does not exists")
    })
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
    @ApiOperation(value = "given a workflow id, Returns the list of all \"Input Ports\" and \"Output Ports\" ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "provenance found for the given workflow id"),
            @ApiResponse(code = 404, message = "provenance for the given workflow id does not exists")
    })
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
    @ApiOperation(value = "given a workflow id and a process name, Returns the list of all \"Input Ports\" and \"Output Ports\" of the given process")
    @ApiResponses({
            @ApiResponse(code = 200, message = "provenance found for the given workflow id"),
            @ApiResponse(code = 404, message = "provenance for the given workflow id does not exists")
    })
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
    @ApiOperation(value = "given a workflow id, a process name and a value, Returns the list of the  \"Output Ports\"  having the value")
    @ApiResponses({
            @ApiResponse(code = 200, message = "provenance found for the given workflow id"),
            @ApiResponse(code = 404, message = "provenance for the given workflow id does not exists")
    })
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
