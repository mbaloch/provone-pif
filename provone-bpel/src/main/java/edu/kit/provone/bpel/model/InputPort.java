package edu.kit.provone.bpel.model;

/**
 * Created by mukhtar on 15.02.17.
 */
public class InputPort extends Port{

    @Override
    public String toString() {
        return "InputPort{" +
                "variableQname=" + variableQname +
                ",\n particle=" + particle +
                '}';
    }

}
