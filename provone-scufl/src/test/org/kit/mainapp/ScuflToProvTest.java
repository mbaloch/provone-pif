package org.kit.mainapp;

//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by mukhtar on 12.07.17.
 */
//@RunWith(Arquillian.class)
public class ScuflToProvTest {
    @org.junit.Test
    public void parseCreateGraph() throws Exception {
        ScuflToProv toProv = new ScuflToProv();
//		toProv.parseCreateGraph("files/Example_workflow_for_REST_and_XPath_activities-v1.t2flow");
        toProv.parseCreateGraph("/home/mukhtar/IdeaProjects/provone-pif/provone-provenance/src/main/resources/Hello_abcd-v1.t2flow");
    }

//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(ScuflToProv.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }

}
