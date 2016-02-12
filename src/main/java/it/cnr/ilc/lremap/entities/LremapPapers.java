/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@Entity
@Table(name = "lremap_papers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LremapPapers.findAll", query = "SELECT l FROM LremapPapers l"),
    @NamedQuery(name = "LremapPapers.findByResourceid", query = "SELECT l FROM LremapPapers l WHERE l.resourceid = :resourceid"),
    @NamedQuery(name = "LremapPapers.findByPaperid", query = "SELECT l FROM LremapPapers l WHERE l.paperid = :paperid")})
public class LremapPapers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "resourceid")
    private String resourceid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "paperid")
    private int paperid;
    @Lob
    @Size(max = 65535)
    @Column(name = "status")
    private String status;
    @Lob
    @Size(max = 65535)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "category1")
    private String category1;
    @JoinColumn(name = "resourceid", referencedColumnName = "resourceid", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private LremapResource lremapResource;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lremapPapers")
    private List<LremapAuthors> lremapAuthorsList;

    public LremapPapers() {
    }

    public LremapPapers(String resourceid) {
        this.resourceid = resourceid;
    }

    public LremapPapers(String resourceid, int paperid) {
        this.resourceid = resourceid;
        this.paperid = paperid;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public int getPaperid() {
        return paperid;
    }

    public void setPaperid(int paperid) {
        this.paperid = paperid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public LremapResource getLremapResource() {
        return lremapResource;
    }

    public void setLremapResource(LremapResource lremapResource) {
        this.lremapResource = lremapResource;
    }

    @XmlTransient
    public List<LremapAuthors> getLremapAuthorsList() {
        return lremapAuthorsList;
    }

    public void setLremapAuthorsList(List<LremapAuthors> lremapAuthorsList) {
        this.lremapAuthorsList = lremapAuthorsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourceid != null ? resourceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapPapers)) {
            return false;
        }
        LremapPapers other = (LremapPapers) object;
        if ((this.resourceid == null && other.resourceid != null) || (this.resourceid != null && !this.resourceid.equals(other.resourceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapPapers[ resourceid=" + resourceid + " ]";
    }
    
}
