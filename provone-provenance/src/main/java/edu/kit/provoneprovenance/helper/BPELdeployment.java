package edu.kit.provoneprovenance.helper;

/**
 * Created by mukhtar on 29.06.17.
 */
public class BPELdeployment {
    String packageName;
    String deploymentDate;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDeploymentDate() {
        return deploymentDate;
    }

    public void setDeploymentDate(String deploymentDate) {
        this.deploymentDate = deploymentDate;
    }

    public String getGetDeploymentpath() {
        return getDeploymentpath;
    }

    public void setGetDeploymentpath(String getDeploymentpath) {
        this.getDeploymentpath = getDeploymentpath;
    }

    String getDeploymentpath;
}
