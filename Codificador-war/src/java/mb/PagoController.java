/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import catalogo.entidad.FormaPago;
import ejb.CrearCFDILocal;
import java.io.FileNotFoundException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.TransformerException;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Contribuyente;
import nomina.entidad.DeduccionPercepcion;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;
import nomina.entidad.Pago;
import nomina.servicio.ComprobanteLFacadeLocal;
import nomina.servicio.PagoFacadeLocal;
import sat.CEstado;
import sat.CMetodoPago;
import sat.CMoneda;
import sat.CTipoDeComprobante;
import sat.CTipoFactor;
import sat.CTipoNomina;
import sat.CUsoCFDI;
import sat.Comprobante;
import sat.Comprobante.CfdiRelacionados.CfdiRelacionado;
import sat.Nomina;
import sat.Pagos;
import utilerias.CertificadoUsuario;

/**
 *
 * @author ovante
 */
@Named(value = "pagoController")
@SessionScoped
public class PagoController implements Serializable {

    @EJB
    private CrearCFDILocal crearCFDI;

    @EJB
    private PagoFacadeLocal pagoFacade;

    @EJB
    private ComprobanteLFacadeLocal comprobanteLFacade;
    
    
    private Contribuyente contribuyente;
    
    private List<Pago> aPago;
    private Empresa empresa;
    private FormaPago formaPago = new FormaPago();
    private String facturaRuta;
    private Date fecha;
    private String uuidRelacionado;

    
    
    @PostConstruct
    public void init(){
         aPago = new ArrayList<Pago>(); 
         contribuyente = null;
         empresa = (Empresa) this.recuperarParametroObject("empresaActual");
         fecha = Calendar.getInstance().getTime();
         uuidRelacionado = "";

    }

    /**
     * Creates a new instance of PagoController
     */
    public PagoController() {
       
    }
    
    public void addPago(ComprobanteL comprobante){
         Pago pago = null;
        for(Pago pg:aPago){
            if(pg.getComprobantePagado().getIdComprobante() == comprobante.getIdComprobante()){
                pago = pg;
                break;
            }
        }
        if (pago == null){
            pago = new Pago();        
            pago.setComprobantePagado(comprobante);
            pago.setMonto(comprobante.getSaldo());
            pago.setIdPago(0);
            aPago.add(pago);
            contribuyente = pago.getComprobantePagado().getContribuyente1();
        }
     
        
    }
    
    public double getTotal(){
        double total= 0;
        try{
             for(Pago pg:aPago){
                 total +=pg.getMonto();
             }
        }catch (Exception e){}
        return total;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }
   
    
    
    public void erasePago(Pago pago){    
        int contador = -1;
        for(Pago pg:aPago){
            contador++ ;
            if(Objects.equals(pg.getComprobantePagado().getIdComprobante(), pago.getComprobantePagado().getIdComprobante())){
                
                aPago.remove(contador);                
                break;
            }
        }
        
       
        
    }
    
    
    public void generar(ActionEvent e){
        
        /*fecha actual */
        GregorianCalendar c = new GregorianCalendar();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -2);
        Date time = calendar.getTime();
        c.setTime(time);
        
        
          XMLGregorianCalendar newXMLGregorianCalendar = null;
        
