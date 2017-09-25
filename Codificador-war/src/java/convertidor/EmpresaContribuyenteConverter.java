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
import nomina.entidad.EmpresaContribuyente;
import nomina.servicio.EmpresaContribuyenteFacadeLocal;

/**
 *
 * @author ovante
 */
@FacesConverter("empresaContribuyenteConverter")
public class EmpresaContribuyenteConverter implements Converter{
    EmpresaContribuyenteFacadeLocal empresaContribuyenteFacade = lookupEmpresaContribuyenteFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                EmpresaContribuyente service = empresaContribuyenteFacade.getEmpleado(value);
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
                valor=String.valueOf(((EmpresaContribuyente) value).getContribuyente().getNombre());
            } catch (Exception e) {
                valor=null;
            }
            return valor;
        } else {
            return null;
        }
    }

    private EmpresaContribuyenteFacadeLocal lookupEmpresaContribuyenteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaContribuyenteFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/EmpresaContribuyenteFacade!nomina.servicio.EmpresaContribuyenteFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
