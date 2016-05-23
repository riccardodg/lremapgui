/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.controllers.managedbeans;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
import com.sun.javafx.scene.control.skin.VirtualFlow;
import it.cnr.ilc.lremap.controller.LremapResourceNormJpaController;
import it.cnr.ilc.lremap.entities.LremapResourceNorm;
import it.cnr.ilc.lremap.entities.LremapResourceNormLangDim;
import it.cnr.ilc.lremap.entities.LremapResourcePivotedLangNorm;
import it.cnr.ilc.lremap.entities.viewedentities.ViewedResourceNorm;
import it.cnr.ilc.managedbeans.LreMapSearchPanelView;
import it.cnr.ilc.utils.MapUtility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ManagedBean(name = "resNormService")
@ViewScoped
public class ResNormService implements Serializable {

    //@ManagedProperty("#{dtLreMapSearchPanelView}")
    //private LreMapSearchPanelView panelview;
    String sep = "#%#", uuid, key;
    String name, type, prodstatus;
    private HashMap hm = new HashMap<String, List<LremapResourceNorm>>();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("it.cnr.ilc_LREMap_war_1.0-SNAPSHOTPU");

    LremapResourceNormJpaController resNormController = new LremapResourceNormJpaController(emf);
    private List<LremapResourceNorm> resNorm;
    private List<ViewedResourceNorm> groupedResNorm;
    private int total = 0;
    private int distinct = 0;

    public List<LremapResourceNorm> getListOfNormResources() {
        List<LremapResourceNorm> lresNorm;
        lresNorm = resNormController.findLremapResourceNormEntities();
        //prepareHashMapForOutPut();
        //fillListForOutPut(getHm());

        return lresNorm;
    }

    public List<ViewedResourceNorm> getListOfViewedNormResources() {
        List<ViewedResourceNorm> outs;
        prepareHashMapForOutPut();
        outs = fillListForOutPut(getHm());
        return outs;
    }

    public List<ViewedResourceNorm> getListOfViewedNormResourcesFromFilters(HashMap<String, String> filterHm, HashMap<String, String> otherfilterHm) {
        List<ViewedResourceNorm> outs;
        prepareHashMapForOutPutWithPredicates(filterHm, otherfilterHm);
        outs = fillListForOutPut(getHm());
        setDistinct(outs.size());
        return outs;
    }

    private void prepareHashMapForOutPut() {
        List<String> already = new ArrayList<String>();
        List<LremapResourceNorm> lresNorm;
        lresNorm = resNormController.findLremapResourceNormEntities();
        HashMap locHm = new HashMap<String, List<LremapResourceNorm>>();
        for (LremapResourceNorm res : lresNorm) {
            name = res.getName();
            type = res.getType();
            prodstatus = res.getProdstatus();
            List<LremapResourceNorm> localList = new ArrayList<LremapResourceNorm>();
            key = String.format("%s%s%s%s%s", name, sep, type, sep, prodstatus);
            uuid = MapUtility.getHashFromString(key);
            uuid = String.format("%s%s%s", key, sep, uuid);
            if (already.contains(uuid)) {
                // get list of resources
                //System.err.println("contained "+key);
                // get the list by key
                localList = (List) locHm.get(uuid);
                localList.add(res);
                locHm.put(uuid, localList);
            } else {
                // add the uuid and 
                already.add(uuid);

                // init the lis
                localList.add(res);
                // manage the map
                locHm.put(uuid, localList);
                //System.err.println("NOT contained "+key);
            }
        } //rof
        setTotal(lresNorm.size());
        setHm(locHm);

    }

