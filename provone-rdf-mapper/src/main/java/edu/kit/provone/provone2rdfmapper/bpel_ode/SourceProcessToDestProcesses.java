package edu.kit.provone.provone2rdfmapper.bpel_ode;

import org.apache.jena.rdf.model.Resource;

public class SourceProcessToDestProcesses {
    Resource sourceProcess;
    Resource destinationProcess;
    Resource inputPort;
    Resource outputPort;
    Resource dataLink;

    @Override
    public String toString() {
        return "SourceProcessToDestProcesses{" +
                "sourceProcess=" + sourceProcess +
                ", destinationProcess=" + destinationProcess +
                ", inputPort=" + inputPort +
                ", outputPort=" + outputPort +
                ", dataLink=" + dataLink +
                ", sourceProcessTitle='" + sourceProcessTitle + '\'' +
                ", destinationProcessTitle='" + destinationProcessTitle + '\'' +
                ", sourceProcessOperationTitle='" + sourceProcessOperationTitle + '\'' +
                ", inputPortVariableName='" + inputPortVariableName + '\'' +
                ", inputVariableParticleName='" + inputVariableParticleName + '\'' +
                ", outputPortVariableName='" + outputPortVariableName + '\'' +
                ", outputVariableParticleName='" + outputVariableParticleName + '\'' +
                ", destinationProcessOperationTitle='" + destinationProcessOperationTitle + '\'' +
                '}';
    }


    String sourceProcessTitle;
    String destinationProcessTitle;
    String sourceProcessOperationTitle;
    String inputPortVariableName;
    String inputVariableParticleName;
    String outputPortVariableName;
    String outputVariableParticleName;

    public String getInputPortVariableName() {
        return inputPortVariableName;
    }

    public void setInputPortVariableName(String inputPortVariableName) {
        this.inputPortVariableName = inputPortVariableName;
    }

    public String getInputVariableParticleName() {
        return inputVariableParticleName;
    }

    public void setInputVariableParticleName(String inputVariableParticleName) {
        this.inputVariableParticleName = inputVariableParticleName;
    }

    public String getOutputPortVariableName() {
        return outputPortVariableName;
    }

    public void setOutputPortVariableName(String outputPortVariableName) {
        this.outputPortVariableName = outputPortVariableName;
    }

    public String getOutputVariableParticleName() {
        return outputVariableParticleName;
    }

    public void setOutputVariableParticleName(String outputVariableParticleName) {
        this.outputVariableParticleName = outputVariableParticleName;
    }

    public Resource getSourceProcess() {
        return sourceProcess;
    }

    public void setSourceProcess(Resource sourceProcess) {
        this.sourceProcess = sourceProcess;
    }

    public Resource getDestinationProcess() {
        return destinationProcess;
    }

    public void setDestinationProcess(Resource destinationProcess) {
        this.destinationProcess = destinationProcess;
    }

    public Resource getInputPort() {
        return inputPort;
    }

    public void setInputPort(Resource inputPort) {
        this.inputPort = inputPort;
    }

    public Resource getOutputPort() {
        return outputPort;
    }

    public void setOutputPort(Resource outputPort) {
        this.outputPort = outputPort;
    }

    public Resource getDataLink() {
        return dataLink;
    }

    public void setDataLink(Resource dataLink) {
        this.dataLink = dataLink;
    }

    public String getSourceProcessTitle() {
        return sourceProcessTitle;
    }

    public void setSourceProcessTitle(String sourceProcessTitle) {
        this.sourceProcessTitle = sourceProcessTitle;
    }

    public String getDestinationProcessTitle() {
        return destinationProcessTitle;
    }

    public void setDestinationProcessTitle(String destinationProcessTitle) {
        this.destinationProcessTitle = destinationProcessTitle;
    }

    public String getSourceProcessOperationTitle() {
        return sourceProcessOperationTitle;
    }

    public void setSourceProcessOperationTitle(String sourceProcessOperationTitle) {
        this.sourceProcessOperationTitle = sourceProcessOperationTitle;
    }

    public String getDestinationProcessOperationTitle() {
        return destinationProcessOperationTitle;
    }

    public void setDestinationProcessOperationTitle(String destinationProcessOperationTitle) {
        this.destinationProcessOperationTitle = destinationProcessOperationTitle;
    }

    String destinationProcessOperationTitle;
}
