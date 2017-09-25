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
import nomina.entidad.Empresa;
import nomina.servicio.EmpresaFacadeLocal;

/**
 *
 * @author ovante
 */
@FacesConverter("empresaConverter")
public class EmpresaConverter implements Converter {

    EmpresaFacadeLocal empresaFacade = lookupEmpresaFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                Empresa service = empresaFacade.getEmpresa(value);
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
                valor=String.valueOf(((Empresa) value).getContribuyente().getRfc());
            } catch (Exception e) {
                valor=null;
            }
            return valor;
        } else {
            return null;
        }
    }

    private EmpresaFacadeLocal lookupEmpresaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/EmpresaFacade!nomina.servicio.EmpresaFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
