package edu.kit.provone.provone2rdfmapper.model;

/**
 * Created by mukhtar on 18.10.16.
 */

public class Process {
    private String id;
    private String instanceId;
    private String processId;
    private String processInstanceId;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id='" + id + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", processId='" + processId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
