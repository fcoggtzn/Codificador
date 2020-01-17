/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import catalogo.entidad.FormaPago;
import catalogo.entidad.MetodoPago;
import catalogo.entidad.RegimenFiscal;
import catalogo.entidad.UsoCfdi;
import catalogo.servicio.FormaPagoFacadeLocal;
import catalogo.servicio.MetodoPagoFacadeLocal;
import catalogo.servicio.RegimenFiscalFacadeLocal;
import catalogo.servicio.TipoRegimenFacadeLocal;
import catalogo.servicio.UsoCfdiFacadeLocal;
import com.sun.xml.ws.util.Pool;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
import javax.mail.util.ByteArrayDataSource;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceRef;
import nomina.entidad.Archivos;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Configuracion;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;
import nomina.entidad.Folio;
import nomina.servicio.ArchivosFacadeLocal;
import nomina.servicio.ComprobanteLFacadeLocal;
import nomina.servicio.ContribuyenteFacadeLocal;
import nomina.servicio.EmpresaFacadeLocal;
import nomina.servicio.FolioFacadeLocal;
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
import produccion.Resultado;
import utilerias.MyNameSpaceMapperComprobante;
import utilerias.MyNameSpaceMapperPago;
import utilerias.NumeroALetras;
import webServiceSatPrueba.TimbradoServiceService;

/**
 *
 * @author franciscogutierrez
 */
@Stateless
public class CrearCFDI implements CrearCFDILocal {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/www.sefactura.com.mx/sefacturapac/TimbradoService.wsdl")
    private produccion.TimbradoServiceService service_1;

    @EJB
    private ComprobanteLFacadeLocal comprobanteLFacade;
    @EJB
    private EmpresaFacadeLocal empresaFacade;

   

    @EJB
    private ArchivosFacadeLocal archivosFacade;

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/pruebas.sefactura.com.mx_3014/sefacturapac/TimbradoService.wsdl")
    private TimbradoServiceService service;

    @EJB
    private Xslt2CadenaLocal xslt2Cadena;
    @EJB
    private FirmaLocal firma;
    @EJB
    private ContribuyenteFacadeLocal contribuyenteFacade;
    @EJB
    private FolioFacadeLocal folioFacade;
  
    
    /*paga gener el formato de impresion */ 
    @EJB
    FormaPagoFacadeLocal formaPagoFacade;
    @EJB
    MetodoPagoFacadeLocal metodoPagoFacade;
    @EJB
    RegimenFiscalFacadeLocal regimenFiscalFacade;
    @EJB
    UsoCfdiFacadeLocal usoCfdiFacade;
    
    /*generar envio por Correo*/
      @EJB
    private EmailLocal email;
    

    private String cadenaOriginal;
    private Comprobante cfdi;
    private ComprobanteL comprobanteX;
    private produccion.Resultado resultadoDeTimbre;
    private static final String ruta = System.getProperty("user.dir")+"/";


