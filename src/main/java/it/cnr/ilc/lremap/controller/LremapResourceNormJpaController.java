/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controller;

import it.cnr.ilc.lremap.controllers.exceptions.IllegalOrphanException;
import it.cnr.ilc.lremap.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.lremap.controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import it.cnr.ilc.lremap.entities.LremapResourceKeys;
import it.cnr.ilc.lremap.entities.LremapResourceLangNorm;
import it.cnr.ilc.lremap.entities.LremapPapersNorm;
import it.cnr.ilc.lremap.entities.LremapResourceNorm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapResourceNormJpaController implements Serializable {

    public LremapResourceNormJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapResourceNorm lremapResourceNorm) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        LremapResourceKeys lremapResourceKeysOrphanCheck = lremapResourceNorm.getLremapResourceKeys();
        if (lremapResourceKeysOrphanCheck != null) {
            LremapResourceNorm oldLremapResourceNormOfLremapResourceKeys = lremapResourceKeysOrphanCheck.getLremapResourceNorm();
            if (oldLremapResourceNormOfLremapResourceKeys != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The LremapResourceKeys " + lremapResourceKeysOrphanCheck + " already has an item of type LremapResourceNorm whose lremapResourceKeys column cannot be null. Please make another selection for the lremapResourceKeys field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceKeys lremapResourceKeys = lremapResourceNorm.getLremapResourceKeys();
            if (lremapResourceKeys != null) {
                lremapResourceKeys = em.getReference(lremapResourceKeys.getClass(), lremapResourceKeys.getResourceid());
                lremapResourceNorm.setLremapResourceKeys(lremapResourceKeys);
            }
            LremapResourceLangNorm lremapResourceLangNorm = lremapResourceNorm.getLremapResourceLangNorm();
            if (lremapResourceLangNorm != null) {
                lremapResourceLangNorm = em.getReference(lremapResourceLangNorm.getClass(), lremapResourceLangNorm.getResourceid());
                lremapResourceNorm.setLremapResourceLangNorm(lremapResourceLangNorm);
            }
            LremapPapersNorm lremapPapersNorm = lremapResourceNorm.getLremapPapersNorm();
            if (lremapPapersNorm != null) {
                lremapPapersNorm = em.getReference(lremapPapersNorm.getClass(), lremapPapersNorm.getResourceid());
                lremapResourceNorm.setLremapPapersNorm(lremapPapersNorm);
            }
            em.persist(lremapResourceNorm);
            if (lremapResourceKeys != null) {
                lremapResourceKeys.setLremapResourceNorm(lremapResourceNorm);
                lremapResourceKeys = em.merge(lremapResourceKeys);
            }
            if (lremapResourceLangNorm != null) {
                LremapResourceNorm oldLremapResourceNormOfLremapResourceLangNorm = lremapResourceLangNorm.getLremapResourceNorm();
                if (oldLremapResourceNormOfLremapResourceLangNorm != null) {
                    oldLremapResourceNormOfLremapResourceLangNorm.setLremapResourceLangNorm(null);
                    oldLremapResourceNormOfLremapResourceLangNorm = em.merge(oldLremapResourceNormOfLremapResourceLangNorm);
                }
                lremapResourceLangNorm.setLremapResourceNorm(lremapResourceNorm);
                lremapResourceLangNorm = em.merge(lremapResourceLangNorm);
            }
            if (lremapPapersNorm != null) {
                LremapResourceNorm oldLremapResourceNormOfLremapPapersNorm = lremapPapersNorm.getLremapResourceNorm();
                if (oldLremapResourceNormOfLremapPapersNorm != null) {
                    oldLremapResourceNormOfLremapPapersNorm.setLremapPapersNorm(null);
                    oldLremapResourceNormOfLremapPapersNorm = em.merge(oldLremapResourceNormOfLremapPapersNorm);
                }
                lremapPapersNorm.setLremapResourceNorm(lremapResourceNorm);
                lremapPapersNorm = em.merge(lremapPapersNorm);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapResourceNorm(lremapResourceNorm.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapResourceNorm " + lremapResourceNorm + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapResourceNorm lremapResourceNorm) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceNorm persistentLremapResourceNorm = em.find(LremapResourceNorm.class, lremapResourceNorm.getResourceid());
            LremapResourceKeys lremapResourceKeysOld = persistentLremapResourceNorm.getLremapResourceKeys();
            LremapResourceKeys lremapResourceKeysNew = lremapResourceNorm.getLremapResourceKeys();
            LremapResourceLangNorm lremapResourceLangNormOld = persistentLremapResourceNorm.getLremapResourceLangNorm();
            LremapResourceLangNorm lremapResourceLangNormNew = lremapResourceNorm.getLremapResourceLangNorm();
            LremapPapersNorm lremapPapersNormOld = persistentLremapResourceNorm.getLremapPapersNorm();
            LremapPapersNorm lremapPapersNormNew = lremapResourceNorm.getLremapPapersNorm();
            List<String> illegalOrphanMessages = null;
            if (lremapResourceKeysNew != null && !lremapResourceKeysNew.equals(lremapResourceKeysOld)) {
                LremapResourceNorm oldLremapResourceNormOfLremapResourceKeys = lremapResourceKeysNew.getLremapResourceNorm();
                if (oldLremapResourceNormOfLremapResourceKeys != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The LremapResourceKeys " + lremapResourceKeysNew + " already has an item of type LremapResourceNorm whose lremapResourceKeys column cannot be null. Please make another selection for the lremapResourceKeys field.");
                }
            }
            if (lremapResourceLangNormOld != null && !lremapResourceLangNormOld.equals(lremapResourceLangNormNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain LremapResourceLangNorm " + lremapResourceLangNormOld + " since its lremapResourceNorm field is not nullable.");
            }
            if (lremapPapersNormOld != null && !lremapPapersNormOld.equals(lremapPapersNormNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain LremapPapersNorm " + lremapPapersNormOld + " since its lremapResourceNorm field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lremapResourceKeysNew != null) {
                lremapResourceKeysNew = em.getReference(lremapResourceKeysNew.getClass(), lremapResourceKeysNew.getResourceid());
                lremapResourceNorm.setLremapResourceKeys(lremapResourceKeysNew);
            }
            if (lremapResourceLangNormNew != null) {
                lremapResourceLangNormNew = em.getReference(lremapResourceLangNormNew.getClass(), lremapResourceLangNormNew.getResourceid());
                lremapResourceNorm.setLremapResourceLangNorm(lremapResourceLangNormNew);
            }
            if (lremapPapersNormNew != null) {
                lremapPapersNormNew = em.getReference(lremapPapersNormNew.getClass(), lremapPapersNormNew.getResourceid());
                lremapResourceNorm.setLremapPapersNorm(lremapPapersNormNew);
            }
            lremapResourceNorm = em.merge(lremapResourceNorm);
            if (lremapResourceKeysOld != null && !lremapResourceKeysOld.equals(lremapResourceKeysNew)) {
                lremapResourceKeysOld.setLremapResourceNorm(null);
                lremapResourceKeysOld = em.merge(lremapResourceKeysOld);
            }
            if (lremapResourceKeysNew != null && !lremapResourceKeysNew.equals(lremapResourceKeysOld)) {
                lremapResourceKeysNew.setLremapResourceNorm(lremapResourceNorm);
                lremapResourceKeysNew = em.merge(lremapResourceKeysNew);
            }
            if (lremapResourceLangNormNew != null && !lremapResourceLangNormNew.equals(lremapResourceLangNormOld)) {
                LremapResourceNorm oldLremapResourceNormOfLremapResourceLangNorm = lremapResourceLangNormNew.getLremapResourceNorm();
                if (oldLremapResourceNormOfLremapResourceLangNorm != null) {
                    oldLremapResourceNormOfLremapResourceLangNorm.setLremapResourceLangNorm(null);
                    oldLremapResourceNormOfLremapResourceLangNorm = em.merge(oldLremapResourceNormOfLremapResourceLangNorm);
                }
                lremapResourceLangNormNew.setLremapResourceNorm(lremapResourceNorm);
                lremapResourceLangNormNew = em.merge(lremapResourceLangNormNew);
            }
            if (lremapPapersNormNew != null && !lremapPapersNormNew.equals(lremapPapersNormOld)) {
                LremapResourceNorm oldLremapResourceNormOfLremapPapersNorm = lremapPapersNormNew.getLremapResourceNorm();
                if (oldLremapResourceNormOfLremapPapersNorm != null) {
                    oldLremapResourceNormOfLremapPapersNorm.setLremapPapersNorm(null);
                    oldLremapResourceNormOfLremapPapersNorm = em.merge(oldLremapResourceNormOfLremapPapersNorm);
                }
                lremapPapersNormNew.setLremapResourceNorm(lremapResourceNorm);
                lremapPapersNormNew = em.merge(lremapPapersNormNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapResourceNorm.getResourceid();
                if (findLremapResourceNorm(id) == null) {
                    throw new NonexistentEntityException("The lremapResourceNorm with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceNorm lremapResourceNorm;
            try {
                lremapResourceNorm = em.getReference(LremapResourceNorm.class, id);
                lremapResourceNorm.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapResourceNorm with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            LremapResourceLangNorm lremapResourceLangNormOrphanCheck = lremapResourceNorm.getLremapResourceLangNorm();
            if (lremapResourceLangNormOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapResourceNorm (" + lremapResourceNorm + ") cannot be destroyed since the LremapResourceLangNorm " + lremapResourceLangNormOrphanCheck + " in its lremapResourceLangNorm field has a non-nullable lremapResourceNorm field.");
            }
            LremapPapersNorm lremapPapersNormOrphanCheck = lremapResourceNorm.getLremapPapersNorm();
            if (lremapPapersNormOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapResourceNorm (" + lremapResourceNorm + ") cannot be destroyed since the LremapPapersNorm " + lremapPapersNormOrphanCheck + " in its lremapPapersNorm field has a non-nullable lremapResourceNorm field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            LremapResourceKeys lremapResourceKeys = lremapResourceNorm.getLremapResourceKeys();
            if (lremapResourceKeys != null) {
                lremapResourceKeys.setLremapResourceNorm(null);
                lremapResourceKeys = em.merge(lremapResourceKeys);
            }
            em.remove(lremapResourceNorm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapResourceNorm> findLremapResourceNormEntities() {
        return findLremapResourceNormEntities(true, -1, -1);
    }

    public List<LremapResourceNorm> findLremapResourceNormEntities(int maxResults, int firstResult) {
        return findLremapResourceNormEntities(false, maxResults, firstResult);
    }

    private List<LremapResourceNorm> findLremapResourceNormEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapResourceNorm.class));
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

    public LremapResourceNorm findLremapResourceNorm(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapResourceNorm.class, id);
        } finally {
            em.close();
        }
    }

    
    
    public List<String> findDistinctTypesFromLremapResourceNorm() {
        List<String> out;
        EntityManager em = getEntityManager();
        
        Query q = em.createNamedQuery("LremapResourceNorm.findDistinctType");
        try {
            out=q.getResultList();
            //System.err.println("AAA "+out);
            return out;
        } finally {
            em.close();
        }
    }

    public int getLremapResourceNormCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapResourceNorm> rt = cq.from(LremapResourceNorm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
