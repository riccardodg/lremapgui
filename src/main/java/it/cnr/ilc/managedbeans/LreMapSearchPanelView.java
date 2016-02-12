/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.managedbeans;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import it.cnr.ilc.lremap.controllers.managedbeans.LreMapSearchPanelService;
import it.cnr.ilc.lremap.entities.LremapSideTableAvail;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtypePK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@ManagedBean(name = "dtLreMapSearchPanelView")
@ViewScoped
public class LreMapSearchPanelView implements Serializable {

    @ManagedProperty("#{lreMapSearchPanelService}")
    private LreMapSearchPanelService service;
    private List<String> names = new ArrayList<String>();
    private List<String> types = new ArrayList<String>();
    private String name;
    private List<LremapSideTableGroupedtype> groupedtypes;
    private LremapSideTableGroupedtype type;

    private List<String> selectedTypeOptions;
    private List<LremapSideTableGroupedtype> selectedGroupedTypes;
    private LremapSideTableGroupedtype selectedGroupedType;
    private LremapSideTableGroupedtypePK pk;
    
    private LremapSideTableAvail resAvail;
    private List<LremapSideTableAvail> resAvails;
    private LremapSideTableAvail selectedResAvail;
    
    private List<LremapSideTableAvail> resOtherAvails;
    private LremapSideTableAvail selectedResOtherAvail;

    @PostConstruct
    public void init() {
        //setResNorm(service.getListOfNormResources());
        setNames(service.getDistinctNames());
        setGroupedtypes(service.getDistinctTypes());
        setResAvails(service.getResAvails());
        setResOtherAvails(service.getResOtherAvails());
        //setTypes(service.getDistinctTypes());
    }

//    public List<String> completeNames(String query) {
//        TrieMetaData namesTrie = service.getDistinctNames();
//        System.err.println("trie -"+namesTrie.toString()+"- str -"+query+"- ");
//        List<String> results = new ArrayList<String>();
//        results = MapUtility.getResultFromTrie(namesTrie, query, false);
//
//        return results;
//    }
    public List<String> completeNames(String query) {
        List<String> results = Lists.newArrayList(Collections2.filter(
                getNames(), Predicates.containsPattern(query)));
        return results;
    }

//    public List<LremapSideTableGroupedtype> completeTypes(String query) {
//
//        List<LremapSideTableGroupedtype> results = (LremapSideTableGroupedtype)Lists.newArrayList(Collections2.filter(
//                getTypes(), Predicates.containsPattern(query)));
//        return results;
//
//    }
    public List<LremapSideTableGroupedtype> completeGroupedTypes(String query) {
        List<LremapSideTableGroupedtype> suggestions = new ArrayList<LremapSideTableGroupedtype>();

        for (LremapSideTableGroupedtype p : getGroupedtypes()) {
            if (p.getLremapSideTableGroupedtypePK().getValue().startsWith(query)) {
                suggestions.add(p);
            }
        }

        return suggestions;
    }

    /**
     * @param service the service to set
     */
    public void setService(LreMapSearchPanelService service) {
        this.service = service;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the groupedtypes
     */
    public List<LremapSideTableGroupedtype> getGroupedtypes() {
        return groupedtypes;
    }

    /**
     * @param groupedtypes the groupedtypes to set
     */
    public void setGroupedtypes(List<LremapSideTableGroupedtype> groupedtypes) {
        this.groupedtypes = groupedtypes;
    }

    /**
     * @return the type
     */
    public LremapSideTableGroupedtype getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(LremapSideTableGroupedtype type) {
        this.type = type;
    }

    /**
     * @return the types
     */
    public List<String> getTypes() {
        for (LremapSideTableGroupedtype res : getGroupedtypes()) {
            types.add(res.getLremapSideTableGroupedtypePK().getValue());
        }
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    /**
     * @return the selectedTypeOptions
     */
    public List<String> getSelectedTypeOptions() {
        return selectedTypeOptions;
    }

    /**
     * @param selectedTypeOptions the selectedTypeOptions to set
     */
    public void setSelectedTypeOptions(List<String> selectedTypeOptions) {
        this.selectedTypeOptions = selectedTypeOptions;
    }

    /**
     * @return the selectedGroupedTypes
     */
    public List<LremapSideTableGroupedtype> getSelectedGroupedTypes() {
        return selectedGroupedTypes;
    }

    /**
     * @param selectedGroupedTypes the selectedGroupedTypes to set
     */
    public void setSelectedGroupedTypes(List<LremapSideTableGroupedtype> selectedGroupedTypes) {
        this.selectedGroupedTypes = selectedGroupedTypes;
    }

    /**
     * @return the pk
     */
    public LremapSideTableGroupedtypePK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(LremapSideTableGroupedtypePK pk) {
        this.pk = pk;
    }

    /**
     * @return the selectedGroupedType
     */
    public LremapSideTableGroupedtype getSelectedGroupedType() {
        return selectedGroupedType;
    }

    /**
     * @param selectedGroupedType the selectedGroupedType to set
     */
    public void setSelectedGroupedType(LremapSideTableGroupedtype selectedGroupedType) {
        this.selectedGroupedType = selectedGroupedType;
    }

    /**
     * @return the resAvail
     */
    public LremapSideTableAvail getResAvail() {
        return resAvail;
    }

    /**
     * @param resAvail the resAvail to set
     */
    public void setResAvail(LremapSideTableAvail resAvail) {
        this.resAvail = resAvail;
    }

    /**
     * @return the resAvails
     */
    public List<LremapSideTableAvail> getResAvails() {
        return resAvails;
    }

    /**
     * @param resAvails the resAvails to set
     */
    public void setResAvails(List<LremapSideTableAvail> resAvails) {
        this.resAvails = resAvails;
    }

    /**
     * @return the selectedResAvail
     */
    public LremapSideTableAvail getSelectedResAvail() {
        return selectedResAvail;
    }

    /**
     * @param selectedResAvail the selectedResAvail to set
     */
    public void setSelectedResAvail(LremapSideTableAvail selectedResAvail) {
        this.selectedResAvail = selectedResAvail;
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

    /**
     * @return the selectedResOtherAvail
     */
    public LremapSideTableAvail getSelectedResOtherAvail() {
        return selectedResOtherAvail;
    }

    /**
     * @param selectedResOtherAvail the selectedResOtherAvail to set
     */
    public void setSelectedResOtherAvail(LremapSideTableAvail selectedResOtherAvail) {
        this.selectedResOtherAvail = selectedResOtherAvail;
    }

}
