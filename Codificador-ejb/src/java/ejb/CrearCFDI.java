/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.BufferedInputStream;
import java.io.File;
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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
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
import nomina.entidad.ComprobanteL;
import nomina.entidad.Empleado;
import nomina.servicio.ArchivosFacadeLocal;
import nomina.servicio.ComprobanteLFacadeLocal;
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
import utilerias.Transformacion;
import webServiceSatPrueba.Resultado;
import webServiceSatPrueba.TimbradoServiceService;

/**
 *
 * @author franciscogutierrez
 */
@Stateless
public class CrearCFDI implements CrearCFDILocal {

    @EJB
    private ComprobanteLFacadeLocal comprobanteLFacade;

    @Resource(name = "correo")
    private Session correo;

    @EJB
    private ArchivosFacadeLocal archivosFacade;

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
    public void crear(Comprobante cfdi, ComprobanteL comprobanteX) throws FileNotFoundException, DatatypeConfigurationException, TransformerConfigurationException, TransformerException, NoSuchAlgorithmException {

        /**
         * ***** metodo de serializar ****
         */
        File baseDir = new File(".");
        File outDir = new File(baseDir, "out");
        File xmlfile = new File(outDir, "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".xml");

        StreamResult result = new StreamResult(xmlfile);
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
        //String factura = "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".xml";
        cadenaOriginal = xslt2Cadena.cadena(xmlfile.getAbsolutePath());

        String firmar = firma.firmar(cadenaOriginal, cfdi.getEmisor().getRfc());
        cfdi.setSello(firmar);

        try {
            JAXBContext jc = JAXBContext.newInstance(sat.Comprobante.class, sat.Nomina.class);
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

        Resultado resultadoDeTimbre = timbrado(xmlfile.getAbsolutePath());
        System.out.println(resultadoDeTimbre.getStatus());
        System.out.println(resultadoDeTimbre.getCodigo());
        System.out.println(resultadoDeTimbre.getTimbre());
        int val1=resultadoDeTimbre.getTimbre().indexOf("UUID=\"") + 6;
        int val2=resultadoDeTimbre.getTimbre().indexOf("\" Version=\"1.1\"");
        String uuidT = resultadoDeTimbre.getTimbre().substring(val1, val2);
        System.out.println("UUID:"+uuidT);

        nomina.entidad.Archivos archivo_XML = new nomina.entidad.Archivos();
        archivo_XML.setComprobanteL(comprobanteX);
        archivo_XML.setContenido(resultadoDeTimbre.getTimbre().getBytes());
        archivo_XML.setTipo("XML");
        archivo_XML.setNombre(comprobanteX.getSerie() + comprobanteX.getFolio() + ".xml");
        archivo_XML.setIdarchivos(0);
        this.archivosFacade.create(archivo_XML);

        nomina.entidad.Archivos archivo_CBB = new nomina.entidad.Archivos();
        archivo_CBB.setComprobanteL(comprobanteX);
        archivo_CBB.setContenido(Base64.getDecoder().decode(resultadoDeTimbre.getCodigo().getBytes()));
        archivo_CBB.setTipo("CBB");
        archivo_CBB.setNombre(comprobanteX.getSerie() + comprobanteX.getFolio() + ".png");
        archivo_CBB.setIdarchivos(0);
        this.archivosFacade.create(archivo_CBB);

        //crear archivo pdf
        try {
            Transformacion transforma = new Transformacion();
            byte datos[] = transforma.generaPDF(getXML(comprobanteX.getContribuyente().getRfc()), resultadoDeTimbre.getTimbre().getBytes(), cfdi);
            nomina.entidad.Archivos archivo_PDF = new nomina.entidad.Archivos();
            archivo_PDF.setComprobanteL(comprobanteX);
            archivo_PDF.setContenido(datos);
            archivo_PDF.setTipo("PDF");
            archivo_PDF.setNombre(comprobanteX.getSerie() + comprobanteX.getFolio() + ".pdf");
            archivo_PDF.setIdarchivos(0);
            this.archivosFacade.create(archivo_PDF);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        try {
            comprobanteX.setUuid(uuidT);
            Integer valorTempo=Integer.parseInt(comprobanteX.getFolio())-1;
            comprobanteX.setFolio(valorTempo.toString());
            comprobanteLFacade.edit(comprobanteX);
            Empleado empleadoN = (Empleado) this.recuperarParametroObject("empleadoN");
            this.sendMail(empleadoN.getContribuyente().getEmail()/*"fco@ovante.com.mx"*/, "Correo", "Documentos Nomina", cfdi);

            /**
             * *** metodo para generar PDF ***
             */
        } catch (NamingException ex) {
            Logger.getLogger(CrearCFDI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(CrearCFDI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private byte[] getXML(String rfc) {
        byte[] llave = new byte[0];
        try {

            BufferedInputStream bis;
            InputStream keyResource = CertificadoUsuario.class.getResourceAsStream("../resources/" + rfc + "/impresionCFDI.xsl");
            bis = new BufferedInputStream(keyResource);
            llave = new byte[keyResource.available()];
            bis.read(llave);
            bis.close();
            return llave;
        } catch (IOException ex) {
            Logger.getLogger(CrearCFDI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return llave;
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

    private void sendMail(String email, String subject, String body, Comprobante cfdi) throws NamingException, MessagingException {
        MimeMessage message = new MimeMessage(correo);
        message.setSubject(subject);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        //message.setText(body);
        // Now set the actual message
        messageBodyPart.setText(body);

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        File baseDir = new File(".");
        File outDir = new File(baseDir, "out");
        File pdffile = new File(outDir, "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".pdf");
        DataSource source = new FileDataSource(pdffile);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(pdffile.getName());
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        baseDir = new File(".");
        outDir = new File(baseDir, "out");
        pdffile = new File(outDir, "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".xml");
        source = new FileDataSource(pdffile);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(pdffile.getName());
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        message.setContent(multipart);

        // Send message
        Transport.send(message);
    }
    
    protected Object recuperarParametroObject(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object retorno = session.getAttribute(parametro);
        return retorno;
    }
}
