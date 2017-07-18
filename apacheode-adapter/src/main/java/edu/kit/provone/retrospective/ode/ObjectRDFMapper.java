package edu.kit.provone.retrospective.ode;

import edu.kit.provone.retrospective.ode.model.Data;
import edu.kit.provone.retrospective.ode.model.ProcessExec;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

/**
 * Created by mukhtar on 16.07.17.
 */
public class ObjectRDFMapper {

    public RDFUtility getRdfUtility() {
        return rdfUtility;
    }

    private RDFUtility rdfUtility = new RDFUtility();
    public Resource createWorkflowExecResource(ProcessExec processExec) {
        Resource workflowExecResource = rdfUtility.createProcessExec(
                processExec.getProcessInstanceId(), processExec.getProcessId(), processExec.getId()
                , processExec.getStartTime(), processExec.getEndTime(),
                processExec.getCompleted().toString());
        return workflowExecResource;
    }
    public Resource createProcessExecResource( ProcessExec processExec, Resource workflowResource) {

        Resource processExecResource = rdfUtility.createProcessExec(
                processExec.getProcessInstanceId(), processExec.getTitle(), processExec.getId()
                , processExec.getStartTime(), processExec.getEndTime(),
                processExec.getCompleted().toString());
        Property isPartOf = rdfUtility.isPartOf(processExecResource, workflowResource);
        return processExecResource;

    }
    public void processProducedData(String workflowId, String processId, Data data) {

    }


    public void processConsumedData(String workflowId, String processId, Data data) {

    }
}
