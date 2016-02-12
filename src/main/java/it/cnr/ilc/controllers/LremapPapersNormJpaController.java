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
import it.cnr.ilc.lremap.entities.LremapResourceNorm;
import it.cnr.ilc.lremap.entities.LremapAuthorsNorm;
import it.cnr.ilc.lremap.entities.LremapPapersNorm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class LremapPapersNormJpaController implements Serializable {

    public LremapPapersNormJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LremapPapersNorm lremapPapersNorm) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (lremapPapersNorm.getLremapAuthorsNormList() == null) {
            lremapPapersNorm.setLremapAuthorsNormList(new ArrayList<LremapAuthorsNorm>());
        }
        List<String> illegalOrphanMessages = null;
        LremapResourceNorm lremapResourceNormOrphanCheck = lremapPapersNorm.getLremapResourceNorm();
        if (lremapResourceNormOrphanCheck != null) {
            LremapPapersNorm oldLremapPapersNormOfLremapResourceNorm = lremapResourceNormOrphanCheck.getLremapPapersNorm();
            if (oldLremapPapersNormOfLremapResourceNorm != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The LremapResourceNorm " + lremapResourceNormOrphanCheck + " already has an item of type LremapPapersNorm whose lremapResourceNorm column cannot be null. Please make another selection for the lremapResourceNorm field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapResourceNorm lremapResourceNorm = lremapPapersNorm.getLremapResourceNorm();
            if (lremapResourceNorm != null) {
                lremapResourceNorm = em.getReference(lremapResourceNorm.getClass(), lremapResourceNorm.getResourceid());
                lremapPapersNorm.setLremapResourceNorm(lremapResourceNorm);
            }
            List<LremapAuthorsNorm> attachedLremapAuthorsNormList = new ArrayList<LremapAuthorsNorm>();
            for (LremapAuthorsNorm lremapAuthorsNormListLremapAuthorsNormToAttach : lremapPapersNorm.getLremapAuthorsNormList()) {
                lremapAuthorsNormListLremapAuthorsNormToAttach = em.getReference(lremapAuthorsNormListLremapAuthorsNormToAttach.getClass(), lremapAuthorsNormListLremapAuthorsNormToAttach.getLremapAuthorsNormPK());
                attachedLremapAuthorsNormList.add(lremapAuthorsNormListLremapAuthorsNormToAttach);
            }
            lremapPapersNorm.setLremapAuthorsNormList(attachedLremapAuthorsNormList);
            em.persist(lremapPapersNorm);
            if (lremapResourceNorm != null) {
                lremapResourceNorm.setLremapPapersNorm(lremapPapersNorm);
                lremapResourceNorm = em.merge(lremapResourceNorm);
            }
            for (LremapAuthorsNorm lremapAuthorsNormListLremapAuthorsNorm : lremapPapersNorm.getLremapAuthorsNormList()) {
                LremapPapersNorm oldLremapPapersNormOfLremapAuthorsNormListLremapAuthorsNorm = lremapAuthorsNormListLremapAuthorsNorm.getLremapPapersNorm();
                lremapAuthorsNormListLremapAuthorsNorm.setLremapPapersNorm(lremapPapersNorm);
                lremapAuthorsNormListLremapAuthorsNorm = em.merge(lremapAuthorsNormListLremapAuthorsNorm);
                if (oldLremapPapersNormOfLremapAuthorsNormListLremapAuthorsNorm != null) {
                    oldLremapPapersNormOfLremapAuthorsNormListLremapAuthorsNorm.getLremapAuthorsNormList().remove(lremapAuthorsNormListLremapAuthorsNorm);
                    oldLremapPapersNormOfLremapAuthorsNormListLremapAuthorsNorm = em.merge(oldLremapPapersNormOfLremapAuthorsNormListLremapAuthorsNorm);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLremapPapersNorm(lremapPapersNorm.getResourceid()) != null) {
                throw new PreexistingEntityException("LremapPapersNorm " + lremapPapersNorm + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LremapPapersNorm lremapPapersNorm) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LremapPapersNorm persistentLremapPapersNorm = em.find(LremapPapersNorm.class, lremapPapersNorm.getResourceid());
            LremapResourceNorm lremapResourceNormOld = persistentLremapPapersNorm.getLremapResourceNorm();
            LremapResourceNorm lremapResourceNormNew = lremapPapersNorm.getLremapResourceNorm();
            List<LremapAuthorsNorm> lremapAuthorsNormListOld = persistentLremapPapersNorm.getLremapAuthorsNormList();
            List<LremapAuthorsNorm> lremapAuthorsNormListNew = lremapPapersNorm.getLremapAuthorsNormList();
            List<String> illegalOrphanMessages = null;
            if (lremapResourceNormNew != null && !lremapResourceNormNew.equals(lremapResourceNormOld)) {
                LremapPapersNorm oldLremapPapersNormOfLremapResourceNorm = lremapResourceNormNew.getLremapPapersNorm();
                if (oldLremapPapersNormOfLremapResourceNorm != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The LremapResourceNorm " + lremapResourceNormNew + " already has an item of type LremapPapersNorm whose lremapResourceNorm column cannot be null. Please make another selection for the lremapResourceNorm field.");
                }
            }
            for (LremapAuthorsNorm lremapAuthorsNormListOldLremapAuthorsNorm : lremapAuthorsNormListOld) {
                if (!lremapAuthorsNormListNew.contains(lremapAuthorsNormListOldLremapAuthorsNorm)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LremapAuthorsNorm " + lremapAuthorsNormListOldLremapAuthorsNorm + " since its lremapPapersNorm field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lremapResourceNormNew != null) {
                lremapResourceNormNew = em.getReference(lremapResourceNormNew.getClass(), lremapResourceNormNew.getResourceid());
                lremapPapersNorm.setLremapResourceNorm(lremapResourceNormNew);
            }
            List<LremapAuthorsNorm> attachedLremapAuthorsNormListNew = new ArrayList<LremapAuthorsNorm>();
            for (LremapAuthorsNorm lremapAuthorsNormListNewLremapAuthorsNormToAttach : lremapAuthorsNormListNew) {
                lremapAuthorsNormListNewLremapAuthorsNormToAttach = em.getReference(lremapAuthorsNormListNewLremapAuthorsNormToAttach.getClass(), lremapAuthorsNormListNewLremapAuthorsNormToAttach.getLremapAuthorsNormPK());
                attachedLremapAuthorsNormListNew.add(lremapAuthorsNormListNewLremapAuthorsNormToAttach);
            }
            lremapAuthorsNormListNew = attachedLremapAuthorsNormListNew;
            lremapPapersNorm.setLremapAuthorsNormList(lremapAuthorsNormListNew);
            lremapPapersNorm = em.merge(lremapPapersNorm);
            if (lremapResourceNormOld != null && !lremapResourceNormOld.equals(lremapResourceNormNew)) {
                lremapResourceNormOld.setLremapPapersNorm(null);
                lremapResourceNormOld = em.merge(lremapResourceNormOld);
            }
            if (lremapResourceNormNew != null && !lremapResourceNormNew.equals(lremapResourceNormOld)) {
                lremapResourceNormNew.setLremapPapersNorm(lremapPapersNorm);
                lremapResourceNormNew = em.merge(lremapResourceNormNew);
            }
            for (LremapAuthorsNorm lremapAuthorsNormListNewLremapAuthorsNorm : lremapAuthorsNormListNew) {
                if (!lremapAuthorsNormListOld.contains(lremapAuthorsNormListNewLremapAuthorsNorm)) {
                    LremapPapersNorm oldLremapPapersNormOfLremapAuthorsNormListNewLremapAuthorsNorm = lremapAuthorsNormListNewLremapAuthorsNorm.getLremapPapersNorm();
                    lremapAuthorsNormListNewLremapAuthorsNorm.setLremapPapersNorm(lremapPapersNorm);
                    lremapAuthorsNormListNewLremapAuthorsNorm = em.merge(lremapAuthorsNormListNewLremapAuthorsNorm);
                    if (oldLremapPapersNormOfLremapAuthorsNormListNewLremapAuthorsNorm != null && !oldLremapPapersNormOfLremapAuthorsNormListNewLremapAuthorsNorm.equals(lremapPapersNorm)) {
                        oldLremapPapersNormOfLremapAuthorsNormListNewLremapAuthorsNorm.getLremapAuthorsNormList().remove(lremapAuthorsNormListNewLremapAuthorsNorm);
                        oldLremapPapersNormOfLremapAuthorsNormListNewLremapAuthorsNorm = em.merge(oldLremapPapersNormOfLremapAuthorsNormListNewLremapAuthorsNorm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lremapPapersNorm.getResourceid();
                if (findLremapPapersNorm(id) == null) {
                    throw new NonexistentEntityException("The lremapPapersNorm with id " + id + " no longer exists.");
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
            LremapPapersNorm lremapPapersNorm;
            try {
                lremapPapersNorm = em.getReference(LremapPapersNorm.class, id);
                lremapPapersNorm.getResourceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lremapPapersNorm with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<LremapAuthorsNorm> lremapAuthorsNormListOrphanCheck = lremapPapersNorm.getLremapAuthorsNormList();
            for (LremapAuthorsNorm lremapAuthorsNormListOrphanCheckLremapAuthorsNorm : lremapAuthorsNormListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LremapPapersNorm (" + lremapPapersNorm + ") cannot be destroyed since the LremapAuthorsNorm " + lremapAuthorsNormListOrphanCheckLremapAuthorsNorm + " in its lremapAuthorsNormList field has a non-nullable lremapPapersNorm field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            LremapResourceNorm lremapResourceNorm = lremapPapersNorm.getLremapResourceNorm();
            if (lremapResourceNorm != null) {
                lremapResourceNorm.setLremapPapersNorm(null);
                lremapResourceNorm = em.merge(lremapResourceNorm);
            }
            em.remove(lremapPapersNorm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LremapPapersNorm> findLremapPapersNormEntities() {
        return findLremapPapersNormEntities(true, -1, -1);
    }

    public List<LremapPapersNorm> findLremapPapersNormEntities(int maxResults, int firstResult) {
        return findLremapPapersNormEntities(false, maxResults, firstResult);
    }

    private List<LremapPapersNorm> findLremapPapersNormEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LremapPapersNorm.class));
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

    public LremapPapersNorm findLremapPapersNorm(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LremapPapersNorm.class, id);
        } finally {
            em.close();
        }
    }

    public int getLremapPapersNormCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LremapPapersNorm> rt = cq.from(LremapPapersNorm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
