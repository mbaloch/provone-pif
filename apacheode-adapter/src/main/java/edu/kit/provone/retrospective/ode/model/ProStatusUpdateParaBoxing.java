package edu.kit.provone.retrospective.ode.model;

/**
 * Created by mukhtar on 15.12.16.
 */
public class ProStatusUpdateParaBoxing {
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

    public ProcessExec getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(ProcessExec processInfo) {
        this.processInfo = processInfo;
    }

    ProcessExec processInfo;
}
