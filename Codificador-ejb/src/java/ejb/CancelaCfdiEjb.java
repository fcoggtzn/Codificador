/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.WebServiceRef;
import nomina.entidad.Archivos;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Configuracion;
import nomina.entidad.Empresa;
import nomina.servicio.ArchivosFacadeLocal;
import nomina.servicio.ComprobanteLFacadeLocal;
import utilerias.CertificadoUsuario;
import utilerias.Transformacion;
import webServiceSat.prueba.Cancelaciones33Service;
import webServiceSat.prueba.ResultadoCancelacion;
import webServiceSatPrueba.TimbradoServiceService;

/**
 *
 * @author ovante
 */
@Stateless
public class CancelaCfdiEjb implements CancelaCfdiEjbLocal {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/www.sefactura.com.mx/sefacturapac/Cancelaciones_33.wsdl")
    private webServiceCancelacionProduccion.Cancelaciones33Service service_2;

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/pruebas.sefactura.com.mx_3014/sefacturapac/Cancelaciones_33.wsdl")
    private Cancelaciones33Service cancelacionPrueba;

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
           String status;


  
            if (UUID == null) {
                UUID = "6F288FE6-98B5-4820-89E6-A448212913EF";
            }
          
                 comprobanteX = comprobanteFacade.findByUUID(UUID); 
         if (comprobanteX.getEstatus() != -1){        
        /*encontrar el XSL del comprobante */
        if ( configura.isPrueba()){
                webServiceSat.prueba.SolCancelacion33 solicitud = new webServiceSat.prueba.SolCancelacion33();
              /* metodo viejo de cancelacion sin confirmacion
                                webServiceSatPrueba.SolCancelacion solicitud = new    webServiceSatPrueba.SolCancelacion();
                */
               // campos opcionales en el nuevo metodo  
                       solicitud.setCertificado(certificadoUsuario.getBase64Certificado());
               // campos opcionales en el nuevo metodo  
                       solicitud.setLlavePrivada(certificadoUsuario.getBase64Llave());      
               //   ya no se enviae 
                       solicitud.setPassword(Base64.getEncoder().encodeToString(String.valueOf(certificadoUsuario.getClave()).getBytes("UTF8")));
               solicitud.setRfcEmisor(empresaTemp.getContribuyente().getRfc());
               solicitud.getUuid().add(UUID);
               //    cambia a metodo nuevo  String cancelacion = cancelacion(solicitud,configura.getLoginWeb(),configura.getPassWeb());
               String cancelacionPrueba1 = this.cancelacionPrueba(solicitud,configura.getLoginWeb(),configura.getPassWeb());
               
              System.out.println(cancelacionPrueba1);
              comprobanteX.setNotas(cancelacionPrueba1 + comprobanteX.getNotas());
              
               ResultadoCancelacion consultaCancelacion = this.consultaCancelacion(comprobanteX.getContribuyente().getRfc(), comprobanteX.getContribuyente1().getRfc(), UUID, comprobanteX.getTotal(),"12345", configura.getLoginWeb(),configura.getPassWeb());
              System.out.println(consultaCancelacion.getCodigoEstatus());
              System.out.println(consultaCancelacion.getEsCancelable());
              status = consultaCancelacion.getEstado();
    
                            

        }
        else
        {
                 /*   produccion.SolCancelacion solicitud = new    produccion.SolCancelacion();
                 solicitud.setCertificado(certificadoUsuario.getBase64Certificado());
                 solicitud.setPassword(Base64.getEncoder().encodeToString(String.valueOf(certificadoUsuario.getClave()).getBytes("UTF8")));
                 solicitud.setLlavePrivada(certificadoUsuario.getBase64Llave());            
                 solicitud.getUuid().add(UUID); forma vieja de hacer la solicitud*/
            
                    
                      webServiceCancelacionProduccion.SolCancelacion33 solicitud = new webServiceCancelacionProduccion.SolCancelacion33();
              /* metodo viejo de cancelacion sin confirmacion
                                webServiceSatPrueba.SolCancelacion solicitud = new    webServiceSatPrueba.SolCancelacion();
                */
               // campos opcionales en el nuevo metodo  
                       solicitud.setCertificado(certificadoUsuario.getBase64Certificado());
               // campos opcionales en el nuevo metodo  
                       solicitud.setLlavePrivada(certificadoUsuario.getBase64Llave());      
               //   ya no se enviae 
                       solicitud.setPassword(Base64.getEncoder().encodeToString(String.valueOf(certificadoUsuario.getClave()).getBytes("UTF8")));
               solicitud.setRfcEmisor(empresaTemp.getContribuyente().getRfc());
               solicitud.getUuid().add(UUID);
             /*   String cancelacion = cancelacionProduccion(solicitud,configura.getLoginWeb(),configura.getPassWeb());
                System.out.println(cancelacion);
               System.out.println(cancelacionPrueba); Metodo */
              comprobanteX.setNotas(cancelacionPrueba + comprobanteX.getNotas());
                String cancelacionPrueba1 = this.cancelacionProduccion(solicitud,configura.getLoginWeb(),configura.getPassWeb());
               
              System.out.println(cancelacionPrueba1);
              comprobanteX.setNotas(cancelacionPrueba1 + comprobanteX.getNotas());
              
              webServiceCancelacionProduccion.ResultadoCancelacion consultaCancelacionProduccion = this.consultaCancelacionProduccion(comprobanteX.getContribuyente().getRfc(), comprobanteX.getContribuyente1().getRfc(), UUID, comprobanteX.getTotal(),"12345", configura.getLoginWeb(),configura.getPassWeb());
              System.out.println(consultaCancelacionProduccion.getCodigoEstatus());
              System.out.println(consultaCancelacionProduccion.getEsCancelable());
              
                 status = consultaCancelacionProduccion.getEstado();
      
                 
        }
        
        
                      comprobanteFacade.edit(comprobanteX); //guardar cambios en comprobante
      if(!status.equals("Cancelado")){
          comprobanteX.setEstatus(-6);
            comprobanteFacade.edit(comprobanteX); //guardar cambios en comprobante
      }
      else{
            comprobanteX.setEstatus(-1);
            comprobanteFacade.edit(comprobanteX); //guardar cambios en comprobante
      //   comprobanteX = comprobanteFacade.findByUUID(UUID); 
        /*encontrar el XSL del comprobante */
         findXML = comprobanteFacade.findXML(comprobanteX);
         if (comprobanteX.getTipo().equals("P")){
              this.comprobanteFacade.bonifica(findXML);
            
         }
        try {
            regeneraPDF();
        } catch (NamingException ex) {
            Logger.getLogger(CancelaCfdiEjb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(CancelaCfdiEjb.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
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

    private String cancelacionPrueba(webServiceSat.prueba.SolCancelacion33 solicitud, java.lang.String usuario, java.lang.String clave) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webServiceSat.prueba.Cancelaciones33 port = this.cancelacionPrueba.getCancelaciones33Port();
        return port.cancelacion33(solicitud, usuario, clave);
    }

    private ResultadoCancelacion consultaCancelacion(java.lang.String rfcEmisor, java.lang.String rfcReceptor, java.lang.String uuid, double total, java.lang.String ultimos, java.lang.String usuario, java.lang.String clave) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webServiceSat.prueba.Cancelaciones33 port = cancelacionPrueba.getCancelaciones33Port();
        return port.consultaCancelacion(rfcEmisor, rfcReceptor, uuid, total, ultimos, usuario, clave);
    }

    private String cancelacionProduccion(webServiceCancelacionProduccion.SolCancelacion33 solicitud, java.lang.String usuario, java.lang.String clave) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webServiceCancelacionProduccion.Cancelaciones33 port = service_2.getCancelaciones33Port();
        return port.cancelacion33(solicitud, usuario, clave);
    }

    private webServiceCancelacionProduccion.ResultadoCancelacion consultaCancelacionProduccion(java.lang.String rfcEmisor, java.lang.String rfcReceptor, java.lang.String uuid, double total, java.lang.String ultimos, java.lang.String usuario, java.lang.String clave) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webServiceCancelacionProduccion.Cancelaciones33 port = service_2.getCancelaciones33Port();
        return port.consultaCancelacion(rfcEmisor, rfcReceptor, uuid, total, ultimos, usuario, clave);
    }
    
     
    
}
