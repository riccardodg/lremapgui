/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.entities;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "lremap_resource_lang_norm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapResourceLangNorm.findAll", query = "SELECT l FROM LremapResourceLangNorm l"),
    @NamedQuery(name = "LremapResourceLangNorm.findByResourceid", query = "SELECT l FROM LremapResourceLangNorm l WHERE l.resourceid = :resourceid")})
public class LremapResourceLangNorm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "resourceid")
    private String resourceid;
    @Lob
    @Size(max = 65535)
    @Column(name = "lang1")
    private String lang1;
    @Lob
    @Size(max = 65535)
    @Column(name = "lang2")
    private String lang2;
    @Lob
    @Size(max = 65535)
    @Column(name = "lang3")
    private String lang3;
    @Lob
    @Size(max = 65535)
    @Column(name = "lang4")
    private String lang4;
    @Lob
    @Size(max = 65535)
    @Column(name = "lang5")
    private String lang5;
    @Lob
    @Size(max = 65535)
    @Column(name = "langother")
    private String langother;
    @JoinColumn(name = "resourceid", referencedColumnName = "resourceid", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private LremapResourceNorm lremapResourceNorm;

    public LremapResourceLangNorm() {
    }

    public LremapResourceLangNorm(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getLang1() {
        return lang1;
    }

    public void setLang1(String lang1) {
        this.lang1 = lang1;
    }

    public String getLang2() {
        return lang2;
    }

    public void setLang2(String lang2) {
        this.lang2 = lang2;
    }

    public String getLang3() {
        return lang3;
    }

    public void setLang3(String lang3) {
        this.lang3 = lang3;
    }

    public String getLang4() {
        return lang4;
    }

    public void setLang4(String lang4) {
        this.lang4 = lang4;
    }

    public String getLang5() {
        return lang5;
    }

    public void setLang5(String lang5) {
        this.lang5 = lang5;
    }

    public String getLangother() {
        return langother;
    }

    public void setLangother(String langother) {
        this.langother = langother;
    }

    public LremapResourceNorm getLremapResourceNorm() {
        return lremapResourceNorm;
    }

    public void setLremapResourceNorm(LremapResourceNorm lremapResourceNorm) {
        this.lremapResourceNorm = lremapResourceNorm;
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
        if (!(object instanceof LremapResourceLangNorm)) {
            return false;
        }
        LremapResourceLangNorm other = (LremapResourceLangNorm) object;
        if ((this.resourceid == null && other.resourceid != null) || (this.resourceid != null && !this.resourceid.equals(other.resourceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapResourceLangNorm[ resourceid=" + resourceid + " ]";
    }
    
}
