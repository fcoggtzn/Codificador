/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import catalogo.entidad.RiesgoPuesto;
import catalogo.servicio.RiesgoPuestoFacadeLocal;
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
@FacesConverter("RiesgoPuestoConverter")
public class RiesgoPuestoConverter implements Converter {

    RiesgoPuestoFacadeLocal riesgoPuestoFacade = lookupRiesgoPuestoFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        RiesgoPuesto rp = riesgoPuestoFacade.RiesgoPuestoByDesc(value);
        return rp;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            RiesgoPuesto rp;
            try {
                rp = riesgoPuestoFacade.RiesgoPuestoByCve((String) value);
                return rp.getDescripcion();
            } catch (Exception e) {
                RiesgoPuesto name = (RiesgoPuesto) value;
                return name.getDescripcion();//
            }
        } else {
            return "";
        }
    }

    private RiesgoPuestoFacadeLocal lookupRiesgoPuestoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (RiesgoPuestoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/RiesgoPuestoFacade!catalogo.servicio.RiesgoPuestoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
