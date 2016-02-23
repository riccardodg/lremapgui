/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controller;

import it.cnr.ilc.lremap.controller.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.controller.exceptions.PreexistingEntityException;
import it.cnr.ilc.lremap.entities.LremapConferenceYears;
import it.cnr.ilc.lremap.entities.LremapConferenceYearsPK;
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
public class LremapConferenceYearsJpaController1 implements Serializable {

    public LremapConferenceYearsJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapConferenceYears lremapConferenceYears) throws PreexistingEntityException, Exception {
        if (lremapConferenceYears.getLremapConferenceYearsPK() == null) {
            lremapConferenceYears.setLremapConferenceYearsPK(new LremapConferenceYearsPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lremapConferenceYears);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapConferenceYears(lremapConferenceYears.getLremapConferenceYearsPK()) != null) {
                throw new PreexistingEntityException("LremapConferenceYears " + lremapConferenceYears + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapConferenceYears lremapConferenceYears) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lremapConferenceYears = em.merge(lremapConferenceYears);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LremapConferenceYearsPK id = lremapConferenceYears.getLremapConferenceYearsPK();
                if (findLremapConferenceYears(id) == null) {
                    throw new NonexistentEntityException("The lremapConferenceYears with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LremapConferenceYearsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapConferenceYears lremapConferenceYears;
            try {
                lremapConferenceYears = em.getReference(LremapConferenceYears.class, id);
                lremapConferenceYears.getLremapConferenceYearsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapConferenceYears with id " + id + " no longer exists.", enfe);
            }
            em.remove(lremapConferenceYears);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapConferenceYears> findLremapConferenceYearsEntities() {
        return findLremapConferenceYearsEntities(true, -1, -1);
    }

    public List<LremapConferenceYears> findLremapConferenceYearsEntities(int maxResults, int firstResult) {
        return findLremapConferenceYearsEntities(false, maxResults, firstResult);
    }

    private List<LremapConferenceYears> findLremapConferenceYearsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapConferenceYears.class));
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

    public LremapConferenceYears findLremapConferenceYears(LremapConferenceYearsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapConferenceYears.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapConferenceYearsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapConferenceYears> rt = cq.from(LremapConferenceYears.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
