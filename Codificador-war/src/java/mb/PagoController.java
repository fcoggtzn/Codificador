/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empresa;
import nomina.entidad.Pago;
import nomina.servicio.ComprobanteLFacadeLocal;
import nomina.servicio.PagoFacadeLocal;

/**
 *
 * @author ovante
 */
@Named(value = "pagoController")
@SessionScoped
public class PagoController implements Serializable {

    @EJB
    private PagoFacadeLocal pagoFacade;

    @EJB
    private ComprobanteLFacadeLocal comprobanteLFacade;
    
    private Contribuyente contribuyente;
    
    private List<Pago> aPago;
    private Empresa empresa;
    
    
    @PostConstruct
    public void init(){
         aPago = new ArrayList<Pago>(); 
         contribuyente = null;
         empresa = (Empresa) this.recuperarParametroObject("empresaActual");

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
        
        /*genera comprobantes de pago sin tener que generar timbre */ 
        
        ComprobanteL comprobanteDePago = new ComprobanteL();
        comprobanteDePago.setPago("Metodo Pago");
        comprobanteDePago.setFecha(Calendar.getInstance().getTime());
        comprobanteDePago.setContribuyente(empresa.getContribuyente());
        comprobanteDePago.setContribuyente1(contribuyente);
        comprobanteDePago.setTotal(getTotal());
        comprobanteDePago.setSaldo(0.0);
        comprobanteDePago.setIdComprobante(0);        
        comprobanteDePago.setTipo("P");
        comprobanteDePago.setUuid(Calendar.getInstance().getTime().toString());
        this.comprobanteLFacade.crearPago(comprobanteDePago);
        
        for(Pago pg:this.aPago){
           pg.setPago(comprobanteDePago);
           this.pagoFacade.create(pg);
            ComprobanteL comprobantePagado = pg.getComprobantePagado();
            Double saldo = comprobantePagado.getSaldo();
             saldo -= pg.getMonto();
             comprobantePagado.setSaldo(saldo);
             this.comprobanteLFacade.edit(comprobantePagado);
        }
        
        
        
        
        
        
        
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
    
    
}
