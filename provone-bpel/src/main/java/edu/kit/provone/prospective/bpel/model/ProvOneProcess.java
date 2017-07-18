package edu.kit.provone.prospective.bpel.model;

/**
 * Created by mukhtar on 18.10.16.
 */

public class ProvOneProcess {
    private String id;
    private String instanceId;
    private String processId;
    private String processInstanceId;
    private String title;
    private String activityName;
    private String operationName;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    private String activityType;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
//        return "ProvOneProcess{" +
//                "id='" + id + '\'' +
//                ", instanceId='" + instanceId + '\'' +
//                ", processId='" + processId + '\'' +
//                ", processInstanceId='" + processInstanceId + '\'' +
//                ", title='" + title + '\'' +
//                '}';
return "Name:"+activityName+"\n"+"Operation:"+getOperationName();
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
