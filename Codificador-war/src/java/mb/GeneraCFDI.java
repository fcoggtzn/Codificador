/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.CrearCFDILocal;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.time.temporal.ChronoUnit;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import nomina.entidad.DeduccionPercepcion;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;
import nomina.entidad.Folio;
import nomina.entidad.ComprobanteL;
import nomina.servicio.ComprobanteLFacadeLocal;
import nomina.servicio.FolioFacadeLocal;
import org.primefaces.context.RequestContext;

import sat.CEstado;
import sat.CMetodoPago;
import sat.CMoneda;
import sat.CTipoDeComprobante;
import sat.CTipoFactor;
import sat.CTipoNomina;
import sat.CUsoCFDI;
import sat.Comprobante;
import sat.Nomina;
import utilerias.CertificadoUsuario;

/**
 *
 * @author ovante
 */
@Named(value = "generaCFDI")
@SessionScoped
public class GeneraCFDI implements Serializable {

    @EJB
    private CrearCFDILocal crearCFDI;
    @EJB
    private FolioFacadeLocal folioFacade;
    @EJB
    private ComprobanteLFacadeLocal comprobanteFacade;

    private Comprobante cfdi;
    private final boolean activoNomina = true;
    private Date fechaIPago;
    private Date fechaFPago;
    private Date fechaPago;


    private Double diasPagados = 30.0;
    private ComprobanteL comprobanteX;
    private   String facturaRuta ;

    Empleado empleado;
    Empresa empresa;
    Folio folio;
    Comprobante.Emisor emisor;
    Comprobante.Receptor receptor;
    Nomina.Percepciones percepciones;
    Nomina.Deducciones deducciones;

    public Date getFechaIPago() {
        return fechaIPago;
    }

    public void setFechaIPago(Date fechaIPago) {
        this.fechaIPago = fechaIPago;
    }

    public Date getFechaFPago() {
        return fechaFPago;
    }

