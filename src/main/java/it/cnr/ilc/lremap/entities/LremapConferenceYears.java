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
@Table(name = "lremap_conference_years")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapConferenceYears.findAll", query = "SELECT l FROM LremapConferenceYears l"),
    @NamedQuery(name = "LremapConferenceYears.findByConf", query = "SELECT l FROM LremapConferenceYears l WHERE l.lremapConferenceYearsPK.conf = :conf"),
    @NamedQuery(name = "LremapConferenceYears.findByYear", query = "SELECT l FROM LremapConferenceYears l WHERE l.lremapConferenceYearsPK.year = :year")})
public class LremapConferenceYears implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapConferenceYearsPK lremapConferenceYearsPK;

    public LremapConferenceYears() {
    }

    public LremapConferenceYears(LremapConferenceYearsPK lremapConferenceYearsPK) {
        this.lremapConferenceYearsPK = lremapConferenceYearsPK;
    }

    public LremapConferenceYears(String conf, String year) {
        this.lremapConferenceYearsPK = new LremapConferenceYearsPK(conf, year);
    }

    public LremapConferenceYearsPK getLremapConferenceYearsPK() {
        return lremapConferenceYearsPK;
    }

    public void setLremapConferenceYearsPK(LremapConferenceYearsPK lremapConferenceYearsPK) {
        this.lremapConferenceYearsPK = lremapConferenceYearsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lremapConferenceYearsPK != null ? lremapConferenceYearsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapConferenceYears)) {
            return false;
        }
        LremapConferenceYears other = (LremapConferenceYears) object;
        if ((this.lremapConferenceYearsPK == null && other.lremapConferenceYearsPK != null) || (this.lremapConferenceYearsPK != null && !this.lremapConferenceYearsPK.equals(other.lremapConferenceYearsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapConferenceYears[ lremapConferenceYearsPK=" + lremapConferenceYearsPK + " ]";
    }
    
}
