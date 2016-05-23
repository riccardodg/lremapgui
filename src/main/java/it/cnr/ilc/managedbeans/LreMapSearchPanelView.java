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
import it.cnr.ilc.lremap.controllers.managedbeans.ResNormService;
import it.cnr.ilc.lremap.entities.LremapConferenceYears;
import it.cnr.ilc.lremap.entities.LremapResourceNormLangDim;
import it.cnr.ilc.lremap.entities.LremapResourcePivotedLangNorm;
import it.cnr.ilc.lremap.entities.LremapSideTableAvail;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtypePK;
import it.cnr.ilc.lremap.entities.LremapSideTableModality;
import it.cnr.ilc.lremap.entities.LremapSideTableStatus;
import it.cnr.ilc.lremap.entities.LremapSideTableUse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@ManagedBean(name = "dtLreMapSearchPanelView")
@ViewScoped
public class LreMapSearchPanelView implements Serializable {

    @ManagedProperty("#{lreMapSearchPanelService}")
    private LreMapSearchPanelService service;

    //@ManagedProperty("#{resNormService}")
    @ManagedProperty("#{dtPaginatorResourceNormView}")
    private PaginatorResourceNormView respaginatorview;

    @ManagedProperty("#{resNormService}")
    private ResNormService resnormservice;
    //ResNormService resnormservice=respaginatorview.getService();
    private List<String> names = new ArrayList<String>();
    private List<String> types = new ArrayList<String>();
    private String name;
    private List<LremapSideTableGroupedtype> groupedtypes = new ArrayList<LremapSideTableGroupedtype>();
    private LremapSideTableGroupedtype type;

    private List<String> selectedTypeOptions;
    private List<LremapSideTableGroupedtype> selectedGroupedTypes;
    private LremapSideTableGroupedtype selectedGroupedType;
    private LremapSideTableGroupedtypePK pk;

    private LremapSideTableAvail resAvail;
    private List<LremapSideTableAvail> resAvails;
    private LremapSideTableAvail selectedResAvail;// = new LremapSideTableAvail();

    private LremapSideTableModality resMod;
    private List<LremapSideTableModality> resMods;
    private LremapSideTableModality selectedResMod; //= new LremapSideTableModality();

    private List<LremapSideTableAvail> resOtherAvails;
    private LremapSideTableAvail selectedResOtherAvail;

    private LremapSideTableUse resUse;
    private List<LremapSideTableUse> resUses;
    private LremapSideTableUse selectedResUse;// = new LremapSideTableUse();

    private LremapSideTableStatus resStatus;
    private List<LremapSideTableStatus> resStatuses;
    private LremapSideTableStatus selectedResStatus;// = new LremapSideTableStatus();

    private LremapConferenceYears resConf;
    private List<LremapConferenceYears> resConfs;
    private LremapConferenceYears selectedResConf;// = new LremapConferenceYears();
    private List<LremapResourceNormLangDim> resLangDim;
    private List<LremapResourcePivotedLangNorm> resPivotedLang;

    // strings and list of string for languages and dimensions
    private List<String> langDims = new ArrayList<String>();
    private String langDim = "";
    
    private List<String> langs = new ArrayList<String>();
    private String lang = "";

    // list of filters
    //private HashMap<String, String> filterHm = new HashMap<String, String>();
    private int total = 0;
    private int distinct = 0;

    @PostConstruct
    public void init() {
        //setResNorm(service.getListOfNormResources());
        setNames(service.getDistinctNames());
        setGroupedtypes(service.getDistinctTypes());
        setResAvails(service.getResAvails());
        setResMods(service.getResMods());
        setResUses(service.getResUses());
        setResStatuses(service.getResStatuses());
        setResConfs(service.getResConfs());
        setResLangDim(service.getResLangDim());
        setLangDims(service.getLangDims());
        setResPivotedLang(service.getResLangs());
        setLangs(service.getLangs());

    }

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

