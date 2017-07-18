package edu.kit.provone.retrospective.ode.model;

/**
 * Created by mukhtar on 10.03.17.
 */
public class ActivityExec extends ProcessExec {
    private String activityName;
    private String activityType;

    @Override
    public String toString() {
        return "ActivityExec{" +
                ", activityName='" + activityName + '\'' +
                ", activityType='" + activityType + '\'' +
                '}';
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