    /**
     * @param args the command line arguments
     */
    public void crear(Comprobante cfdi, ComprobanteL comprobanteX) throws EJBException, FileNotFoundException, DatatypeConfigurationException, TransformerConfigurationException, TransformerException, NoSuchAlgorithmException {
        Empresa empresaTemp = (Empresa) this.recuperarParametroObject("empresaActual");
        if (!cfdi.getTipoDeComprobante().value().equals("P")) {
            Folio folioEmpresa = folioFacade.getFolioEmpresa(empresaTemp,cfdi.getTipoDeComprobante().value());
            folioFacade.folioInc(folioEmpresa);
            cfdi.setFolio(folioEmpresa.getFolio().toString());
            comprobanteX.setFolio(folioEmpresa.getFolio().toString());
        }
        
        this.cfdi = cfdi;
        this.comprobanteX = comprobanteX;
       
        /**
         * ***** metodo de serializar ****
         */
        File baseDir = new File(ruta+"empresas/"+cfdi.getEmisor().getRfc());
        File outDir = new File(baseDir, "out");
        File xmlfile = new File(outDir, "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".xml");

        StreamResult result = new StreamResult(xmlfile);
        try {
            JAXBContext jc = JAXBContext.newInstance(sat.Comprobante.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
            
            
            if (cfdi.getTipoDeComprobante().value().equals("P")) {
                jc = JAXBContext.newInstance(sat.Comprobante.class,sat.Pagos.class);
               
                m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd");
                m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNameSpaceMapperPago());
            } else {
                if (cfdi.getTipoDeComprobante().value().equals("N")) {
                    jc = JAXBContext.newInstance(sat.Comprobante.class, sat.Nomina.class);
                    m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd");
                    m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNameSpaceMapper());
                } else {
                    m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNameSpaceMapperComprobante());
                }
            }
            //    m.setProperty("com.sun.xml.bind.marshaller.namespacePrefixMapper", new MyNamespaceMapper());
            m.marshal(cfdi, result);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new EJBException(e.getCause().toString());
        }

        /**
         * * metodo para obtener la cadena original ******
         */
        //String factura = "factura" + cfdi.getFolio() + "-" + cfdi.getSerie() + ".xml";
        cadenaOriginal = xslt2Cadena.cadena(xmlfile.getAbsolutePath());

        String firmar = firma.firmar(cadenaOriginal, cfdi.getEmisor().getRfc());
        cfdi.setSello(firmar);

