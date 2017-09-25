/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import catalogo.entidad.TipoRegimen;
import catalogo.servicio.TipoRegimenFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ovante
 */
@FacesConverter("RegimenConverter")
public class RegimenConverter implements Converter {

    TipoRegimenFacadeLocal tipoRegimenFacade = lookupTipoRegimenFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        TipoRegimen t = tipoRegimenFacade.getTipoRegimenByDescripcion(value);
        return t;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            TipoRegimen t;
            try {
                t = tipoRegimenFacade.getTipoRegimenByRF((String) value);
                return t.getDescripcion();
            } catch (Exception e) {
                TipoRegimen name = (TipoRegimen) value;
                return name.getDescripcion();//
            }
        } else {
            return "";
        }
    }

    private TipoRegimenFacadeLocal lookupTipoRegimenFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoRegimenFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/TipoRegimenFacade!catalogo.servicio.TipoRegimenFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
