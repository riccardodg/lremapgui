/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.managedbeans;

import it.cnr.ilc.controllers.managedbeans.ResNormService;
import it.cnr.ilc.lremap.entities.LremapResourceNorm;
import it.cnr.ilc.lremap.entities.viewedentities.ViewedResourceNorm;
import it.test.Car;
import it.test.CarService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@ManagedBean(name = "dtPaginatorResourceNormView")
@ViewScoped
public class PaginatorResourceNormView implements Serializable {

    private List<LremapResourceNorm> resNorm;
    private List<ViewedResourceNorm> groupedResNorm;

    @ManagedProperty("#{resNormService}")
    private ResNormService service;
    
    @PostConstruct
    public void init() {
        //setResNorm(service.getListOfNormResources());
        setGroupedResNorm(service.getListOfViewedNormResources());
    }

    
    public void setService(ResNormService service) {
        this.service = service;
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
