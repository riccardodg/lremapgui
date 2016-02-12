/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controller;

import it.cnr.ilc.lremap.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.controllers.exceptions.PreexistingEntityException;
import it.cnr.ilc.lremap.entities.LremapSideTableAvail;
import it.cnr.ilc.lremap.entities.LremapSideTableAvailPK;
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
public class LremapSideTableAvailJpaController implements Serializable {

    public LremapSideTableAvailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapSideTableAvail lremapSideTableAvail) throws PreexistingEntityException, Exception {
        if (lremapSideTableAvail.getLremapSideTableAvailPK() == null) {
            lremapSideTableAvail.setLremapSideTableAvailPK(new LremapSideTableAvailPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lremapSideTableAvail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapSideTableAvail(lremapSideTableAvail.getLremapSideTableAvailPK()) != null) {
                throw new PreexistingEntityException("LremapSideTableAvail " + lremapSideTableAvail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapSideTableAvail lremapSideTableAvail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lremapSideTableAvail = em.merge(lremapSideTableAvail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LremapSideTableAvailPK id = lremapSideTableAvail.getLremapSideTableAvailPK();
                if (findLremapSideTableAvail(id) == null) {
                    throw new NonexistentEntityException("The lremapSideTableAvail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LremapSideTableAvailPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapSideTableAvail lremapSideTableAvail;
            try {
                lremapSideTableAvail = em.getReference(LremapSideTableAvail.class, id);
                lremapSideTableAvail.getLremapSideTableAvailPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapSideTableAvail with id " + id + " no longer exists.", enfe);
            }
            em.remove(lremapSideTableAvail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapSideTableAvail> findLremapSideTableAvailEntities() {
        return findLremapSideTableAvailEntities(true, -1, -1);
    }

    public List<LremapSideTableAvail> findLremapSideTableAvailEntities(int maxResults, int firstResult) {
        return findLremapSideTableAvailEntities(false, maxResults, firstResult);
    }

    private List<LremapSideTableAvail> findLremapSideTableAvailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapSideTableAvail.class));
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

    public LremapSideTableAvail findLremapSideTableAvail(LremapSideTableAvailPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapSideTableAvail.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapSideTableAvailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapSideTableAvail> rt = cq.from(LremapSideTableAvail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
