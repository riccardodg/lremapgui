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
@Table(name = "lremap_side_table_avail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapSideTableAvail.findAll", query = "SELECT l FROM LremapSideTableAvail l"),
    @NamedQuery(name = "LremapSideTableAvail.findByValue", query = "SELECT l FROM LremapSideTableAvail l WHERE l.lremapSideTableAvailPK.value = :value"),
    @NamedQuery(name = "LremapSideTableAvail.findByGrouping", query = "SELECT l FROM LremapSideTableAvail l WHERE l.lremapSideTableAvailPK.grouping = :grouping")})
public class LremapSideTableAvail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapSideTableAvailPK lremapSideTableAvailPK;

    public LremapSideTableAvail() {
    }

    public LremapSideTableAvail(LremapSideTableAvailPK lremapSideTableAvailPK) {
        this.lremapSideTableAvailPK = lremapSideTableAvailPK;
    }

    public LremapSideTableAvail(String value, String grouping) {
        this.lremapSideTableAvailPK = new LremapSideTableAvailPK(value, grouping);
    }

    public LremapSideTableAvailPK getLremapSideTableAvailPK() {
        return lremapSideTableAvailPK;
    }

    public void setLremapSideTableAvailPK(LremapSideTableAvailPK lremapSideTableAvailPK) {
        this.lremapSideTableAvailPK = lremapSideTableAvailPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lremapSideTableAvailPK != null ? lremapSideTableAvailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapSideTableAvail)) {
            return false;
        }
        LremapSideTableAvail other = (LremapSideTableAvail) object;
        if ((this.lremapSideTableAvailPK == null && other.lremapSideTableAvailPK != null) || (this.lremapSideTableAvailPK != null && !this.lremapSideTableAvailPK.equals(other.lremapSideTableAvailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapSideTableAvail[ lremapSideTableAvailPK=" + lremapSideTableAvailPK + " ]";
    }
    
}
