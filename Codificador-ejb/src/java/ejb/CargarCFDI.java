/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import sat.Comprobante;
import timbre.TimbreFiscalDigital;

/**
 *
 * @author ovante
 */
@Stateless
public class CargarCFDI implements CargarCFDILocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Comprobante loadComprobante(String cfdi){
        
            
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Comprobante.class,TimbreFiscalDigital.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            //System.out.println(cfdi);
            Comprobante comprobante = (Comprobante) unmarshaller.unmarshal(new StringReader(cfdi));
            TimbreFiscalDigital timbre;
            timbre = (TimbreFiscalDigital)comprobante.getComplemento().get(0).getAny().get(0);            
            System.out.println(timbre);
            return comprobante;
        } catch (JAXBException ex) {
           Logger.getLogger(CargarCFDI.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    return null;
    }
}
