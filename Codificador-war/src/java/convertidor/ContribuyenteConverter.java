/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

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
import nomina.entidad.Contribuyente;
import nomina.servicio.ContribuyenteFacadeLocal;

/**
 *
 * @author ovante
 */
@FacesConverter("contribuyenteConverter")
public class ContribuyenteConverter implements Converter{

    ContribuyenteFacadeLocal contribuyenteFacade = lookupContribuyenteFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                Contribuyente service =contribuyenteFacade.getContribuyente(value);
                return service;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Contribuyyente no valido."));
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null) {
            return String.valueOf(((Contribuyente) value).getRfc());
        }
        else {
            return null;
        }
    }

    private ContribuyenteFacadeLocal lookupContribuyenteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ContribuyenteFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/ContribuyenteFacade!nomina.servicio.ContribuyenteFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
