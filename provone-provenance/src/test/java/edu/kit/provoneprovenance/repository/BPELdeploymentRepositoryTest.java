package edu.kit.provoneprovenance.repository;

import edu.kit.provoneprovenance.model.BPELdeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BPELdeploymentRepositoryTest {
    @Inject
    private BPELdeploymentRepository bpeLdeploymentRepository;

    @Test
    public void find() throws Exception {
    }

    @Test
    public void create() throws Exception {
        assertEquals(Long.valueOf(0), bpeLdeploymentRepository.countAll());
        assertEquals(0, bpeLdeploymentRepository.findAll().size());
        BPELdeployment bpeLdeployment = new BPELdeployment("bpelAdd", new Date(), "workflows/bpelAdd");
        bpeLdeployment = bpeLdeploymentRepository.create(bpeLdeployment);
        Long deploymentId = bpeLdeployment.getId();
        assertNotNull(deploymentId);

    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void countAll() throws Exception {
        // assertEquals(Long.valueOf(0),bpeLdeploymentRepository.countAll());
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BPELdeploymentRepository.class)
                .addClass(BPELdeployment.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                //   .addAsManifestResource( "META-INF/test-persistence.xml,test-persistence.xml");
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
    }

}
