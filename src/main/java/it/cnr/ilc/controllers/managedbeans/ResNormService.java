/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.controllers.managedbeans;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
import it.cnr.ilc.controllers.LremapResourceNormJpaController;
import it.cnr.ilc.lremap.entities.LremapResourceNorm;
import it.cnr.ilc.lremap.entities.viewedentities.ViewedResourceNorm;
import it.cnr.ilc.utils.MapUtility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean(name = "resNormService")
@ApplicationScoped
public class ResNormService implements Serializable{

    String sep = "#%#", uuid, key;
    String name, type, prodstatus;
    private HashMap hm = new HashMap<String, List<LremapResourceNorm>>();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("it.cnr.ilc_LREMap_war_1.0-SNAPSHOTPU");

    LremapResourceNormJpaController resNormController = new LremapResourceNormJpaController(emf);
    private List<LremapResourceNorm> resNorm;
    private List<ViewedResourceNorm> groupedResNorm;

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
        outs=fillListForOutPut(getHm());
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

}
