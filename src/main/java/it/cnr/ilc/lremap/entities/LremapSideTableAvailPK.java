/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@Embeddable
public class LremapSideTableAvailPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "grouping")
    private String grouping;

    public LremapSideTableAvailPK() {
    }

    public LremapSideTableAvailPK(String value, String grouping) {
        this.value = value;
        this.grouping = grouping;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (value != null ? value.hashCode() : 0);
        hash += (grouping != null ? grouping.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapSideTableAvailPK)) {
            return false;
        }
        LremapSideTableAvailPK other = (LremapSideTableAvailPK) object;
        if ((this.value == null && other.value != null) || (this.value != null && !this.value.equals(other.value))) {
            return false;
        }
        if ((this.grouping == null && other.grouping != null) || (this.grouping != null && !this.grouping.equals(other.grouping))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapSideTableAvailPK[ value=" + value + ", grouping=" + grouping + " ]";
    }
    
}
