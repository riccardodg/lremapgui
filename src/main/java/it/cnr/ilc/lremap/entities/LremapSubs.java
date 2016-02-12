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
@Table(name = "lremap_subs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapSubs.findAll", query = "SELECT l FROM LremapSubs l"),
    @NamedQuery(name = "LremapSubs.findByResourceid", query = "SELECT l FROM LremapSubs l WHERE l.resourceid = :resourceid"),
    @NamedQuery(name = "LremapSubs.findByConf", query = "SELECT l FROM LremapSubs l WHERE l.conf = :conf"),
    @NamedQuery(name = "LremapSubs.findByYear", query = "SELECT l FROM LremapSubs l WHERE l.year = :year"),
    @NamedQuery(name = "LremapSubs.findByPasscode", query = "SELECT l FROM LremapSubs l WHERE l.passcode = :passcode")})
public class LremapSubs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "resourceid")
    private String resourceid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CONF")
    private String conf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "YEAR")
    private String year;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "passcode")
    private String passcode;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lremapSubs")
    private LremapResourceKeys lremapResourceKeys;

    public LremapSubs() {
    }

    public LremapSubs(String resourceid) {
        this.resourceid = resourceid;
    }

    public LremapSubs(String resourceid, String conf, String year, String passcode) {
        this.resourceid = resourceid;
        this.conf = conf;
        this.year = year;
        this.passcode = passcode;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public LremapResourceKeys getLremapResourceKeys() {
        return lremapResourceKeys;
    }

    public void setLremapResourceKeys(LremapResourceKeys lremapResourceKeys) {
        this.lremapResourceKeys = lremapResourceKeys;
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
        if (!(object instanceof LremapSubs)) {
            return false;
        }
        LremapSubs other = (LremapSubs) object;
        if ((this.resourceid == null && other.resourceid != null) || (this.resourceid != null && !this.resourceid.equals(other.resourceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapSubs[ resourceid=" + resourceid + " ]";
    }
    
}
