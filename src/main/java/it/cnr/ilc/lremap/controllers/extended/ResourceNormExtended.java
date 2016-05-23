/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controllers.extended;

import it.cnr.ilc.lremap.controller.LremapResourceNormJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */

public class ResourceNormExtended extends LremapResourceNormJpaController{
    
    public ResourceNormExtended(EntityManagerFactory emf) {
        super(emf);
    }

    public List<String> findDistinctNamesFromLremapResourceNorm() {
        List<String> out;
        EntityManager em = getEntityManager();
        //Query q = em.createNamedQuery("SELECT distinct l.name FROM LremapResourceNorm l order by 1 asc");
        Query q = em.createNamedQuery("LremapResourceNorm.findDistinctName");
        try {
            out=q.getResultList();
            //System.err.println("AAA "+out);
            return out;
        } finally {
            em.close();
        }
    }
    
}
