/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import catalogo.entidad.MetodoPago;
import catalogo.servicio.MetodoPagoFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ovante
 */
@FacesConverter("metodoPagoConverter")
public class MetodoPagoConverter implements Converter{

    MetodoPagoFacadeLocal metodoPagoFacade = lookupMetodoPagoFacadeLocal();

  

  

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
 if(value != null && value.trim().length() > 0) {
            try {
                MetodoPago findID = metodoPagoFacade.findbyID(value);
                return findID;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    if(value != null) {
            return ((MetodoPago)value).getMetodoPago();
        }
        else {
            return null;
        }    }

    private MetodoPagoFacadeLocal lookupMetodoPagoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (MetodoPagoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/MetodoPagoFacade!catalogo.servicio.MetodoPagoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    

    
}
