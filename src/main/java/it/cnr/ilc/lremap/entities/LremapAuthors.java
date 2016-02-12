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
@Table(name = "lremap_authors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapAuthors.findAll", query = "SELECT l FROM LremapAuthors l"),
    @NamedQuery(name = "LremapAuthors.findByResourceid", query = "SELECT l FROM LremapAuthors l WHERE l.lremapAuthorsPK.resourceid = :resourceid"),
    @NamedQuery(name = "LremapAuthors.findByAuthornumber", query = "SELECT l FROM LremapAuthors l WHERE l.lremapAuthorsPK.authornumber = :authornumber")})
public class LremapAuthors implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LremapAuthorsPK lremapAuthorsPK;
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
    private LremapPapers lremapPapers;

    public LremapAuthors() {
    }

    public LremapAuthors(LremapAuthorsPK lremapAuthorsPK) {
        this.lremapAuthorsPK = lremapAuthorsPK;
    }

    public LremapAuthors(String resourceid, String authornumber) {
        this.lremapAuthorsPK = new LremapAuthorsPK(resourceid, authornumber);
    }

    public LremapAuthorsPK getLremapAuthorsPK() {
        return lremapAuthorsPK;
    }

    public void setLremapAuthorsPK(LremapAuthorsPK lremapAuthorsPK) {
        this.lremapAuthorsPK = lremapAuthorsPK;
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

    public LremapPapers getLremapPapers() {
        return lremapPapers;
    }

    public void setLremapPapers(LremapPapers lremapPapers) {
        this.lremapPapers = lremapPapers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lremapAuthorsPK != null ? lremapAuthorsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapAuthors)) {
            return false;
        }
        LremapAuthors other = (LremapAuthors) object;
        if ((this.lremapAuthorsPK == null && other.lremapAuthorsPK != null) || (this.lremapAuthorsPK != null && !this.lremapAuthorsPK.equals(other.lremapAuthorsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapAuthors[ lremapAuthorsPK=" + lremapAuthorsPK + " ]";
    }
    
}
