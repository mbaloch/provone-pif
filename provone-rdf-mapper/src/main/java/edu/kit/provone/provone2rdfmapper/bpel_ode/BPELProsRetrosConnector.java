package edu.kit.provone.provone2rdfmapper.bpel_ode;

import edu.kit.provone.provone2rdfmapper.SCUFLRetroToProspAttachment;
import edu.kit.provone.provone2rdfmapper.bpelprospective.BPELProspectiveFactory;
import edu.kit.provone.retrospective.ode.Ode2ProvONE;
import edu.kit.provone.retrospective.ode.model.Data;
import kit.edu.mainapp.TavernaRetrospective2ProvONE;
import org.apache.axis2.AxisFault;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.ode.bpel.pmapi.TEventInfo;
import org.apache.xmlbeans.XmlException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mukhtar on 18.07.17.
 */
public class BPELProsRetrosConnector {


    public static void main(String[] args) throws Exception {
        BPELProspectiveFactory bpelProspectiveFactory = new BPELProspectiveFactory();
        String prospectiveGraphID = bpelProspectiveFactory.generateProspective();
        Ode2ProvONE ode2ProvONE = new Ode2ProvONE();
        try {
            List<TEventInfo> eventsByInstanceId = ode2ProvONE.getEventsByInstanceId("400");
            // eventsByInstanceId.forEach(System.out::println);
            Model retrospectiveRDFModel = ode2ProvONE.generateRetrospective(eventsByInstanceId);

            retrospectiveRDFModel.write(System.out);
            Map<String, List<Data>> dataList = ode2ProvONE.getCommunicationbyInstanceId("400");
            //dataList.stream().forEach(System.out::println);
//            System.out.println("testing");
            attachProspectiveAndRetrospective(retrospectiveRDFModel, prospectiveGraphID);

        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }

    }

    Model attachDataToModel(Model retroModel, List<Data> inDataList, List<Data> outDataList) {

        return null;
    }

    static void attachProspectiveAndRetrospective(Model retrospective, String prospecitveGraphId) {
        final String wfprov = "http://purl.org/wf4ever/wfprov#";
        final String provone = "http://purl.org/provone#";
        final String type = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";

        List<Resource> processExeList = new ArrayList<Resource>();
        List<Resource> dataList = new ArrayList<Resource>();
        StmtIterator iter = null;
        Resource processExecResource = null;
        Model retrospectiveRDFModel = retrospective;
        Resource workflowResource = null;
        BPELRetroToProspAttachment prospAttachment = new BPELRetroToProspAttachment(retrospectiveRDFModel, prospecitveGraphId);
        try {
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

//            Property describedByWorkflowProperty = retrospectiveRDFModel.createProperty(wfprov + "describedByWorkflow");
//            Statement property = processExeResource.getProperty(describedByWorkflowProperty);

            Property titleProperty = retrospectiveRDFModel.getProperty(DCTerms.title.toString());
            Statement titleStatement = processExeResource.getProperty(titleProperty);
//
//            if (property != null) {
//                String workflowId = property.getObject().toString();
//                String processTitle = titleStatement.getObject().toString();
//                System.out.println("workflowTitle:" + processTitle);
//                prospAttachment.createWorkflowExecInstance(processExeResource, workflowId);
//                workflowResource = processExeResource;
//            } else {
            String processTitle = titleStatement.getObject().toString();
            System.out.println("Title Nromal:" + processTitle);
            if (processTitle.matches("^.+?\\d$")) {
                processTitle = processTitle.substring(0, processTitle.length() - 2);
            }
            prospAttachment.createProcessExecInstance(processExeResource, processTitle);

//            }
        }

//        for (Resource dataResource : dataList) {
//            System.out.println("Resource:" + dataResource);
//
//            Property titleProperty = retrospectiveRDFModel.getProperty(DCTerms.title.toString());
//            Statement titleStatement = dataResource.getProperty(titleProperty);
//            if (titleStatement != null) {
//                String dataTitle = titleStatement.getObject().toString();
//                System.out.println("Title Nromal:" + dataTitle);
//                prospAttachment.createDataOnLink(dataResource, dataTitle);
//            }
//
//
//        }
        prospAttachment.updateJena(retrospectiveRDFModel);


    }

}

