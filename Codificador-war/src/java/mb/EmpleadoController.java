/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import catalogo.entidad.Estado;
import catalogo.entidad.PeriodicidadPago;
import catalogo.entidad.RiesgoPuesto;
import catalogo.entidad.TipoContrato;
import catalogo.entidad.TipoRegimen;
import catalogo.servicio.EstadoFacadeLocal;
import catalogo.servicio.PeriodicidadPagoFacadeLocal;
import catalogo.servicio.RiesgoPuestoFacadeLocal;
import catalogo.servicio.TipoContratoFacadeLocal;
import catalogo.servicio.TipoRegimenFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empleado;
import nomina.servicio.ContribuyenteFacadeLocal;
import nomina.servicio.EmpleadoFacadeLocal;

/**
 *
 * @author ovante
 */
@Named(value = "empleadoController")
@SessionScoped
public class EmpleadoController implements Serializable {

    @EJB
    private ContribuyenteFacadeLocal contribuyenteFacade;

    @EJB
    TipoRegimenFacadeLocal tipoRegimenFacade;
    @EJB
    RiesgoPuestoFacadeLocal riesgoPuestoFacade;
    @EJB
    PeriodicidadPagoFacadeLocal periodicidadPagoFacade;
    @EJB
    EstadoFacadeLocal estadoFacade;
    @EJB
    EmpleadoFacadeLocal empleadoFacade;
    @EJB
    TipoContratoFacadeLocal tipoContratoFacade;

    private String textoBoton = "Guardar";
    private List<Empleado> empleados;
    private Empleado empleado;

    private List<TipoContrato> tiposContrato;
    private TipoContrato tipoContrato;
    private Estado estado;
    private PeriodicidadPago periodicidadPago;
    private RiesgoPuesto riesgoPuesto;
    private TipoRegimen tipoRegimen;

    /**
     * Creates a new instance of EmpleadoController
     */
    public EmpleadoController() {

    }

    @PostConstruct
    public void init() {
        this.empleado = new Empleado();
        this.empleado.setContribuyente(new Contribuyente());
        this.tipoContrato = new TipoContrato();
        this.tipoRegimen = new TipoRegimen();
        this.estado=new Estado();
        this.periodicidadPago= new PeriodicidadPago();
        this.riesgoPuesto=new RiesgoPuesto();
        //this.tiposContrato = tipoContratoFacade.findAll();
    }

    public String getTextoBoton() {
        return textoBoton;
    }

    public void setTextoBoton(String textoBoton) {
        this.textoBoton = textoBoton;
    }

    public List<Empleado> getEmpleados() {
        return empleadoFacade.findAll();
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void guardarEmpleado() {
        empleado.setTipoContrato(tipoContrato.getTipoContrato());
        empleado.setTipoRegimen(tipoRegimen.getTipoRegimen());
        empleado.setClaveEntFed(estado.getCodigo());
        empleado.setPeriodicidadPago(periodicidadPago.getPeriodicidadPago());
        empleado.setRiesgoPuesto(riesgoPuesto.getRiesgoPuesto());

        if (this.textoBoton.equals("Guardar")) {
            this.empleadoFacade.create(empleado);
        }
        if (this.textoBoton.equals("Modificar")) {
            this.contribuyenteFacade.edit(empleado.getContribuyente());
            this.empleadoFacade.edit(empleado);
        }
        limpiarEmpleado();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.textoBoton, "Guardado Exitosamente"));
    }
    
    public void eliminarEmpleado(){
        empleadoFacade.remove(empleado);
        limpiarEmpleado();
    }

    public void limpiarEmpleado() {
        empleado = new Empleado();
        empleado.setContribuyente(new Contribuyente());
        tipoContrato = tipoContratoFacade.getPrimerTipoContrato();
        estado = estadoFacade.getPrimerEstado();
        periodicidadPago = periodicidadPagoFacade.getPrimerPeriodicidad();
        riesgoPuesto = riesgoPuestoFacade.primerRiesgoPuesto();
        tipoRegimen = tipoRegimenFacade.getPrimerTipoRegimen();
        textoBoton = "Guardar";
    }

    public List<TipoContrato> getTiposContrato() {
        return tipoContratoFacade.findAll();
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        if (tipoContrato != null) {
            this.tipoContrato = tipoContrato;
        }
    }

    public List<Estado> getEstados() {
        return estadoFacade.findAll();
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        if (estado != null) {
            this.estado = estado;
        }
    }

    public List<PeriodicidadPago> getPeriodicidadesPago() {
        return periodicidadPagoFacade.findAll();
    }

    public PeriodicidadPago getPeriodicidadPago() {
        return periodicidadPago;
    }

    public void setPeriodicidadPago(PeriodicidadPago periodicidadPago) {
        if (periodicidadPago != null) {
            this.periodicidadPago = periodicidadPago;
        }
    }

    public List<RiesgoPuesto> getRiesgosPuesto() {
        return riesgoPuestoFacade.findAll();
    }

    public RiesgoPuesto getRiesgoPuesto() {
        return riesgoPuesto;
    }

    public void setRiesgoPuesto(RiesgoPuesto riesgoPuesto) {
        if (riesgoPuesto != null) {
            this.riesgoPuesto = riesgoPuesto;
        }
    }

    public TipoRegimen getTipoRegimen() {
        return tipoRegimen;
    }

    public void setTipoRegimen(TipoRegimen tipoRegimen) {
        if (tipoRegimen != null) {
            this.tipoRegimen = tipoRegimen;
        }
    }

    public List<TipoRegimen> getTiposRegimen() {
        return tipoRegimenFacade.findAll();
    }

    public void actualizar(Empleado empleado) {
        textoBoton = "Modificar";
        this.empleado = empleado;
        tipoContrato = tipoContratoFacade.getTipoContratoByTC(empleado.getTipoContrato());
        estado = estadoFacade.getEstadoByCve(empleado.getClaveEntFed());
        periodicidadPago = periodicidadPagoFacade.getPeriodicidadByPer(empleado.getPeriodicidadPago());
        riesgoPuesto = riesgoPuestoFacade.RiesgoPuestoByCve(empleado.getRiesgoPuesto());
        tipoRegimen = tipoRegimenFacade.getTipoRegimenByRF(empleado.getTipoRegimen());
    }

}
