package edu.kit.provone.provone2rdfmapper.model;

/**
 * Created by mukhtar on 15.12.16.
 */
public class ProdConsDataParaBoxing {
    String workflowId;
    String processId;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    Data data;
}
