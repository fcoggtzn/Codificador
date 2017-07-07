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
    public void crear() throws FileNotFoundException, DatatypeConfigurationException, TransformerConfigurationException, TransformerException, NoSuchAlgorithmException {
        // TODO code application logic here
        /*  Crear xml */

        Comprobante cfdi = new Comprobante();
        cfdi.setSerie("A");
        cfdi.setFolio("01");
        Comprobante.Emisor emisor = new Comprobante.Emisor();
        emisor.setNombre("Pruebas SA ");
        emisor.setRfc("TME960709LR2");
        emisor.setRegimenFiscal("601");
        //601 Morales
        //603 
        Comprobante.Receptor receptor = new Comprobante.Receptor();
        receptor.setNombre("Pruebas y Mas S de RL MI de CV");
        receptor.setRfc("WEB070411754");
        
        // uso del cfdi
        receptor.setUsoCFDI(CUsoCFDI.G_01);
        
        
         /*ingresar emisor y receptor */
         cfdi.setEmisor(emisor);
         cfdi.setReceptor(receptor);

        //   cfdi.setMoneda(CMoneda.);
        cfdi.setTipoCambio(BigDecimal.ONE);
        cfdi.setMetodoPago(CMetodoPago.PUE);
        /*   cfdi.setF*/
        cfdi.setLugarExpedicion("20140");
        cfdi.setVersion("3.3");
        
        
        
        /*
        CfdiRelacionado relacion =  new CfdiRelacionado();
        relacion.setUUID("5FB2822E-396D-4725-8521-CDC4BDD20CCF");
        cfdi.getCfdiRelacionados().setTipoRelacion("04");
        cfdi.getCfdiRelacionados().getCfdiRelacionado().add(relacion);
         */
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(Calendar.getInstance().getTime());
        XMLGregorianCalendar newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        newXMLGregorianCalendar.setTimezone( DatatypeConstants.FIELD_UNDEFINED );
        newXMLGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        
        cfdi.setFecha(newXMLGregorianCalendar);
       
        CertificadoUsuario certificadoUsuario = new CertificadoUsuario("TME960709LR2");

        

        cfdi.setCertificado(certificadoUsuario.getBase64Certificado());
        //cfdi.setNoCertificado("20001000000300022763");
        cfdi.setNoCertificado(certificadoUsuario.getCertNumber());
        cfdi.setTipoCambio(new BigDecimal(1.0));
        cfdi.setMoneda(CMoneda.MXN);
        cfdi.setTipoDeComprobante(CTipoDeComprobante.I);

        cfdi.setFormaPago("01");

        Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();
        
        
        /*agregar concepto */
        concepto.setCantidad(new BigDecimal(1));
        concepto.setClaveProdServ("01010101");
        concepto.setNoIdentificacion("001-002");
        concepto.setClaveUnidad("KGM");
        concepto.setUnidad("Pieza");
        concepto.setDescripcion("Pieza de prueba");
        concepto.setValorUnitario(new BigDecimal(130).setScale(2));
        concepto.setImporte(new BigDecimal(130).setScale(2));
        concepto.setDescuento(BigDecimal.ZERO);
        
        
        /*impuestos del concepto */ 
        Comprobante.Conceptos.Concepto.Impuestos impuestosConcepto = new Comprobante.Conceptos.Concepto.Impuestos();
        Comprobante.Conceptos.Concepto.Impuestos.Traslados transladosConcepto = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
        Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado translado = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
        translado.setImpuesto("002");
        translado.setTasaOCuota(new BigDecimal(0.16).setScale(2, RoundingMode.HALF_UP));
        translado.setBase(new BigDecimal(130).setScale(2));
        translado.setImporte(new BigDecimal(130 * 0.16).setScale(2, RoundingMode.HALF_UP));
        translado.setTipoFactor(CTipoFactor.TASA);      
        transladosConcepto.getTraslado().add(translado);
        impuestosConcepto.setTraslados(transladosConcepto);

        
        /*agregar impuesto al concepto*/
        concepto.setImpuestos(impuestosConcepto);
        
        

        cfdi.setDescuento(BigDecimal.ZERO);
        cfdi.setSubTotal(new BigDecimal(130).setScale(2));

        Comprobante.Impuestos impuestos = new Comprobante.Impuestos();
        impuestos.setTotalImpuestosTrasladados(new BigDecimal(130 * 0.16).setScale(2, RoundingMode.HALF_UP));
        Comprobante.Impuestos.Traslados translados = new Comprobante.Impuestos.Traslados();
        Comprobante.Impuestos.Traslados.Traslado transladoIVA = new Comprobante.Impuestos.Traslados.Traslado();
        transladoIVA.setImporte(new BigDecimal(130 * .16).setScale(2, RoundingMode.HALF_UP));
        transladoIVA.setImpuesto("002");
        //001 isr
        //002 iva
        //003 ieps
        transladoIVA.setTasaOCuota(new BigDecimal(16.0 / 100).setScale(2, RoundingMode.HALF_UP));
        transladoIVA.setTipoFactor(CTipoFactor.TASA);

        translados.getTraslado().add(transladoIVA);
        impuestos.setTraslados(translados);
        cfdi.setImpuestos(impuestos);

        cfdi.setTotal(new BigDecimal(130 * 1.16).setScale(2, RoundingMode.HALF_UP));

        Comprobante.Conceptos conceptos = new Comprobante.Conceptos();
        conceptos.getConcepto().add(concepto);
        cfdi.setConceptos(conceptos);

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

        
            String firmar = firma.firmar(cadenaOriginal, "TME960709LR2");
            cfdi.setSello(firmar);

            try {
                JAXBContext jc = JAXBContext.newInstance(sat.Comprobante.class);
                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
                m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
                
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
        String usuario= "WEB070411754";
        String clave="WEB070411754";
        String cfdiString;
        Archivo archivo = new Archivo();
        cfdiString = archivo.LeerString(cfdi);
        webServiceSatPrueba.TimbradoService port = service.getTimbradoServicePort();
        return port.timbrado(cfdiString, usuario, clave);
    }
}
