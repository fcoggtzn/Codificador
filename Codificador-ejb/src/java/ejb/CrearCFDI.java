/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceRef;
import sat.CMetodoPago;
import sat.CMoneda;
import sat.CTipoDeComprobante;
import sat.CTipoFactor;
import sat.CUsoCFDI;
import sat.Comprobante;
import sat.Comprobante.Complemento;
import sat.CEstado;
import sat.CTipoNomina;
import sat.Nomina;
import utilerias.Archivo;
import utilerias.CertificadoUsuario;
import utilerias.MyNameSpaceMapper;
import webServiceSatPrueba.Resultado;
import webServiceSatPrueba.TimbradoServiceService;

/**
 *
 * @author franciscogutierrez
 */
@Stateless
public class CrearCFDI implements CrearCFDILocal {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/pruebas.sefactura.com.mx_3014/sefacturapac/TimbradoService.wsdl")
    private TimbradoServiceService service;

    @EJB
    private Xslt2CadenaLocal xslt2Cadena;
    @EJB
    private FirmaLocal firma;
    private String cadenaOriginal;
    

    /**
     * @param args the command line arguments
     */
    public void crear(Comprobante cfdi) throws FileNotFoundException, DatatypeConfigurationException, TransformerConfigurationException, TransformerException, NoSuchAlgorithmException {
      
        /**
         * ***** metodo de serializar ****
         */
        
        StreamResult result = new StreamResult("factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".xml");
        try {
            JAXBContext jc = JAXBContext.newInstance(sat.Comprobante.class);

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //    m.setProperty("com.sun.xml.bind.marshaller.namespacePrefixMapper", new MyNamespaceMapper());
            m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNameSpaceMapper());
            m.marshal(cfdi, result);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

        /**
         * * metodo para obtener la cadena original ******
         */
        String factura = "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".xml";
        cadenaOriginal = xslt2Cadena.cadena(factura);

        String firmar = firma.firmar(cadenaOriginal, cfdi.getEmisor().getRfc());
        cfdi.setSello(firmar);

        try {
            JAXBContext jc = JAXBContext.newInstance(sat.Comprobante.class,sat.Nomina.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);            
            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
            if (cfdi.getTipoDeComprobante().value().equals("N")) {                
                m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd");
            }
            //    m.setProperty("com.sun.xml.bind.marshaller.namespacePrefixMapper", new MyNamespaceMapper());
            m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNameSpaceMapper());
            m.marshal(cfdi, result);

        } catch (JAXBException e) {
            System.out.println(e.getCause());
        }
        /**
         * *** metodo para mandar timbrar ***
         */

        Resultado resultadoDeTimbre = timbrado(factura);
        System.out.println(resultadoDeTimbre.getStatus());
        System.out.println(resultadoDeTimbre.getCodigo());
        System.out.println(resultadoDeTimbre.getTimbre());
        /**
         * *** metodo para generar PDF ***
         */

    }

    private static byte[] getBytes(InputStream is) {
        int totalBytes = 714;
        byte[] buffer = null;
        try {
            buffer = new byte[totalBytes];
            is.read(buffer, 0, totalBytes);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Resultado timbrado(java.lang.String cfdi) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        String usuario = "WEB070411754";
        String clave = "WEB070411754";
        String cfdiString;
        Archivo archivo = new Archivo();
        cfdiString = archivo.LeerString(cfdi);
        webServiceSatPrueba.TimbradoService port = service.getTimbradoServicePort();
        return port.timbrado(cfdiString, usuario, clave);
    }
}
