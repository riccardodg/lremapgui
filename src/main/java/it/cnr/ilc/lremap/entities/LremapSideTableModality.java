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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@Entity
@Table(name = "lremap_side_table_modality")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapSideTableModality.findAll", query = "SELECT l FROM LremapSideTableModality l"),
    @NamedQuery(name = "LremapSideTableModality.findByValue", query = "SELECT l FROM LremapSideTableModality l WHERE l.value = :value"),
    @NamedQuery(name = "LremapSideTableModality.findByGrouping", query = "SELECT l FROM LremapSideTableModality l WHERE l.grouping = :grouping"),
    @NamedQuery(name = "LremapSideTableModality.findById", query = "SELECT l FROM LremapSideTableModality l WHERE l.id = :id")})
public class LremapSideTableModality implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "grouping")
    private String grouping;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public LremapSideTableModality() {
    }

    public LremapSideTableModality(Integer id) {
        this.id = id;
    }

    public LremapSideTableModality(Integer id, String value, String grouping) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapSideTableModality)) {
            return false;
        }
        LremapSideTableModality other = (LremapSideTableModality) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapSideTableModality[ id=" + id + " ]";
    }
    
}
