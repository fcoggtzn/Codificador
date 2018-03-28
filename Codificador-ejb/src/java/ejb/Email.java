/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.NamingException;
import nomina.entidad.Archivos;
import nomina.entidad.ComprobanteL;
import sat.Comprobante;

/**
 *
 * @author ovante
 */
@Stateless
public class Email implements EmailLocal {
    
     @Resource(name = "correo")
    private Session correo;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
     @Override
    public void sendMail( String body, ComprobanteL comprobanteX) throws NamingException, MessagingException {
        String email = comprobanteX.getContribuyente().getEmail();
        String emailto =        comprobanteX.getContribuyente1().getEmail();
        MimeMessage message = new MimeMessage(correo);
        message.setSubject("Sistema Sole 3.3 CFDI "+comprobanteX.getContribuyente().getRfc()+"  "+comprobanteX.getSerie()+"-"+comprobanteX.getFolio());
        message.setRecipients(Message.RecipientType.TO, emailto);        
        message.setRecipients(Message.RecipientType.BCC, email);        
        message.setFrom(email);
        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        //message.setText(body);
        // Now set the actual message
        messageBodyPart.setText(body );

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        /*
        File baseDir = new File(ruta+"empresas/"+cfdi.getEmisor().getRfc());
        File outDir = new File(baseDir, "out");
        File pdffile = new File(outDir, "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".pdf");               
        DataSource source = new FileDataSource(pdffile);*/
        
        byte[] bytesPDF=null;
        byte[] bytesXML=null;
         for( Archivos archivo: comprobanteX.getArchivosCollection()){
            if(archivo.getTipo().equals("XML")){
             bytesXML= archivo.getContenido();

            }
            if(archivo.getTipo().equals("PDF")){
             bytesPDF= archivo.getContenido();

            }
        }
         
        ByteArrayDataSource bds = new ByteArrayDataSource(bytesPDF, "application/pdf"); 
        messageBodyPart.setDataHandler(new DataHandler(bds));        
        messageBodyPart.setFileName(comprobanteX.getSerie()+"-"+comprobanteX.getFolio()+".pdf");
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
//        baseDir = new File(".");
  //      outDir = new File(baseDir, "out");
        /*pdffile = new File(outDir, "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".xml");
        source = new FileDataSource(pdffile);
        messageBodyPart.setDataHandler(new DataHandler(source));
*/
        ByteArrayDataSource bdxml = new ByteArrayDataSource(bytesXML,"text/xml; charset=UTF-8"); 
                messageBodyPart.setDataHandler(new DataHandler(bdxml));        

        messageBodyPart.setFileName(comprobanteX.getSerie()+"-"+comprobanteX.getFolio()+".xml");
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        message.setContent(multipart);

        // Send message
        Transport.send(message);
    }
}
