/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;
import nomina.entidad.Archivos;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Configuracion;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empresa;
import nomina.servicio.ArchivosFacadeLocal;
import nomina.servicio.ComprobanteLFacadeLocal;
import utilerias.CertificadoUsuario;
import utilerias.Transformacion;
import webServiceSatPrueba.TimbradoServiceService;

/**
 *
 * @author ovante
 */
@Stateless
public class CancelaCfdiEjb implements CancelaCfdiEjbLocal {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/www.sefactura.com.mx/sefacturapac/TimbradoService.wsdl")
    private produccion.TimbradoServiceService service_1;

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/pruebas.sefactura.com.mx_3014/sefacturapac/TimbradoService.wsdl")
    private TimbradoServiceService service;
    
    
    @EJB
    private ComprobanteLFacadeLocal comprobanteFacade;
    
    @EJB
    private ArchivosFacadeLocal archivoFacade;
    
      @EJB
    private EmailLocal email;
    
    private ComprobanteL comprobanteX;
    private Archivos findXML;
    
    
    
     public void cancela(String UUID) throws IOException {
           Empresa empresaTemp = (Empresa) this.recuperarParametroObject("empresaActual");
           CertificadoUsuario certificadoUsuario = new CertificadoUsuario(empresaTemp.getContribuyente().getRfc());
           Object[] vectorConfiguracion = empresaTemp.getConfiguracionCollection().toArray();
           Configuracion configura;
           configura = (Configuracion) vectorConfiguracion[0];


        try {
            if (UUID == null) {
                UUID = "6F288FE6-98B5-4820-89E6-A448212913EF";
            }
          
            GregorianCalendar c = new GregorianCalendar();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, -2);
            Date time = calendar.getTime();
            c.setTime(time);
               XMLGregorianCalendar fecha_actual = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            fecha_actual.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            fecha_actual.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(CancelaCfdiEjb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if ( configura.isPrueba()){
                webServiceSatPrueba.SolCancelacion solicitud = new    webServiceSatPrueba.SolCancelacion();
                solicitud.setCertificado(certificadoUsuario.getBase64Certificado());
                solicitud.setPassword(Base64.getEncoder().encodeToString(String.valueOf(certificadoUsuario.getClave()).getBytes("UTF8")));
                solicitud.setLlavePrivada(certificadoUsuario.getBase64Llave());            
                solicitud.getUuid().add(UUID);
               String cancelacion = cancelacion(solicitud,configura.getLoginWeb(),configura.getPassWeb());
               System.out.println(cancelacion);
        }
        else
        {
                    produccion.SolCancelacion solicitud = new    produccion.SolCancelacion();
                 solicitud.setCertificado(certificadoUsuario.getBase64Certificado());
                 solicitud.setPassword(Base64.getEncoder().encodeToString(String.valueOf(certificadoUsuario.getClave()).getBytes("UTF8")));
                 solicitud.setLlavePrivada(certificadoUsuario.getBase64Llave());            
                 solicitud.getUuid().add(UUID);
                String cancelacion = cancelacionProduccion(solicitud,configura.getLoginWeb(),configura.getPassWeb());
                System.out.println(cancelacion);
        }
         comprobanteX = comprobanteFacade.findByUUID(UUID); 
        /*encontrar el XSL del comprobante */
         findXML = comprobanteFacade.findXML(comprobanteX);
        try {
            regeneraPDF();
        } catch (NamingException ex) {
            Logger.getLogger(CancelaCfdiEjb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(CancelaCfdiEjb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     protected Object recuperarParametroObject(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object retorno = session.getAttribute(parametro);
        return retorno;
    }


    private String cancelacion(webServiceSatPrueba.SolCancelacion solicitud, java.lang.String usuario, java.lang.String clave) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webServiceSatPrueba.TimbradoService port = service.getTimbradoServicePort();
        return port.cancelacion(solicitud, usuario, clave);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private String cancelacionProduccion(produccion.SolCancelacion solicitud, java.lang.String usuario, java.lang.String clave) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        produccion.TimbradoService port = service_1.getTimbradoServicePort();
        return port.cancelacion(solicitud, usuario, clave);
    }
    
    
    
      public void regeneraPDF() throws NamingException, MessagingException {

        //crear archivo pdf
        try {
            Transformacion transforma = new Transformacion();
            byte datos[] = transforma.generaPDF(CertificadoUsuario.getXSL(comprobanteX.getContribuyente().getRfc(),"C"),  findXML.getContenido(), comprobanteX.getContribuyente().getRfc(),comprobanteX.getFolio(),comprobanteX.getSerie());
            nomina.entidad.Archivos archivo_PDF  = this.comprobanteFacade.findPDF(comprobanteX);
            
            archivo_PDF.setContenido(datos);
            
            archivoFacade.edit(archivo_PDF);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        try {            
            
            email.sendMail( "Su cfdi fue cancelado por datos erroneos  "+comprobanteX.getContribuyente().getNotas(), comprobanteX);
            
        } catch (Exception e) {
           System.out.println(e.getMessage());
           throw e;
        }
    }
}
