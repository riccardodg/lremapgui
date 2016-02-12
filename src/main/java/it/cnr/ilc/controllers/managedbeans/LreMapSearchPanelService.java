/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.controllers.managedbeans;

import it.cnr.ilc.controllers.LremapResourceNormJpaController;
import it.cnr.ilc.controllers.LremapSideTableAvailJpaController;
import it.cnr.ilc.controllers.LremapSideTableGroupedtypeJpaController;
import it.cnr.ilc.lremap.entities.LremapSideTableAvail;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.utils.MapConstants;
import it.cnr.ilc.utils.MapUtility;
import it.cnr.ilc.utils.ResourceComplexType;
import it.cnr.ilc.utils.TrieMetaData;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@ManagedBean(name = "lreMapSearchPanelService")
@ApplicationScoped
public class LreMapSearchPanelService {

    private List<String> names = new ArrayList<String>();
    private List<String> types = new ArrayList<String>();
    private List<LremapSideTableGroupedtype> groupedtypes;
    private List<LremapSideTableAvail> resAvails=new ArrayList<LremapSideTableAvail>();
    private List<LremapSideTableAvail> resOtherAvails=new ArrayList<LremapSideTableAvail>();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("it.cnr.ilc_LREMap_war_1.0-SNAPSHOTPU");

    LremapResourceNormJpaController resNormController = new LremapResourceNormJpaController(emf);
    LremapSideTableGroupedtypeJpaController groupedtypecontroller= new LremapSideTableGroupedtypeJpaController(emf);
    LremapSideTableAvailJpaController availController = new LremapSideTableAvailJpaController(emf);

    public List<String> getDistinctNames() {
        if (names.isEmpty()) {
            names = resNormController.findDistinctNamesFromLremapResourceNorm();
            // add Trie
//            names.add("wordnet");
//            names.add("wordnet3");
//            names.add("wordnet2");

        }

        //System.err.println("names " + names);

        return names;
    }

    public List<LremapSideTableGroupedtype> getDistinctTypes() {
        groupedtypes = groupedtypecontroller.findLremapSideTableGroupedtypeEntities();
        return groupedtypes;
    }

    /**
     * @return the names
     */
    public List<String> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(List<String> names) {
        this.names = names;
    }

    /**
     * @return the types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    /**
     * @return the resAvails
     */
    public List<LremapSideTableAvail> getResAvails() {
        List<LremapSideTableAvail> provided=new ArrayList<LremapSideTableAvail>();
        List<LremapSideTableAvail> other=new ArrayList<LremapSideTableAvail>();
        List<LremapSideTableAvail> all;
        
        all=availController.findLremapSideTableAvailEntities();
        for (LremapSideTableAvail res: all){
            if (res.getLremapSideTableAvailPK().getGrouping().equals(MapConstants.LREC))
                  provided.add(res);
            else
                other.add(res);
                
        }
        resAvails.addAll(provided);
        setResOtherAvails(other);
        return resAvails;
    }

    /**
     * @param resAvails the resAvails to set
     */
    public void setResAvails(List<LremapSideTableAvail> resAvails) {
        this.resAvails = resAvails;
    }

    /**
     * @return the resOtherAvails
     */
    public List<LremapSideTableAvail> getResOtherAvails() {
        return resOtherAvails;
    }

    /**
     * @param resOtherAvails the resOtherAvails to set
     */
    public void setResOtherAvails(List<LremapSideTableAvail> resOtherAvails) {
        this.resOtherAvails = resOtherAvails;
    }

}
