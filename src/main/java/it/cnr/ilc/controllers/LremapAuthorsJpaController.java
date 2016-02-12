/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.controllers;

import it.cnr.ilc.controllers.exceptions.NonexistentEntityException;
import it.cnr.ilc.controllers.exceptions.PreexistingEntityException;
import it.cnr.ilc.lremap.entities.LremapAuthors;
import it.cnr.ilc.lremap.entities.LremapAuthorsPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import it.cnr.ilc.lremap.entities.LremapPapers;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapAuthorsJpaController implements Serializable {

    public LremapAuthorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapAuthors lremapAuthors) throws PreexistingEntityException, Exception {
        if (lremapAuthors.getLremapAuthorsPK() == null) {
            lremapAuthors.setLremapAuthorsPK(new LremapAuthorsPK());
        }
        lremapAuthors.getLremapAuthorsPK().setResourceid(lremapAuthors.getLremapPapers().getResourceid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapPapers lremapPapers = lremapAuthors.getLremapPapers();
            if (lremapPapers != null) {
                lremapPapers = em.getReference(lremapPapers.getClass(), lremapPapers.getResourceid());
                lremapAuthors.setLremapPapers(lremapPapers);
            }
            em.persist(lremapAuthors);
            if (lremapPapers != null) {
                lremapPapers.getLremapAuthorsList().add(lremapAuthors);
                lremapPapers = em.merge(lremapPapers);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapAuthors(lremapAuthors.getLremapAuthorsPK()) != null) {
                throw new PreexistingEntityException("LremapAuthors " + lremapAuthors + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapAuthors lremapAuthors) throws NonexistentEntityException, Exception {
        lremapAuthors.getLremapAuthorsPK().setResourceid(lremapAuthors.getLremapPapers().getResourceid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapAuthors persistentLremapAuthors = em.find(LremapAuthors.class, lremapAuthors.getLremapAuthorsPK());
            LremapPapers lremapPapersOld = persistentLremapAuthors.getLremapPapers();
            LremapPapers lremapPapersNew = lremapAuthors.getLremapPapers();
            if (lremapPapersNew != null) {
                lremapPapersNew = em.getReference(lremapPapersNew.getClass(), lremapPapersNew.getResourceid());
                lremapAuthors.setLremapPapers(lremapPapersNew);
            }
            lremapAuthors = em.merge(lremapAuthors);
            if (lremapPapersOld != null && !lremapPapersOld.equals(lremapPapersNew)) {
                lremapPapersOld.getLremapAuthorsList().remove(lremapAuthors);
                lremapPapersOld = em.merge(lremapPapersOld);
            }
            if (lremapPapersNew != null && !lremapPapersNew.equals(lremapPapersOld)) {
                lremapPapersNew.getLremapAuthorsList().add(lremapAuthors);
                lremapPapersNew = em.merge(lremapPapersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LremapAuthorsPK id = lremapAuthors.getLremapAuthorsPK();
                if (findLremapAuthors(id) == null) {
                    throw new NonexistentEntityException("The lremapAuthors with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LremapAuthorsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapAuthors lremapAuthors;
            try {
                lremapAuthors = em.getReference(LremapAuthors.class, id);
                lremapAuthors.getLremapAuthorsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapAuthors with id " + id + " no longer exists.", enfe);
            }
            LremapPapers lremapPapers = lremapAuthors.getLremapPapers();
            if (lremapPapers != null) {
                lremapPapers.getLremapAuthorsList().remove(lremapAuthors);
                lremapPapers = em.merge(lremapPapers);
            }
            em.remove(lremapAuthors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapAuthors> findLremapAuthorsEntities() {
        return findLremapAuthorsEntities(true, -1, -1);
    }

    public List<LremapAuthors> findLremapAuthorsEntities(int maxResults, int firstResult) {
        return findLremapAuthorsEntities(false, maxResults, firstResult);
    }

    private List<LremapAuthors> findLremapAuthorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapAuthors.class));
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

    public LremapAuthors findLremapAuthors(LremapAuthorsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapAuthors.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapAuthorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapAuthors> rt = cq.from(LremapAuthors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
