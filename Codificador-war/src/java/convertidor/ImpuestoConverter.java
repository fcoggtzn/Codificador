/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import catalogo.entidad.Impuesto;
import catalogo.entidad.Unidad;
import catalogo.servicio.ImpuestoFacadeLocal;
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
@FacesConverter("impuestoConverter")
public class ImpuestoConverter implements Converter{

    ImpuestoFacadeLocal impuestoFacade = lookupImpuestoFacadeLocal();

    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                
                Impuesto impuesto = impuestoFacade.findImpuesto(value);
                return impuesto;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null) {
            return ((Impuesto)value).getCodigo();
        }
        else {
            return null;
        }
    }

    private ImpuestoFacadeLocal lookupImpuestoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ImpuestoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/ImpuestoFacade!catalogo.servicio.ImpuestoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
