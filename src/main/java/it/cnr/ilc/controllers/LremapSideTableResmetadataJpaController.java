/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.controllers;

import it.cnr.ilc.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.controllers.exceptions.PreexistingEntityException;
import it.cnr.ilc.lremap.entities.LremapSideTableResmetadata;
import it.cnr.ilc.lremap.entities.LremapSideTableResmetadataPK;
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
public class LremapSideTableResmetadataJpaController implements Serializable {

    public LremapSideTableResmetadataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapSideTableResmetadata lremapSideTableResmetadata) throws PreexistingEntityException, Exception {
        if (lremapSideTableResmetadata.getLremapSideTableResmetadataPK() == null) {
            lremapSideTableResmetadata.setLremapSideTableResmetadataPK(new LremapSideTableResmetadataPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lremapSideTableResmetadata);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapSideTableResmetadata(lremapSideTableResmetadata.getLremapSideTableResmetadataPK()) != null) {
                throw new PreexistingEntityException("LremapSideTableResmetadata " + lremapSideTableResmetadata + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapSideTableResmetadata lremapSideTableResmetadata) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lremapSideTableResmetadata = em.merge(lremapSideTableResmetadata);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LremapSideTableResmetadataPK id = lremapSideTableResmetadata.getLremapSideTableResmetadataPK();
                if (findLremapSideTableResmetadata(id) == null) {
                    throw new NonexistentEntityException("The lremapSideTableResmetadata with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LremapSideTableResmetadataPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapSideTableResmetadata lremapSideTableResmetadata;
            try {
                lremapSideTableResmetadata = em.getReference(LremapSideTableResmetadata.class, id);
                lremapSideTableResmetadata.getLremapSideTableResmetadataPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapSideTableResmetadata with id " + id + " no longer exists.", enfe);
            }
            em.remove(lremapSideTableResmetadata);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapSideTableResmetadata> findLremapSideTableResmetadataEntities() {
        return findLremapSideTableResmetadataEntities(true, -1, -1);
    }

    public List<LremapSideTableResmetadata> findLremapSideTableResmetadataEntities(int maxResults, int firstResult) {
        return findLremapSideTableResmetadataEntities(false, maxResults, firstResult);
    }

    private List<LremapSideTableResmetadata> findLremapSideTableResmetadataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapSideTableResmetadata.class));
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

    public LremapSideTableResmetadata findLremapSideTableResmetadata(LremapSideTableResmetadataPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapSideTableResmetadata.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapSideTableResmetadataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapSideTableResmetadata> rt = cq.from(LremapSideTableResmetadata.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
