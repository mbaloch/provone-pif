package edu.kit.provone.provone2rdfmapper;

import edu.kit.provone.provone2rdfmapper.utility.RDFUtility;
import edu.kit.scufl.core.RDF;
import edu.kit.scufl.mainapp.SCUFLRetro2ProvONE;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.DCTerms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mukhtar on 12.07.17.
 */
public class MapperRunner {

    public static void main(String args[]){
        final String wfprov = "http://purl.org/wf4ever/wfprov#";
        final String provone = "http://purl.org/provone#";
        final String type = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";

        SCUFLRetro2ProvONE scuflRetro2ProvONE = new SCUFLRetro2ProvONE();
        List<Resource> processExeList = new ArrayList<Resource>();
        StmtIterator iter= null;
        Resource processExecResource=null;
        SCUFLRetroToProspAttachment prospAttachment=null;
        Model retrospectiveRDFModel = null;
        Resource workflowResource = null;

        boolean found=false;
        try {
            retrospectiveRDFModel =
                    scuflRetro2ProvONE.getRetrospectiveRDFModel
                            ("/home/mukhtar/IdeaProjects/provone-pif/provone-provenance/src/main/resources/workflowrun.prov.ttl");
            prospAttachment=new SCUFLRetroToProspAttachment(retrospectiveRDFModel);
            iter= retrospectiveRDFModel.listStatements();
                while ( iter.hasNext() ) {
                    Statement stmt = iter.next();
                    Resource s = stmt.getSubject();
                    Resource p = stmt.getPredicate();
                    RDFNode o = stmt.getObject();
//                    Property describedByWorkflowProperty = retrospectiveRDFModel.createProperty(wfprov + "describedByWorkflow");
//                    Statement property = s.getProperty(describedByWorkflowProperty);

                    Property typeProperty = retrospectiveRDFModel.createProperty(type);
                    Property processExec = retrospectiveRDFModel.createProperty(provone + "ProcessExec");
                    Statement typeStatement = s.getProperty(typeProperty);
//                    System.out.println("TypeStatement:"+typeStatement+"  typeproperty:"+typeProperty);
                    if(typeStatement !=null && processExec.toString().equals(o.toString()) ) {
//                         workflowId = property.getObject().toString();
                        processExecResource=s;
//                        System.out.println("subject:"+s);
//                        System.out.println("prediate:"+p);
//                        System.out.println("object:"+o);
//                        System.out.println("found at"+workflowId);
                        processExeList.add(s);
//                        found=true;
                    }
                }



    } catch (Exception e) {
            e.printStackTrace();
        }
     finally {
        if ( iter != null ) iter.close();
//         if(found)   prospAttachment.createWorkflowExecInstance(processExecResource,workflowId);
    }
        for (Resource processExeResource: processExeList) {

            Property describedByWorkflowProperty = retrospectiveRDFModel.createProperty(wfprov + "describedByWorkflow");
            Statement property = processExeResource.getProperty(describedByWorkflowProperty);

            Property titleProperty = retrospectiveRDFModel.getProperty(DCTerms.title.toString());
            Statement titleStatement = processExeResource.getProperty(titleProperty);

            if(property != null){
                String workflowId = property.getObject().toString();
                String processTitle = titleStatement.getObject().toString();
                System.out.println("workflowTitle:"+processTitle);
                prospAttachment.createWorkflowExecInstance(processExeResource,workflowId);
                workflowResource = processExeResource;
            }else{
                String processTitle = titleStatement.getObject().toString();
                System.out.println("Title Nromal:"+processTitle);
                prospAttachment.createProcessExecInstance(processExeResource, processTitle);

            }
        }
//        System.out.println(processExeList.size());
//        for (Resource processExeResource: processExeList) {
//            Property titleProperty = retrospectiveRDFModel.getProperty(DCTerms.title.toString());
//            Statement titleStatement = processExeResource.getProperty(titleProperty);
//            String processTitle = titleStatement.getObject().toString();
//            System.out.println("Titles:"+processTitle);
//        }
//        retrospectiveRDFModel.write(System.out);

        prospAttachment.updateJena(workflowResource);

    }
}
