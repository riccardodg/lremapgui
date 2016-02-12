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
@Table(name = "lremap_side_table_groupedtype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapSideTableGroupedtype.findAll", query = "SELECT l FROM LremapSideTableGroupedtype l"),
    @NamedQuery(name = "LremapSideTableGroupedtype.findByValue", query = "SELECT l FROM LremapSideTableGroupedtype l WHERE l.lremapSideTableGroupedtypePK.value = :value order by 1"),
    @NamedQuery(name = "LremapSideTableGroupedtype.findByGrouping", query = "SELECT l FROM LremapSideTableGroupedtype l WHERE l.lremapSideTableGroupedtypePK.grouping = :grouping")})
public class LremapSideTableGroupedtype implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapSideTableGroupedtypePK lremapSideTableGroupedtypePK;

    public LremapSideTableGroupedtype() {
    }

    public LremapSideTableGroupedtype(LremapSideTableGroupedtypePK lremapSideTableGroupedtypePK) {
        this.lremapSideTableGroupedtypePK = lremapSideTableGroupedtypePK;
    }

    public LremapSideTableGroupedtype(String value, String grouping) {
        this.lremapSideTableGroupedtypePK = new LremapSideTableGroupedtypePK(value, grouping);
    }

    public LremapSideTableGroupedtypePK getLremapSideTableGroupedtypePK() {
        return lremapSideTableGroupedtypePK;
    }

    public void setLremapSideTableGroupedtypePK(LremapSideTableGroupedtypePK lremapSideTableGroupedtypePK) {
        this.lremapSideTableGroupedtypePK = lremapSideTableGroupedtypePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lremapSideTableGroupedtypePK != null ? lremapSideTableGroupedtypePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapSideTableGroupedtype)) {
            return false;
        }
        LremapSideTableGroupedtype other = (LremapSideTableGroupedtype) object;
        if ((this.lremapSideTableGroupedtypePK == null && other.lremapSideTableGroupedtypePK != null) || (this.lremapSideTableGroupedtypePK != null && !this.lremapSideTableGroupedtypePK.equals(other.lremapSideTableGroupedtypePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype[ lremapSideTableGroupedtypePK=" + lremapSideTableGroupedtypePK + " ]";
    }
    
}
