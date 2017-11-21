/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import catalogo.entidad.FormaPago;
import catalogo.servicio.FormaPagoFacadeLocal;
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
@FacesConverter("formaPagoConverter")
public class FormaPagoConverter implements Converter{

    FormaPagoFacadeLocal formaPagoFacade = lookupFormaPagoFacadeLocal();

  

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
 if(value != null && value.trim().length() > 0) {
            try {
                FormaPago findID = formaPagoFacade.findbyID(value);
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
            return ((FormaPago)value).getFormaPago();
        }
        else {
            return null;
        }    }

    private FormaPagoFacadeLocal lookupFormaPagoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FormaPagoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/FormaPagoFacade!catalogo.servicio.FormaPagoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
