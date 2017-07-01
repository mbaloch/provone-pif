package edu.kit.provone.bpel;

import org.apache.ode.bpel.o.OProcess;
import org.apache.ode.bpel.o.OSwitch;

/**
 * Created by mukhtar on 27.04.17.
 */
public class PCase  extends OSwitch.OCase{
    public PCase(OProcess owner){
        super(owner);
    }
}
