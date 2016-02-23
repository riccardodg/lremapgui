/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controller;

import it.cnr.ilc.lremap.controller.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.entities.LremapSideTableStatus;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapSideTableStatusJpaController implements Serializable {

    public LremapSideTableStatusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapSideTableStatus lremapSideTableStatus) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lremapSideTableStatus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapSideTableStatus lremapSideTableStatus) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lremapSideTableStatus = em.merge(lremapSideTableStatus);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lremapSideTableStatus.getId();
                if (findLremapSideTableStatus(id) == null) {
                    throw new NonexistentEntityException("The lremapSideTableStatus with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapSideTableStatus lremapSideTableStatus;
            try {
                lremapSideTableStatus = em.getReference(LremapSideTableStatus.class, id);
                lremapSideTableStatus.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapSideTableStatus with id " + id + " no longer exists.", enfe);
            }
            em.remove(lremapSideTableStatus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapSideTableStatus> findLremapSideTableStatusEntities() {
        return findLremapSideTableStatusEntities(true, -1, -1);
    }

    public List<LremapSideTableStatus> findLremapSideTableStatusEntities(int maxResults, int firstResult) {
        return findLremapSideTableStatusEntities(false, maxResults, firstResult);
    }

    private List<LremapSideTableStatus> findLremapSideTableStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapSideTableStatus.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public LremapSideTableStatus findLremapSideTableStatus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapSideTableStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapSideTableStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapSideTableStatus> rt = cq.from(LremapSideTableStatus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
