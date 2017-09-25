/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import catalogo.entidad.TipoContrato;
import catalogo.servicio.TipoContratoFacadeLocal;
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
@FacesConverter("TipoContratoConverter")
public class TipoContratoConverter implements Converter {

    TipoContratoFacadeLocal tipoContratoFacade = lookupTipoContratoFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        TipoContrato tc = tipoContratoFacade.getTipoContratoByDesc(value);
        return tc;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            TipoContrato tc;
            try {
                tc = tipoContratoFacade.getTipoContratoByTC((String) value);
                return tc.getDescripcion();
            } catch (Exception e) {
                TipoContrato name = (TipoContrato)value;
                return name.getDescripcion();//
            }
        } else {
            return "";
        }
    }

    private TipoContratoFacadeLocal lookupTipoContratoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoContratoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/TipoContratoFacade!catalogo.servicio.TipoContratoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
