package edu.kit.scufl.mainapp;

import static org.junit.Assert.*;

/**
 * Created by Vaibhav on 12/07/2017.
 */
public class SCUFLRetro2ProvONETest {
    @org.junit.Test
    public void generateRetro() throws Exception {
        SCUFLRetro2ProvONE scuflRetro2ProvONE = new SCUFLRetro2ProvONE();
        scuflRetro2ProvONE.generateRetro("F:\\KIT\\provenance.ttl");
    }

    @org.junit.Test
    public void getRetrospectiveRDFModel() throws Exception {
    }

}