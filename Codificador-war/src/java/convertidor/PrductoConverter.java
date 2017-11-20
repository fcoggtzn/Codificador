/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import factura.entidad.Producto;
import factura.servicio.ProductoFacadeLocal;
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
@FacesConverter("productoConverter")
public class PrductoConverter implements Converter {

    ProductoFacadeLocal productoFacade = lookupProductoFacadeLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {

            Producto listaProducto = productoFacade.getProducto(value);
            return listaProducto;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((Producto) value).getClaveIdentificacion();
        } else {
            return null;
        }
    }

    private ProductoFacadeLocal lookupProductoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProductoFacadeLocal) c.lookup("java:global/Codificador/Codificador-ejb/ProductoFacade!factura.servicio.ProductoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
