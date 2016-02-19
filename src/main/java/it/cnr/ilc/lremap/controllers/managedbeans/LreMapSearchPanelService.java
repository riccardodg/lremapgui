/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controllers.managedbeans;

import it.cnr.ilc.lremap.controller.LremapSideTableAvailJpaController;
import it.cnr.ilc.lremap.controller.LremapSideTableGroupedtypeJpaController;
import it.cnr.ilc.lremap.controllers.extended.ResourceNormExtended;
import it.cnr.ilc.lremap.entities.LremapSideTableAvail;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.utils.MapConstants;
import it.cnr.ilc.utils.MapUtility;
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
    private List<LremapSideTableGroupedtype> groupedtypes = new ArrayList<LremapSideTableGroupedtype>();
    private List<LremapSideTableAvail> resAvails = new ArrayList<LremapSideTableAvail>();
    private List<LremapSideTableAvail> resOtherAvails = new ArrayList<LremapSideTableAvail>();
    private List<LremapSideTableAvail> resAllAvails = new ArrayList<LremapSideTableAvail>();

    public List<LremapSideTableAvail> getResAllAvails() {
        return resAllAvails;
    }

    public void setResAllAvails(List<LremapSideTableAvail> resAllAvails) {
        this.resAllAvails = resAllAvails;
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("it.cnr.ilc_LREMap_war_1.0-SNAPSHOTPU");

    ResourceNormExtended resNormController = new ResourceNormExtended(emf);
    LremapSideTableGroupedtypeJpaController groupedtypecontroller = new LremapSideTableGroupedtypeJpaController(emf);
    LremapSideTableAvailJpaController availController = new LremapSideTableAvailJpaController(emf);

    public List<String> getDistinctNames() {

        if (names.isEmpty()) {
            names = resNormController.findDistinctTypesFromLremapResourceNorm();
        }
        return names;
    }

    public List<LremapSideTableGroupedtype> getDistinctTypes() {
        List<LremapSideTableGroupedtype> all = new ArrayList<LremapSideTableGroupedtype>();
        List<LremapSideTableGroupedtype> provided = new ArrayList<LremapSideTableGroupedtype>();
        List<LremapSideTableGroupedtype> other = new ArrayList<LremapSideTableGroupedtype>();

        if (!groupedtypes.isEmpty()) {
            return groupedtypes;
        } else {

            all = groupedtypecontroller.findLremapSideTableGroupedtypeEntities();
            for (LremapSideTableGroupedtype res : all) {
                if (res.getLremapSideTableGroupedtypePK().getGrouping().equals(MapConstants.OTHER)) {
                    other.add(res);
                } else {
                    provided.add(res);
                }
            }
            
            groupedtypes.addAll(provided);
            groupedtypes.addAll(other);
            return groupedtypes;
        }
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
        List<LremapSideTableAvail> provided = new ArrayList<LremapSideTableAvail>();
        List<LremapSideTableAvail> other = new ArrayList<LremapSideTableAvail>();
        List<LremapSideTableAvail> all;// =new ArrayList<LremapSideTableAvail>();
        //List<LremapSideTableAvail> all =new ArrayList<LremapSideTableAvail>();
        if (!resAvails.isEmpty()) {
            return resAvails;
        } else {

            all = availController.findLremapSideTableAvailEntities();
            for (LremapSideTableAvail res : all) {
                if (res.getLremapSideTableAvailPK().getGrouping().equals(MapConstants.LREC)) {
                    provided.add(res);
                } else {
                    other.add(res);
                }

            }
            resAvails.addAll(provided);
            resAvails.addAll(all);
            setResOtherAvails(other);
            all.addAll(provided);
            all.addAll(other);
            setResAllAvails(all);

            return resAvails;
        }
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
