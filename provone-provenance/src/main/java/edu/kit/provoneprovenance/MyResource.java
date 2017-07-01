package edu.kit.provoneprovenance;

import edu.kit.provoneprovenance.helper.BPELdeployment;
import edu.kit.scufl.mainapp.MainAppRunner;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.kit.mainapp.ScuflToProv;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Date;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("workflow")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @Path("scufl")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        ScuflToProv toProv = new ScuflToProv();
//		toProv.parseCreateGraph("files/Example_workflow_for_REST_and_XPath_activities-v1.t2flow");
        toProv.parseCreateGraph("/home/mukhtar/Desktop/final3/provonepif/provone-provenance/src/main/resources/Hello_abcd-v1.t2flow");
        return "Got it!";
    }
    @Path("scuflRetro")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String scuflRetro() {
        String ttlFilePath="/home/mukhtar/Desktop/final3/provonepif/provone-provenance/src/main/resources/workflowrun.pov.ttl";
        MainAppRunner appRunner=new MainAppRunner();
        appRunner.appRunner(ttlFilePath);
        return "Got the retrospective !";
    }

    @POST
    @Path("bpelworkflow")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadPdfFile(@FormDataParam("file") InputStream fileInputStream,
                                  @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception
    {
        String deploymentRootPath = "/home/mukhtar/Desktop/final3/workflows/bpelworkflows";
        String fileName = null;
        String uploadFilePath = null;

        try {
            fileName = fileMetaData.getFileName();
            uploadFilePath = writeToFileServer(fileInputStream, fileName,deploymentRootPath);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }

        BPELdeployment bpeldeploymentdesc=new BPELdeployment();
        Date currentDateTime=new Date();
        bpeldeploymentdesc.setDeploymentDate(currentDateTime.toString());
        bpeldeploymentdesc.setPackageName(fileMetaData.getName());
        bpeldeploymentdesc.setGetDeploymentpath(uploadFilePath);

        return Response.ok("File uploaded successfully at " + uploadFilePath).build();

    }
    private String writeToFileServer(InputStream inputStream, String fileName, String deplyometRootPath) throws IOException {

        OutputStream outputStream = null;
        String qualifiedUploadFilePath = deplyometRootPath + fileName;

        try {
            outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally{
            //release resource, if any
            outputStream.close();
        }
        return qualifiedUploadFilePath;
    }

}



