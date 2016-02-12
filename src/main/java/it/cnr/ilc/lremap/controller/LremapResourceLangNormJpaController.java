/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controller;

import it.cnr.ilc.lremap.controllers.exceptions.IllegalOrphanException;
import it.cnr.ilc.lremap.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.controllers.exceptions.PreexistingEntityException;
import it.cnr.ilc.lremap.entities.LremapResourceLangNorm;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import it.cnr.ilc.lremap.entities.LremapResourceNorm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapResourceLangNormJpaController implements Serializable {

    public LremapResourceLangNormJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapResourceLangNorm lremapResourceLangNorm) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        LremapResourceNorm lremapResourceNormOrphanCheck = lremapResourceLangNorm.getLremapResourceNorm();
        if (lremapResourceNormOrphanCheck != null) {
            LremapResourceLangNorm oldLremapResourceLangNormOfLremapResourceNorm = lremapResourceNormOrphanCheck.getLremapResourceLangNorm();
            if (oldLremapResourceLangNormOfLremapResourceNorm != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The LremapResourceNorm " + lremapResourceNormOrphanCheck + " already has an item of type LremapResourceLangNorm whose lremapResourceNorm column cannot be null. Please make another selection for the lremapResourceNorm field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceNorm lremapResourceNorm = lremapResourceLangNorm.getLremapResourceNorm();
            if (lremapResourceNorm != null) {
                lremapResourceNorm = em.getReference(lremapResourceNorm.getClass(), lremapResourceNorm.getResourceid());
                lremapResourceLangNorm.setLremapResourceNorm(lremapResourceNorm);
            }
            em.persist(lremapResourceLangNorm);
            if (lremapResourceNorm != null) {
                lremapResourceNorm.setLremapResourceLangNorm(lremapResourceLangNorm);
                lremapResourceNorm = em.merge(lremapResourceNorm);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapResourceLangNorm(lremapResourceLangNorm.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapResourceLangNorm " + lremapResourceLangNorm + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapResourceLangNorm lremapResourceLangNorm) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceLangNorm persistentLremapResourceLangNorm = em.find(LremapResourceLangNorm.class, lremapResourceLangNorm.getResourceid());
            LremapResourceNorm lremapResourceNormOld = persistentLremapResourceLangNorm.getLremapResourceNorm();
            LremapResourceNorm lremapResourceNormNew = lremapResourceLangNorm.getLremapResourceNorm();
            List<String> illegalOrphanMessages = null;
            if (lremapResourceNormNew != null && !lremapResourceNormNew.equals(lremapResourceNormOld)) {
                LremapResourceLangNorm oldLremapResourceLangNormOfLremapResourceNorm = lremapResourceNormNew.getLremapResourceLangNorm();
                if (oldLremapResourceLangNormOfLremapResourceNorm != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The LremapResourceNorm " + lremapResourceNormNew + " already has an item of type LremapResourceLangNorm whose lremapResourceNorm column cannot be null. Please make another selection for the lremapResourceNorm field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lremapResourceNormNew != null) {
                lremapResourceNormNew = em.getReference(lremapResourceNormNew.getClass(), lremapResourceNormNew.getResourceid());
                lremapResourceLangNorm.setLremapResourceNorm(lremapResourceNormNew);
            }
            lremapResourceLangNorm = em.merge(lremapResourceLangNorm);
            if (lremapResourceNormOld != null && !lremapResourceNormOld.equals(lremapResourceNormNew)) {
                lremapResourceNormOld.setLremapResourceLangNorm(null);
                lremapResourceNormOld = em.merge(lremapResourceNormOld);
            }
            if (lremapResourceNormNew != null && !lremapResourceNormNew.equals(lremapResourceNormOld)) {
                lremapResourceNormNew.setLremapResourceLangNorm(lremapResourceLangNorm);
                lremapResourceNormNew = em.merge(lremapResourceNormNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapResourceLangNorm.getResourceid();
                if (findLremapResourceLangNorm(id) == null) {
                    throw new NonexistentEntityException("The lremapResourceLangNorm with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceLangNorm lremapResourceLangNorm;
            try {
                lremapResourceLangNorm = em.getReference(LremapResourceLangNorm.class, id);
                lremapResourceLangNorm.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapResourceLangNorm with id " + id + " no longer exists.", enfe);
            }
            LremapResourceNorm lremapResourceNorm = lremapResourceLangNorm.getLremapResourceNorm();
            if (lremapResourceNorm != null) {
                lremapResourceNorm.setLremapResourceLangNorm(null);
                lremapResourceNorm = em.merge(lremapResourceNorm);
            }
            em.remove(lremapResourceLangNorm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapResourceLangNorm> findLremapResourceLangNormEntities() {
        return findLremapResourceLangNormEntities(true, -1, -1);
    }

    public List<LremapResourceLangNorm> findLremapResourceLangNormEntities(int maxResults, int firstResult) {
        return findLremapResourceLangNormEntities(false, maxResults, firstResult);
    }

    private List<LremapResourceLangNorm> findLremapResourceLangNormEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapResourceLangNorm.class));
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

    public LremapResourceLangNorm findLremapResourceLangNorm(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapResourceLangNorm.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapResourceLangNormCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapResourceLangNorm> rt = cq.from(LremapResourceLangNorm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
