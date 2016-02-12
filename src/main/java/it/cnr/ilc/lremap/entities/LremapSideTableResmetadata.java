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
@Table(name = "lremap_side_table_resmetadata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapSideTableResmetadata.findAll", query = "SELECT l FROM LremapSideTableResmetadata l"),
    @NamedQuery(name = "LremapSideTableResmetadata.findByAttribute", query = "SELECT l FROM LremapSideTableResmetadata l WHERE l.lremapSideTableResmetadataPK.attribute = :attribute"),
    @NamedQuery(name = "LremapSideTableResmetadata.findByValue", query = "SELECT l FROM LremapSideTableResmetadata l WHERE l.lremapSideTableResmetadataPK.value = :value")})
public class LremapSideTableResmetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapSideTableResmetadataPK lremapSideTableResmetadataPK;

    public LremapSideTableResmetadata() {
    }

    public LremapSideTableResmetadata(LremapSideTableResmetadataPK lremapSideTableResmetadataPK) {
        this.lremapSideTableResmetadataPK = lremapSideTableResmetadataPK;
    }

    public LremapSideTableResmetadata(String attribute, String value) {
        this.lremapSideTableResmetadataPK = new LremapSideTableResmetadataPK(attribute, value);
    }

    public LremapSideTableResmetadataPK getLremapSideTableResmetadataPK() {
        return lremapSideTableResmetadataPK;
    }

    public void setLremapSideTableResmetadataPK(LremapSideTableResmetadataPK lremapSideTableResmetadataPK) {
        this.lremapSideTableResmetadataPK = lremapSideTableResmetadataPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lremapSideTableResmetadataPK != null ? lremapSideTableResmetadataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapSideTableResmetadata)) {
            return false;
        }
        LremapSideTableResmetadata other = (LremapSideTableResmetadata) object;
        if ((this.lremapSideTableResmetadataPK == null && other.lremapSideTableResmetadataPK != null) || (this.lremapSideTableResmetadataPK != null && !this.lremapSideTableResmetadataPK.equals(other.lremapSideTableResmetadataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapSideTableResmetadata[ lremapSideTableResmetadataPK=" + lremapSideTableResmetadataPK + " ]";
    }
    
}