        try {
             newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
             newXMLGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
             newXMLGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*fecha del pago */
           GregorianCalendar c2 = new GregorianCalendar();       
           c2.setTime(fecha);
          // c2.add(Calendar.DATE, 1);
        
        
          XMLGregorianCalendar newXMLGregorianCalendar2 = null;
        
        try {
             newXMLGregorianCalendar2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
             newXMLGregorianCalendar2.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
             newXMLGregorianCalendar2.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        /*genera comprobantes de pago sin tener que generar timbre */ 
        
        ComprobanteL comprobanteDePago = new ComprobanteL();
        comprobanteDePago.setPago("Metodo Pago");
        comprobanteDePago.setSerie("P"); //serie p se utiliza para pagos
        comprobanteDePago.setFecha(Calendar.getInstance().getTime());
        comprobanteDePago.setContribuyente(empresa.getContribuyente());
        comprobanteDePago.setContribuyente1(contribuyente);
        comprobanteDePago.setTotal(getTotal()); //getTotal() en pagos el total es cero en facade debo cambiar
        comprobanteDePago.setSaldo(0.0);        
        comprobanteDePago.setIdComprobante(0);        
        comprobanteDePago.setTipo("P");
        comprobanteDePago.setUuid(Calendar.getInstance().getTime().toString());
        this.comprobanteLFacade.crearPago(comprobanteDePago); //genere la transaccion al final se guardara el pago
        
       
        
        Pagos pagos = new Pagos();
        pagos.setVersion("1.0");
        
                Pagos.Pago pagox = new Pagos.Pago();
                pagox.setFechaPago(newXMLGregorianCalendar2); //especifica la fecha del pago
                pagox.setFormaDePagoP(this.formaPago.getFormaPago()); //se tiene que especificar la forma de pago 
                pagox.setMonedaP(sat.CMoneda.MXN);
           //     pagox.setTipoCambioP( new BigDecimal(1));
                pagox.setMonto(new BigDecimal(0.0));
            
        
        
        for(Pago pg:this.aPago){
           pg.setPago(comprobanteDePago);
         //al final  this.pagoFacade.create(pg);
            ComprobanteL comprobantePagado = pg.getComprobantePagado();
            
           
            Double saldo = comprobantePagado.getSaldo();
            
            
                
        /*  version para un solo pago.      Pagos.Pago pagox = new Pagos.Pago();
                pagox.setFechaPago(newXMLGregorianCalendar2); //especifica la fecha del pago
                pagox.setFormaDePagoP(this.formaPago.getFormaPago()); //se tiene que especificar la forma de pago 
                pagox.setMonedaP(sat.CMoneda.MXN);
           //     pagox.setTipoCambioP( new BigDecimal(1));*/
                pagox.setMonto(new BigDecimal(Math.round((pg.getMonto()+ pagox.getMonto().doubleValue()) *100)/100));
            
            
            
               Pagos.Pago.DoctoRelacionado docRelacionado = new Pagos.Pago.DoctoRelacionado();
                docRelacionado.setIdDocumento(comprobantePagado.getUuid());
                docRelacionado.setMonedaDR(pagox.getMonedaP());
             //   docRelacionado.setTipoCambioDR(BigDecimal.ONE);
                docRelacionado.setMetodoDePagoDR(sat.CMetodoPago.PPD);
                docRelacionado.setImpSaldoAnt(new BigDecimal(Math.round(saldo*100)/100));
                  Long size = new Long(comprobantePagado.getPagoCollection().size());
                docRelacionado.setNumParcialidad(BigInteger.ONE.add(BigInteger.valueOf(size)));

            
                
            
             saldo -= pg.getMonto();
           // al final  this.comprobanteLFacade.edit(comprobantePagado); //Descontar pagos del comprobante de pago ***verificar si lo hace bien *****
             
         
        
                
                docRelacionado.setImpPagado(new BigDecimal(Math.round(pg.getMonto()*100)/100));
             
                docRelacionado.setImpSaldoInsoluto(new BigDecimal(Math.round(saldo*100)/100));
                
                pagox.getDoctoRelacionado().add(docRelacionado);
                
          //version para un solo pago      pagos.getPago().add(pagox);
                
        }
        
          pagos.getPago().add(pagox);
                
        
        Comprobante cfdi = new Comprobante();

        // TODO code application logic here
        /*  Crear xml */
         // Empleado empleado = (Empleado) this.recuperarParametroObject("empleadoN");

         
       
        cfdi.setSerie(comprobanteDePago.getSerie());
        cfdi.setFolio(comprobanteDePago.getFolio());
  //      folioFacade.folioInc(folio);
        Comprobante.Emisor 
        emisor = new Comprobante.Emisor();
        emisor.setNombre(empresa.getContribuyente().getNombre());
        emisor.setRfc(empresa.getContribuyente().getRfc());
        emisor.setRegimenFiscal(empresa.getRegimenFiscal());
        //601 Morales
        //603 
        Comprobante.Receptor
        receptor = new Comprobante.Receptor();
        //  receptor.setNombre("Pruebas y Mas S de RL MI de CV");
        receptor.setNombre(contribuyente.getNombre());
        receptor.setRfc(contribuyente.getRfc());

        // uso del cfdi        
        receptor.setUsoCFDI(CUsoCFDI.P_01); // para PAGOS

        /*ingresar emisor y receptor */
        cfdi.setEmisor(emisor);
        cfdi.setReceptor(receptor);

        //   cfdi.setMoneda(CMoneda.);        
        //cfdi.setMetodoPago(CMetodoPago.PUE); //NO DEBE EXISTIR
        
        /*   cfdi.setF*/
        cfdi.setLugarExpedicion(empresa.getCp());//falta codigo postal
        cfdi.setVersion("3.3");

        
          if (!uuidRelacionado.isEmpty()){
                 
                     Comprobante.CfdiRelacionados cfdiR = new Comprobante.CfdiRelacionados();
                     CfdiRelacionado cfdiRelacionado1 = new CfdiRelacionado();
                     cfdiRelacionado1.setUUID(uuidRelacionado);
                        cfdiR.setTipoRelacion("04");
                        cfdiR.getCfdiRelacionado().add(cfdiRelacionado1);
                        cfdi.setCfdiRelacionados(cfdiR);
                
        /*
        CfdiRelacionado relacion =  new CfdiRelacionado();
        relacion.setUUID("5FB2822E-396D-4725-8521-CDC4BDD20CCF");
        cfdi.getCfdiRelacionados().setTipoRelacion("04");
        cfdi.getCfdiRelacionados().getCfdiRelacionado().add(relacion);
        SIMPRE QUE SEA SUSTITUCION
         */
        }
        
      
               
        //FECHA DEL CALENDARIO SACADA DEL SISTEMA 
        cfdi.setFecha(newXMLGregorianCalendar);

        //OBTIENE EL CERTIFICADO DE LA EMPRESA
        CertificadoUsuario certificadoUsuario = new CertificadoUsuario(empresa.getContribuyente().getRfc()/*"TME960709LR2"*/);

        cfdi.setCertificado(certificadoUsuario.getBase64Certificado());
        //cfdi.setNoCertificado("20001000000300022763");
        cfdi.setNoCertificado(certificadoUsuario.getCertNumber());
        //cfdi.setTipoCambio(new BigDecimal(1.0)); //NO EXISTE TIPO DE CAMBIO EN PAGO
        cfdi.setMoneda(CMoneda.XXX); //COMO ES PAGO CAMBIAR A XXX

        Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();
      

            // cfdi.setTipoCambio(BigDecimal.ONE); es pago no existe

            //cfdi.setFormaPago("01"); //debe poner la forma de pago en este caso pongo efectivo
            cfdi.setTipoDeComprobante(CTipoDeComprobante.P);
            
            
           
           

           /************** flata lo de los pagos ****/

                Comprobante.Complemento complemento = new Comprobante.Complemento();
            complemento.getAny().add(pagos);
            cfdi.getComplemento().add(complemento);

            //        <cfdi:Concepto cantidad="1" unidad="ACT" descripcion="Pago de nómina" valorUnitario="7500.05" importe="7500.05" />
            /*agregar concepto */
            concepto.setCantidad(new BigDecimal(1));
            /* concepto.setClaveProdServ("01010101");
        concepto.setNoIdentificacion("001-002");
        concepto.setClaveUnidad("KGM");*/
            concepto.setClaveUnidad("ACT");
            concepto.setClaveProdServ("84111506");
            // concepto.setUnidad("ACT");
            concepto.setDescripcion("Pago");

            concepto.setValorUnitario(new BigDecimal(0));
            concepto.setImporte(new BigDecimal(0));
            //concepto.setDescuento(new BigDecimal(0)); //es pago no debe de existir

            
            cfdi.setSubTotal(new BigDecimal(0));
            //cfdi.setDescuento(new BigDecimal(0)); nod ebe existir
            cfdi.setTotal(new BigDecimal(0));

            // cfdi.setFormaPago("99"); no debe existir
            
        Comprobante.Conceptos conceptos = new Comprobante.Conceptos();
        conceptos.getConcepto().add(concepto);
        cfdi.setConceptos(conceptos);
        
        try {
             this.crearCFDI.crear(cfdi, comprobanteDePago);
             this.crearCFDI.generaPDF();
                    facturaRuta = "/Codificador-war/faces/descargas?serie=" + cfdi.getSerie() + "&folio=" + cfdi.getFolio() + "&rfc=" + cfdi.getEmisor().getRfc() + "&tipo=PDF";

        } catch (EJBException | FileNotFoundException | DatatypeConfigurationException | TransformerException | NoSuchAlgorithmException | NamingException | MessagingException ex) {
           
            this.setMessage(ex.getMessage() );
            Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        
        
        for(Pago pg:this.aPago){
           pg.setPago(comprobanteDePago);
           this.pagoFacade.create(pg);
            ComprobanteL comprobantePagado = pg.getComprobantePagado();
            Double saldo = comprobantePagado.getSaldo();            
             saldo -= pg.getMonto();
             comprobantePagado.setSaldo(saldo);
             this.comprobanteLFacade.edit(comprobantePagado); //Descontar pagos del comprobante de pago ***verificar si lo hace bien *****
                
        }
        
     }    

    public String getFacturaRuta() {
        return facturaRuta;
    }

    public void setFacturaRuta(String facturaRuta) {
        this.facturaRuta = facturaRuta;
    }
        
        
        
    

    public List<Pago> getAPago() {
        return aPago;
    }

    public void setAPago(List<Pago> aPago) {
        this.aPago = aPago;
    }
    
    
    public String limpiarPago(){
        aPago = new ArrayList<Pago>();
        return "pago";
    }
    
    
     
      private Object recuperarParametroObject(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object retorno = session.getAttribute(parametro);
        return retorno;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }
    
     public void setMessage(String messagetxt) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messagetxt, null);

        context.addMessage(null, message);
        context.addMessage(null, new FacesMessage("Reportar problema a ovante.com.mx"));
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUuidRelacionado() {
        return uuidRelacionado;
    }

    public void setUuidRelacionado(String uuidRelacionado) {
        this.uuidRelacionado = uuidRelacionado;
    }
     
     
      
}
