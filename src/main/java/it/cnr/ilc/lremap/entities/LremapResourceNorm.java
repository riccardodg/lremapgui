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
import javax.persistence.Lob;
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
@Table(name = "lremap_resource_norm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapResourceNorm.findDistinctName1", query = "SELECT distinct l.name FROM LremapResourceNorm l"),
    @NamedQuery(name = "LremapResourceNorm.findDistinctType", query = "SELECT distinct l.type FROM LremapResourceNorm l"),
    @NamedQuery(name = "LremapResourceNorm.findDistinctStatus", query = "SELECT distinct l.prodstatus FROM LremapResourceNorm l"),
    @NamedQuery(name = "LremapResourceNorm.findDistinctUse", query = "SELECT distinct l.resourceusage FROM LremapResourceNorm l"),
    @NamedQuery(name = "LremapResourceNorm.findAll", query = "SELECT l FROM LremapResourceNorm l"),
    @NamedQuery(name = "LremapResourceNorm.findByResourceid", query = "SELECT l FROM LremapResourceNorm l WHERE l.resourceid = :resourceid"),
    @NamedQuery(name = "LremapResourceNorm.findByConf", query = "SELECT l FROM LremapResourceNorm l WHERE l.conf = :conf"),
    @NamedQuery(name = "LremapResourceNorm.findByYear", query = "SELECT l FROM LremapResourceNorm l WHERE l.year = :year"),
    @NamedQuery(name = "LremapResourceNorm.findByPasscode", query = "SELECT l FROM LremapResourceNorm l WHERE l.passcode = :passcode"),
    @NamedQuery(name = "LremapResourceNorm.findByResid", query = "SELECT l FROM LremapResourceNorm l WHERE l.resid = :resid")

})
public class LremapResourceNorm implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "resid")
    private int resid;
    @Lob
    @Size(max = 65535)
    @Column(name = "type")
    private String type;
    @Lob
    @Size(max = 65535)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "prodstatus")
    private String prodstatus;
    @Lob
    @Size(max = 65535)
    @Column(name = "modality")
    private String modality;
    @Lob
    @Size(max = 65535)
    @Column(name = "resourceusage")
    private String resourceusage;
    @Lob
    @Size(max = 65535)
    @Column(name = "avail")
    private String avail;
    @Lob
    @Size(max = 65535)
    @Column(name = "url")
    private String url;
    @Lob
    @Size(max = 65535)
    @Column(name = "size")
    private String size;
    @Lob
    @Size(max = 65535)
    @Column(name = "unit")
    private String unit;
    @Lob
    @Size(max = 65535)
    @Column(name = "license")
    private String license;
    @Lob
    @Size(max = 65535)
    @Column(name = "doc")
    private String doc;
    @JoinColumn(name = "resourceid", referencedColumnName = "resource_normid", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private LremapResourceKeys lremapResourceKeys;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lremapResourceNorm")
    private LremapResourceLangNorm lremapResourceLangNorm;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lremapResourceNorm")
    private LremapPapersNorm lremapPapersNorm;

    public LremapResourceNorm() {
    }

    public LremapResourceNorm(String resourceid) {
        this.resourceid = resourceid;
    }

    public LremapResourceNorm(String resourceid, String conf, String year, String passcode, int resid) {
        this.resourceid = resourceid;
        this.conf = conf;
        this.year = year;
        this.passcode = passcode;
        this.resid = resid;
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

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProdstatus() {
        return prodstatus;
    }

    public void setProdstatus(String prodstatus) {
        this.prodstatus = prodstatus;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getResourceusage() {
        return resourceusage;
    }

    public void setResourceusage(String resourceusage) {
        this.resourceusage = resourceusage;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public LremapResourceKeys getLremapResourceKeys() {
        return lremapResourceKeys;
    }

    public void setLremapResourceKeys(LremapResourceKeys lremapResourceKeys) {
        this.lremapResourceKeys = lremapResourceKeys;
    }

    public LremapResourceLangNorm getLremapResourceLangNorm() {
        return lremapResourceLangNorm;
    }

    public void setLremapResourceLangNorm(LremapResourceLangNorm lremapResourceLangNorm) {
        this.lremapResourceLangNorm = lremapResourceLangNorm;
    }

    public LremapPapersNorm getLremapPapersNorm() {
        return lremapPapersNorm;
    }

    public void setLremapPapersNorm(LremapPapersNorm lremapPapersNorm) {
        this.lremapPapersNorm = lremapPapersNorm;
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
        if (!(object instanceof LremapResourceNorm)) {
            return false;
        }
        LremapResourceNorm other = (LremapResourceNorm) object;
        if ((this.resourceid == null && other.resourceid != null) || (this.resourceid != null && !this.resourceid.equals(other.resourceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapResourceNorm[ resourceid=" + resourceid + " ]";
    }

}
