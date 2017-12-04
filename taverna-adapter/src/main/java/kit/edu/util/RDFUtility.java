package kit.edu.util;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 * Created by mukhtar on 01.03.17.
 */
public class RDFUtility {
    final String provone = "http://purl.org/provone#";
    final String prov = "http://www.w3.org/ns/prov#";
    final String nsr = "http://kit.edu/rp/";
    final String nsp = "http://kit.edu/pp/";
    final String wfms = "http://www.wfms.org/registry.xsd#";
    final String wfprov = "http://purl.org/wf4ever/wfprov#";
    final Model model = ModelFactory.createDefaultModel();
    public RDFUtility() {
        model.setNsPrefix("provone", provone);
        model.setNsPrefix("prov", prov);
        model.setNsPrefix("kitnsr", nsr);
        model.setNsPrefix("kitnsp", nsp);
        model.setNsPrefix("wfms", wfms);
    }
//Methods for retrospective provenance

    public Resource createProcessExec(String processId, String processTitle,String activityId, String startTime, String endTime, String completed, String wrkflwID) {
        Resource processExecResource = model.createResource(nsr + "processExec_" + processId);
        //short uri   Resource process=model.createResource("http://"+processId);
        processExecResource.addProperty(RDF.type, model.createResource(provone + "ProcessExec"));
        processExecResource.addProperty(DCTerms.identifier, processId);
        processExecResource.addProperty(DCTerms.title, processTitle);

        Property startTimeProperty = model.createProperty(prov + "startTime");
        if(endTime !=null){
            Property endTimeProperty = model.createProperty(prov + "endTime");
            processExecResource.addProperty(endTimeProperty, endTime);
        }
        Property activityIdProperty=model.createProperty(wfms+"activityId");
        processExecResource.addProperty(activityIdProperty,activityId);

        processExecResource.addProperty(startTimeProperty, startTime);
        //   process.addProperty(prov, processTitle);
        Property completedProperty = model.createProperty(wfms + "completed");
        processExecResource.addProperty(completedProperty, completed);
        if(null != wrkflwID){
        Property describedByWorkflowProperty = model.createProperty(wfprov + "describedByWorkflow");
        processExecResource.addProperty(describedByWorkflowProperty, wrkflwID);
        }

        return processExecResource;
    }


    public Resource createData(String dataId, String label, String type, String value) {
        Resource dataResource = model.createResource(nsr + "Data_" + dataId);
        dataResource.addProperty(RDF.type, model.createResource(provone + "Data"));
        dataResource.addProperty(RDFS.label, label);
        Property typeProperty = model.createProperty(wfms + "type");
        dataResource.addProperty(typeProperty, type);
        return dataResource;

    }

    public Resource createCollection(String collectionId) {
        Resource collectionResource = model.createResource(nsr + "collection_" + collectionId);
        collectionResource.addProperty(DCTerms.identifier, collectionId);
        return collectionResource;
    }
    public Property hadMember(Resource collection, Resource data) {

        Property hadMemberProperty = model.createProperty(prov + "hadMember");
        collection.addProperty(hadMemberProperty, data);
        return hadMemberProperty;
    }


    public Property wasDerivedFromData(Resource from_data, Resource to_data) {

        Property wasDerivedFromDataProperty = model.createProperty(prov + "wasDerivedFrom");
        from_data.addProperty(wasDerivedFromDataProperty, to_data);
        return wasDerivedFromDataProperty;
    }

    public Property wasInformedBy(Resource processExec1, Resource processExec2) {

        Property wasInformedByProperty = model.createProperty(prov + "wasInformedBy");
        processExec2.addProperty(wasInformedByProperty, processExec1);
        return wasInformedByProperty;
    }

    public Property wasAssociatedWith(Resource processExec, Resource process) {

        Property wasAssociatedWithProperty = model.createProperty(prov + "wasAssociatedWith");
        processExec.addProperty(wasAssociatedWithProperty, process);
        return wasAssociatedWithProperty;
    }
    public Property wasAssociatedWith(Resource processExec, String process) {

        Property wasAssociatedWithProperty = model.createProperty(prov + "wasAssociatedWith");
        processExec.addProperty(wasAssociatedWithProperty, process);
        return wasAssociatedWithProperty;
    }

    public Property isPartOf(Resource processExecPart, Resource processExecWhole) {

        Property isPartOfProperty = model.createProperty(prov + "isPartOf");
        processExecPart.addProperty(isPartOfProperty, processExecWhole);
        return isPartOfProperty;
    }
    public Property used(Resource processExec, Resource data) {

        Property usedProperty = model.createProperty(prov + "used");
        processExec.addProperty(usedProperty, data);
        return usedProperty;
    }
    public Property wasGeneratedBy( Resource data,Resource processExec) {

        Property wasGeneratedByProperty = model.createProperty(prov + "wasGeneratedBy");
        data.addProperty(wasGeneratedByProperty, processExec);
        return wasGeneratedByProperty;
    }
//Methods for prospective provenance

