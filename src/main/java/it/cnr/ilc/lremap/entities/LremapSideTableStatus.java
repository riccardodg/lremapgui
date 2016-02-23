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
@Table(name = "lremap_side_table_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapSideTableStatus.findAll", query = "SELECT l FROM LremapSideTableStatus l"),
    @NamedQuery(name = "LremapSideTableStatus.findByValue", query = "SELECT l FROM LremapSideTableStatus l WHERE l.value = :value"),
    @NamedQuery(name = "LremapSideTableStatus.findByGrouping", query = "SELECT l FROM LremapSideTableStatus l WHERE l.grouping = :grouping"),
    @NamedQuery(name = "LremapSideTableStatus.findById", query = "SELECT l FROM LremapSideTableStatus l WHERE l.id = :id")})
public class LremapSideTableStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 370)
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

    public LremapSideTableStatus() {
    }

    public LremapSideTableStatus(Integer id) {
        this.id = id;
    }

    public LremapSideTableStatus(Integer id, String value, String grouping) {
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
        if (!(object instanceof LremapSideTableStatus)) {
            return false;
        }
        LremapSideTableStatus other = (LremapSideTableStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapSideTableStatus[ id=" + id + " ]";
    }
    
}
