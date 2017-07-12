package edu.kit.provone.prospective.bpel.utils;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;

/**
 * Created by mukhtar on 02.02.17.
 */
public class RDFUtility {

    final String provone = "http://purl.org/provone#";
    final String prov = "http://www.w3.org/ns/prov#";
    final String ns = "http://kit.edu/pp/";
    final String wfms = "http://www.wfms.org/registry.xsd#";
  //  String directory = "/home/mukhtar/apache/RDFStore/MyDatabases/DB1" ;
    //final Dataset ds = TDBFactory.createDataset(directory) ;
    final Model model = ModelFactory.createDefaultModel();

    public RDFUtility() {
        model.setNsPrefix("provone", provone);
        model.setNsPrefix("prov", prov);
        model.setNsPrefix("example", ns);
        model.setNsPrefix("wfms", wfms);
    }

    public Resource createProcess(String processId, String processTitle) {

        Resource process = model.createResource(ns + "process_" + processId);

        //short uri   Resource process=model.createResource("http://"+processId);
        process.addProperty(RDF.type, model.createResource(provone + "Process"));
        process.addProperty(DCTerms.identifier, processId);
        process.addProperty(DCTerms.title, processTitle);
        Property property = model.createProperty(wfms + "package");
      //  Property operationName=model.createProperty()
        // process.addProperty(property,"gov.llnl.uvcdat.cdms");

        //    model.write(System.out);
        // model.write(System.out,"Turtle");
        //       Property subProcessProperty=model.createProperty(provone+"hasSubProcess");
        //       model.add(workflow,subProcessProperty,process);
        return process;
    }

    public Resource createInputPort(String inputPortId, String variableName, String variableType) {

        Resource inputport = model.createResource(ns + "inputport_" + inputPortId);
        inputport.addProperty(RDF.type, model.createResource(provone + "InputPort"));
        inputport.addProperty(DCTerms.identifier, inputPortId);
        inputport.addProperty(DCTerms.title, variableName);
        Property property = model.createProperty(wfms + "signature");
        inputport.addProperty(property, variableType);
//        scopeVariables.forEach((variable,type)->{
//            Property property=model.createProperty(wfms+"signature");
//            inputport.addProperty(property,variable+":"+type);
//        });

        //   model.write(System.out);
        // model.write(System.out,"Turtle");
        return inputport;

    }

    public Resource createOutputPort(String outputPortId, String variableName, String variableType) {

        Resource outputPort = model.createResource(ns + "outputport_" + outputPortId);
        outputPort.addProperty(RDF.type, model.createResource(provone + "OutputPort"));
        outputPort.addProperty(DCTerms.identifier, outputPortId);
        outputPort.addProperty(DCTerms.title, variableName);
        Property property = model.createProperty(wfms + "signature");
        outputPort.addProperty(property, variableType);

//        scopeVariables.forEach((variable,type)->{
//            Property property=model.createProperty(wfms+"signature");
//            outputPort.addProperty(property,variable+":"+type);
//        });


        //   model.write(System.out);
        //model.write(System.out,"Turtle");
        return outputPort;
    }

    public Resource createDataLink(String dataLinkId) {

        Resource dataLink = model.createResource(ns + "DL_" + dataLinkId);
        dataLink.addProperty(RDF.type, model.createResource(provone + "DataLink"));
        dataLink.addProperty(DCTerms.identifier, dataLinkId);

        //model.write(System.out,"Turtle");
        return dataLink;
    }

    public Resource createSeqCtrlLink(Resource process1, Resource process2, String sequenceCLID) {
        String p1Id = process1.getProperty(DCTerms.identifier).getString();
        String p2Id = process2.getProperty(DCTerms.identifier).getString();
        //  UUID uuid=UUID.randomUUID();
        // Resource seqCtrlLink=model.createResource(ns+p1Id+"_"+p2Id+"CL");
        Resource seqCtrlLink = model.createResource(ns + "SCL_" + sequenceCLID);
        //short uri   Resource seqCtrlLink=model.createResource("http://"+p1Id+"_"+p2Id+"CL");
        seqCtrlLink.addProperty(RDF.type, model.createResource(provone + "SeqCtrlLink"));
        seqCtrlLink.addProperty(DCTerms.identifier, sequenceCLID);
        return seqCtrlLink;
    }

