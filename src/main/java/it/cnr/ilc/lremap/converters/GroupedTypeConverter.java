/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lremap.converters;

import it.cnr.ilc.lremap.controllers.managedbeans.LreMapSearchPanelService;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtype;
import it.cnr.ilc.lremap.entities.LremapSideTableGroupedtypePK;
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
@FacesConverter(value = "groupedTypeConverter", forClass = LremapSideTableGroupedtype.class)
public class GroupedTypeConverter implements Converter {

    private LremapSideTableGroupedtypePK pk;

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

                    LremapSideTableGroupedtypePK lpk = new LremapSideTableGroupedtypePK(value, group);
                    LremapSideTableGroupedtype ret = service.findLremapSideTableGroupedtype(lpk);
                    return ret;
                } else {
                    return null;
                }

            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String group, value;
        if (o != null && !(o instanceof String)) {
            LremapSideTableGroupedtype ret = (LremapSideTableGroupedtype) o;
            group = ret.getLremapSideTableGroupedtypePK().getGrouping();
            value = ret.getLremapSideTableGroupedtypePK().getValue();

            return value + "%%%" + group;
        } else {
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
