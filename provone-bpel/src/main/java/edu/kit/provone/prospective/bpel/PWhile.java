package edu.kit.provone.prospective.bpel;

import org.apache.ode.bpel.o.OActivity;
import org.apache.ode.bpel.o.OProcess;
import org.apache.ode.bpel.o.OWhile;

/**
 * Created by mukhtar on 27.04.17.
 */
public class PWhile extends OWhile {
    public PWhile(OProcess owner, OActivity parent) {
        super(owner, parent);
    }
}
