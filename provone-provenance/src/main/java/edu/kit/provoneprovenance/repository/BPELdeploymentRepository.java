package edu.kit.provoneprovenance.repository;

import edu.kit.provoneprovenance.model.BPELdeployment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@Transactional(SUPPORTS)
public class BPELdeploymentRepository {
    @PersistenceContext(unitName = "bpelDeploymentPU")
    private EntityManager em;

    public BPELdeployment find(Long id) {
        return em.find(BPELdeployment.class, id);

    }

    @Transactional(REQUIRED)
    public BPELdeployment create(BPELdeployment bpeLdeployment) {
        em.persist(bpeLdeployment);
        return bpeLdeployment;

    }

    @Transactional(REQUIRED)
    public void delete(Long id) {
        em.remove(em.getReference(BPELdeployment.class, id));

    }

    public List<BPELdeployment> findAll() {
        TypedQuery<BPELdeployment> query = em.createQuery(
                "SELECT deployment from BPELdeployment deployment", BPELdeployment.class
        );
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("select count (d) from BPELdeployment d", Long.class);
        return query.getSingleResult();
    }

}
