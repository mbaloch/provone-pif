package org.kit.mainapp;

//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.runner.RunWith;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by mukhtar on 12.07.17.
 */
//@RunWith(Arquillian.class)
public class ScuflToProvTest {
    @org.junit.Test
    public void parseCreateGraph() throws Exception {
        ScuflToProv toProv = new ScuflToProv();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resource = classLoader.getResource("Hello_abcd_half.t2flow");
        String t2flowFilePath = resource.getFile();

        toProv.parseCreateGraph(t2flowFilePath);
//        toProv.parseCreateGraph("F://KIT/Complete Projects/ScuflToProv/files/Hello_abcd_half.t2flow");
    }

//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(ScuflToProv.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }

}
