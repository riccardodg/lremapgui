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
@Table(name = "lremap_resource_norm_lang_dim")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapResourceNormLangDim.findAll", query = "SELECT l FROM LremapResourceNormLangDim l"),
    @NamedQuery(name = "LremapResourceNormLangDim.findByResourceid", query = "SELECT l FROM LremapResourceNormLangDim l WHERE l.lremapResourceNormLangDimPK.resourceid = :resourceid"),
    @NamedQuery(name = "LremapResourceNormLangDim.findByLangType", query = "SELECT l FROM LremapResourceNormLangDim l WHERE l.lremapResourceNormLangDimPK.langType = :langType")})
public class LremapResourceNormLangDim implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapResourceNormLangDimPK lremapResourceNormLangDimPK;
    @JoinColumn(name = "resourceid", referencedColumnName = "resourceid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private LremapResourceLangNorm lremapResourceLangNorm;

    public LremapResourceNormLangDim() {
    }

    public LremapResourceNormLangDim(LremapResourceNormLangDimPK lremapResourceNormLangDimPK) {
        this.lremapResourceNormLangDimPK = lremapResourceNormLangDimPK;
    }

    public LremapResourceNormLangDim(String resourceid, String langType) {
        this.lremapResourceNormLangDimPK = new LremapResourceNormLangDimPK(resourceid, langType);
    }

    public LremapResourceNormLangDimPK getLremapResourceNormLangDimPK() {
        return lremapResourceNormLangDimPK;
    }

    public void setLremapResourceNormLangDimPK(LremapResourceNormLangDimPK lremapResourceNormLangDimPK) {
        this.lremapResourceNormLangDimPK = lremapResourceNormLangDimPK;
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
        hash += (lremapResourceNormLangDimPK != null ? lremapResourceNormLangDimPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapResourceNormLangDim)) {
            return false;
        }
        LremapResourceNormLangDim other = (LremapResourceNormLangDim) object;
        if ((this.lremapResourceNormLangDimPK == null && other.lremapResourceNormLangDimPK != null) || (this.lremapResourceNormLangDimPK != null && !this.lremapResourceNormLangDimPK.equals(other.lremapResourceNormLangDimPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapResourceNormLangDim[ lremapResourceNormLangDimPK=" + lremapResourceNormLangDimPK + " ]";
    }
    
}
