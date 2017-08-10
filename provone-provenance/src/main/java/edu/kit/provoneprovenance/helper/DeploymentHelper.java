package edu.kit.provoneprovenance.helper;

import edu.kit.provoneprovenance.model.BPELdeployment;
import edu.kit.provoneprovenance.model.PackageType;
import edu.kit.provoneprovenance.repository.BPELdeploymentRepository;
import org.apache.commons.io.FilenameUtils;

import javax.inject.Inject;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MultivaluedMap;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeploymentHelper {
    @Inject
    BPELdeploymentRepository bpeLdeploymentRepository;

    public String writeToFileServer(InputStream inputStream, String fileName, Configuration configuration) throws IOException {

        OutputStream outputStream = null;
        String workflowDir = (String) configuration.getProperty("workflow-dir");
        String qualifiedUploadFilePath = workflowDir + "/" + fileName;
        String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
        java.nio.file.Path outputDir = Paths.get(workflowDir + "/" + fileNameWithOutExt);

        try {
            outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            //release resource, if any
            outputStream.close();
        }
        unzip(Paths.get(qualifiedUploadFilePath), outputDir);
        createDeploymentEntry(fileNameWithOutExt, outputDir.toString());
        return qualifiedUploadFilePath;
    }

    public Long createDeploymentEntry(String packageName, String deploymentDir) {
        boolean alreadDeployed = bpeLdeploymentRepository.findAll()
                .stream()
                .anyMatch(bpeLdeployment -> bpeLdeployment.getPackageName().equals(packageName));
        if (!alreadDeployed) {
            BPELdeployment bpeLdeployment = new BPELdeployment(packageName, new Date(), deploymentDir, PackageType.BPEL);
            BPELdeployment bpeLdeployment1 = bpeLdeploymentRepository.create(bpeLdeployment);
            return bpeLdeployment1.getId();
        } else {
            return Long.parseLong("-1");
        }

    }

    public String getFileName(MultivaluedMap<String, String> multivaluedMap) {

        String[] contentDisposition = multivaluedMap.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {

            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String exactFileName = name[1].trim().replaceAll("\"", "");
                return exactFileName;
            }
        }
        return "UnknownFile";
    }

    public void unzip(java.nio.file.Path zipFile, java.nio.file.Path outputPath) {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {

            ZipEntry entry = zis.getNextEntry();

            while (entry != null) {

                java.nio.file.Path newFilePath = outputPath.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(newFilePath);
                } else {
                    if (!Files.exists(newFilePath.getParent())) {
                        Files.createDirectories(newFilePath.getParent());
                    }
                    try (OutputStream bos = Files.newOutputStream(outputPath.resolve(newFilePath))) {
                        byte[] buffer = new byte[Math.toIntExact(entry.getSize())];

                        int location;

                        while ((location = zis.read(buffer)) != -1) {
                            bos.write(buffer, 0, location);
                        }
                    }
                }
                entry = zis.getNextEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //handle your exception
        }
    }
}
