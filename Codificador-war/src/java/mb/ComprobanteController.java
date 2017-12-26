/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private Date fechaInicio;
    private Date fechaFin;

    /**
     * Creates a new instance of ComprobanteController
     */
    public ComprobanteController() {
        empresa = (Empresa) this.recuperarParametroObject("empresaActual");
        Calendar calendar = Calendar.getInstance();                
        fechaInicio= calendar.getTime();
        calendar.add(Calendar.HOUR, -24);
        fechaFin = calendar.getTime();
        

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
    }
    
     public List<Contribuyente> completeTextEmpleado(String query) {        
        
        List<Contribuyente> valor = this.contribuyenteFacade.findcontribuyentesByRFC(query);
        System.out.println(valor.size());
        return valor;
    }
     
    public void buscarComprobantes (ActionEvent actionEvent){
      comprobantes = comprobanteLFacade.findComprobanteEmpresaContribuyente(empresa,fechaInicio,fechaFin,contribuyente);
     
    }
    
    
    public String valorEstatus(int estatus){
        String retorno= null;
        if (estatus == 1){
            retorno ="A";
        }
        if (estatus ==-1){
            retorno ="C";
        }
        
        
        return retorno;
        
    }


}
