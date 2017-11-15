/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;



import catalogo.entidad.ProdServ;
import catalogo.servicio.ProdServFacadeLocal;
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
@FacesConverter("claveProdServConverter")
public class ClaveProdServConverter implements Converter{

    ProdServFacadeLocal prodServFacade = lookupProdServFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
         if(value != null && value.trim().length() > 0) {
            try {
                
                ProdServ findProdServID =
                        prodServFacade.findProdServ(value).get(0);
                return findProdServID;
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
            return ((ProdServ)value).getClaveProdServ().toString();
        }
        else {
            return null;
        }
        
       
    }

    private ProdServFacadeLocal lookupProdServFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProdServFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/ProdServFacade!catalogo.servicio.ProdServFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
