package edu.kit.provoneprovenance.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("provenance")
public class ProvenanceEndpoint {
    @GET
    public Response generateProspectiveProvenance(Long packageId) {

    }
}
