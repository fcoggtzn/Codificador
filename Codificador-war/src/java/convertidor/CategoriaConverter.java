/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import factura.entidad.Categoria;
import factura.servicio.CategoriaFacadeLocal;
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
@FacesConverter("categoriaConverter")
public class CategoriaConverter implements Converter{

    CategoriaFacadeLocal categoriaFacade = lookupCategoriaFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                Categoria findUnidadID = categoriaFacade.findID(value);
                return findUnidadID;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((Categoria) value).getIdcategoria().toString();
        } else {
            return null;
        }
    }

    private CategoriaFacadeLocal lookupCategoriaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (CategoriaFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/CategoriaFacade!factura.servicio.CategoriaFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
