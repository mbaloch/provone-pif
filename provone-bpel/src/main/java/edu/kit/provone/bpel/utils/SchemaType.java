package edu.kit.provone.bpel.utils;

import groovy.xml.QName;

/**
 * Created by mukhtar on 10.05.17.
 */
public class SchemaType {
    public QName getDataType() {
        return dataType;
    }

    public SchemaType(String particleName, QName dataType) {
        this.particleName = particleName;
        this.dataType = dataType;
    }

    public void setDataType(QName dataType) {
        this.dataType = dataType;
    }

    private QName dataType;
    private String particleName;

    @Override
    public String toString() {
        return "SchemaType{" +
                "dataType=" + dataType +
                ", particleName='" + particleName + '\'' +
                '}';
    }

    public String getParticleName() {
        return particleName;
    }

    public void setParticleName(String particleName) {
        this.particleName = particleName;
    }
}
