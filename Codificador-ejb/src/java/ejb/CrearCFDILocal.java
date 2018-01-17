/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import nomina.entidad.ComprobanteL;

import sat.Comprobante;

/**throw new EJBException("Finder Exception occurred");
 *
 * @author franciscogutierrez
 */
@Local
public interface CrearCFDILocal {

    public void crear(Comprobante cfdi, ComprobanteL comprobanteX) throws EJBException, FileNotFoundException, DatatypeConfigurationException, TransformerConfigurationException, TransformerException, NoSuchAlgorithmException;

    public void generaPDF() throws NamingException, MessagingException;
    
    public Comprobante leerCFDI(ComprobanteL comprobanteX);
    public void sendMail(String email, String emailto, String body, Comprobante cfdi,ComprobanteL comprobanteX) throws NamingException, MessagingException;

  
}
