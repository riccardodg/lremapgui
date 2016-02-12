/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controller;

import it.cnr.ilc.lremap.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.controllers.exceptions.PreexistingEntityException;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtypePK;
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
public class LremapSideTableGroupedtypeJpaController implements Serializable {

    public LremapSideTableGroupedtypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapSideTableGroupedtype lremapSideTableGroupedtype) throws PreexistingEntityException, Exception {
        if (lremapSideTableGroupedtype.getLremapSideTableGroupedtypePK() == null) {
            lremapSideTableGroupedtype.setLremapSideTableGroupedtypePK(new LremapSideTableGroupedtypePK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lremapSideTableGroupedtype);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapSideTableGroupedtype(lremapSideTableGroupedtype.getLremapSideTableGroupedtypePK()) != null) {
                throw new PreexistingEntityException("LremapSideTableGroupedtype " + lremapSideTableGroupedtype + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapSideTableGroupedtype lremapSideTableGroupedtype) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lremapSideTableGroupedtype = em.merge(lremapSideTableGroupedtype);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LremapSideTableGroupedtypePK id = lremapSideTableGroupedtype.getLremapSideTableGroupedtypePK();
                if (findLremapSideTableGroupedtype(id) == null) {
                    throw new NonexistentEntityException("The lremapSideTableGroupedtype with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LremapSideTableGroupedtypePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapSideTableGroupedtype lremapSideTableGroupedtype;
            try {
                lremapSideTableGroupedtype = em.getReference(LremapSideTableGroupedtype.class, id);
                lremapSideTableGroupedtype.getLremapSideTableGroupedtypePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapSideTableGroupedtype with id " + id + " no longer exists.", enfe);
            }
            em.remove(lremapSideTableGroupedtype);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapSideTableGroupedtype> findLremapSideTableGroupedtypeEntities() {
        return findLremapSideTableGroupedtypeEntities(true, -1, -1);
    }

    public List<LremapSideTableGroupedtype> findLremapSideTableGroupedtypeEntities(int maxResults, int firstResult) {
        return findLremapSideTableGroupedtypeEntities(false, maxResults, firstResult);
    }

    private List<LremapSideTableGroupedtype> findLremapSideTableGroupedtypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapSideTableGroupedtype.class));
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

    public LremapSideTableGroupedtype findLremapSideTableGroupedtype(LremapSideTableGroupedtypePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapSideTableGroupedtype.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapSideTableGroupedtypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapSideTableGroupedtype> rt = cq.from(LremapSideTableGroupedtype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
