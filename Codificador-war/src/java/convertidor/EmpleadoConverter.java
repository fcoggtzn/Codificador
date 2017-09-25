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
import nomina.entidad.Empleado;
import nomina.servicio.EmpleadoFacadeLocal;

/**
 *
 * @author ovante
 */
@FacesConverter("empleadoConverter")
public class EmpleadoConverter implements Converter {

    EmpleadoFacadeLocal empleadoFacade = lookupEmpleadoFacadeLocal();
    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                Empleado service = empleadoFacade.getEmpleado(value);
                return service;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Contribuyyente no valido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            String valor=null;
            try {
                valor=String.valueOf(((Empleado) value).getContribuyente().getNombre());
            } catch (Exception e) {
                valor=null;
            }
            return valor;
        } else {
            return null;
        }
    }

    private EmpleadoFacadeLocal lookupEmpleadoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpleadoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/EmpleadoFacade!nomina.servicio.EmpleadoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
