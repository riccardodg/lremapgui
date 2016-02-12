/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@Entity
@Table(name = "lremap_resource_keys")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapResourceKeys.findAll", query = "SELECT l FROM LremapResourceKeys l"),
    @NamedQuery(name = "LremapResourceKeys.findByResourceid", query = "SELECT l FROM LremapResourceKeys l WHERE l.resourceid = :resourceid")})
public class LremapResourceKeys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "resourceid")
    private String resourceid;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lremapResourceKeys")
    private LremapResourceNorm lremapResourceNorm;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lremapResourceKeys")
    private LremapResource lremapResource;
    @JoinColumn(name = "resourceid", referencedColumnName = "resourceid", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private LremapSubs lremapSubs;
    @JoinColumn(name = "resource_normid", referencedColumnName = "resourceid")
    @ManyToOne(optional = false)
    private LremapSubsNorm resourceNormid;

    public LremapResourceKeys() {
    }

    public LremapResourceKeys(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public LremapResourceNorm getLremapResourceNorm() {
        return lremapResourceNorm;
    }

    public void setLremapResourceNorm(LremapResourceNorm lremapResourceNorm) {
        this.lremapResourceNorm = lremapResourceNorm;
    }

    public LremapResource getLremapResource() {
        return lremapResource;
    }

    public void setLremapResource(LremapResource lremapResource) {
        this.lremapResource = lremapResource;
    }

    public LremapSubs getLremapSubs() {
        return lremapSubs;
    }

    public void setLremapSubs(LremapSubs lremapSubs) {
        this.lremapSubs = lremapSubs;
    }

    public LremapSubsNorm getResourceNormid() {
        return resourceNormid;
    }

    public void setResourceNormid(LremapSubsNorm resourceNormid) {
        this.resourceNormid = resourceNormid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourceid != null ? resourceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapResourceKeys)) {
            return false;
        }
        LremapResourceKeys other = (LremapResourceKeys) object;
        if ((this.resourceid == null && other.resourceid != null) || (this.resourceid != null && !this.resourceid.equals(other.resourceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapResourceKeys[ resourceid=" + resourceid + " ]";
    }
    
}
