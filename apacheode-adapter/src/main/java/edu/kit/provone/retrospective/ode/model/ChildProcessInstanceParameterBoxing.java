package edu.kit.provone.retrospective.ode.model;

/**
 * Created by mukhtar on 15.12.16.
 */
public class ChildProcessInstanceParameterBoxing {
    String parentProcessUUD;
    String currentProcessUUID;

    public String getParentProcessUUD() {
        return parentProcessUUD;
    }

    public void setParentProcessUUD(String parentProcessUUD) {
        this.parentProcessUUD = parentProcessUUD;
    }

    public String getCurrentProcessUUID() {
        return currentProcessUUID;
    }

    public void setCurrentProcessUUID(String currentProcessUUID) {
        this.currentProcessUUID = currentProcessUUID;
    }

    public ProcessExec getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(ProcessExec processInfo) {
        this.processInfo = processInfo;
    }

    ProcessExec processInfo;
}
