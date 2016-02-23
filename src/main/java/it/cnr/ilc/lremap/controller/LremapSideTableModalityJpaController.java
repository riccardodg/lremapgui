/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controller;

import it.cnr.ilc.lremap.controller.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.entities.LremapSideTableModality;
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
public class LremapSideTableModalityJpaController implements Serializable {

    public LremapSideTableModalityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapSideTableModality lremapSideTableModality) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lremapSideTableModality);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapSideTableModality lremapSideTableModality) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lremapSideTableModality = em.merge(lremapSideTableModality);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lremapSideTableModality.getId();
                if (findLremapSideTableModality(id) == null) {
                    throw new NonexistentEntityException("The lremapSideTableModality with id " + id + " no longer exists.");
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
            LremapSideTableModality lremapSideTableModality;
            try {
                lremapSideTableModality = em.getReference(LremapSideTableModality.class, id);
                lremapSideTableModality.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapSideTableModality with id " + id + " no longer exists.", enfe);
            }
            em.remove(lremapSideTableModality);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapSideTableModality> findLremapSideTableModalityEntities() {
        return findLremapSideTableModalityEntities(true, -1, -1);
    }

    public List<LremapSideTableModality> findLremapSideTableModalityEntities(int maxResults, int firstResult) {
        return findLremapSideTableModalityEntities(false, maxResults, firstResult);
    }

    private List<LremapSideTableModality> findLremapSideTableModalityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapSideTableModality.class));
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

    public LremapSideTableModality findLremapSideTableModality(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapSideTableModality.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapSideTableModalityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapSideTableModality> rt = cq.from(LremapSideTableModality.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
