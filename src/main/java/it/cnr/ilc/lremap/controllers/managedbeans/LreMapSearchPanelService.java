/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controllers.managedbeans;

import it.cnr.ilc.lremap.controller.LremapConferenceYearsJpaController;
import it.cnr.ilc.lremap.controller.LremapSideTableAvailJpaController;
import it.cnr.ilc.lremap.controller.LremapSideTableGroupedtypeJpaController;
import it.cnr.ilc.lremap.controller.LremapSideTableModalityJpaController;
import it.cnr.ilc.lremap.controller.LremapSideTableStatusJpaController;
import it.cnr.ilc.lremap.controller.LremapSideTableUseJpaController;
import it.cnr.ilc.lremap.controllers.extended.ResourceNormExtended;
import it.cnr.ilc.lremap.entities.LremapConferenceYears;
import it.cnr.ilc.lremap.entities.LremapSideTableAvail;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.lremap.entities.LremapSideTableModality;
import it.cnr.ilc.lremap.entities.LremapSideTableStatus;
import it.cnr.ilc.lremap.entities.LremapSideTableUse;
import it.cnr.ilc.utils.MapConstants;
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
    private List<LremapSideTableModality> resMods = new ArrayList<LremapSideTableModality>();
    private List<LremapSideTableAvail> resAllAvails = new ArrayList<LremapSideTableAvail>();
    private List<LremapSideTableUse> resUses = new ArrayList<LremapSideTableUse>();
    private List<LremapSideTableStatus> resStatuses = new ArrayList<LremapSideTableStatus>();
    private List<LremapConferenceYears> resConfs = new ArrayList<LremapConferenceYears>();

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
    LremapSideTableModalityJpaController modController = new LremapSideTableModalityJpaController(emf);
    LremapSideTableUseJpaController useController = new LremapSideTableUseJpaController(emf);
    LremapSideTableStatusJpaController statusController = new LremapSideTableStatusJpaController(emf);
    LremapConferenceYearsJpaController confController = new LremapConferenceYearsJpaController(emf);

    public List<String> getDistinctNames() {

        if (names.isEmpty()) {
            names = resNormController.findDistinctTypesFromLremapResourceNorm();
        }
        return names;
    }

    public List<LremapSideTableGroupedtype> getDistinctTypes() {
        List<LremapSideTableGroupedtype> all = new ArrayList<LremapSideTableGroupedtype>();
        List<LremapSideTableGroupedtype> dataset = new ArrayList<LremapSideTableGroupedtype>();
        List<LremapSideTableGroupedtype> restol = new ArrayList<LremapSideTableGroupedtype>();
        List<LremapSideTableGroupedtype> guidelines = new ArrayList<LremapSideTableGroupedtype>();
        List<LremapSideTableGroupedtype> eval = new ArrayList<LremapSideTableGroupedtype>();
        List<LremapSideTableGroupedtype> other = new ArrayList<LremapSideTableGroupedtype>();

        if (!groupedtypes.isEmpty()) {
            return groupedtypes;
        } else {

            all = groupedtypecontroller.findLremapSideTableGroupedtypeEntities();
            for (LremapSideTableGroupedtype res : all) {
                if (res.getLremapSideTableGroupedtypePK().getGrouping().equals(MapConstants.OTHER)) {
                    other.add(res);
                }
                if (res.getLremapSideTableGroupedtypePK().getGrouping().equals(MapConstants.RD)) {
                    dataset.add(res);
                }

                if (res.getLremapSideTableGroupedtypePK().getGrouping().equals(MapConstants.EV)) {
                    eval.add(res);
                }

                if (res.getLremapSideTableGroupedtypePK().getGrouping().equals(MapConstants.RT)) {
                    restol.add(res);
                }

                if (res.getLremapSideTableGroupedtypePK().getGrouping().equals(MapConstants.RG)) {
                    guidelines.add(res);
                }
            }

            groupedtypes.addAll(dataset);
            groupedtypes.addAll(restol);
            groupedtypes.addAll(guidelines);
            groupedtypes.addAll(eval);
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
            //setResOtherAvails(other);
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

//    /**
//     * @return the resOtherAvails
//     */
//    public List<LremapSideTableAvail> getResOtherAvails() {
//        return resOtherAvails;
//    }
//
//    /**
//     * @param resOtherAvails the resOtherAvails to set
//     */
//    public void setResOtherAvails(List<LremapSideTableAvail> resOtherAvails) {
//        this.resOtherAvails = resOtherAvails;
//    }
    /**
     * @return the resMods
     */
    public List<LremapSideTableModality> getResMods() {
        List<LremapSideTableModality> provided = new ArrayList<LremapSideTableModality>();
        List<LremapSideTableModality> other = new ArrayList<LremapSideTableModality>();
        List<LremapSideTableModality> all;// =new ArrayList<LremapSideTableAvail>();
        //List<LremapSideTableAvail> all =new ArrayList<LremapSideTableAvail>();
        if (!resMods.isEmpty()) {
            return resMods;
        } else {

            all = modController.findLremapSideTableModalityEntities();
            for (LremapSideTableModality res : all) {

                if (res.getGrouping().equals(MapConstants.LREC)) {
                    provided.add(res);
                } else {
                    other.add(res);
                }

            }

        }
        resMods.addAll(provided);
        resMods.addAll(all);

        return resMods;
    }

    /**
     * @return the resUses
     */
    public List<LremapSideTableUse> getResUses() {
        List<LremapSideTableUse> provided = new ArrayList<LremapSideTableUse>();
        List<LremapSideTableUse> other = new ArrayList<LremapSideTableUse>();
        List<LremapSideTableUse> all;// =new ArrayList<LremapSideTableAvail>();
        //List<LremapSideTableAvail> all =new ArrayList<LremapSideTableAvail>();
        if (!resUses.isEmpty()) {
            return resUses;
        } else {

            all = useController.findLremapSideTableUseEntities();
            for (LremapSideTableUse res : all) {
                if (res.getLremapSideTableUsePK().getGrouping().equals(MapConstants.LREC)) {
                    provided.add(res);
                } else {
                    other.add(res);
                }

            }
            resUses.addAll(provided);
            resUses.addAll(all);
            return resUses;
        }

    }

    /**
     * @param resUses the resUses to set
     */
    public void setResUses(List<LremapSideTableUse> resUses) {

        this.resUses = resUses;
    }

    /**
     * @return the resStatuses
     */
    public List<LremapSideTableStatus> getResStatuses() {
        List<LremapSideTableStatus> provided = new ArrayList<LremapSideTableStatus>();
        List<LremapSideTableStatus> other = new ArrayList<LremapSideTableStatus>();
        List<LremapSideTableStatus> all;// =new ArrayList<LremapSideTableAvail>();
        //List<LremapSideTableAvail> all =new ArrayList<LremapSideTableAvail>();
        if (!resStatuses.isEmpty()) {
            return resStatuses;
        } else {

            all = statusController.findLremapSideTableStatusEntities();
            for (LremapSideTableStatus res : all) {
                if (res.getGrouping().equals(MapConstants.LREC)) {
                    provided.add(res);
                } else {
                    other.add(res);
                }

            }
            resStatuses.addAll(provided);
            resStatuses.addAll(all);

            return resStatuses;
        }

    }

    /**
     * @param resStatuses the resStatuses to set
     */
    public void setResStatuses(List<LremapSideTableStatus> resStatuses) {
        this.resStatuses = resStatuses;
    }

    /**
     * @return the resConfs
     */
    public List<LremapConferenceYears> getResConfs() {
        List<LremapConferenceYears> cy2016 = new ArrayList<LremapConferenceYears>();
        List<LremapConferenceYears> cy2015 = new ArrayList<LremapConferenceYears>();
        List<LremapConferenceYears> cy2014 = new ArrayList<LremapConferenceYears>();
        List<LremapConferenceYears> cy2013 = new ArrayList<LremapConferenceYears>();
        List<LremapConferenceYears> cy2012 = new ArrayList<LremapConferenceYears>();
        List<LremapConferenceYears> cy2011 = new ArrayList<LremapConferenceYears>();
        List<LremapConferenceYears> cy2010 = new ArrayList<LremapConferenceYears>();
        List<LremapConferenceYears> other = new ArrayList<LremapConferenceYears>();
        List<LremapConferenceYears> all;// =new ArrayList<LremapSideTableAvail>();
        //List<LremapSideTableAvail> all =new ArrayList<LremapSideTableAvail>();
        if (!resConfs.isEmpty()) {
            return resConfs;
        } else {

            all = confController.findLremapConferenceYearsEntities();
            for (LremapConferenceYears res : all) {
                if (res.getLremapConferenceYearsPK().getYear().equals(MapConstants._2016_)) {
                    cy2016.add(res);
                }
                if (res.getLremapConferenceYearsPK().getYear().equals(MapConstants._2015_)) {
                    cy2015.add(res);
                }

                if (res.getLremapConferenceYearsPK().getYear().equals(MapConstants._2014_)) {
                    cy2014.add(res);
                }

                if (res.getLremapConferenceYearsPK().getYear().equals(MapConstants._2013_)) {
                    cy2013.add(res);
                }
                if (res.getLremapConferenceYearsPK().getYear().equals(MapConstants._2012_)) {
                    cy2012.add(res);
                }
                if (res.getLremapConferenceYearsPK().getYear().equals(MapConstants._2011_)) {
                    cy2011.add(res);
                }
                if (res.getLremapConferenceYearsPK().getYear().equals(MapConstants._2010_)) {
                    cy2010.add(res);
                }

            }
            resConfs.addAll(cy2016);
            resConfs.addAll(cy2015);
            resConfs.addAll(cy2014);
            resConfs.addAll(cy2013);
            resConfs.addAll(cy2012);
            resConfs.addAll(cy2011);
            resConfs.addAll(cy2010);

            return resConfs;
        }
    }

    /**
     * @param resConfs the resConfs to set
     */
    public void setResConfs(List<LremapConferenceYears> resConfs) {
        this.resConfs = resConfs;
    }

}
