/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.converters;

import it.cnr.ilc.lremap.controllers.managedbeans.LreMapSearchPanelService;
import it.cnr.ilc.lremap.entities.LremapConferenceYears;
import it.cnr.ilc.lremap.entities.LremapSideTableModality;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
//@FacesConverter("conferenceYearsConverter, forClass = LremapConferenceYears.class")
@FacesConverter(forClass = LremapSideTableModality.class, value = "modalityConverter")
public class ModalityConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (value != null && !value.isEmpty()) {
            try {
                LreMapSearchPanelService service = (LreMapSearchPanelService) fc.getExternalContext().getApplicationMap().get("lreMapSearchPanelService");
                //System.err.println("getAsObject OUT -" + value + "-");
                //System.err.println("getAsObject OUT -" + service.getResConfs().get(Integer.parseInt(value)) + "-");
                LremapSideTableModality ret = service.findLremapSideModality(Integer.parseInt(value));
                return ret;

            } catch (Exception e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));

            }
        } else {
            return null;
        }
        //return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        //System.err.println("getAsString " + object.toString());
        if (object != null && !(object instanceof String)) {

            LremapSideTableModality ret = (LremapSideTableModality) object;
            if (ret.getId() != null) {
                //System.err.println("getAsString ID " + ret.getId());
                return ret.getId().toString();
            } else {
                return "-1";
            }
        } else {
            return "";
        }
    }

}
