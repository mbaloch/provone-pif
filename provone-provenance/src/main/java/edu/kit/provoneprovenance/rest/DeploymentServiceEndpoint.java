package edu.kit.provoneprovenance.rest;

import edu.kit.provoneprovenance.helper.DeploymentHelper;
import edu.kit.provoneprovenance.helper.IDeploymentService;
import edu.kit.provoneprovenance.model.BPELdeployment;
import edu.kit.provoneprovenance.model.PackageType;
import edu.kit.provoneprovenance.repository.BPELdeploymentRepository;
import org.apache.commons.io.FilenameUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Path("deploy")
public class DeploymentServiceEndpoint implements IDeploymentService {
    @Context
    private Configuration configuration;
    @Inject
    DeploymentHelper deploymentHelper;

    @Override
    public Response downloadZippedFile() {

        return Response.ok("got it").build();
    }

    @Override
    public Response deployBPELZippedPackage(MultipartFormDataInput multipartFormDataInput) {
        // local variables
        MultivaluedMap<String, String> multivaluedMap = null;
        String fileName = null;
        InputStream inputStream = null;
        String uploadFilePath = null;

        try {
            Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
            List<InputPart> lstInputPart = map.get("workflowPackage");

            for (InputPart inputPart : lstInputPart) {

                // get filename to be uploaded
                multivaluedMap = inputPart.getHeaders();
                fileName = deploymentHelper.getFileName(multivaluedMap);

                if (null != fileName && !"".equalsIgnoreCase(fileName)) {

                    // write & upload file to UPLOAD_FILE_SERVER
                    inputStream = inputPart.getBody(InputStream.class, null);
                    uploadFilePath = deploymentHelper.writeToFileServer(inputStream, fileName, configuration);

                    // close the stream
                    inputStream.close();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            // release resources, if any
        }
        return Response.ok("File uploaded successfully at " + uploadFilePath).build();
    }

    @Override
    public Response deploySCUFLPackage(MultipartFormDataInput multipartFormDataInput) {
        return null;
    }

}
