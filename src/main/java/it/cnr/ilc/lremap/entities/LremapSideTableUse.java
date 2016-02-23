/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@Entity
@Table(name = "lremap_side_table_use")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapSideTableUse.findAll", query = "SELECT l FROM LremapSideTableUse l"),
    @NamedQuery(name = "LremapSideTableUse.findByValue", query = "SELECT l FROM LremapSideTableUse l WHERE l.lremapSideTableUsePK.value = :value"),
    @NamedQuery(name = "LremapSideTableUse.findByGrouping", query = "SELECT l FROM LremapSideTableUse l WHERE l.lremapSideTableUsePK.grouping = :grouping")})
public class LremapSideTableUse implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapSideTableUsePK lremapSideTableUsePK;

    public LremapSideTableUse() {
    }

    public LremapSideTableUse(LremapSideTableUsePK lremapSideTableUsePK) {
        this.lremapSideTableUsePK = lremapSideTableUsePK;
    }

    public LremapSideTableUse(String value, String grouping) {
        this.lremapSideTableUsePK = new LremapSideTableUsePK(value, grouping);
    }

    public LremapSideTableUsePK getLremapSideTableUsePK() {
        return lremapSideTableUsePK;
    }

    public void setLremapSideTableUsePK(LremapSideTableUsePK lremapSideTableUsePK) {
        this.lremapSideTableUsePK = lremapSideTableUsePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lremapSideTableUsePK != null ? lremapSideTableUsePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapSideTableUse)) {
            return false;
        }
        LremapSideTableUse other = (LremapSideTableUse) object;
        if ((this.lremapSideTableUsePK == null && other.lremapSideTableUsePK != null) || (this.lremapSideTableUsePK != null && !this.lremapSideTableUsePK.equals(other.lremapSideTableUsePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapSideTableUse[ lremapSideTableUsePK=" + lremapSideTableUsePK + " ]";
    }
    
}
