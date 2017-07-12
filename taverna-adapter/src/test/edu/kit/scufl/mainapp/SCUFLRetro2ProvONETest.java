package edu.kit.scufl.mainapp;

import static org.junit.Assert.*;

/**
 * Created by Vaibhav on 12/07/2017.
 */
public class SCUFLRetro2ProvONETest {
    @org.junit.Test
    public void generateRetro() throws Exception {
        SCUFLRetro2ProvONE scuflRetro2ProvONE = new SCUFLRetro2ProvONE();
        int count=0;
        for (int i=1;i<10;i++) {
            try {
                scuflRetro2ProvONE.getRetrospectiveRDFModel("/home/mukhtar/IdeaProjects/provone-pif/provone-provenance/src/main/resources/provenance.ttl");
                System.out.println("Passed");
            }catch (Exception e){
                System.out.println(e.getMessage());
                ++count;
            }
        }
        System.out.println("failed:"+count);

    }

    @org.junit.Test
    public void getRetrospectiveRDFModel() throws Exception {
    }

}