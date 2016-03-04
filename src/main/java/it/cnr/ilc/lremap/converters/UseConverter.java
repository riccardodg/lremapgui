/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.converters;

import it.cnr.ilc.lremap.controllers.managedbeans.LreMapSearchPanelService;
import it.cnr.ilc.lremap.entities.LremapSideTableAvail;
import it.cnr.ilc.lremap.entities.LremapSideTableAvailPK;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.lremap.entities.LremapSideTableUse;
import it.cnr.ilc.lremap.entities.LremapSideTableUsePK;
import java.util.Arrays;
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
@FacesConverter(value = "useConverter", forClass = LremapSideTableUse.class)
public class UseConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        //System.err.println("getAsObject " + submittedValue);

        if (submittedValue == null) {
            return null;
        } else {
            try {
                //int number = Integer.parseInt(submittedValue);
                LreMapSearchPanelService service = (LreMapSearchPanelService) fc.getExternalContext().getApplicationMap().get("lreMapSearchPanelService");
                String group, value;
                String[] values = new String[2];
                values = submittedValue.split("%%%");
                //System.err.println("getAsObject " + submittedValue + "- " + Arrays.toString(values) + "- ");
                if (values.length == 2) {
                    value = values[0];
                    group = values[1];
                    LremapSideTableUsePK pk = new LremapSideTableUsePK(value, group);
                    LremapSideTableUse ret = service.findLremapSideUse(pk);
                    return ret;
                } else {
                    return null;
                }

            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Availability"));
            }
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String group, value;
        if (o != null && !(o instanceof String)) {
            LremapSideTableUse ret = (LremapSideTableUse) o;
            group = ret.getLremapSideTableUsePK().getGrouping();
            value = ret.getLremapSideTableUsePK().getValue();

            return value + "%%%" + group;
        } else {
            return null;
        }
    }

}
