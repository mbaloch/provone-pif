package edu.kit.provone.provone2rdfmapper;


import edu.kit.provone.provone2rdfmapper.model.Data;
import edu.kit.provone.provone2rdfmapper.model.ProcessExec;

/**
 * Created by mukhtar on 14.11.16.
 */
public interface IProvenanceAPI {

    void createProcessExecInstance(ProcessExec process);
    void createWorkflowExecInstance(ProcessExec process);

    void createChildProcessInstance(String parentProcessUUID, String currentProcess,
                                    ProcessExec processInfo);

    void updateProcessStatus(ProcessExec processInfo);

    void processProducedData(String workflowId, String processId, Data data);

    void processConsumedData(String workflowId, String processId, Data data);
}
