/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import catalogo.entidad.PeriodicidadPago;
import catalogo.servicio.PeriodicidadPagoFacadeLocal;
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
@FacesConverter("PeriodicidadPagoConverter")
public class PeriodicidadPagoConverter implements Converter {

    PeriodicidadPagoFacadeLocal periodicidadPagoFacade = lookupPeriodicidadPagoFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        PeriodicidadPago pp = periodicidadPagoFacade.getPeriodicidadByDesc(value);
        return pp;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            PeriodicidadPago pp;
            try {
                pp = periodicidadPagoFacade.getPeriodicidadByPer((String) value);
                return pp.getDescripcion();
            } catch (Exception e) {
                PeriodicidadPago name = (PeriodicidadPago) value;
                return name.getDescripcion();//
            }
        } else {
            return "";
        }
    }

    private PeriodicidadPagoFacadeLocal lookupPeriodicidadPagoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PeriodicidadPagoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/PeriodicidadPagoFacade!catalogo.servicio.PeriodicidadPagoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