        try {
            JAXBContext jc = JAXBContext.newInstance(sat.Comprobante.class);

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
            if (cfdi.getTipoDeComprobante().value().equals("P")) {
                jc = JAXBContext.newInstance(sat.Comprobante.class,sat.Pagos.class);
                m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd");
                m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNameSpaceMapperPago());
            } else {
                if (cfdi.getTipoDeComprobante().value().equals("N")) {
                    jc = JAXBContext.newInstance(sat.Comprobante.class, sat.Nomina.class);
                    m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd");
                    m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNameSpaceMapper());
                } else {
                    m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNameSpaceMapperComprobante());
                }
            }
            m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
            //    m.setProperty("com.sun.xml.bind.marshaller.namespacePrefixMapper", new MyNamespaceMapper());
            m.marshal(cfdi, result);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new EJBException(e.getCause().toString());
        }

        /**
         * *** metodo para mandar timbrar ***
         */
        Empresa empresa = empresaFacade.getEmpresa(cfdi.getEmisor().getRfc());
        Object[] vectorConfiguracion = empresa.getConfiguracionCollection().toArray();
        Configuracion configura;
        configura = (Configuracion) vectorConfiguracion[0];
        if (configura.isPrueba()) {
            webServiceSatPrueba.Resultado resultPruebas = timbrado(xmlfile.getAbsolutePath());
            resultadoDeTimbre = new Resultado();
            resultadoDeTimbre.setCodigo(resultPruebas.getCodigo());
            resultadoDeTimbre.setStatus(resultPruebas.getStatus());
            resultadoDeTimbre.setTimbre(resultPruebas.getTimbre());
        } else {
            resultadoDeTimbre = timbradoProduccion(xmlfile.getAbsolutePath(), configura.getLoginWeb(), configura.getPassWeb());

        }
        
        
        /*mandar excepcion que no lo timbro  */
        System.out.println(resultadoDeTimbre.getStatus());
        if (!resultadoDeTimbre.getStatus().isEmpty()){
          
           throw new EJBException("Error: "+resultadoDeTimbre.getStatus());           
        }
        System.out.println(resultadoDeTimbre.getCodigo());
        System.out.println(resultadoDeTimbre.getTimbre());
        int val1 = resultadoDeTimbre.getTimbre().indexOf("==\" UUID=\"") + 10;
        int val2 = resultadoDeTimbre.getTimbre().indexOf("\" Version=\"1.1\"");
        String uuidT = resultadoDeTimbre.getTimbre().substring(val1, val2);
        System.out.println("UUID:" + uuidT);

        nomina.entidad.Archivos archivo_XML = new nomina.entidad.Archivos();
        archivo_XML.setComprobanteL(comprobanteX);
        try {
            archivo_XML.setContenido(resultadoDeTimbre.getTimbre().getBytes("UTF8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CrearCFDI.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        
        /*crear formato xml de impresion */
         
        String addenda = " <cfdi:Addenda>"
                + "        <clienteDatos  condicionesDePago=\"CONTADO\" "
                + " consignado=\"MISMO\""
                + " numcliente=\"05740\""
                + " numpedido=\"\" "
                + " totalenletra=\""+new NumeroALetras(comprobanteX.getTotal())+"\""
                + " vencimiento=\"06/12/17\" vendedor=\"FZH\">"
                + "            <cfdi:DomicilioCliente"
                + " direccion=\""+comprobanteX.getContribuyente().getImpresion() +"\" />"
                + "</clienteDatos>"
                + "<notas notas=\""+comprobanteX.getNotas()+"\" />"
                + "</cfdi:Addenda>"
                + "</cfdi:Comprobante>";

        String replace = resultadoDeTimbre.getTimbre().replace("</cfdi:Comprobante>", addenda);
        replace = replace.replace("Impuesto=\"001\"", "Impuesto=\"001 ISR\"");
        replace = replace.replace("Impuesto=\"002\"", "Impuesto=\"002 IVA\"");
        replace = replace.replace("Impuesto=\"003\"", "Impuesto=\"003 IEPS\"");
        
        replace = replace.replace("TipoDeComprobante=\"I\"", "TipoDeComprobante=\"I Ingreso\"");
        replace = replace.replace("TipoDeComprobante=\"E\"", "TipoDeComprobante=\"E Egreso\"");
        replace = replace.replace("TipoDeComprobante=\"N\"", "TipoDeComprobante=\"N Nomina\"");
        replace = replace.replace("TipoDeComprobante=\"P\"", "TipoDeComprobante=\"P Pago\"");

       if(cfdi.getTipoDeComprobante() != CTipoDeComprobante.P) {
           //remplaza forma de pago
           String formaPago = "FormaPago=\"" + cfdi.getFormaPago() + "\"";
           //Busca la forma de pago
           FormaPago formaFind = this.formaPagoFacade.findbyID(cfdi.getFormaPago());
           String formaPagoR = "FormaPago=\"" + cfdi.getFormaPago() + " " + formaFind.getDescripcion() + "\"";
           replace = replace.replace(formaPago, formaPagoR);

           //replaza metododePago
           String metodoPago = "MetodoPago=\"" + cfdi.getMetodoPago() + "\"";
           //Busca  metodo de pago
           MetodoPago findMetodo = this.metodoPagoFacade.findbyID(cfdi.getMetodoPago().value());
           String metodoPagoR = "MetodoPago=\"" + cfdi.getMetodoPago() + " " + findMetodo.getDescripcion() + "\"";
           replace = replace.replace(metodoPago, metodoPagoR);
      
         //replaza Regimen
        String regimen = "RegimenFiscal=\""+cfdi.getEmisor().getRegimenFiscal()+"\"";
        
        //Busca  metodo de pago
        RegimenFiscal findRegimen = this.regimenFiscalFacade.findbyID(cfdi.getEmisor().getRegimenFiscal());
        String regimenR = "RegimenFiscal=\""+cfdi.getEmisor().getRegimenFiscal()+" "+findRegimen.getDescripcion()+"\"";
        replace = replace.replace(regimen, regimenR);
        
     
    
       //replaza metododePago
        String usoCfdi = "UsoCFDI=\""+cfdi.getReceptor().getUsoCFDI().value()+"\"";
        //Busca  metodo de pago
        UsoCfdi findUso = this.usoCfdiFacade.findId(cfdi.getReceptor().getUsoCFDI().value());
        String usoCfdiR = "UsoCFDI=\""+cfdi.getReceptor().getUsoCFDI().value()+" "+findUso.getDescripcion()+"\"";
        replace = replace.replace(usoCfdi, usoCfdiR);
     
  }
        
        
        nomina.entidad.Archivos archivo_IMP = new nomina.entidad.Archivos();

        archivo_IMP.setComprobanteL(comprobanteX);
        try {
            archivo_IMP.setContenido(replace.getBytes("UTF8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CrearCFDI.class.getName()).log(Level.SEVERE, null, ex);
        }
        archivo_IMP.setTipo("IMP");
        archivo_IMP.setNombre(comprobanteX.getSerie() + comprobanteX.getFolio() + "_imp.xml");
        archivo_IMP.setIdarchivos(0);
        this.archivosFacade.create(archivo_IMP);
        this.resultadoDeTimbre.setTimbre(replace);
        

        /*
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
         */
        comprobanteX.setUuid(uuidT);
        /*  Integer valorTempo = Integer.parseInt(comprobanteX.getFolio()) - 1;
comprobanteX.setFolio(valorTempo.toString()); esta mamada que ----error en obj -- */
     //   comprobanteX.setPago(cfdi.getFormaPago());
        comprobanteLFacade.edit(comprobanteX);
        

    }

    public void generaPDF() throws NamingException, MessagingException {

        //crear archivo pdf
        try {
            Transformacion transforma = new Transformacion();
            byte datos[] = transforma.generaPDF(CertificadoUsuario.getXSL(comprobanteX.getContribuyente().getRfc(),comprobanteX.getTipo()), resultadoDeTimbre.getTimbre().getBytes("UTF8"), cfdi);
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
            //Empleado empleadoN = (Empleado) this.recuperarParametroObject("empleadoN");
            List<Contribuyente> findcontribuyentesByRFC = contribuyenteFacade.findcontribuyentesByRFC(cfdi.getReceptor().getRfc());
            Contribuyente contrib = findcontribuyentesByRFC.get(0);
            System.out.println("Enviando comprobante");
            comprobanteX = this.comprobanteLFacade.find(comprobanteX.getIdComprobante());
            email.sendMail( "Documentos cfdi "+comprobanteX.getContribuyente().getNotas(), comprobanteX);
            
        } catch (Exception e) {
           System.out.println(e.getMessage());
           throw e;
        }
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
    private webServiceSatPrueba.Resultado timbrado(java.lang.String cfdi) {
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

   

    protected Object recuperarParametroObject(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object retorno = session.getAttribute(parametro);
        return retorno;
    }

    private Resultado timbradoProduccion(java.lang.String cfdi, java.lang.String usuario, java.lang.String clave) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.

        String cfdiString;
        Archivo archivo = new Archivo();
        cfdiString = archivo.LeerString(cfdi);
        produccion.TimbradoService port = service_1.getTimbradoServicePort();
        return port.timbrado(cfdiString, usuario, clave);
    }

    @Override
    public Comprobante leerCFDI(ComprobanteL comprobanteX) {
        Comprobante regreso = null;
        for( Archivos archivo: comprobanteX.getArchivosCollection()){
            if(archivo.getTipo().equals("XML")){
                try{
                 JAXBContext context = JAXBContext.newInstance(Comprobante.class);

	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    regreso = (Comprobante) unmarshaller.unmarshal( new ByteArrayInputStream(archivo.getContenido()));
                }
                catch(Exception e){
                    LOG.severe(e.getMessage());
                }

            }
        }
        System.out.println("Lectura de cfdi");
        System.out.println(regreso.getEmisor().getRfc());
        return regreso;
    }
    private static final Logger LOG = Logger.getLogger(CrearCFDI.class.getName());
}
