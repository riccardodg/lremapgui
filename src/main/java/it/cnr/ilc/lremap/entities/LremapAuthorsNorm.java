/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@Entity
@Table(name = "lremap_authors_norm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapAuthorsNorm.findAll", query = "SELECT l FROM LremapAuthorsNorm l"),
    @NamedQuery(name = "LremapAuthorsNorm.findByResourceid", query = "SELECT l FROM LremapAuthorsNorm l WHERE l.lremapAuthorsNormPK.resourceid = :resourceid"),
    @NamedQuery(name = "LremapAuthorsNorm.findByAuthornumber", query = "SELECT l FROM LremapAuthorsNorm l WHERE l.lremapAuthorsNormPK.authornumber = :authornumber")})
public class LremapAuthorsNorm implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapAuthorsNormPK lremapAuthorsNormPK;
    @Lob
    @Size(max = 65535)
    @Column(name = "username")
    private String username;
    @Lob
    @Size(max = 65535)
    @Column(name = "firstname")
    private String firstname;
    @Lob
    @Size(max = 65535)
    @Column(name = "lastname")
    private String lastname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Lob
    @Size(max = 65535)
    @Column(name = "email")
    private String email;
    @Lob
    @Size(max = 65535)
    @Column(name = "affiliation")
    private String affiliation;
    @Lob
    @Size(max = 65535)
    @Column(name = "country")
    private String country;
    @JoinColumn(name = "resourceid", referencedColumnName = "resourceid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private LremapPapersNorm lremapPapersNorm;

    public LremapAuthorsNorm() {
    }

    public LremapAuthorsNorm(LremapAuthorsNormPK lremapAuthorsNormPK) {
        this.lremapAuthorsNormPK = lremapAuthorsNormPK;
    }

    public LremapAuthorsNorm(String resourceid, String authornumber) {
        this.lremapAuthorsNormPK = new LremapAuthorsNormPK(resourceid, authornumber);
    }

    public LremapAuthorsNormPK getLremapAuthorsNormPK() {
        return lremapAuthorsNormPK;
    }

    public void setLremapAuthorsNormPK(LremapAuthorsNormPK lremapAuthorsNormPK) {
        this.lremapAuthorsNormPK = lremapAuthorsNormPK;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LremapPapersNorm getLremapPapersNorm() {
        return lremapPapersNorm;
    }

    public void setLremapPapersNorm(LremapPapersNorm lremapPapersNorm) {
        this.lremapPapersNorm = lremapPapersNorm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lremapAuthorsNormPK != null ? lremapAuthorsNormPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapAuthorsNorm)) {
            return false;
        }
        LremapAuthorsNorm other = (LremapAuthorsNorm) object;
        if ((this.lremapAuthorsNormPK == null && other.lremapAuthorsNormPK != null) || (this.lremapAuthorsNormPK != null && !this.lremapAuthorsNormPK.equals(other.lremapAuthorsNormPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapAuthorsNorm[ lremapAuthorsNormPK=" + lremapAuthorsNormPK + " ]";
    }
    
}
