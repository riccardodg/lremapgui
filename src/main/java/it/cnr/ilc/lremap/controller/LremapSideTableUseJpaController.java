/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controller;

import it.cnr.ilc.lremap.controller.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.controller.exceptions.PreexistingEntityException;
import it.cnr.ilc.lremap.entities.LremapSideTableUse;
import it.cnr.ilc.lremap.entities.LremapSideTableUsePK;
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
public class LremapSideTableUseJpaController implements Serializable {

    public LremapSideTableUseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapSideTableUse lremapSideTableUse) throws PreexistingEntityException, Exception {
        if (lremapSideTableUse.getLremapSideTableUsePK() == null) {
            lremapSideTableUse.setLremapSideTableUsePK(new LremapSideTableUsePK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lremapSideTableUse);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapSideTableUse(lremapSideTableUse.getLremapSideTableUsePK()) != null) {
                throw new PreexistingEntityException("LremapSideTableUse " + lremapSideTableUse + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapSideTableUse lremapSideTableUse) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lremapSideTableUse = em.merge(lremapSideTableUse);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LremapSideTableUsePK id = lremapSideTableUse.getLremapSideTableUsePK();
                if (findLremapSideTableUse(id) == null) {
                    throw new NonexistentEntityException("The lremapSideTableUse with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LremapSideTableUsePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapSideTableUse lremapSideTableUse;
            try {
                lremapSideTableUse = em.getReference(LremapSideTableUse.class, id);
                lremapSideTableUse.getLremapSideTableUsePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapSideTableUse with id " + id + " no longer exists.", enfe);
            }
            em.remove(lremapSideTableUse);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapSideTableUse> findLremapSideTableUseEntities() {
        return findLremapSideTableUseEntities(true, -1, -1);
    }

    public List<LremapSideTableUse> findLremapSideTableUseEntities(int maxResults, int firstResult) {
        return findLremapSideTableUseEntities(false, maxResults, firstResult);
    }

    private List<LremapSideTableUse> findLremapSideTableUseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapSideTableUse.class));
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

    public LremapSideTableUse findLremapSideTableUse(LremapSideTableUsePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapSideTableUse.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapSideTableUseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapSideTableUse> rt = cq.from(LremapSideTableUse.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
