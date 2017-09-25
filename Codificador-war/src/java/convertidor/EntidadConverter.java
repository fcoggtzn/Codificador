/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import catalogo.entidad.Estado;
import catalogo.servicio.EstadoFacadeLocal;
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
@FacesConverter("EntidadConverter")
public class EntidadConverter implements Converter{

    EstadoFacadeLocal estadoFacade = lookupEstadoFacadeLocal();
    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Estado e= estadoFacade.getEstadoByEstado(value);
        return e;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Estado e;
            try {
                e=estadoFacade.getEstadoByCve((String)value);
                return e.getEstado();
            } catch (Exception ex) {
                Estado name = (Estado)value;
                return name.getEstado();
            }
        } else {
            return "";
        }
        

    }

    private EstadoFacadeLocal lookupEstadoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EstadoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/EstadoFacade!catalogo.servicio.EstadoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
