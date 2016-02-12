/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.converters;

import it.cnr.ilc.lremap.controllers.managedbeans.LreMapSearchPanelService;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtypePK;
import javax.faces.application.FacesMessage;
import javax.faces.convert.ConverterException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@FacesConverter("groupedTypeConverter")
public class GroupedTypeConverter implements Converter {
    
    private LremapSideTableGroupedtypePK pk;

    

    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        System.err.println("getAsObject "+submittedValue);

        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                //int number = Integer.parseInt(submittedValue);
                LreMapSearchPanelService service = (LreMapSearchPanelService) fc.getExternalContext().getApplicationMap().get("lreMapSearchPanelService");

                for (LremapSideTableGroupedtype p : service.getDistinctTypes()) {
                    if (p.getLremapSideTableGroupedtypePK().getValue().equals(submittedValue)) {
                        System.err.println("P "+p.getLremapSideTableGroupedtypePK().getValue());
                        return p;
                    }
                }

            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.err.println("getAsString "+((String) o).toString());
        if(o != null) {
            
            //return ((LremapSideTableGroupedtype) o).getLremapSideTableGroupedtypePK().toString();
            //return String.valueOf(((LremapSideTableGroupedtype) o).toString());
            return "io";
        }
        else {
            return null;
        }
    }

    /**
     * @return the pk
     */
    public LremapSideTableGroupedtypePK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(LremapSideTableGroupedtypePK pk) {
        this.pk = pk;
    }

}
