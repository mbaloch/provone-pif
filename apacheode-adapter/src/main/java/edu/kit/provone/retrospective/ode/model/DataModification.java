package edu.kit.provone.retrospective.ode.model;

/**
 * Created by mukhtar on 13.03.17.
 */
public class DataModification {
    String variableName;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getDataNode() {
        return dataNode;
    }

    @Override
    public String toString() {
        return "DataModification{" +
                "variableName='" + variableName + '\'' +
                ", dataNode=" + dataNode +
                '}';
    }

    public void setDataNode(String dataNode) {
        this.dataNode = dataNode;
    }

    String dataNode;
}
