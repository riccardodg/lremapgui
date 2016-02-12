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
import it.cnr.ilc.lremap.entities.LremapPapers;
import it.cnr.ilc.lremap.entities.LremapResource;
import it.cnr.ilc.lremap.entities.LremapResourceLang;
import it.cnr.ilc.lremap.entities.LremapResourceKeys;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapResourceJpaController implements Serializable {

    public LremapResourceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapResource lremapResource) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        LremapResourceKeys lremapResourceKeysOrphanCheck = lremapResource.getLremapResourceKeys();
        if (lremapResourceKeysOrphanCheck != null) {
            LremapResource oldLremapResourceOfLremapResourceKeys = lremapResourceKeysOrphanCheck.getLremapResource();
            if (oldLremapResourceOfLremapResourceKeys != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The LremapResourceKeys " + lremapResourceKeysOrphanCheck + " already has an item of type LremapResource whose lremapResourceKeys column cannot be null. Please make another selection for the lremapResourceKeys field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapPapers lremapPapers = lremapResource.getLremapPapers();
            if (lremapPapers != null) {
                lremapPapers = em.getReference(lremapPapers.getClass(), lremapPapers.getResourceid());
                lremapResource.setLremapPapers(lremapPapers);
            }
            LremapResourceLang lremapResourceLang = lremapResource.getLremapResourceLang();
            if (lremapResourceLang != null) {
                lremapResourceLang = em.getReference(lremapResourceLang.getClass(), lremapResourceLang.getResourceid());
                lremapResource.setLremapResourceLang(lremapResourceLang);
            }
            LremapResourceKeys lremapResourceKeys = lremapResource.getLremapResourceKeys();
            if (lremapResourceKeys != null) {
                lremapResourceKeys = em.getReference(lremapResourceKeys.getClass(), lremapResourceKeys.getResourceid());
                lremapResource.setLremapResourceKeys(lremapResourceKeys);
            }
            em.persist(lremapResource);
            if (lremapPapers != null) {
                LremapResource oldLremapResourceOfLremapPapers = lremapPapers.getLremapResource();
                if (oldLremapResourceOfLremapPapers != null) {
                    oldLremapResourceOfLremapPapers.setLremapPapers(null);
                    oldLremapResourceOfLremapPapers = em.merge(oldLremapResourceOfLremapPapers);
                }
                lremapPapers.setLremapResource(lremapResource);
                lremapPapers = em.merge(lremapPapers);
            }
            if (lremapResourceLang != null) {
                LremapResource oldLremapResourceOfLremapResourceLang = lremapResourceLang.getLremapResource();
                if (oldLremapResourceOfLremapResourceLang != null) {
                    oldLremapResourceOfLremapResourceLang.setLremapResourceLang(null);
                    oldLremapResourceOfLremapResourceLang = em.merge(oldLremapResourceOfLremapResourceLang);
                }
                lremapResourceLang.setLremapResource(lremapResource);
                lremapResourceLang = em.merge(lremapResourceLang);
            }
            if (lremapResourceKeys != null) {
                lremapResourceKeys.setLremapResource(lremapResource);
                lremapResourceKeys = em.merge(lremapResourceKeys);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapResource(lremapResource.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapResource " + lremapResource + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapResource lremapResource) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResource persistentLremapResource = em.find(LremapResource.class, lremapResource.getResourceid());
            LremapPapers lremapPapersOld = persistentLremapResource.getLremapPapers();
            LremapPapers lremapPapersNew = lremapResource.getLremapPapers();
            LremapResourceLang lremapResourceLangOld = persistentLremapResource.getLremapResourceLang();
            LremapResourceLang lremapResourceLangNew = lremapResource.getLremapResourceLang();
            LremapResourceKeys lremapResourceKeysOld = persistentLremapResource.getLremapResourceKeys();
            LremapResourceKeys lremapResourceKeysNew = lremapResource.getLremapResourceKeys();
            List<String> illegalOrphanMessages = null;
            if (lremapPapersOld != null && !lremapPapersOld.equals(lremapPapersNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain LremapPapers " + lremapPapersOld + " since its lremapResource field is not nullable.");
            }
            if (lremapResourceLangOld != null && !lremapResourceLangOld.equals(lremapResourceLangNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain LremapResourceLang " + lremapResourceLangOld + " since its lremapResource field is not nullable.");
            }
            if (lremapResourceKeysNew != null && !lremapResourceKeysNew.equals(lremapResourceKeysOld)) {
                LremapResource oldLremapResourceOfLremapResourceKeys = lremapResourceKeysNew.getLremapResource();
                if (oldLremapResourceOfLremapResourceKeys != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The LremapResourceKeys " + lremapResourceKeysNew + " already has an item of type LremapResource whose lremapResourceKeys column cannot be null. Please make another selection for the lremapResourceKeys field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lremapPapersNew != null) {
                lremapPapersNew = em.getReference(lremapPapersNew.getClass(), lremapPapersNew.getResourceid());
                lremapResource.setLremapPapers(lremapPapersNew);
            }
            if (lremapResourceLangNew != null) {
                lremapResourceLangNew = em.getReference(lremapResourceLangNew.getClass(), lremapResourceLangNew.getResourceid());
                lremapResource.setLremapResourceLang(lremapResourceLangNew);
            }
            if (lremapResourceKeysNew != null) {
                lremapResourceKeysNew = em.getReference(lremapResourceKeysNew.getClass(), lremapResourceKeysNew.getResourceid());
                lremapResource.setLremapResourceKeys(lremapResourceKeysNew);
            }
            lremapResource = em.merge(lremapResource);
            if (lremapPapersNew != null && !lremapPapersNew.equals(lremapPapersOld)) {
                LremapResource oldLremapResourceOfLremapPapers = lremapPapersNew.getLremapResource();
                if (oldLremapResourceOfLremapPapers != null) {
                    oldLremapResourceOfLremapPapers.setLremapPapers(null);
                    oldLremapResourceOfLremapPapers = em.merge(oldLremapResourceOfLremapPapers);
                }
                lremapPapersNew.setLremapResource(lremapResource);
                lremapPapersNew = em.merge(lremapPapersNew);
            }
            if (lremapResourceLangNew != null && !lremapResourceLangNew.equals(lremapResourceLangOld)) {
                LremapResource oldLremapResourceOfLremapResourceLang = lremapResourceLangNew.getLremapResource();
                if (oldLremapResourceOfLremapResourceLang != null) {
                    oldLremapResourceOfLremapResourceLang.setLremapResourceLang(null);
                    oldLremapResourceOfLremapResourceLang = em.merge(oldLremapResourceOfLremapResourceLang);
                }
                lremapResourceLangNew.setLremapResource(lremapResource);
                lremapResourceLangNew = em.merge(lremapResourceLangNew);
            }
            if (lremapResourceKeysOld != null && !lremapResourceKeysOld.equals(lremapResourceKeysNew)) {
                lremapResourceKeysOld.setLremapResource(null);
                lremapResourceKeysOld = em.merge(lremapResourceKeysOld);
            }
            if (lremapResourceKeysNew != null && !lremapResourceKeysNew.equals(lremapResourceKeysOld)) {
                lremapResourceKeysNew.setLremapResource(lremapResource);
                lremapResourceKeysNew = em.merge(lremapResourceKeysNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapResource.getResourceid();
                if (findLremapResource(id) == null) {
                    throw new NonexistentEntityException("The lremapResource with id " + id + " no longer exists.");
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
            LremapResource lremapResource;
            try {
                lremapResource = em.getReference(LremapResource.class, id);
                lremapResource.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapResource with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            LremapPapers lremapPapersOrphanCheck = lremapResource.getLremapPapers();
            if (lremapPapersOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapResource (" + lremapResource + ") cannot be destroyed since the LremapPapers " + lremapPapersOrphanCheck + " in its lremapPapers field has a non-nullable lremapResource field.");
            }
            LremapResourceLang lremapResourceLangOrphanCheck = lremapResource.getLremapResourceLang();
            if (lremapResourceLangOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapResource (" + lremapResource + ") cannot be destroyed since the LremapResourceLang " + lremapResourceLangOrphanCheck + " in its lremapResourceLang field has a non-nullable lremapResource field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            LremapResourceKeys lremapResourceKeys = lremapResource.getLremapResourceKeys();
            if (lremapResourceKeys != null) {
                lremapResourceKeys.setLremapResource(null);
                lremapResourceKeys = em.merge(lremapResourceKeys);
            }
            em.remove(lremapResource);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapResource> findLremapResourceEntities() {
        return findLremapResourceEntities(true, -1, -1);
    }

    public List<LremapResource> findLremapResourceEntities(int maxResults, int firstResult) {
        return findLremapResourceEntities(false, maxResults, firstResult);
    }

    private List<LremapResource> findLremapResourceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapResource.class));
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

    public LremapResource findLremapResource(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapResource.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapResourceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapResource> rt = cq.from(LremapResource.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