    public Resource createWorkflow(String workflowId, String workflowTitle) {

        Resource workflow = model.createResource(ns + workflowId);
        workflow.addProperty(RDF.type, model.createResource(provone + "Workflow"));
        workflow.addProperty(DCTerms.identifier, workflowId);
        workflow.addProperty(DCTerms.title, workflowTitle);
        //   model.write(System.out);
        //model.write(System.out,"Turtle");
        return workflow;
    }

    public Resource createUser() {

        Resource user = model.createResource(ns + "u1");
        user.addProperty(RDF.type, model.createResource(provone + "User"));
        user.addProperty(DCTerms.identifier, "John");
        //   model.write(System.out);
        // model.write(System.out,"Turtle");
        return user;
    }

    public Property hasSubProcess(Resource top_process, Resource sub_process) {

        Property property = model.createProperty(provone + "hasSubProcess");
        top_process.addProperty(property, sub_process);
        return property;
        //model.add(top_process,property,sub_process);
        // model.write(System.out);

    }

    public Property sourcePToCL(Resource source_process, Resource controlLink) {

        Property property = model.createProperty(provone + "sourcePToCL");
        source_process.addProperty(property, controlLink);
        return property;
        //model.add(source_process,property,controlLink);

    }

    public Property CLtoDestP(Resource dest_process, Resource controlLink) {

        Property property = model.createProperty(provone + "CLToDestP");
        controlLink.addProperty(property, dest_process);
        return property;
        // model.add(controlLink,property,dest_process);

    }

    public Property hasInPort(Resource process, Resource inport) {

        Property property = model.createProperty(provone + "hasInPort");
        process.addProperty(property, inport);
        return property;
        //model.add(process,property,inport);

    }

    public Property hasOutPort(Resource process, Resource outport) {

        Property property = model.createProperty(provone + "hasOutPort");
        process.addProperty(property, outport);
        return property;

        //model.add(process,property,outport);

    }

    public Property hasDefaultParam(Resource inputport, Resource data) {

        Property property = model.createProperty(provone + "hasDefaultParam");
        inputport.addProperty(property, data);
        return property;

    }

    public Property DLToInPort(Resource datalink, Resource inputport) {

        Property property = model.createProperty(provone + "DLToInPort");
        datalink.addProperty(property, inputport);
        return property;

        // model.add(datalink,property,inputport);

    }

    public Property outPortToDL(Resource outputport, Resource datalink) {

        Property property = model.createProperty(provone + "outPortToDL");
        outputport.addProperty(property, datalink);
        return property;
        //model.add(outputport,property,datalink);

    }

    public Property inPortToDL(Resource inputport, Resource datalink) {

        Property property = model.createProperty(provone + "inPortToDL");
        inputport.addProperty(property, datalink);


        //model.add(outputport,property,datalink);
        return property;

    }

    public Property DLToOutPort(Resource datalink, Resource outputport) {

        Property property = model.createProperty(provone + "DLToOutPort");
        datalink.addProperty(property, outputport);
        return property;

        //   model.add(outputport,property,datalink);

    }

    public Property wasAttributedTo(Resource process, Resource user) {

        Property property = model.createProperty(prov + "wasAttributedTo");
        process.addProperty(property, user);
        return property;
        //model.add(process,property,user);

    }

    public Property wasDerivedFrom(Resource from_process_workflow, Resource to_process_workflow) {

        Property property = model.createProperty(prov + "wasDerivedFrom");
        from_process_workflow.addProperty(property, to_process_workflow);
        return property;

//        model.add(from_process_workflow,property,to_process_workflow);

    }


    public Model getModel() {
        return this.model;
    }
   // public Dataset getDataset(){return this.ds;}


}
