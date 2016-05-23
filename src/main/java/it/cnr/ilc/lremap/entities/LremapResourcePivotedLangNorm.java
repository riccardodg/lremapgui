/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@Entity
@Table(name = "lremap_resource_pivoted_lang_norm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapResourcePivotedLangNorm.findAll", query = "SELECT l FROM LremapResourcePivotedLangNorm l"),
    @NamedQuery(name = "LremapResourcePivotedLangNorm.findByResourceid", query = "SELECT l FROM LremapResourcePivotedLangNorm l WHERE l.lremapResourcePivotedLangNormPK.resourceid = :resourceid"),
    @NamedQuery(name = "LremapResourcePivotedLangNorm.findByLanguage", query = "SELECT l FROM LremapResourcePivotedLangNorm l WHERE l.lremapResourcePivotedLangNormPK.language = :language")})
public class LremapResourcePivotedLangNorm implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapResourcePivotedLangNormPK lremapResourcePivotedLangNormPK;
    @JoinColumn(name = "resourceid", referencedColumnName = "resourceid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private LremapResourceLangNorm lremapResourceLangNorm;

    public LremapResourcePivotedLangNorm() {
    }

    public LremapResourcePivotedLangNorm(LremapResourcePivotedLangNormPK lremapResourcePivotedLangNormPK) {
        this.lremapResourcePivotedLangNormPK = lremapResourcePivotedLangNormPK;
    }

    public LremapResourcePivotedLangNorm(String resourceid, String language) {
        this.lremapResourcePivotedLangNormPK = new LremapResourcePivotedLangNormPK(resourceid, language);
    }

    public LremapResourcePivotedLangNormPK getLremapResourcePivotedLangNormPK() {
        return lremapResourcePivotedLangNormPK;
    }

    public void setLremapResourcePivotedLangNormPK(LremapResourcePivotedLangNormPK lremapResourcePivotedLangNormPK) {
        this.lremapResourcePivotedLangNormPK = lremapResourcePivotedLangNormPK;
    }

    public LremapResourceLangNorm getLremapResourceLangNorm() {
        return lremapResourceLangNorm;
    }

    public void setLremapResourceLangNorm(LremapResourceLangNorm lremapResourceLangNorm) {
        this.lremapResourceLangNorm = lremapResourceLangNorm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lremapResourcePivotedLangNormPK != null ? lremapResourcePivotedLangNormPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapResourcePivotedLangNorm)) {
            return false;
        }
        LremapResourcePivotedLangNorm other = (LremapResourcePivotedLangNorm) object;
        if ((this.lremapResourcePivotedLangNormPK == null && other.lremapResourcePivotedLangNormPK != null) || (this.lremapResourcePivotedLangNormPK != null && !this.lremapResourcePivotedLangNormPK.equals(other.lremapResourcePivotedLangNormPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapResourcePivotedLangNorm[ lremapResourcePivotedLangNormPK=" + lremapResourcePivotedLangNormPK + " ]";
    }
    
}
