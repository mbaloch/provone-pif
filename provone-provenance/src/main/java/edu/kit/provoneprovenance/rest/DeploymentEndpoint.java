package edu.kit.provoneprovenance.rest;

import edu.kit.provoneprovenance.JAXRSConfiguration;
import edu.kit.provoneprovenance.model.BPELdeployment;
import edu.kit.provoneprovenance.repository.BPELdeploymentRepository;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("workflows")
public class DeploymentEndpoint {

    @Inject
    BPELdeploymentRepository bpeLdeploymentRepository;

    @Context
    private Configuration configuration;

    public BPELdeployment getDeployment(Long id) {
        return bpeLdeploymentRepository.find(id);
    }

    public BPELdeployment createDeployment(BPELdeployment bpeLdeployment) {
        return bpeLdeploymentRepository.create(bpeLdeployment);
    }

    public void deleteDeployment(Long id) {
        bpeLdeploymentRepository.delete(id);
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getDeployments() {
        List<BPELdeployment> bpeLdeployments = bpeLdeploymentRepository.findAll();
        return Response.ok(bpeLdeployments).build();
    }

    public Long countDeployments() {
        return bpeLdeploymentRepository.countAll();
    }


}
