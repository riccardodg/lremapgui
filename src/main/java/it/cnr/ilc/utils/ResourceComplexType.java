/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.utils;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class ResourceComplexType {
    private String groupValue;
    private String typeValue;

    public ResourceComplexType(String groupValue, String typeValue) {
        this.groupValue = groupValue;
        this.typeValue = typeValue;
    }

    /**
     * @return the groupValue
     */
    public String getGroupValue() {
        return groupValue;
    }

    /**
     * @param groupValue the groupValue to set
     */
    public void setGroupValue(String groupValue) {
        this.groupValue = groupValue;
    }

    /**
     * @return the typeValue
     */
    public String getTypeValue() {
        return typeValue;
    }

    /**
     * @param typeValue the typeValue to set
     */
    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
    
    
}
