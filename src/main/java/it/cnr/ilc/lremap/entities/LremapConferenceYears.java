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
@Table(name = "lremap_conference_years")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapConferenceYears.findAll", query = "SELECT l FROM LremapConferenceYears l"),
    @NamedQuery(name = "LremapConferenceYears.findById", query = "SELECT l FROM LremapConferenceYears l WHERE l.id = :id"),
    @NamedQuery(name = "LremapConferenceYears.findByConf", query = "SELECT l FROM LremapConferenceYears l WHERE l.conf = :conf"),
    @NamedQuery(name = "LremapConferenceYears.findByYear", query = "SELECT l FROM LremapConferenceYears l WHERE l.year = :year")})
public class LremapConferenceYears implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CONF")
    private String conf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "YEAR")
    private String year;

    public LremapConferenceYears() {
    }

    public LremapConferenceYears(Integer id) {
        this.id = id;
    }

    public LremapConferenceYears(Integer id, String conf, String year) {
        this.id = id;
        this.conf = conf;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
        if (!(object instanceof LremapConferenceYears)) {
            return false;
        }
        LremapConferenceYears other = (LremapConferenceYears) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapConferenceYears[ id=" + id + " ]";
    }
    
}
