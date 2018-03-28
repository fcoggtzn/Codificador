/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import nomina.entidad.ComprobanteL;
import sat.Comprobante;

/**
 *
 * @author ovante
 */
@Local
public interface EmailLocal {

    public void sendMail( String body,  ComprobanteL comprobanteX) throws NamingException, MessagingException;
    
}
