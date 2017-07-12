package edu.kit.provone.provone2rdfmapper.model;

/**
 * Created by mukhtar on 14.12.16.
 */
public class NewProcessInstanceParameterBoxing {
    String uuid;
    ProcessExec processInfo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ProcessExec getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(ProcessExec processInfo) {
        this.processInfo = processInfo;
    }
}
