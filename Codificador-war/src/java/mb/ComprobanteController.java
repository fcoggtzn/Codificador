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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empresa;
import nomina.servicio.ComprobanteLFacadeLocal;
import nomina.servicio.ContribuyenteFacadeLocal;

/**
 *
 * @author ovante
 */
@Named(value = "comprobanteController")
@SessionScoped
public class ComprobanteController implements Serializable {

    @EJB
    private ComprobanteLFacadeLocal comprobanteLFacade;

    @EJB
    private ContribuyenteFacadeLocal contribuyenteFacade;

    private final Empresa empresa;
    private Contribuyente contribuyente;
    private List<ComprobanteL> comprobantes;
    private List<ComprobanteL> comprobantesSinPago;

    private Date fechaInicio;
    private Date fechaFin;
    
    private double totalAplicadas=0.0;
    private double totalCanceladas=0.0;
    private double totalSaldadas=0.0;
    
    private String tipo;

    /**
     * Creates a new instance of ComprobanteController
     */
    public ComprobanteController() {
        empresa = (Empresa) this.recuperarParametroObject("empresaActual");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        fechaInicio = calendar.getTime();
         calendar.add(Calendar.DATE, 8);
        calendar.set(Calendar.MILLISECOND, -1);
        fechaFin = calendar.getTime();
                System.out.println(fechaFin);
                tipo="-";

        
       
      //  comprobantes = comprobanteLFacade.findComprobanteEmpresaContribuyente(empresa, fechaInicio, fechaFin, contribuyente);

    }
    
   

    private Object recuperarParametroObject(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object retorno = session.getAttribute(parametro);
        return retorno;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }
    
    
    public void filtraComprobantes(ComprobanteL comprobante) {
            List<ComprobanteL> comprobantesSinPagoN = new ArrayList<ComprobanteL>();

         for (ComprobanteL x:comprobantesSinPago){
             if (Objects.equals(x.getContribuyente1().getIdContribuyente(), comprobante.getContribuyente1().getIdContribuyente())){                 
                comprobantesSinPagoN.add(x);
             }
             if (Objects.equals(x.getIdComprobante(), comprobante.getIdComprobante())){
                 comprobantesSinPagoN.remove(x);
             }
         }
         comprobantesSinPago = comprobantesSinPagoN;
    }
    
    public void agregarComprobante(ComprobanteL comprobante){
          comprobantesSinPago.add(comprobante);
        
    }
    

    public List<ComprobanteL> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(List<ComprobanteL> comprobantes) {
        this.comprobantes = comprobantes;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.fechaInicio = FechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date FechaFin) {
        this.fechaFin = FechaFin; 
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(FechaFin);
         calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.MILLISECOND, -1);
        fechaFin = calendar.getTime();
        System.out.println(fechaFin);
    }

    public List<Contribuyente> completeTextEmpleado(String query) {

        List<Contribuyente> valor = this.contribuyenteFacade.findcontribuyentesByRFC(query);
        System.out.println(valor.size());
        return valor;
    }

    public void buscarComprobantes(ActionEvent actionEvent) {
        if (tipo.equals("-")){
        comprobantes = comprobanteLFacade.findComprobanteEmpresaContribuyente(empresa, fechaInicio, fechaFin, contribuyente);}
        else
        {
            comprobantes = comprobanteLFacade.findComprobanteEmpresaContribuyente(empresa, fechaInicio, fechaFin, contribuyente,tipo);
        }

    }
    
    public void buscarComprobantesSinPago(ActionEvent actionEvent) {
        //buscarComprobantes(actionEvent);
       
        comprobantesSinPago  = comprobanteLFacade.findComprobanteEmpresaContribuyenteConSaldo(empresa, fechaInicio, fechaFin, contribuyente);
        

    }
    
    
    
    public void buscarComprobantesPago(ActionEvent actionEvent) {
        //buscarComprobantes(actionEvent);
       
        comprobantes  = comprobanteLFacade.findComprobanteEmpresaContribuyenteSinSaldo(empresa, fechaInicio, fechaFin, contribuyente);
        

    }

    public List<ComprobanteL> getComprobantesSinPago() {
        return comprobantesSinPago;
    }

    public void setComprobantesSinPago(List<ComprobanteL> comprobantesSinPago) {
        this.comprobantesSinPago = comprobantesSinPago;
    }
    
    
/* pendiente de meter el estado de pagada */
    public String valorEstatus(int estatus) {
        String retorno = null;
        if (estatus == 1) {
            retorno = "A";
        }
        if (estatus == -1) {
            retorno = "C";
        }
        if (estatus == -2) {
            retorno = "X";
        }
          if (estatus == -6) {
            retorno = "PC";
        }
        return retorno;

    }
    
    
    public String getComprobantesTotal(){
          totalAplicadas=0.0;
          totalCanceladas=0.0;
          totalSaldadas=0.0;
        if (comprobantes != null)
        for(ComprobanteL comp:comprobantes){
            if (comp.getEstatus() == null || comp.getEstatus() == 1) {
                totalAplicadas += comp.getTotal();
                if (comp.getSaldo() != null && comp.getSaldo() == 0) {
                    totalSaldadas += comp.getTotal();
                }
            }
            if (comp.getEstatus() == null || comp.getEstatus() == -1) {
                totalCanceladas += comp.getTotal();
            }
        }
        return "Totales";
    }

    public double getTotalAplicadas() {
        return totalAplicadas;
    }

    public void setTotalAplicadas(double totalAplicadas) {
        this.totalAplicadas = totalAplicadas;
    }

    public double getTotalCanceladas() {
        return totalCanceladas;
    }

    public void setTotalCanceladas(double totalCanceladas) {
        this.totalCanceladas = totalCanceladas;
    }

    public double getTotalSaldadas() {
        return totalSaldadas;
    }

    public void setTotalSaldadas(double totalSaldadas) {
        this.totalSaldadas = totalSaldadas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
      
    

}
