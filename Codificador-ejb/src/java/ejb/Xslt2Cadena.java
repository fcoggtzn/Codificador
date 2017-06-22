/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author franciscogutierrez
 */
@Stateless
public class Xslt2Cadena implements Xslt2CadenaLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public String cadena(String factura){
             StringWriter writer = new StringWriter();   
        try {
            /*** metodo para obtener la cadena original *******/
            InputStream cadenaOriginal = Xslt2Cadena.class.getClassLoader().getResourceAsStream("./lib/cadena33.xslt");
            
            Source xsltSource = new StreamSource(cadenaOriginal);
            StreamSource source2 = new StreamSource(factura);
            //StreamResult result2 = new StreamResult("cadena" + cfdi.getFolio()+"-"+cfdi.getSerie() + ".xml");           
            StreamResult result2 = new StreamResult(writer);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer2 = tFactory.newTransformer(xsltSource);
            transformer2.transform(source2, result2);
            Logger.getLogger(Xslt2Cadena.class.getName()).log(Level.INFO, null, writer.toString());
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Xslt2Cadena.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Xslt2Cadena.class.getName()).log(Level.SEVERE, null, ex);
        }
     return        writer.toString();
    }
}