    public void setFechaFPago(Date fechaFPago) {
        this.fechaFPago = fechaFPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    

    public Double getDiasPagados() {
        return diasPagados;
    }

    public void setDiasPagados(Double diasPagados) {
        this.diasPagados = diasPagados;
    }

    /**
     * Creates a new instance of GeneraCFDI
     */
    public GeneraCFDI() {
        empleado = (Empleado) this.recuperarParametroObject("empleadoN");
        empresa = (Empresa) this.recuperarParametroObject("empresaActual");
        Calendar calendar = Calendar.getInstance();
        fechaFPago = calendar.getTime();
        fechaPago  = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        fechaIPago = calendar.getTime();
    }

    protected Object recuperarParametroObject(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object retorno = session.getAttribute(parametro);
        return retorno;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addMessageError(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void generarNomina(ActionEvent event) throws NamingException, MessagingException, TransformerConfigurationException, RemoteException {
      
        try {
            if (this.fechaIPago != null) {
                if ((this.diasPagados > 0) || (this.diasPagados != null)) {
                    try {
                        llenarCFDI();
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(PruebaFirma.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // String firmar = firma.firmar("VivaMexico","TME960709LR2");
                    addMessage("Generando Nomina");
                    guardarComprobante("N", 1);
                    this.crearCFDI.crear(cfdi, comprobanteX);
                    this.crearCFDI.generaPDF();
                    //RequestContext.getCurrentInstance().execute("window.open('" + "/Codificador-war/faces/descargas?serie=" + cfdi.getSerie() + "&folio=" + cfdi.getFolio() + "&rfc=" + cfdi.getEmisor().getRfc() + "&tipo=PDF" + "','_blank')");
                    facturaRuta = "/Codificador-war/faces/descargas?serie=" + cfdi.getSerie() + "&folio=" + cfdi.getFolio() + "&rfc=" + cfdi.getEmisor().getRfc() + "&tipo=PDF";

                    try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/Codificador-war/faces/factura/iNominaView.xhtml");
                    } catch (IOException ex) {
                        addMessageError(ex.getMessage());

                        Logger.getLogger(GeneraCFDI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    addMessageError("Días a pagar debe ser mayor a cero");
                }
            } else {
                addMessageError("El valor de la fecha no debe ser nulo");
            }

        } catch (Exception ex) {
            this.comprobanteX.setEstatus(-2);
            this.comprobanteX.setNotas(ex.getMessage());
            this.comprobanteFacade.edit(comprobanteX);
            addMessageError(ex.getMessage());
            Logger.getLogger(GeneraCFDI.class.getName()).log(Level.SEVERE, null, ex);

        }

       
    }

    public void llenarCFDI() throws DatatypeConfigurationException {
        // TODO code application logic here
        /*  Crear xml */
        empleado = (Empleado) this.recuperarParametroObject("empleadoN");

        cfdi = new Comprobante();
        folio = folioFacade.getFolioEmpresa(empresa);
        cfdi.setSerie(folio.getSerie());
        cfdi.setFolio(folio.getFolio().toString());
  //      folioFacade.folioInc(folio);
        emisor = new Comprobante.Emisor();
        emisor.setNombre(empresa.getContribuyente().getNombre());
        emisor.setRfc(empresa.getContribuyente().getRfc());
        emisor.setRegimenFiscal(empresa.getRegimenFiscal());
        //601 Morales
        //603 
        receptor = new Comprobante.Receptor();
        //  receptor.setNombre("Pruebas y Mas S de RL MI de CV");
        receptor.setNombre(empleado.getContribuyente().getNombre());
        receptor.setRfc(empleado.getContribuyente().getRfc());

        // uso del cfdi
        //receptor.setUsoCFDI(CUsoCFDI.G_01); //aquieren mercancias
        receptor.setUsoCFDI(CUsoCFDI.P_01); // para nomina

        /*ingresar emisor y receptor */
        cfdi.setEmisor(emisor);
        cfdi.setReceptor(receptor);

        //   cfdi.setMoneda(CMoneda.);        
        cfdi.setMetodoPago(CMetodoPago.PUE);
        /*   cfdi.setF*/
        cfdi.setLugarExpedicion(empresa.getCp());//falta codigo postal
        cfdi.setVersion("3.3");

        /*
        CfdiRelacionado relacion =  new CfdiRelacionado();
        relacion.setUUID("5FB2822E-396D-4725-8521-CDC4BDD20CCF");
        cfdi.getCfdiRelacionados().setTipoRelacion("04");
        cfdi.getCfdiRelacionados().getCfdiRelacionado().add(relacion);
         */
        GregorianCalendar c = new GregorianCalendar();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -2);
        Date time = calendar.getTime();
        c.setTime(time);
        XMLGregorianCalendar newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        newXMLGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        newXMLGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);

        c.setTime(fechaPago);
        XMLGregorianCalendar fechaPapgo = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        fechaPapgo.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        fechaPapgo.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        fechaPapgo.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);

        calendar.add(Calendar.MONTH, -1);
        c.setTime(this.fechaIPago);
        XMLGregorianCalendar fechaIPapgo = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        fechaIPapgo.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        fechaIPapgo.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        fechaIPapgo.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
        
        
        calendar.add(Calendar.MONTH, -1);
        c.setTime(this.fechaFPago);
        XMLGregorianCalendar fechaFPapgo = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        fechaFPapgo.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        fechaFPapgo.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        fechaFPapgo.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);


        calendar.add(Calendar.YEAR, -1);
        c.setTime(empleado.getFechaInicio());
        XMLGregorianCalendar fechaInicio = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        fechaInicio.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        fechaInicio.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        fechaInicio.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);

        //Interval interval = new Interval(empleado.getFechaInicio(), this.fechaIPago);
        Calendar calendarInicio = Calendar.getInstance();
        calendarInicio.setTime(empleado.getFechaInicio());
        Calendar calendarPago = Calendar.getInstance();
        calendarPago.setTime(this.fechaIPago);

        long daysBetween = ChronoUnit.DAYS.between(calendarInicio.toInstant(), calendarPago.toInstant());

        cfdi.setFecha(newXMLGregorianCalendar);

        CertificadoUsuario certificadoUsuario = new CertificadoUsuario(empresa.getContribuyente().getRfc()/*"TME960709LR2"*/);

        cfdi.setCertificado(certificadoUsuario.getBase64Certificado());
        //cfdi.setNoCertificado("20001000000300022763");
        cfdi.setNoCertificado(certificadoUsuario.getCertNumber());
        cfdi.setTipoCambio(new BigDecimal(1.0));
        cfdi.setMoneda(CMoneda.MXN);

        Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();
        if (this.activoNomina) {
            cfdi.setFormaPago("PAGO EN UNA SOLA EXHIBICION");

            cfdi.setTipoDeComprobante(CTipoDeComprobante.N);

            /*Complemento de nomina */
            Nomina nomina = new Nomina();
            nomina.setVersion("1.2");
            nomina.setTipoNomina(CTipoNomina.O);
            nomina.setFechaPago(fechaPapgo);

            nomina.setFechaInicialPago(fechaIPapgo);
            nomina.setFechaFinalPago(fechaFPapgo);
            nomina.setNumDiasPagados(new BigDecimal(this.diasPagados).setScale(3, RoundingMode.HALF_UP));
            Nomina.Emisor emisorNomina = new Nomina.Emisor();
            //    emisorNomina.setRfcPatronOrigen(emisor.getRfc());
            emisorNomina.setRegistroPatronal(empresa.getRegistroPatronal()/*"5525665412"*/);

            nomina.setEmisor(emisorNomina);

            Nomina.Receptor empleado = new Nomina.Receptor();
            empleado.setCurp(this.empleado.getCurp()/*"GUNF750511HASTJR05"*/);
            empleado.setNumSeguridadSocial(this.empleado.getNumseguroSocial()/*"04078873454"*/);
            empleado.setTipoContrato(this.empleado.getTipoContrato()/*"01"*/);
            empleado.setFechaInicioRelLaboral(fechaInicio);

            empleado.setAntigüedad("P" + (daysBetween / 7) + "W");
            empleado.setClaveEntFed(CEstado.AGU);
            empleado.setTipoRegimen(this.empleado.getTipoRegimen()/*"02"*/); //02-Sueldos,03 Jubilados, 04 Pensionados, 09 Asimilados Honorarios
            empleado.setNumEmpleado(this.empleado.getNumEmpleado().toString()/*"001"*/);
            empleado.setPeriodicidadPago(this.empleado.getPeriodicidadPago()/*"06"*/); //ver hoja 34  
            empleado.setRiesgoPuesto(this.empleado.getRiesgoPuesto()/*"2"*/);
            empleado.setSalarioDiarioIntegrado(new BigDecimal(this.empleado.getSalarioDiarioIntegrado()/*435.50*/).setScale(2, RoundingMode.HALF_UP));
            nomina.setReceptor(empleado);
            percepciones = new Nomina.Percepciones();
            deducciones = new Nomina.Deducciones();
            Double pTotalExento = 0.0;
            Double pTotalGravado = 0.0;
            Double pTotalT = 0.0;
            Double dTotalExento = 0.0;
            Double dTotalGravado = 0.0;
            Double dTotalT = 0.0;
            for (DeduccionPercepcion perDed : this.empleado.getDeduccionPercepcionCollection()) {
                if (perDed.getPercepcion() != null) {
                    Nomina.Percepciones.Percepcion valPer = new Nomina.Percepciones.Percepcion();
                    valPer.setTipoPercepcion(perDed.getPercepcion().getTipoPercepcion());
                    valPer.setClave(perDed.getPercepcion().getTipoClave());
                    valPer.setConcepto(perDed.getPercepcion().getConcepto());
                    valPer.setImporteExento(new BigDecimal(perDed.getExento()).setScale(2, RoundingMode.HALF_UP));
                    valPer.setImporteGravado(new BigDecimal(perDed.getGravado()).setScale(2, RoundingMode.HALF_UP));
                    percepciones.getPercepcion().add(valPer);
                    pTotalExento = pTotalExento + perDed.getExento();
                    pTotalGravado = pTotalGravado + perDed.getGravado();
                    pTotalT = pTotalExento + pTotalGravado;
                } else {
                    Nomina.Deducciones.Deduccion valDed = new Nomina.Deducciones.Deduccion();
                    valDed.setTipoDeduccion(perDed.getDeduccion().getTipoDeduccion());
                    valDed.setClave(perDed.getDeduccion().getTipoClave());
                    valDed.setConcepto(perDed.getDeduccion().getConcepto());
                    valDed.setImporte(new BigDecimal(perDed.getExento() + perDed.getGravado()).setScale(2, RoundingMode.HALF_UP));
                    deducciones.getDeduccion().add(valDed);
                    dTotalExento = dTotalExento + perDed.getExento();
                    dTotalGravado = dTotalGravado + perDed.getGravado();
                    dTotalT = dTotalExento + dTotalGravado;
                }

            }
            percepciones.setTotalExento(new BigDecimal(pTotalExento).setScale(2, RoundingMode.HALF_UP)); //suma de percepciones 
            percepciones.setTotalGravado(new BigDecimal(pTotalGravado).setScale(2, RoundingMode.HALF_UP)); //suma de percepciones 
            percepciones.setTotalSueldos(new BigDecimal(pTotalT).setScale(2, RoundingMode.HALF_UP)); //suma de percepciones 
            deducciones.setTotalImpuestosRetenidos(new BigDecimal(dTotalGravado).setScale(2, RoundingMode.HALF_UP));
            deducciones.setTotalOtrasDeducciones(new BigDecimal(dTotalExento).setScale(2, RoundingMode.HALF_UP));

            nomina.setPercepciones(percepciones);
            nomina.setDeducciones(deducciones);

            //nomina con calculos TotalDeducciones="1234.09" TotalOtrosPagos="0.0" TotalPercepciones="7500.05"
            nomina.setTotalOtrosPagos(new BigDecimal(pTotalExento).setScale(2, RoundingMode.HALF_UP));
            nomina.setTotalPercepciones(new BigDecimal(pTotalT).setScale(2, RoundingMode.HALF_UP));
            nomina.setTotalDeducciones(new BigDecimal(dTotalT).setScale(2, RoundingMode.HALF_UP));

            Comprobante.Complemento complemento = new Comprobante.Complemento();
            complemento.getAny().add(nomina);
            cfdi.getComplemento().add(complemento);

            //        <cfdi:Concepto cantidad="1" unidad="ACT" descripcion="Pago de nómina" valorUnitario="7500.05" importe="7500.05" />
            /*agregar concepto */
            concepto.setCantidad(new BigDecimal(1));
            /* concepto.setClaveProdServ("01010101");
        concepto.setNoIdentificacion("001-002");
        concepto.setClaveUnidad("KGM");*/
            concepto.setClaveUnidad("ACT");
            concepto.setClaveProdServ("84111505");
            // concepto.setUnidad("ACT");
            concepto.setDescripcion("Pago de nómina");

            concepto.setValorUnitario(new BigDecimal(pTotalT).setScale(2, RoundingMode.HALF_UP));
            concepto.setImporte(new BigDecimal(pTotalT).setScale(2, RoundingMode.HALF_UP));
            concepto.setDescuento(new BigDecimal(dTotalT).setScale(2, RoundingMode.HALF_UP));

            //subTotal="7500.05" descuento="1234.09" Moneda="MXN" TipoCambio="1" total="6265.96" 
            cfdi.setSubTotal(new BigDecimal(pTotalT).setScale(2, RoundingMode.HALF_UP));
            cfdi.setDescuento(new BigDecimal(dTotalT).setScale(2, RoundingMode.HALF_UP));
            cfdi.setTotal(new BigDecimal(pTotalT - dTotalT).setScale(2, RoundingMode.HALF_UP));

            cfdi.setFormaPago("99");
        } else {

            cfdi.setTipoCambio(BigDecimal.ONE);

            cfdi.setFormaPago("01");
            cfdi.setTipoDeComprobante(CTipoDeComprobante.I);

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

        }
        Comprobante.Conceptos conceptos = new Comprobante.Conceptos();
        conceptos.getConcepto().add(concepto);
        cfdi.setConceptos(conceptos);
        /*Comprobante.Impuestos imps=new Comprobante.Impuestos();
        imps.setTotalImpuestosTrasladados(BigDecimal.ZERO);
        cfdi.setImpuestos(imps);*/

    }

    public void guardarComprobante(String tipo, int estatus) {
        comprobanteX = new ComprobanteL();
        comprobanteX.setIdComprobante(0);
        comprobanteX.setFolio(folio.getFolio().toString());
        comprobanteX.setSerie(folio.getSerie());
        comprobanteX.setContribuyente(empresa.getContribuyente());
        comprobanteX.setContribuyente1(empleado.getContribuyente());
        comprobanteX.setTipo(tipo);
        comprobanteX.setTotal(cfdi.getTotal().doubleValue());
        comprobanteX.setFecha(cfdi.getFecha().toGregorianCalendar().getTime());
        comprobanteX.setEstatus(estatus);
        //comprobanteX.setImpuesto(cfdi.getImpuestos().getTotalImpuestosTrasladados().doubleValue());
        comprobanteX.setImpuesto(0.0);
        comprobanteX.setImpuestoRetenido(deducciones.getTotalImpuestosRetenidos().doubleValue() + deducciones.getTotalOtrasDeducciones().byteValue());
        
        
        
        comprobanteX.setSubtotal(cfdi.getSubTotal().doubleValue());
        comprobanteX.setUuid(cfdi.getNoCertificado());
        comprobanteX.setPago("Nomina");
        comprobanteFacade.create(comprobanteX);
    }

    public String getFacturaRuta() {
        return facturaRuta;
    }

    public void setFacturaRuta(String facturaRuta) {
        this.facturaRuta = facturaRuta;
    }
    
    
 
}
