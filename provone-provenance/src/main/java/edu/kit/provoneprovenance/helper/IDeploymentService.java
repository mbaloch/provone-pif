package edu.kit.provoneprovenance.helper;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface IDeploymentService {

    @GET
    @Path("/download/zip")
    @Produces("application/zip")
    Response downloadZippedFile();


    @POST
    @Path("/bpel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response deployBPELZippedPackage(MultipartFormDataInput multipartFormDataInput);

    @POST
    @Path("/scufl")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response deploySCUFLPackage(MultipartFormDataInput multipartFormDataInput);
}
