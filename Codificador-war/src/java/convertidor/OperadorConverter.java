/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import servicio.entidad.Operador;
import servicio.servicio.OperadorFacadeLocal;

/**
 *
 * @author ovante
 */
@FacesConverter("operadorConverter")
public class OperadorConverter implements Converter{

    OperadorFacadeLocal operadorFacade = lookupOperadorFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Operador operador = this.operadorFacade.findOperador(value);
        return operador;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
          if(value != null) {
            return String.valueOf(((Operador) value).getAlias());
        }
        else {
            return null;
        }
    }

    private OperadorFacadeLocal lookupOperadorFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OperadorFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/OperadorFacade!servicio.servicio.OperadorFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
