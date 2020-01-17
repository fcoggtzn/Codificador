/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import nomina.entidad.Contribuyente;
import nomina.servicio.ContribuyenteFacadeLocal;
import servicio.entidad.Operador;
import servicio.entidad.Servicio;
import servicio.servicio.ServicioFacadeLocal;

/**
 *
 * @author ovante
 */
@Named(value = "servicioMB")
@SessionScoped
public class ServicioMB implements Serializable {

    @EJB
    private ContribuyenteFacadeLocal contribuyenteFacade;

    @EJB
    private ServicioFacadeLocal servicioFacade;
    private Operador operador;
    private Servicio servicio;
    private Contribuyente cliente;
    private Date fechaInicio;
    private Date fechaFin;

    /**
     * Creates a new instance of ServicioMB
     */
    public ServicioMB() {
        
    }
    
      @PostConstruct
    public void limpiar(){
        this.cliente = null;
        this.operador = null;
        this.servicio = new Servicio(); 
        servicio.setIdservicio(0);
        Date fecha = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico")).getTime();
        servicio.setFecha(fecha);
        fechaInicio = fecha;
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico"));
        instance.add(Calendar.DATE, 1);        
        fechaFin = instance.getTime();;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
        this.operador = servicio.getIdOperador();
        this.cliente =  this.contribuyenteFacade.getContribuyente(servicio.getClienteRfc());
        System.out.println("Entro en seleccion");
    }

    public Contribuyente getCliente() {
        return cliente;
    }

    public void setCliente(Contribuyente cliente) {
        this.cliente = cliente;
    }
    
    
    public void grabar(){
      
        this.servicio.setClienteRfc(this.cliente.getRfc());
        this.servicio.setIdContribuyente(this.cliente.getIdContribuyente());
        this.servicio.setIdOperador(operador);
        if (this.servicio.getIdservicio() == 0){
        this.servicioFacade.create(servicio);}
        else
        {
            this.servicioFacade.edit(servicio);
        }
        this.limpiar();        
        
       
        
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
    public List<Servicio> getServicios(){
       
        return this.servicioFacade.getRangoFecha(fechaInicio, fechaFin);
        
    }
    
  
    
    
    
    
}
