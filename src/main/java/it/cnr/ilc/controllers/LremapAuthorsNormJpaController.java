/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.controllers;

import it.cnr.ilc.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.controllers.exceptions.PreexistingEntityException;
import it.cnr.ilc.lremap.entities.LremapAuthorsNorm;
import it.cnr.ilc.lremap.entities.LremapAuthorsNormPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import it.cnr.ilc.lremap.entities.LremapPapersNorm;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapAuthorsNormJpaController implements Serializable {

    public LremapAuthorsNormJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapAuthorsNorm lremapAuthorsNorm) throws PreexistingEntityException, Exception {
        if (lremapAuthorsNorm.getLremapAuthorsNormPK() == null) {
            lremapAuthorsNorm.setLremapAuthorsNormPK(new LremapAuthorsNormPK());
        }
        lremapAuthorsNorm.getLremapAuthorsNormPK().setResourceid(lremapAuthorsNorm.getLremapPapersNorm().getResourceid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapPapersNorm lremapPapersNorm = lremapAuthorsNorm.getLremapPapersNorm();
            if (lremapPapersNorm != null) {
                lremapPapersNorm = em.getReference(lremapPapersNorm.getClass(), lremapPapersNorm.getResourceid());
                lremapAuthorsNorm.setLremapPapersNorm(lremapPapersNorm);
            }
            em.persist(lremapAuthorsNorm);
            if (lremapPapersNorm != null) {
                lremapPapersNorm.getLremapAuthorsNormList().add(lremapAuthorsNorm);
                lremapPapersNorm = em.merge(lremapPapersNorm);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapAuthorsNorm(lremapAuthorsNorm.getLremapAuthorsNormPK()) != null) {
                throw new PreexistingEntityException("LremapAuthorsNorm " + lremapAuthorsNorm + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapAuthorsNorm lremapAuthorsNorm) throws NonexistentEntityException, Exception {
        lremapAuthorsNorm.getLremapAuthorsNormPK().setResourceid(lremapAuthorsNorm.getLremapPapersNorm().getResourceid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapAuthorsNorm persistentLremapAuthorsNorm = em.find(LremapAuthorsNorm.class, lremapAuthorsNorm.getLremapAuthorsNormPK());
            LremapPapersNorm lremapPapersNormOld = persistentLremapAuthorsNorm.getLremapPapersNorm();
            LremapPapersNorm lremapPapersNormNew = lremapAuthorsNorm.getLremapPapersNorm();
            if (lremapPapersNormNew != null) {
                lremapPapersNormNew = em.getReference(lremapPapersNormNew.getClass(), lremapPapersNormNew.getResourceid());
                lremapAuthorsNorm.setLremapPapersNorm(lremapPapersNormNew);
            }
            lremapAuthorsNorm = em.merge(lremapAuthorsNorm);
            if (lremapPapersNormOld != null && !lremapPapersNormOld.equals(lremapPapersNormNew)) {
                lremapPapersNormOld.getLremapAuthorsNormList().remove(lremapAuthorsNorm);
                lremapPapersNormOld = em.merge(lremapPapersNormOld);
            }
            if (lremapPapersNormNew != null && !lremapPapersNormNew.equals(lremapPapersNormOld)) {
                lremapPapersNormNew.getLremapAuthorsNormList().add(lremapAuthorsNorm);
                lremapPapersNormNew = em.merge(lremapPapersNormNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LremapAuthorsNormPK id = lremapAuthorsNorm.getLremapAuthorsNormPK();
                if (findLremapAuthorsNorm(id) == null) {
                    throw new NonexistentEntityException("The lremapAuthorsNorm with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LremapAuthorsNormPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapAuthorsNorm lremapAuthorsNorm;
            try {
                lremapAuthorsNorm = em.getReference(LremapAuthorsNorm.class, id);
                lremapAuthorsNorm.getLremapAuthorsNormPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapAuthorsNorm with id " + id + " no longer exists.", enfe);
            }
            LremapPapersNorm lremapPapersNorm = lremapAuthorsNorm.getLremapPapersNorm();
            if (lremapPapersNorm != null) {
                lremapPapersNorm.getLremapAuthorsNormList().remove(lremapAuthorsNorm);
                lremapPapersNorm = em.merge(lremapPapersNorm);
            }
            em.remove(lremapAuthorsNorm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapAuthorsNorm> findLremapAuthorsNormEntities() {
        return findLremapAuthorsNormEntities(true, -1, -1);
    }

    public List<LremapAuthorsNorm> findLremapAuthorsNormEntities(int maxResults, int firstResult) {
        return findLremapAuthorsNormEntities(false, maxResults, firstResult);
    }

    private List<LremapAuthorsNorm> findLremapAuthorsNormEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapAuthorsNorm.class));
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

    public LremapAuthorsNorm findLremapAuthorsNorm(LremapAuthorsNormPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapAuthorsNorm.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapAuthorsNormCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapAuthorsNorm> rt = cq.from(LremapAuthorsNorm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
