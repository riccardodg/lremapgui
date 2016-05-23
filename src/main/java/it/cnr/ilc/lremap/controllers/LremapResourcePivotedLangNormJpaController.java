/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controllers;

import it.cnr.ilc.lremap.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import it.cnr.ilc.lremap.entities.LremapResourceLangNorm;
import it.cnr.ilc.lremap.entities.LremapResourcePivotedLangNorm;
import it.cnr.ilc.lremap.entities.LremapResourcePivotedLangNormPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapResourcePivotedLangNormJpaController implements Serializable {

    public LremapResourcePivotedLangNormJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapResourcePivotedLangNorm lremapResourcePivotedLangNorm) throws PreexistingEntityException, Exception {
        if (lremapResourcePivotedLangNorm.getLremapResourcePivotedLangNormPK() == null) {
            lremapResourcePivotedLangNorm.setLremapResourcePivotedLangNormPK(new LremapResourcePivotedLangNormPK());
        }
        lremapResourcePivotedLangNorm.getLremapResourcePivotedLangNormPK().setResourceid(lremapResourcePivotedLangNorm.getLremapResourceLangNorm().getResourceid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceLangNorm lremapResourceLangNorm = lremapResourcePivotedLangNorm.getLremapResourceLangNorm();
            if (lremapResourceLangNorm != null) {
                lremapResourceLangNorm = em.getReference(lremapResourceLangNorm.getClass(), lremapResourceLangNorm.getResourceid());
                lremapResourcePivotedLangNorm.setLremapResourceLangNorm(lremapResourceLangNorm);
            }
            em.persist(lremapResourcePivotedLangNorm);
            if (lremapResourceLangNorm != null) {
                lremapResourceLangNorm.getLremapResourcePivotedLangNormList().add(lremapResourcePivotedLangNorm);
                lremapResourceLangNorm = em.merge(lremapResourceLangNorm);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapResourcePivotedLangNorm(lremapResourcePivotedLangNorm.getLremapResourcePivotedLangNormPK()) != null) {
                throw new PreexistingEntityException("LremapResourcePivotedLangNorm " + lremapResourcePivotedLangNorm + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapResourcePivotedLangNorm lremapResourcePivotedLangNorm) throws NonexistentEntityException, Exception {
        lremapResourcePivotedLangNorm.getLremapResourcePivotedLangNormPK().setResourceid(lremapResourcePivotedLangNorm.getLremapResourceLangNorm().getResourceid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourcePivotedLangNorm persistentLremapResourcePivotedLangNorm = em.find(LremapResourcePivotedLangNorm.class, lremapResourcePivotedLangNorm.getLremapResourcePivotedLangNormPK());
            LremapResourceLangNorm lremapResourceLangNormOld = persistentLremapResourcePivotedLangNorm.getLremapResourceLangNorm();
            LremapResourceLangNorm lremapResourceLangNormNew = lremapResourcePivotedLangNorm.getLremapResourceLangNorm();
            if (lremapResourceLangNormNew != null) {
                lremapResourceLangNormNew = em.getReference(lremapResourceLangNormNew.getClass(), lremapResourceLangNormNew.getResourceid());
                lremapResourcePivotedLangNorm.setLremapResourceLangNorm(lremapResourceLangNormNew);
            }
            lremapResourcePivotedLangNorm = em.merge(lremapResourcePivotedLangNorm);
            if (lremapResourceLangNormOld != null && !lremapResourceLangNormOld.equals(lremapResourceLangNormNew)) {
                lremapResourceLangNormOld.getLremapResourcePivotedLangNormList().remove(lremapResourcePivotedLangNorm);
                lremapResourceLangNormOld = em.merge(lremapResourceLangNormOld);
            }
            if (lremapResourceLangNormNew != null && !lremapResourceLangNormNew.equals(lremapResourceLangNormOld)) {
                lremapResourceLangNormNew.getLremapResourcePivotedLangNormList().add(lremapResourcePivotedLangNorm);
                lremapResourceLangNormNew = em.merge(lremapResourceLangNormNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LremapResourcePivotedLangNormPK id = lremapResourcePivotedLangNorm.getLremapResourcePivotedLangNormPK();
                if (findLremapResourcePivotedLangNorm(id) == null) {
                    throw new NonexistentEntityException("The lremapResourcePivotedLangNorm with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LremapResourcePivotedLangNormPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourcePivotedLangNorm lremapResourcePivotedLangNorm;
            try {
                lremapResourcePivotedLangNorm = em.getReference(LremapResourcePivotedLangNorm.class, id);
                lremapResourcePivotedLangNorm.getLremapResourcePivotedLangNormPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapResourcePivotedLangNorm with id " + id + " no longer exists.", enfe);
            }
            LremapResourceLangNorm lremapResourceLangNorm = lremapResourcePivotedLangNorm.getLremapResourceLangNorm();
            if (lremapResourceLangNorm != null) {
                lremapResourceLangNorm.getLremapResourcePivotedLangNormList().remove(lremapResourcePivotedLangNorm);
                lremapResourceLangNorm = em.merge(lremapResourceLangNorm);
            }
            em.remove(lremapResourcePivotedLangNorm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapResourcePivotedLangNorm> findLremapResourcePivotedLangNormEntities() {
        return findLremapResourcePivotedLangNormEntities(true, -1, -1);
    }

    public List<LremapResourcePivotedLangNorm> findLremapResourcePivotedLangNormEntities(int maxResults, int firstResult) {
        return findLremapResourcePivotedLangNormEntities(false, maxResults, firstResult);
    }

    private List<LremapResourcePivotedLangNorm> findLremapResourcePivotedLangNormEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapResourcePivotedLangNorm.class));
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

    public LremapResourcePivotedLangNorm findLremapResourcePivotedLangNorm(LremapResourcePivotedLangNormPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapResourcePivotedLangNorm.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapResourcePivotedLangNormCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapResourcePivotedLangNorm> rt = cq.from(LremapResourcePivotedLangNorm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
