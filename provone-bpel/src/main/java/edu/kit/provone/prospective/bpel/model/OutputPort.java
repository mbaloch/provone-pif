package edu.kit.provone.prospective.bpel.model;

/**
 * Created by mukhtar on 15.02.17.
 */
public class OutputPort extends Port{

    @Override
    public String toString() {
        return "OutputPort{\n" +
                "variableQname=" + variableQname +
                ",\n particle=" + particle +
                '}';
    }

}