    public void filterAndSearch() {
        HashMap<String, String> filterHm = new HashMap<String, String>();
        HashMap<String, String> otherFilterHm = new HashMap<String, String>();
        String CONF, YEAR, type, name, prodstatus, modality,
                resourceusage, avail, dim, lang;

        System.err.println("**** START FILTER ****");
        System.err.println("**** END   FILTER ****");
        if (getName() != null && getName().trim().length() > 0) {
            name = getName();
            System.out.println("Name " + getName());
            setName(getName());
            filterHm.put("name", name);
        } else {
            System.out.println("Name NULL");
        }

        if (getSelectedResAvail() != null) {
            avail = getSelectedResAvail().getLremapSideTableAvailPK().getValue();
            System.out.println("Avail NOT NULL -" + avail + "-");
            filterHm.put("avail", avail);
        } else {
            System.out.println("Avail NULL");
        }

        if (getSelectedResUse() != null) {
            resourceusage = getSelectedResUse().getLremapSideTableUsePK().getValue();
            System.out.println("Use NOT NULL -" + resourceusage + "-");
            filterHm.put("resourceusage", resourceusage);
        } else {
            System.out.println("Use NULL");
        }
        if (getSelectedResConf() == null) {
            System.out.println("Conf  NULL");
        } else {
            CONF = getSelectedResConf().getConf();
            YEAR = getSelectedResConf().getYear();
            System.out.println("Conf  NOT NULL: -" + CONF + "- with YEAR -" + YEAR + "-");
            filterHm.put("conf", CONF);
            filterHm.put("year", YEAR);
        }

        if (getSelectedResStatus() == null) {
            System.out.println("Status  NULL");
        } else {
            prodstatus = getSelectedResStatus().getValue();
            System.out.println("Status  NOT NULL: -" + prodstatus + "-");
            filterHm.put("prodstatus", prodstatus);
        }

        if (getSelectedResMod() == null) {
            System.out.println("Modality  NULL");
        } else {
            modality = getSelectedResMod().getValue();
            System.out.println("Modality  NOT NULL: -" + modality + "-");
            filterHm.put("modality", modality);
        }

        if (getSelectedGroupedType() == null) {
            System.out.println("Type NULL");
        } else {
            type = getSelectedGroupedType().getLremapSideTableGroupedtypePK().getValue();
            System.out.println("Type NOT NULL: -" + type);
            filterHm.put("type", type);
        }

        if ((getLangDim() != null)) {
            if (!"".equals(getLangDim().trim())) {
                dim = getLangDim();
                System.out.println("DIM NOT NULL: -" + dim);
                otherFilterHm.put("dim", dim);
            } else {
                System.out.println("DIM VOID");
            }
        } else {
            System.out.println("DIM NULL");
        }
        
        if ((getLang() != null)) {
            if (!"".equals(getLang().trim())) {
                lang = getLang();
                System.out.println("LANG NOT NULL: -" + lang);
                otherFilterHm.put("lang", lang);
            } else {
                System.out.println("LANG VOID");
            }
        } else {
            System.out.println("LANG NULL");
        }
        //setFilterHm(filterHm);
        getRespaginatorview().setGroupedResNorm(getResnormservice().getListOfViewedNormResourcesFromFilters(filterHm, otherFilterHm));
        setDistinct(getResnormservice().getDistinct());
        setTotal(getResnormservice().getTotal());

    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
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

    /**
     * @return the resMod
     */
    public LremapSideTableModality getResMod() {
        return resMod;
    }

    /**
     * @param resMod the resMod to set
     */
    public void setResMod(LremapSideTableModality resMod) {
        this.resMod = resMod;
    }

    /**
     * @return the resMods
     */
    public List<LremapSideTableModality> getResMods() {
        return resMods;
    }

    /**
     * @param resMods the resMods to set
     */
    public void setResMods(List<LremapSideTableModality> resMods) {
        this.resMods = resMods;
    }

    /**
     * @return the selectedResMod
     */
    public LremapSideTableModality getSelectedResMod() {
        return selectedResMod;
    }

    /**
     * @param selectedResMod the selectedResMod to set
     */
    public void setSelectedResMod(LremapSideTableModality selectedResMod) {
        this.selectedResMod = selectedResMod;
    }

    /**
     * @return the resUse
     */
    public LremapSideTableUse getResUse() {
        return resUse;
    }

    /**
     * @param resUse the resUse to set
     */
    public void setResUse(LremapSideTableUse resUse) {
        this.resUse = resUse;
    }

    /**
     * @return the resUses
     */
    public List<LremapSideTableUse> getResUses() {
        return resUses;
    }

    /**
     * @param resUses the resUses to set
     */
    public void setResUses(List<LremapSideTableUse> resUses) {
        this.resUses = resUses;
    }

    /**
     * @return the selectedResUse
     */
    public LremapSideTableUse getSelectedResUse() {
        return selectedResUse;
    }

    /**
     * @param selectedResUse the selectedResUse to set
     */
    public void setSelectedResUse(LremapSideTableUse selectedResUse) {
        this.selectedResUse = selectedResUse;
    }

    /**
     * @return the resStatus
     */
    public LremapSideTableStatus getResStatus() {
        return resStatus;
    }

    /**
     * @param resStatus the resStatus to set
     */
    public void setResStatus(LremapSideTableStatus resStatus) {
        this.resStatus = resStatus;
    }

    /**
     * @return the resStatuses
     */
    public List<LremapSideTableStatus> getResStatuses() {
        return resStatuses;
    }

    /**
     * @param resStatuses the resStatuses to set
     */
    public void setResStatuses(List<LremapSideTableStatus> resStatuses) {
        this.resStatuses = resStatuses;
    }

    /**
     * @return the selectedResStatus
     */
    public LremapSideTableStatus getSelectedResStatus() {
        return selectedResStatus;
    }

    /**
     * @param selectedResStatus the selectedResStatus to set
     */
    public void setSelectedResStatus(LremapSideTableStatus selectedResStatus) {
        this.selectedResStatus = selectedResStatus;
    }

    /**
     * @return the resConf
     */
    public LremapConferenceYears getResConf() {
        return resConf;
    }

    /**
     * @param resConf the resConf to set
     */
    public void setResConf(LremapConferenceYears resConf) {
        this.resConf = resConf;
    }

    /**
     * @return the resConfs
     */
    public List<LremapConferenceYears> getResConfs() {
        return resConfs;
    }

    /**
     * @param resConfs the resConfs to set
     */
    public void setResConfs(List<LremapConferenceYears> resConfs) {
        this.resConfs = resConfs;
    }

    /**
     * @return the selectedResConf
     */
    public LremapConferenceYears getSelectedResConf() {
        return selectedResConf;
    }

    /**
     * @param selectedResConf the selectedResConf to set
     */
    public void setSelectedResConf(LremapConferenceYears selectedResConf) {
        this.selectedResConf = selectedResConf;
    }

//    /**
//     * @return the filterHm
//     */
//    public HashMap<String, String> getFilterHm() {
//        return filterHm;
//    }
//
//    /**
//     * @param filterHm the filterHm to set
//     */
//    public void setFilterHm(HashMap<String, String> filterHm) {
//        this.filterHm = filterHm;
//    }
    /**
     * @return the resnormservice
     */
    public ResNormService getResnormservice() {
        return resnormservice;
    }

    /**
     * @param resnormservice the resnormservice to set
     */
    public void setResnormservice(ResNormService resnormservice) {
        this.resnormservice = resnormservice;
    }

    /**
     * @return the respaginatorview
     */
    public PaginatorResourceNormView getRespaginatorview() {
        return respaginatorview;
    }

    /**
     * @param respaginatorview the respaginatorview to set
     */
    public void setRespaginatorview(PaginatorResourceNormView respaginatorview) {
        this.respaginatorview = respaginatorview;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the distinct
     */
    public int getDistinct() {
        return distinct;
    }

    /**
     * @param distinct the distinct to set
     */
    public void setDistinct(int distinct) {
        this.distinct = distinct;
    }

    /**
     * @return the resLangDim
     */
    public List<LremapResourceNormLangDim> getResLangDim() {
        return resLangDim;
    }

    /**
     * @param resLangDim the resLangDim to set
     */
    public void setResLangDim(List<LremapResourceNormLangDim> resLangDim) {
        this.resLangDim = resLangDim;
    }

    /**
     * @return the langDims
     */
    public List<String> getLangDims() {
        return langDims;
    }

    /**
     * @param langDims the langDims to set
     */
    public void setLangDims(List<String> langDims) {
        this.langDims = langDims;
    }

    /**
     * @return the langDim
     */
    public String getLangDim() {
        return langDim;
    }

    /**
     * @param langDim the langDim to set
     */
    public void setLangDim(String langDim) {
        this.langDim = langDim;
    }

    /**
     * @return the langs
     */
    public List<String> getLangs() {
        return langs;
    }

    /**
     * @param langs the langs to set
     */
    public void setLangs(List<String> langs) {
        this.langs = langs;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return the resPivotedLang
     */
    public List<LremapResourcePivotedLangNorm> getResPivotedLang() {
        return resPivotedLang;
    }

    /**
     * @param resPivotedLang the resPivotedLang to set
     */
    public void setResPivotedLang(List<LremapResourcePivotedLangNorm> resPivotedLang) {
        this.resPivotedLang = resPivotedLang;
    }

}
