package edu.kit.provone.provone2rdfmapper.model;

/**
 * Created by mukhtar on 18.10.16.
 */

public class ProcessExec {
    private String id;
    private String instanceId;
    private String processId;
    private String processInstanceId;
    private String startTime;
    private String endTime;
    private Boolean completed;
    private String cached;
    private String title;
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    private String processName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProcessExec{" +
                "id='" + id + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", processId='" + processId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", completed='" + completed + '\'' +
                ", cached='" + cached + '\'' +
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getCached() {
        return cached;
    }

    public void setCached(String cached) {
        this.cached = cached;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
