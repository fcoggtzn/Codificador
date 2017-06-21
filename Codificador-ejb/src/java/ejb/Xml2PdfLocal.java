/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;

/**
 *
 * @author franciscogutierrez
 */
@Local
public interface Xml2PdfLocal {

    public byte[] generatePDF(byte[] archivoXslt, byte[] archivoXml);
    
}
