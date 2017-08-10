package edu.kit.provoneprovenance.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mukhtar on 29.06.17.
 */
@Entity
public class BPELdeployment {
    @Column(name = "package_name")
    private String packageName;
    @Column(name = "deployment_date")
    @Temporal(TemporalType.DATE)
    private Date deploymentDate;
    @Column(name = "deployment_path")
    private String deploymentPath;
    @Column(name = "package_type")
    private PackageType type;

    @Id
    @GeneratedValue
    private Long id;

    public BPELdeployment() {
    }

    public BPELdeployment(String packageName, Date deploymentDate, String deploymentPath, PackageType type) {
        this.packageName = packageName;
        this.deploymentDate = deploymentDate;
        this.deploymentPath = deploymentPath;
        this.type = type;
    }

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Date getDeploymentDate() {
        return deploymentDate;
    }

    public void setDeploymentDate(Date deploymentDate) {
        this.deploymentDate = deploymentDate;
    }

    public String getDeploymentPath() {
        return deploymentPath;
    }

    public void setDeploymentPath(String getDeploymentpath) {
        this.deploymentPath = getDeploymentpath;
    }

    @Override
    public String toString() {
        return "BPELdeployment{" +
                "packageName='" + packageName + '\'' +
                ", deploymentDate=" + deploymentDate +
                ", deploymentPath='" + deploymentPath + '\'' +
                ", type=" + type +
                ", id=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
