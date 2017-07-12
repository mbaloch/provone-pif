package edu.kit.provone.prospective.bpel.model;

import groovy.xml.QName;
import edu.kit.provone.prospective.bpel.utils.SchemaType;

import java.util.List;

/**
 * Created by mukhtar on 15.02.17.
 */
public abstract class Port {
    protected String id;
    protected String title;

    private List<DataLink> inPortToDLs;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OutputPort{" +
                "variableQname=" + variableQname +
                ", particle=\n" + particle +
                '}';
    }

    public String getTitle() {
        return variableQname.toString() + "."+ particle.getParticleName();
    }

    public void setTitle(String title) {
        this.title = title;
    }
    protected QName variableQname;
    protected SchemaType particle;

    public QName getVariableQname() {
        return variableQname;
    }

    public void setVariableQname(QName variableQname) {
        this.variableQname = variableQname;
    }

    public SchemaType getParticle() {
        return particle;
    }

    public void setParticle(SchemaType particle) {
        this.particle = particle;
    }
}
