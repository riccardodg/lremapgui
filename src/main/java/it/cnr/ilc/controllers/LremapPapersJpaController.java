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
import it.cnr.ilc.lremap.entities.LremapAuthors;
import it.cnr.ilc.lremap.entities.LremapPapers;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapPapersJpaController implements Serializable {

    public LremapPapersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapPapers lremapPapers) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (lremapPapers.getLremapAuthorsList() == null) {
            lremapPapers.setLremapAuthorsList(new ArrayList<LremapAuthors>());
        }
        List<String> illegalOrphanMessages = null;
        LremapResource lremapResourceOrphanCheck = lremapPapers.getLremapResource();
        if (lremapResourceOrphanCheck != null) {
            LremapPapers oldLremapPapersOfLremapResource = lremapResourceOrphanCheck.getLremapPapers();
            if (oldLremapPapersOfLremapResource != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The LremapResource " + lremapResourceOrphanCheck + " already has an item of type LremapPapers whose lremapResource column cannot be null. Please make another selection for the lremapResource field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResource lremapResource = lremapPapers.getLremapResource();
            if (lremapResource != null) {
                lremapResource = em.getReference(lremapResource.getClass(), lremapResource.getResourceid());
                lremapPapers.setLremapResource(lremapResource);
            }
            List<LremapAuthors> attachedLremapAuthorsList = new ArrayList<LremapAuthors>();
            for (LremapAuthors lremapAuthorsListLremapAuthorsToAttach : lremapPapers.getLremapAuthorsList()) {
                lremapAuthorsListLremapAuthorsToAttach = em.getReference(lremapAuthorsListLremapAuthorsToAttach.getClass(), lremapAuthorsListLremapAuthorsToAttach.getLremapAuthorsPK());
                attachedLremapAuthorsList.add(lremapAuthorsListLremapAuthorsToAttach);
            }
            lremapPapers.setLremapAuthorsList(attachedLremapAuthorsList);
            em.persist(lremapPapers);
            if (lremapResource != null) {
                lremapResource.setLremapPapers(lremapPapers);
                lremapResource = em.merge(lremapResource);
            }
            for (LremapAuthors lremapAuthorsListLremapAuthors : lremapPapers.getLremapAuthorsList()) {
                LremapPapers oldLremapPapersOfLremapAuthorsListLremapAuthors = lremapAuthorsListLremapAuthors.getLremapPapers();
                lremapAuthorsListLremapAuthors.setLremapPapers(lremapPapers);
                lremapAuthorsListLremapAuthors = em.merge(lremapAuthorsListLremapAuthors);
                if (oldLremapPapersOfLremapAuthorsListLremapAuthors != null) {
                    oldLremapPapersOfLremapAuthorsListLremapAuthors.getLremapAuthorsList().remove(lremapAuthorsListLremapAuthors);
                    oldLremapPapersOfLremapAuthorsListLremapAuthors = em.merge(oldLremapPapersOfLremapAuthorsListLremapAuthors);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapPapers(lremapPapers.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapPapers " + lremapPapers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapPapers lremapPapers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapPapers persistentLremapPapers = em.find(LremapPapers.class, lremapPapers.getResourceid());
            LremapResource lremapResourceOld = persistentLremapPapers.getLremapResource();
            LremapResource lremapResourceNew = lremapPapers.getLremapResource();
            List<LremapAuthors> lremapAuthorsListOld = persistentLremapPapers.getLremapAuthorsList();
            List<LremapAuthors> lremapAuthorsListNew = lremapPapers.getLremapAuthorsList();
            List<String> illegalOrphanMessages = null;
            if (lremapResourceNew != null && !lremapResourceNew.equals(lremapResourceOld)) {
                LremapPapers oldLremapPapersOfLremapResource = lremapResourceNew.getLremapPapers();
                if (oldLremapPapersOfLremapResource != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The LremapResource " + lremapResourceNew + " already has an item of type LremapPapers whose lremapResource column cannot be null. Please make another selection for the lremapResource field.");
                }
            }
            for (LremapAuthors lremapAuthorsListOldLremapAuthors : lremapAuthorsListOld) {
                if (!lremapAuthorsListNew.contains(lremapAuthorsListOldLremapAuthors)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LremapAuthors " + lremapAuthorsListOldLremapAuthors + " since its lremapPapers field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lremapResourceNew != null) {
                lremapResourceNew = em.getReference(lremapResourceNew.getClass(), lremapResourceNew.getResourceid());
                lremapPapers.setLremapResource(lremapResourceNew);
            }
            List<LremapAuthors> attachedLremapAuthorsListNew = new ArrayList<LremapAuthors>();
            for (LremapAuthors lremapAuthorsListNewLremapAuthorsToAttach : lremapAuthorsListNew) {
                lremapAuthorsListNewLremapAuthorsToAttach = em.getReference(lremapAuthorsListNewLremapAuthorsToAttach.getClass(), lremapAuthorsListNewLremapAuthorsToAttach.getLremapAuthorsPK());
                attachedLremapAuthorsListNew.add(lremapAuthorsListNewLremapAuthorsToAttach);
            }
            lremapAuthorsListNew = attachedLremapAuthorsListNew;
            lremapPapers.setLremapAuthorsList(lremapAuthorsListNew);
            lremapPapers = em.merge(lremapPapers);
            if (lremapResourceOld != null && !lremapResourceOld.equals(lremapResourceNew)) {
                lremapResourceOld.setLremapPapers(null);
                lremapResourceOld = em.merge(lremapResourceOld);
            }
            if (lremapResourceNew != null && !lremapResourceNew.equals(lremapResourceOld)) {
                lremapResourceNew.setLremapPapers(lremapPapers);
                lremapResourceNew = em.merge(lremapResourceNew);
            }
            for (LremapAuthors lremapAuthorsListNewLremapAuthors : lremapAuthorsListNew) {
                if (!lremapAuthorsListOld.contains(lremapAuthorsListNewLremapAuthors)) {
                    LremapPapers oldLremapPapersOfLremapAuthorsListNewLremapAuthors = lremapAuthorsListNewLremapAuthors.getLremapPapers();
                    lremapAuthorsListNewLremapAuthors.setLremapPapers(lremapPapers);
                    lremapAuthorsListNewLremapAuthors = em.merge(lremapAuthorsListNewLremapAuthors);
                    if (oldLremapPapersOfLremapAuthorsListNewLremapAuthors != null && !oldLremapPapersOfLremapAuthorsListNewLremapAuthors.equals(lremapPapers)) {
                        oldLremapPapersOfLremapAuthorsListNewLremapAuthors.getLremapAuthorsList().remove(lremapAuthorsListNewLremapAuthors);
                        oldLremapPapersOfLremapAuthorsListNewLremapAuthors = em.merge(oldLremapPapersOfLremapAuthorsListNewLremapAuthors);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapPapers.getResourceid();
                if (findLremapPapers(id) == null) {
                    throw new NonexistentEntityException("The lremapPapers with id " + id + " no longer exists.");
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
            LremapPapers lremapPapers;
            try {
                lremapPapers = em.getReference(LremapPapers.class, id);
                lremapPapers.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapPapers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<LremapAuthors> lremapAuthorsListOrphanCheck = lremapPapers.getLremapAuthorsList();
            for (LremapAuthors lremapAuthorsListOrphanCheckLremapAuthors : lremapAuthorsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapPapers (" + lremapPapers + ") cannot be destroyed since the LremapAuthors " + lremapAuthorsListOrphanCheckLremapAuthors + " in its lremapAuthorsList field has a non-nullable lremapPapers field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            LremapResource lremapResource = lremapPapers.getLremapResource();
            if (lremapResource != null) {
                lremapResource.setLremapPapers(null);
                lremapResource = em.merge(lremapResource);
            }
            em.remove(lremapPapers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapPapers> findLremapPapersEntities() {
        return findLremapPapersEntities(true, -1, -1);
    }

    public List<LremapPapers> findLremapPapersEntities(int maxResults, int firstResult) {
        return findLremapPapersEntities(false, maxResults, firstResult);
    }

    private List<LremapPapers> findLremapPapersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapPapers.class));
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

    public LremapPapers findLremapPapers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapPapers.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapPapersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapPapers> rt = cq.from(LremapPapers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