    public Resource createProcess(String processId, String processTitle, String classType, String scriptText) {

        Resource process = model.createResource(nsp + "process_" + processId);
        process.addProperty(RDF.type, model.createResource(provone + "Process"));
        process.addProperty(DCTerms.identifier, processId);
        process.addProperty(DCTerms.title, processTitle);
        Property property = model.createProperty(wfms + "package");
        Property classTypeProperty = model.createProperty(wfms + "class");
		process.addProperty(classTypeProperty, classType);

		if (null != scriptText) {
			Property scriptTextProperty = model.createProperty(wfms + "script");
			process.addProperty(scriptTextProperty, scriptText);
		}
        return process;
    }

    public Resource createInputPort(String inputPortId, String variableName) {

        Resource inputport = model.createResource(nsp + "inputport_" + inputPortId);
        inputport.addProperty(RDF.type, model.createResource(provone + "InputPort"));
        inputport.addProperty(DCTerms.identifier, inputPortId);
        inputport.addProperty(DCTerms.title, variableName);
        return inputport;

    }

    public Resource createOutputPort(String outputPortId, String variableName) {

        Resource outputPort = model.createResource(nsp + "outputport_" + outputPortId);
        outputPort.addProperty(RDF.type, model.createResource(provone + "OutputPort"));
        outputPort.addProperty(DCTerms.identifier, outputPortId);
        outputPort.addProperty(DCTerms.title, variableName);
        return outputPort;
    }

    public Resource createDataLink(String dataLinkId) {

        Resource dataLink = model.createResource(nsp + "DL_" + dataLinkId);
        dataLink.addProperty(RDF.type, model.createResource(provone + "DataLink"));
        dataLink.addProperty(DCTerms.identifier, dataLinkId);

        return dataLink;
    }

    public Resource createSeqCtrlLink(String sequenceCLID) {
        Resource seqCtrlLink = model.createResource(nsp + "SCL_" + sequenceCLID);
        seqCtrlLink.addProperty(RDF.type, model.createResource(provone + "SeqCtrlLink"));
        seqCtrlLink.addProperty(DCTerms.identifier, sequenceCLID);
        return seqCtrlLink;
    }

    public Resource createWorkflow(String workflowId, String workflowTitle, String role) {

        Resource workflow = model.createResource(nsp + workflowId);
        workflow.addProperty(RDF.type, model.createResource(provone + "Workflow"));
        workflow.addProperty(DCTerms.identifier, workflowId);
        workflow.addProperty(DCTerms.title, workflowTitle);
        Property property = model.createProperty(wfms + "Role");
		workflow.addProperty(property, role);
        return workflow;
    }

    public Resource createUser() {

        Resource user = model.createResource(nsp + "u1");
        user.addProperty(RDF.type, model.createResource(provone + "User"));
        user.addProperty(DCTerms.identifier, "John");
        return user;
    }

    public Property hasSubProcess(Resource top_process, Resource sub_process) {

        Property property = model.createProperty(provone + "hasSubProcess");
        top_process.addProperty(property, sub_process);
        return property;
    }

    public Property sourcePToCL(Resource source_process, Resource controlLink) {

        Property property = model.createProperty(provone + "sourcePToCL");
        source_process.addProperty(property, controlLink);
        return property;
    }

    public Property CLtoDestP(Resource dest_process, Resource controlLink) {

        Property property = model.createProperty(provone + "CLToDestP");
        controlLink.addProperty(property, dest_process);
        return property;
    }

    public Property hasInPort(Resource process, Resource inport) {

        Property property = model.createProperty(provone + "hasInPort");
        process.addProperty(property, inport);
        return property;
    }

    public Property hasOutPort(Resource process, Resource outport) {

        Property property = model.createProperty(provone + "hasOutPort");
        process.addProperty(property, outport);
        return property;
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
    }

    public Property outPortToDL(Resource outputport, Resource datalink) {

        Property property = model.createProperty(provone + "outPortToDL");
        outputport.addProperty(property, datalink);
        return property;
    }

    public Property inPortToDL(Resource inputport, Resource datalink) {

        Property property = model.createProperty(provone + "inPortToDL");
        inputport.addProperty(property, datalink);
        return property;
    }

    public Property DLToOutPort(Resource datalink, Resource outputport) {

        Property property = model.createProperty(provone + "DLToOutPort");
        datalink.addProperty(property, outputport);
        return property;
    }

    public Property wasAttributedTo(Resource process, Resource user) {

        Property property = model.createProperty(prov + "wasAttributedTo");
        process.addProperty(property, user);
        return property;
    }

    public Property wasDerivedFrom(Resource from_process_workflow, Resource to_process_workflow) {

        Property property = model.createProperty(prov + "wasDerivedFrom");
        from_process_workflow.addProperty(property, to_process_workflow);
        return property;
    }

    public Model getModel() {
        return this.model;
    }

	public Property getIdentifirePropety() {
		return model.createProperty(wfms+"activityId");
	}

	public void addProperty(Resource dataNode, String dataTitle) {
		dataNode.addProperty(DCTerms.title, dataTitle);
	}
}
