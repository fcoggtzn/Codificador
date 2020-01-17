/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertidor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

/**
 *
 * @author ovante
 */
@FacesConverter("formaPagoServicioConverter")

public class FormaPagoServicioConverter implements Converter{ 

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;    
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String retorno ="";
        if ((int)value == 0 )
               retorno="Pendiente" ;
          if ((int)value == 1 )
               retorno="Efectivo" ;
        if ((int)value == 2 )
               retorno="Credito" ;
        if ((int)value == 3 )
               retorno="Tarjeta" ;
        if ((int)value == 99 )
               retorno="Cancelado" ;
        
         return retorno;
    }
    
}
