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
import it.cnr.ilc.lremap.entities.LremapResource;
import it.cnr.ilc.lremap.entities.LremapResourceLang;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapResourceLangJpaController implements Serializable {

    public LremapResourceLangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapResourceLang lremapResourceLang) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        LremapResource lremapResourceOrphanCheck = lremapResourceLang.getLremapResource();
        if (lremapResourceOrphanCheck != null) {
            LremapResourceLang oldLremapResourceLangOfLremapResource = lremapResourceOrphanCheck.getLremapResourceLang();
            if (oldLremapResourceLangOfLremapResource != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The LremapResource " + lremapResourceOrphanCheck + " already has an item of type LremapResourceLang whose lremapResource column cannot be null. Please make another selection for the lremapResource field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResource lremapResource = lremapResourceLang.getLremapResource();
            if (lremapResource != null) {
                lremapResource = em.getReference(lremapResource.getClass(), lremapResource.getResourceid());
                lremapResourceLang.setLremapResource(lremapResource);
            }
            em.persist(lremapResourceLang);
            if (lremapResource != null) {
                lremapResource.setLremapResourceLang(lremapResourceLang);
                lremapResource = em.merge(lremapResource);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapResourceLang(lremapResourceLang.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapResourceLang " + lremapResourceLang + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapResourceLang lremapResourceLang) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceLang persistentLremapResourceLang = em.find(LremapResourceLang.class, lremapResourceLang.getResourceid());
            LremapResource lremapResourceOld = persistentLremapResourceLang.getLremapResource();
            LremapResource lremapResourceNew = lremapResourceLang.getLremapResource();
            List<String> illegalOrphanMessages = null;
            if (lremapResourceNew != null && !lremapResourceNew.equals(lremapResourceOld)) {
                LremapResourceLang oldLremapResourceLangOfLremapResource = lremapResourceNew.getLremapResourceLang();
                if (oldLremapResourceLangOfLremapResource != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The LremapResource " + lremapResourceNew + " already has an item of type LremapResourceLang whose lremapResource column cannot be null. Please make another selection for the lremapResource field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lremapResourceNew != null) {
                lremapResourceNew = em.getReference(lremapResourceNew.getClass(), lremapResourceNew.getResourceid());
                lremapResourceLang.setLremapResource(lremapResourceNew);
            }
            lremapResourceLang = em.merge(lremapResourceLang);
            if (lremapResourceOld != null && !lremapResourceOld.equals(lremapResourceNew)) {
                lremapResourceOld.setLremapResourceLang(null);
                lremapResourceOld = em.merge(lremapResourceOld);
            }
            if (lremapResourceNew != null && !lremapResourceNew.equals(lremapResourceOld)) {
                lremapResourceNew.setLremapResourceLang(lremapResourceLang);
                lremapResourceNew = em.merge(lremapResourceNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapResourceLang.getResourceid();
                if (findLremapResourceLang(id) == null) {
                    throw new NonexistentEntityException("The lremapResourceLang with id " + id + " no longer exists.");
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
            LremapResourceLang lremapResourceLang;
            try {
                lremapResourceLang = em.getReference(LremapResourceLang.class, id);
                lremapResourceLang.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapResourceLang with id " + id + " no longer exists.", enfe);
            }
            LremapResource lremapResource = lremapResourceLang.getLremapResource();
            if (lremapResource != null) {
                lremapResource.setLremapResourceLang(null);
                lremapResource = em.merge(lremapResource);
            }
            em.remove(lremapResourceLang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapResourceLang> findLremapResourceLangEntities() {
        return findLremapResourceLangEntities(true, -1, -1);
    }

    public List<LremapResourceLang> findLremapResourceLangEntities(int maxResults, int firstResult) {
        return findLremapResourceLangEntities(false, maxResults, firstResult);
    }

    private List<LremapResourceLang> findLremapResourceLangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapResourceLang.class));
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

    public LremapResourceLang findLremapResourceLang(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapResourceLang.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapResourceLangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapResourceLang> rt = cq.from(LremapResourceLang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
