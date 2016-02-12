/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.controllers;

import it.cnr.ilc.controllers.exceptions.IllegalOrphanException;
import it.cnr.ilc.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import it.cnr.ilc.lremap.entities.LremapResourceKeys;
import it.cnr.ilc.lremap.entities.LremapSubsNorm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapSubsNormJpaController implements Serializable {

    public LremapSubsNormJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapSubsNorm lremapSubsNorm) throws PreexistingEntityException, Exception {
        if (lremapSubsNorm.getLremapResourceKeysList() == null) {
            lremapSubsNorm.setLremapResourceKeysList(new ArrayList<LremapResourceKeys>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<LremapResourceKeys> attachedLremapResourceKeysList = new ArrayList<LremapResourceKeys>();
            for (LremapResourceKeys lremapResourceKeysListLremapResourceKeysToAttach : lremapSubsNorm.getLremapResourceKeysList()) {
                lremapResourceKeysListLremapResourceKeysToAttach = em.getReference(lremapResourceKeysListLremapResourceKeysToAttach.getClass(), lremapResourceKeysListLremapResourceKeysToAttach.getResourceid());
                attachedLremapResourceKeysList.add(lremapResourceKeysListLremapResourceKeysToAttach);
            }
            lremapSubsNorm.setLremapResourceKeysList(attachedLremapResourceKeysList);
            em.persist(lremapSubsNorm);
            for (LremapResourceKeys lremapResourceKeysListLremapResourceKeys : lremapSubsNorm.getLremapResourceKeysList()) {
                LremapSubsNorm oldResourceNormidOfLremapResourceKeysListLremapResourceKeys = lremapResourceKeysListLremapResourceKeys.getResourceNormid();
                lremapResourceKeysListLremapResourceKeys.setResourceNormid(lremapSubsNorm);
                lremapResourceKeysListLremapResourceKeys = em.merge(lremapResourceKeysListLremapResourceKeys);
                if (oldResourceNormidOfLremapResourceKeysListLremapResourceKeys != null) {
                    oldResourceNormidOfLremapResourceKeysListLremapResourceKeys.getLremapResourceKeysList().remove(lremapResourceKeysListLremapResourceKeys);
                    oldResourceNormidOfLremapResourceKeysListLremapResourceKeys = em.merge(oldResourceNormidOfLremapResourceKeysListLremapResourceKeys);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapSubsNorm(lremapSubsNorm.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapSubsNorm " + lremapSubsNorm + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapSubsNorm lremapSubsNorm) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapSubsNorm persistentLremapSubsNorm = em.find(LremapSubsNorm.class, lremapSubsNorm.getResourceid());
            List<LremapResourceKeys> lremapResourceKeysListOld = persistentLremapSubsNorm.getLremapResourceKeysList();
            List<LremapResourceKeys> lremapResourceKeysListNew = lremapSubsNorm.getLremapResourceKeysList();
            List<String> illegalOrphanMessages = null;
            for (LremapResourceKeys lremapResourceKeysListOldLremapResourceKeys : lremapResourceKeysListOld) {
                if (!lremapResourceKeysListNew.contains(lremapResourceKeysListOldLremapResourceKeys)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LremapResourceKeys " + lremapResourceKeysListOldLremapResourceKeys + " since its resourceNormid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<LremapResourceKeys> attachedLremapResourceKeysListNew = new ArrayList<LremapResourceKeys>();
            for (LremapResourceKeys lremapResourceKeysListNewLremapResourceKeysToAttach : lremapResourceKeysListNew) {
                lremapResourceKeysListNewLremapResourceKeysToAttach = em.getReference(lremapResourceKeysListNewLremapResourceKeysToAttach.getClass(), lremapResourceKeysListNewLremapResourceKeysToAttach.getResourceid());
                attachedLremapResourceKeysListNew.add(lremapResourceKeysListNewLremapResourceKeysToAttach);
            }
            lremapResourceKeysListNew = attachedLremapResourceKeysListNew;
            lremapSubsNorm.setLremapResourceKeysList(lremapResourceKeysListNew);
            lremapSubsNorm = em.merge(lremapSubsNorm);
            for (LremapResourceKeys lremapResourceKeysListNewLremapResourceKeys : lremapResourceKeysListNew) {
                if (!lremapResourceKeysListOld.contains(lremapResourceKeysListNewLremapResourceKeys)) {
                    LremapSubsNorm oldResourceNormidOfLremapResourceKeysListNewLremapResourceKeys = lremapResourceKeysListNewLremapResourceKeys.getResourceNormid();
                    lremapResourceKeysListNewLremapResourceKeys.setResourceNormid(lremapSubsNorm);
                    lremapResourceKeysListNewLremapResourceKeys = em.merge(lremapResourceKeysListNewLremapResourceKeys);
                    if (oldResourceNormidOfLremapResourceKeysListNewLremapResourceKeys != null && !oldResourceNormidOfLremapResourceKeysListNewLremapResourceKeys.equals(lremapSubsNorm)) {
                        oldResourceNormidOfLremapResourceKeysListNewLremapResourceKeys.getLremapResourceKeysList().remove(lremapResourceKeysListNewLremapResourceKeys);
                        oldResourceNormidOfLremapResourceKeysListNewLremapResourceKeys = em.merge(oldResourceNormidOfLremapResourceKeysListNewLremapResourceKeys);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapSubsNorm.getResourceid();
                if (findLremapSubsNorm(id) == null) {
                    throw new NonexistentEntityException("The lremapSubsNorm with id " + id + " no longer exists.");
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
            LremapSubsNorm lremapSubsNorm;
            try {
                lremapSubsNorm = em.getReference(LremapSubsNorm.class, id);
                lremapSubsNorm.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapSubsNorm with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<LremapResourceKeys> lremapResourceKeysListOrphanCheck = lremapSubsNorm.getLremapResourceKeysList();
            for (LremapResourceKeys lremapResourceKeysListOrphanCheckLremapResourceKeys : lremapResourceKeysListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapSubsNorm (" + lremapSubsNorm + ") cannot be destroyed since the LremapResourceKeys " + lremapResourceKeysListOrphanCheckLremapResourceKeys + " in its lremapResourceKeysList field has a non-nullable resourceNormid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(lremapSubsNorm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapSubsNorm> findLremapSubsNormEntities() {
        return findLremapSubsNormEntities(true, -1, -1);
    }

    public List<LremapSubsNorm> findLremapSubsNormEntities(int maxResults, int firstResult) {
        return findLremapSubsNormEntities(false, maxResults, firstResult);
    }

    private List<LremapSubsNorm> findLremapSubsNormEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapSubsNorm.class));
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

    public LremapSubsNorm findLremapSubsNorm(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapSubsNorm.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapSubsNormCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapSubsNorm> rt = cq.from(LremapSubsNorm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
