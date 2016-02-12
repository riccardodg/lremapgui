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
public class LremapAuthorsNormPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "resourceid")
    private String resourceid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "authornumber")
    private String authornumber;

    public LremapAuthorsNormPK() {
    }

    public LremapAuthorsNormPK(String resourceid, String authornumber) {
        this.resourceid = resourceid;
        this.authornumber = authornumber;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getAuthornumber() {
        return authornumber;
    }

    public void setAuthornumber(String authornumber) {
        this.authornumber = authornumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourceid != null ? resourceid.hashCode() : 0);
        hash += (authornumber != null ? authornumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LremapAuthorsNormPK)) {
            return false;
        }
        LremapAuthorsNormPK other = (LremapAuthorsNormPK) object;
        if ((this.resourceid == null && other.resourceid != null) || (this.resourceid != null && !this.resourceid.equals(other.resourceid))) {
            return false;
        }
        if ((this.authornumber == null && other.authornumber != null) || (this.authornumber != null && !this.authornumber.equals(other.authornumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.cnr.ilc.lremap.entities.LremapAuthorsNormPK[ resourceid=" + resourceid + ", authornumber=" + authornumber + " ]";
    }
    
}
