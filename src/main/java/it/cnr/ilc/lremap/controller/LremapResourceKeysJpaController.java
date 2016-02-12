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
import it.cnr.ilc.lremap.entities.LremapResourceNorm;
import it.cnr.ilc.lremap.entities.LremapResource;
import it.cnr.ilc.lremap.entities.LremapResourceKeys;
import it.cnr.ilc.lremap.entities.LremapSubs;
import it.cnr.ilc.lremap.entities.LremapSubsNorm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapResourceKeysJpaController implements Serializable {

    public LremapResourceKeysJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapResourceKeys lremapResourceKeys) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        LremapSubs lremapSubsOrphanCheck = lremapResourceKeys.getLremapSubs();
        if (lremapSubsOrphanCheck != null) {
            LremapResourceKeys oldLremapResourceKeysOfLremapSubs = lremapSubsOrphanCheck.getLremapResourceKeys();
            if (oldLremapResourceKeysOfLremapSubs != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The LremapSubs " + lremapSubsOrphanCheck + " already has an item of type LremapResourceKeys whose lremapSubs column cannot be null. Please make another selection for the lremapSubs field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceNorm lremapResourceNorm = lremapResourceKeys.getLremapResourceNorm();
            if (lremapResourceNorm != null) {
                lremapResourceNorm = em.getReference(lremapResourceNorm.getClass(), lremapResourceNorm.getResourceid());
                lremapResourceKeys.setLremapResourceNorm(lremapResourceNorm);
            }
            LremapResource lremapResource = lremapResourceKeys.getLremapResource();
            if (lremapResource != null) {
                lremapResource = em.getReference(lremapResource.getClass(), lremapResource.getResourceid());
                lremapResourceKeys.setLremapResource(lremapResource);
            }
            LremapSubs lremapSubs = lremapResourceKeys.getLremapSubs();
            if (lremapSubs != null) {
                lremapSubs = em.getReference(lremapSubs.getClass(), lremapSubs.getResourceid());
                lremapResourceKeys.setLremapSubs(lremapSubs);
            }
            LremapSubsNorm resourceNormid = lremapResourceKeys.getResourceNormid();
            if (resourceNormid != null) {
                resourceNormid = em.getReference(resourceNormid.getClass(), resourceNormid.getResourceid());
                lremapResourceKeys.setResourceNormid(resourceNormid);
            }
            em.persist(lremapResourceKeys);
            if (lremapResourceNorm != null) {
                LremapResourceKeys oldLremapResourceKeysOfLremapResourceNorm = lremapResourceNorm.getLremapResourceKeys();
                if (oldLremapResourceKeysOfLremapResourceNorm != null) {
                    oldLremapResourceKeysOfLremapResourceNorm.setLremapResourceNorm(null);
                    oldLremapResourceKeysOfLremapResourceNorm = em.merge(oldLremapResourceKeysOfLremapResourceNorm);
                }
                lremapResourceNorm.setLremapResourceKeys(lremapResourceKeys);
                lremapResourceNorm = em.merge(lremapResourceNorm);
            }
            if (lremapResource != null) {
                LremapResourceKeys oldLremapResourceKeysOfLremapResource = lremapResource.getLremapResourceKeys();
                if (oldLremapResourceKeysOfLremapResource != null) {
                    oldLremapResourceKeysOfLremapResource.setLremapResource(null);
                    oldLremapResourceKeysOfLremapResource = em.merge(oldLremapResourceKeysOfLremapResource);
                }
                lremapResource.setLremapResourceKeys(lremapResourceKeys);
                lremapResource = em.merge(lremapResource);
            }
            if (lremapSubs != null) {
                lremapSubs.setLremapResourceKeys(lremapResourceKeys);
                lremapSubs = em.merge(lremapSubs);
            }
            if (resourceNormid != null) {
                resourceNormid.getLremapResourceKeysList().add(lremapResourceKeys);
                resourceNormid = em.merge(resourceNormid);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapResourceKeys(lremapResourceKeys.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapResourceKeys " + lremapResourceKeys + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapResourceKeys lremapResourceKeys) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceKeys persistentLremapResourceKeys = em.find(LremapResourceKeys.class, lremapResourceKeys.getResourceid());
            LremapResourceNorm lremapResourceNormOld = persistentLremapResourceKeys.getLremapResourceNorm();
            LremapResourceNorm lremapResourceNormNew = lremapResourceKeys.getLremapResourceNorm();
            LremapResource lremapResourceOld = persistentLremapResourceKeys.getLremapResource();
            LremapResource lremapResourceNew = lremapResourceKeys.getLremapResource();
            LremapSubs lremapSubsOld = persistentLremapResourceKeys.getLremapSubs();
            LremapSubs lremapSubsNew = lremapResourceKeys.getLremapSubs();
            LremapSubsNorm resourceNormidOld = persistentLremapResourceKeys.getResourceNormid();
            LremapSubsNorm resourceNormidNew = lremapResourceKeys.getResourceNormid();
            List<String> illegalOrphanMessages = null;
            if (lremapResourceNormOld != null && !lremapResourceNormOld.equals(lremapResourceNormNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain LremapResourceNorm " + lremapResourceNormOld + " since its lremapResourceKeys field is not nullable.");
            }
            if (lremapResourceOld != null && !lremapResourceOld.equals(lremapResourceNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain LremapResource " + lremapResourceOld + " since its lremapResourceKeys field is not nullable.");
            }
            if (lremapSubsNew != null && !lremapSubsNew.equals(lremapSubsOld)) {
                LremapResourceKeys oldLremapResourceKeysOfLremapSubs = lremapSubsNew.getLremapResourceKeys();
                if (oldLremapResourceKeysOfLremapSubs != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The LremapSubs " + lremapSubsNew + " already has an item of type LremapResourceKeys whose lremapSubs column cannot be null. Please make another selection for the lremapSubs field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lremapResourceNormNew != null) {
                lremapResourceNormNew = em.getReference(lremapResourceNormNew.getClass(), lremapResourceNormNew.getResourceid());
                lremapResourceKeys.setLremapResourceNorm(lremapResourceNormNew);
            }
            if (lremapResourceNew != null) {
                lremapResourceNew = em.getReference(lremapResourceNew.getClass(), lremapResourceNew.getResourceid());
                lremapResourceKeys.setLremapResource(lremapResourceNew);
            }
            if (lremapSubsNew != null) {
                lremapSubsNew = em.getReference(lremapSubsNew.getClass(), lremapSubsNew.getResourceid());
                lremapResourceKeys.setLremapSubs(lremapSubsNew);
            }
            if (resourceNormidNew != null) {
                resourceNormidNew = em.getReference(resourceNormidNew.getClass(), resourceNormidNew.getResourceid());
                lremapResourceKeys.setResourceNormid(resourceNormidNew);
            }
            lremapResourceKeys = em.merge(lremapResourceKeys);
            if (lremapResourceNormNew != null && !lremapResourceNormNew.equals(lremapResourceNormOld)) {
                LremapResourceKeys oldLremapResourceKeysOfLremapResourceNorm = lremapResourceNormNew.getLremapResourceKeys();
                if (oldLremapResourceKeysOfLremapResourceNorm != null) {
                    oldLremapResourceKeysOfLremapResourceNorm.setLremapResourceNorm(null);
                    oldLremapResourceKeysOfLremapResourceNorm = em.merge(oldLremapResourceKeysOfLremapResourceNorm);
                }
                lremapResourceNormNew.setLremapResourceKeys(lremapResourceKeys);
                lremapResourceNormNew = em.merge(lremapResourceNormNew);
            }
            if (lremapResourceNew != null && !lremapResourceNew.equals(lremapResourceOld)) {
                LremapResourceKeys oldLremapResourceKeysOfLremapResource = lremapResourceNew.getLremapResourceKeys();
                if (oldLremapResourceKeysOfLremapResource != null) {
                    oldLremapResourceKeysOfLremapResource.setLremapResource(null);
                    oldLremapResourceKeysOfLremapResource = em.merge(oldLremapResourceKeysOfLremapResource);
                }
                lremapResourceNew.setLremapResourceKeys(lremapResourceKeys);
                lremapResourceNew = em.merge(lremapResourceNew);
            }
            if (lremapSubsOld != null && !lremapSubsOld.equals(lremapSubsNew)) {
                lremapSubsOld.setLremapResourceKeys(null);
                lremapSubsOld = em.merge(lremapSubsOld);
            }
            if (lremapSubsNew != null && !lremapSubsNew.equals(lremapSubsOld)) {
                lremapSubsNew.setLremapResourceKeys(lremapResourceKeys);
                lremapSubsNew = em.merge(lremapSubsNew);
            }
            if (resourceNormidOld != null && !resourceNormidOld.equals(resourceNormidNew)) {
                resourceNormidOld.getLremapResourceKeysList().remove(lremapResourceKeys);
                resourceNormidOld = em.merge(resourceNormidOld);
            }
            if (resourceNormidNew != null && !resourceNormidNew.equals(resourceNormidOld)) {
                resourceNormidNew.getLremapResourceKeysList().add(lremapResourceKeys);
                resourceNormidNew = em.merge(resourceNormidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapResourceKeys.getResourceid();
                if (findLremapResourceKeys(id) == null) {
                    throw new NonexistentEntityException("The lremapResourceKeys with id " + id + " no longer exists.");
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
            LremapResourceKeys lremapResourceKeys;
            try {
                lremapResourceKeys = em.getReference(LremapResourceKeys.class, id);
                lremapResourceKeys.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapResourceKeys with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            LremapResourceNorm lremapResourceNormOrphanCheck = lremapResourceKeys.getLremapResourceNorm();
            if (lremapResourceNormOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapResourceKeys (" + lremapResourceKeys + ") cannot be destroyed since the LremapResourceNorm " + lremapResourceNormOrphanCheck + " in its lremapResourceNorm field has a non-nullable lremapResourceKeys field.");
            }
            LremapResource lremapResourceOrphanCheck = lremapResourceKeys.getLremapResource();
            if (lremapResourceOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapResourceKeys (" + lremapResourceKeys + ") cannot be destroyed since the LremapResource " + lremapResourceOrphanCheck + " in its lremapResource field has a non-nullable lremapResourceKeys field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            LremapSubs lremapSubs = lremapResourceKeys.getLremapSubs();
            if (lremapSubs != null) {
                lremapSubs.setLremapResourceKeys(null);
                lremapSubs = em.merge(lremapSubs);
            }
            LremapSubsNorm resourceNormid = lremapResourceKeys.getResourceNormid();
            if (resourceNormid != null) {
                resourceNormid.getLremapResourceKeysList().remove(lremapResourceKeys);
                resourceNormid = em.merge(resourceNormid);
            }
            em.remove(lremapResourceKeys);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapResourceKeys> findLremapResourceKeysEntities() {
        return findLremapResourceKeysEntities(true, -1, -1);
    }

    public List<LremapResourceKeys> findLremapResourceKeysEntities(int maxResults, int firstResult) {
        return findLremapResourceKeysEntities(false, maxResults, firstResult);
    }

    private List<LremapResourceKeys> findLremapResourceKeysEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapResourceKeys.class));
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

    public LremapResourceKeys findLremapResourceKeys(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapResourceKeys.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapResourceKeysCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapResourceKeys> rt = cq.from(LremapResourceKeys.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
