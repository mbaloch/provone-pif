package edu.kit.provone.provone2rdfmapper;


import kit.edu.mainapp.TavernaRetrospective2ProvONE;
import kit.edu.xpathmapper.RetrospectiveXpathMapper;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.DCTerms;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukhtar on 12.07.17.
 */
public class MapperRunner {

    public static void main(String args[]) {
        final String wfprov = "http://purl.org/wf4ever/wfprov#";
        final String provone = "http://purl.org/provone#";
        final String type = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resource = classLoader.getResource("halfHello_abcd.ttl");
        String ttlFilePath = resource.getFile();
        TavernaRetrospective2ProvONE scuflRetros2ProvONE = new TavernaRetrospective2ProvONE();
        List<Resource> processExeList = new ArrayList<Resource>();
        List<Resource> dataList = new ArrayList<Resource>();
        StmtIterator iter = null;
        Resource processExecResource = null;
        SCUFLRetroToProspAttachment prospAttachment = null;
        Model retrospectiveRDFModel = null;
        Resource workflowResource = null;

        boolean found = false;
        try {
            retrospectiveRDFModel = scuflRetros2ProvONE.getRetrospectiveRDFModel(ttlFilePath);
            prospAttachment = new SCUFLRetroToProspAttachment(retrospectiveRDFModel);
            iter = retrospectiveRDFModel.listStatements();
            while (iter.hasNext()) {
                Statement stmt = iter.next();
                Resource s = stmt.getSubject();
                Resource p = stmt.getPredicate();
                RDFNode o = stmt.getObject();
                Property typeProperty = retrospectiveRDFModel.createProperty(type);
                Property processExec = retrospectiveRDFModel.createProperty(provone + "ProcessExec");

                Property typePropertyData = retrospectiveRDFModel.createProperty(type);
                Property propertyData = retrospectiveRDFModel.createProperty(provone + "Data");

                Statement typeStatement = s.getProperty(typeProperty);
                Statement typeStatementData = s.getProperty(typePropertyData);

                if (typeStatement != null && processExec.toString().equals(o.toString())) {
                    processExeList.add(s);
                } else if (typeStatementData != null && propertyData.toString().equals(o.toString())) {
                    dataList.add(s);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (iter != null) iter.close();
        }
        for (Resource processExeResource : processExeList) {

            Property describedByWorkflowProperty = retrospectiveRDFModel.createProperty(wfprov + "describedByWorkflow");
            Statement property = processExeResource.getProperty(describedByWorkflowProperty);

            Property titleProperty = retrospectiveRDFModel.getProperty(DCTerms.title.toString());
            Statement titleStatement = processExeResource.getProperty(titleProperty);

            if (property != null) {
                String workflowId = property.getObject().toString();
                String processTitle = titleStatement.getObject().toString();
                System.out.println("workflowTitle:" + processTitle);
                prospAttachment.createWorkflowExecInstance(processExeResource, workflowId);
                workflowResource = processExeResource;
            } else {
                String processTitle = titleStatement.getObject().toString();
                System.out.println("Title Nromal:" + processTitle);
                prospAttachment.createProcessExecInstance(processExeResource, processTitle);

            }
        }

        for (Resource dataResource : dataList) {
            System.out.println("Resource:" + dataResource);

            Property titleProperty = retrospectiveRDFModel.getProperty(DCTerms.title.toString());
            Statement titleStatement = dataResource.getProperty(titleProperty);
            if (titleStatement != null) {
                String dataTitle = titleStatement.getObject().toString();
                System.out.println("Title Nromal:" + dataTitle);
                prospAttachment.createDataOnLink(dataResource, dataTitle);
            }


        }
        prospAttachment.updateJena(workflowResource);

    }
}
