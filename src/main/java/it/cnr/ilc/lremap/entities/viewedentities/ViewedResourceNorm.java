/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.entities.viewedentities;

import it.cnr.ilc.lremap.entities.LremapResourceNorm;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class ViewedResourceNorm implements Serializable{
    private String ruuid;
    
    private String type;
    
    private String name;
    
    private String prodstatus;
    
    private List<LremapResourceNorm> resNorms = new ArrayList<LremapResourceNorm>();

    public ViewedResourceNorm(String ruuid, String type, String name, String prodstatus, List<LremapResourceNorm> resNorms) {
        this.ruuid = ruuid;
        this.type = type;
        this.name = name;
        this.prodstatus = prodstatus;
        this.resNorms=resNorms;
    }
    
    

    /**
     * @return the ruuid
     */
    public String getRuuid() {
        return ruuid;
    }

    /**
     * @param ruuid the ruuid to set
     */
    public void setRuuid(String ruuid) {
        this.ruuid = ruuid;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the prodstatus
     */
    public String getProdstatus() {
        return prodstatus;
    }

    /**
     * @param prodstatus the prodstatus to set
     */
    public void setProdstatus(String prodstatus) {
        this.prodstatus = prodstatus;
    }

    /**
     * @return the resNorms
     */
    public List<LremapResourceNorm> getResNorms() {
        return resNorms;
    }

    /**
     * @param resNorms the resNorms to set
     */
    public void setResNorms(List<LremapResourceNorm> resNorms) {
        this.resNorms = resNorms;
    }
    
}
