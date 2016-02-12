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
import it.cnr.ilc.lremap.entities.LremapSubs;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapSubsJpaController implements Serializable {

    public LremapSubsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapSubs lremapSubs) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceKeys lremapResourceKeys = lremapSubs.getLremapResourceKeys();
            if (lremapResourceKeys != null) {
                lremapResourceKeys = em.getReference(lremapResourceKeys.getClass(), lremapResourceKeys.getResourceid());
                lremapSubs.setLremapResourceKeys(lremapResourceKeys);
            }
            em.persist(lremapSubs);
            if (lremapResourceKeys != null) {
                LremapSubs oldLremapSubsOfLremapResourceKeys = lremapResourceKeys.getLremapSubs();
                if (oldLremapSubsOfLremapResourceKeys != null) {
                    oldLremapSubsOfLremapResourceKeys.setLremapResourceKeys(null);
                    oldLremapSubsOfLremapResourceKeys = em.merge(oldLremapSubsOfLremapResourceKeys);
                }
                lremapResourceKeys.setLremapSubs(lremapSubs);
                lremapResourceKeys = em.merge(lremapResourceKeys);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapSubs(lremapSubs.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapSubs " + lremapSubs + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapSubs lremapSubs) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapSubs persistentLremapSubs = em.find(LremapSubs.class, lremapSubs.getResourceid());
            LremapResourceKeys lremapResourceKeysOld = persistentLremapSubs.getLremapResourceKeys();
            LremapResourceKeys lremapResourceKeysNew = lremapSubs.getLremapResourceKeys();
            List<String> illegalOrphanMessages = null;
            if (lremapResourceKeysOld != null && !lremapResourceKeysOld.equals(lremapResourceKeysNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain LremapResourceKeys " + lremapResourceKeysOld + " since its lremapSubs field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lremapResourceKeysNew != null) {
                lremapResourceKeysNew = em.getReference(lremapResourceKeysNew.getClass(), lremapResourceKeysNew.getResourceid());
                lremapSubs.setLremapResourceKeys(lremapResourceKeysNew);
            }
            lremapSubs = em.merge(lremapSubs);
            if (lremapResourceKeysNew != null && !lremapResourceKeysNew.equals(lremapResourceKeysOld)) {
                LremapSubs oldLremapSubsOfLremapResourceKeys = lremapResourceKeysNew.getLremapSubs();
                if (oldLremapSubsOfLremapResourceKeys != null) {
                    oldLremapSubsOfLremapResourceKeys.setLremapResourceKeys(null);
                    oldLremapSubsOfLremapResourceKeys = em.merge(oldLremapSubsOfLremapResourceKeys);
                }
                lremapResourceKeysNew.setLremapSubs(lremapSubs);
                lremapResourceKeysNew = em.merge(lremapResourceKeysNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapSubs.getResourceid();
                if (findLremapSubs(id) == null) {
                    throw new NonexistentEntityException("The lremapSubs with id " + id + " no longer exists.");
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
            LremapSubs lremapSubs;
            try {
                lremapSubs = em.getReference(LremapSubs.class, id);
                lremapSubs.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapSubs with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            LremapResourceKeys lremapResourceKeysOrphanCheck = lremapSubs.getLremapResourceKeys();
            if (lremapResourceKeysOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapSubs (" + lremapSubs + ") cannot be destroyed since the LremapResourceKeys " + lremapResourceKeysOrphanCheck + " in its lremapResourceKeys field has a non-nullable lremapSubs field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(lremapSubs);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapSubs> findLremapSubsEntities() {
        return findLremapSubsEntities(true, -1, -1);
    }

    public List<LremapSubs> findLremapSubsEntities(int maxResults, int firstResult) {
        return findLremapSubsEntities(false, maxResults, firstResult);
    }

    private List<LremapSubs> findLremapSubsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapSubs.class));
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

    public LremapSubs findLremapSubs(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapSubs.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapSubsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapSubs> rt = cq.from(LremapSubs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