    private void prepareHashMapForOutPutWithPredicates(HashMap<String, String> lfilterHm, HashMap<String, String> otherFilterHm) {
        List<String> already = new ArrayList<String>();
        List<LremapResourceNorm> lresNorm = new ArrayList<LremapResourceNorm>();
        List<LremapResourceNorm> lresNormFilteredByLangDim = new ArrayList<LremapResourceNorm>();
        List<LremapResourceNorm> lresNormFilteredByLang = new ArrayList<LremapResourceNorm>();
        List<LremapResourceNorm> lresNormOut_dim = new ArrayList<LremapResourceNorm>();
        List<LremapResourceNorm> lresNormOut_lang = new ArrayList<LremapResourceNorm>();
        List<LremapResourceNormLangDim> langDims = new ArrayList<LremapResourceNormLangDim>();
        List<LremapResourcePivotedLangNorm> pivoted = new VirtualFlow.ArrayLinkedList<LremapResourcePivotedLangNorm>();
        boolean hasDim = false;
        boolean hasLang = false;
        String dimValue = "";
        String langValue = "";
        List<String> dims;// = new ArrayList<String>();
        /*Create list of predicates*/
        CriteriaBuilder qb = emf.getCriteriaBuilder();
        CriteriaQuery cq = qb.createQuery();

        Root<LremapResourceNorm> myobj = cq.from(LremapResourceNorm.class);
        //Constructing list of parameters

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (lfilterHm.isEmpty()) {
            lresNorm = resNormController.findLremapResourceNormEntities();
        } else {

            /*loop over filters*/
            for (Map.Entry<String, String> map : lfilterHm.entrySet()) {
                String key = map.getKey();

                // if (!"dim".equals(key) && !"lang".equals(key)) {
                String value = map.getValue();
                predicates.add(
                        qb.equal(myobj.get(key), value));
                cq.select(myobj)
                        .where(predicates.toArray(new Predicate[]{}));
                //execute query and do something with result
                //System.err.println("STICA key " + key + " value -" + value + "-");
                lresNorm = emf.createEntityManager().createQuery(cq).getResultList();
                //}

            }

        }

        // olther filter
        for (Map.Entry<String, String> map : otherFilterHm.entrySet()) {
            String key = map.getKey();
            if ("dim".equals(key)) {
                hasDim = true;
                dimValue = map.getValue();
            }
            if ("lang".equals(key)) {
                hasLang = true;
                langValue = map.getValue();

            }
        }

//        System.err.println("Predicates " + predicates.toString() + " " + hasDim);
//        System.err.println("Query " + cq.toString());
// check 
        if (hasDim) {
            for (LremapResourceNorm res : lresNorm) {

                //System.err.println("XXXX " + res.getLremapResourceLangNorm());
                if (res.getLremapResourceLangNorm() != null) {
                    langDims = res.getLremapResourceLangNorm().getLremapResourceNormLangDimList();
                    for (LremapResourceNormLangDim langdim : langDims) {
                        if (langdim.getLremapResourceNormLangDimPK().getLangType().equals(dimValue)) {
                            lresNormOut_dim.add(res);
                        }

                    }

                }
            }
        } else {
            lresNormOut_dim.addAll(lresNorm);
        }

        if (hasLang) {
            for (LremapResourceNorm res : lresNormOut_dim) {
                System.err.println("XXXX " + res.getLremapResourceLangNorm());
                if (res.getLremapResourceLangNorm() != null) {
                    pivoted = res.getLremapResourceLangNorm().getLremapResourcePivotedLangNormList();
                    for (LremapResourcePivotedLangNorm langres : pivoted) {
                        if (langres.getLremapResourcePivotedLangNormPK().getLanguage().equals(langValue)) {
                            lresNormOut_lang.add(res);
                        }

                    }

                }

            }
        } else {
            lresNormOut_lang.addAll(lresNormOut_dim);
        }
        HashMap locHm = new HashMap<String, List<LremapResourceNorm>>();
        for (LremapResourceNorm res : lresNormOut_lang) {
            //System.err.println("RN "+res.toString());
            name = res.getName();
            type = res.getType();
            prodstatus = res.getProdstatus();
            List<LremapResourceNorm> localList = new ArrayList<LremapResourceNorm>();
            key = String.format("%s%s%s%s%s", name, sep, type, sep, prodstatus);
            uuid = MapUtility.getHashFromString(key);
            uuid = String.format("%s%s%s", key, sep, uuid);
            if (already.contains(uuid)) {
                // get list of resources
                //System.err.println("contained "+key);
                // get the list by key
                localList = (List) locHm.get(uuid);
                localList.add(res);
                locHm.put(uuid, localList);
            } else {
                // add the uuid and 
                already.add(uuid);

                // init the lis
                localList.add(res);
                // manage the map
                locHm.put(uuid, localList);
                //System.err.println("NOT contained "+key);
            }
        } //rof
        setTotal(lresNorm.size());
        setHm(locHm);

    }

    private List<ViewedResourceNorm> fillListForOutPut(HashMap mp) {
        Iterator it = mp.entrySet().iterator();
        int i = 1;
        ViewedResourceNorm temp;
        List<LremapResourceNorm> items;
        List<ViewedResourceNorm> outs = new ArrayList<ViewedResourceNorm>();
        String id, name, type, prodstatus, key;
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            key = (String) pair.getKey();
            name = key.split(sep)[0];
            type = key.split(sep)[1];
            prodstatus = key.split(sep)[2];
            id = key.split(sep)[3];
            items = (List<LremapResourceNorm>) pair.getValue();

            //System.out.println(i + ": " + pair.getKey() + " = " + pair.getValue());
            temp = new ViewedResourceNorm(id, type, name, prodstatus, items);
            it.remove(); // avoids a ConcurrentModificationException
            outs.add(temp);
            i++;
        }
        return outs;
    }

    /**
     * @return the resNorm
     */
    public List<LremapResourceNorm> getResNorm() {
        return resNorm;
    }

    /**
     * @param resNorm the resNorm to set
     */
    public void setResNorm(List<LremapResourceNorm> resNorm) {
        this.resNorm = resNorm;
    }

    /**
     * @return the hm
     */
    public HashMap getHm() {
        return hm;
    }

    /**
     * @param hm the hm to set
     */
    public void setHm(HashMap hm) {
        this.hm = hm;
    }

    /**
     * @return the groupedResNorm
     */
    public List<ViewedResourceNorm> getGroupedResNorm() {
        return groupedResNorm;
    }

    /**
     * @param groupedResNorm the groupedResNorm to set
     */
    public void setGroupedResNorm(List<ViewedResourceNorm> groupedResNorm) {
        this.groupedResNorm = groupedResNorm;
    }

//    /**
//     * @return the panelview
//     */
//    public LreMapSearchPanelView getPanelview() {
//        return panelview;
//    }
//
//    /**
//     * @param panelview the panelview to set
//     */
//    public void setPanelview(LreMapSearchPanelView panelview) {
//        this.panelview = panelview;
//    }
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

}
