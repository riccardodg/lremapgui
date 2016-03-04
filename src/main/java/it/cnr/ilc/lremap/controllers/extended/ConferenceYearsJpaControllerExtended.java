/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controllers.extended;

import it.cnr.ilc.lremap.controller.LremapConferenceYearsJpaController;
import it.cnr.ilc.lremap.entities.LremapConferenceYears;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class ConferenceYearsJpaControllerExtended extends LremapConferenceYearsJpaController{
    
    public ConferenceYearsJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }

    public LremapConferenceYears findConferenceYearByConfAndYear(String year, String conf) {
        LremapConferenceYears out;
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("SELECT l FROM LremapConferenceYears l WHERE l.year = :year and l.conf = :conf");
        q.setParameter("conf", conf);
        q.setParameter("year", year);
        //Query q = em.createNamedQuery("LremapResourceNorm.findDistinctName");
        try {
            out=(LremapConferenceYears)q.getSingleResult();
                    
            //System.err.println("AAA "+out);
            return out;
        } finally {
            em.close();
        }
    }
    
}
