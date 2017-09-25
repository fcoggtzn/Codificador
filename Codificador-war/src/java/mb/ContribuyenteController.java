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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import nomina.entidad.Configuracion;
import nomina.entidad.Contribuyente;
import nomina.entidad.Deduccion;
import nomina.entidad.DeduccionPercepcion;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;
import nomina.entidad.EmpresaContribuyente;
import nomina.entidad.Percepcion;
import nomina.servicio.ConfiguracionFacadeLocal;
import nomina.servicio.ContribuyenteFacadeLocal;
import nomina.servicio.DeduccionFacadeLocal;
import nomina.servicio.EmpresaContribuyenteFacadeLocal;
import nomina.servicio.EmpresaFacadeLocal;
import nomina.servicio.PercepcionFacadeLocal;
import nomina.servicio.DeduccionPercepcionFacadeLocal;
import nomina.servicio.EmpleadoFacadeLocal;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author ovante
 */
@Named(value = "contribuyenteController")
@SessionScoped
public class ContribuyenteController extends BaseController implements Serializable {

    @EJB
    ContribuyenteFacadeLocal contribuyenteFacade;
    @EJB
    EmpresaFacadeLocal empresaFacade;
    @EJB
    EmpresaContribuyenteFacadeLocal empresaContribuyenteFacade;
    @EJB
    ConfiguracionFacadeLocal configuracionFacade;
    @EJB
    PercepcionFacadeLocal percepcionFacade;
    @EJB
    DeduccionFacadeLocal deduccionFacade;
    @EJB
    DeduccionPercepcionFacadeLocal deduccionPercepcionFacade;
    @EJB
    EmpleadoFacadeLocal empleadoFacade;

    private Contribuyente contribuyente;
    private List<Contribuyente> contribuyentes;
    private String textoBoton = "Guardar";

    private Empresa empresa;
    private EmpresaContribuyente empresaContribuyente;

    private Configuracion configuracion;

    private Percepcion percepcion;
    private Deduccion deduccion;
    private Empleado empleado;
    private DeduccionPercepcion deduccionPercepcion;

    private EmpresaContribuyente empresaContribuyenteNomina;
    private Empleado empleadoNomina;
    private double cantidad = 0.0;

    private DeduccionPercepcion deduccionPercepcionTemp;
    List<DeduccionPercepcion> deduccionesPercepcionesP;
    List<DeduccionPercepcion> deduccionesPercepcionesD;
    List<DeduccionPercepcion> percepcionesEmpleadoNomina;
    List<DeduccionPercepcion> deduccionesEmpleadoNomina;

    /**
     * Creates a new instance of ContribuyenteController
     */
    public ContribuyenteController() {
    }

    @PostConstruct
    public void init() {
        contribuyente = new Contribuyente();
        empresa = new Empresa();
        empresa.setContribuyente(new Contribuyente());
        empresaContribuyente = new EmpresaContribuyente();
        empresaContribuyente.setContribuyente(new Contribuyente());
        empresaContribuyente.setEmpresa(new Empresa());
        configuracion = new Configuracion();
        configuracion.setEmpresa(new Empresa());
        percepcion = new Percepcion();
        deduccion = new Deduccion();
        empresaContribuyenteNomina = new EmpresaContribuyente();
        deduccionesPercepcionesP = new ArrayList<>();
        deduccionesPercepcionesD = new ArrayList<>();
        percepcionesEmpleadoNomina = new ArrayList<>();
        deduccionesEmpleadoNomina = new ArrayList<>();
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public List<Contribuyente> getContribuyentes() {
        contribuyentes = contribuyenteFacade.findAll();
        return contribuyentes;
    }

    public void setContribuyentes(List<Contribuyente> contribuyentes) {
        this.contribuyentes = contribuyentes;
    }

    public String getTextoBoton() {
        return textoBoton;
    }

    public void setTextoBoton(String textoBoton) {
        this.textoBoton = textoBoton;
    }

    public void actualizar(Contribuyente contribuyente) {
        textoBoton = "Modificar";
        this.contribuyente = contribuyente;
    }

    public void limpiarContribuyente() {
        contribuyente = new Contribuyente();
        textoBoton = "Guardar";
    }

    public void guardarContribuyente() {

        if (this.textoBoton.equals("Guardar")) {
            this.contribuyenteFacade.create(contribuyente);
        }
        if (this.textoBoton.equals("Modificar")) {
            contribuyenteFacade.edit(contribuyente);
        }
        limpiarContribuyente();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.textoBoton, "Guardado Exitosamente"));
    }

    public void eliminarContribuyente() {
        contribuyenteFacade.remove(contribuyente);
        limpiarContribuyente();
    }

    // Empresa
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Contribuyente> completeTextRFC(String query) {
        List<Contribuyente> conTemp = contribuyenteFacade.findcontribuyentesByRFC(query);
        return conTemp;
    }

    public List<Empresa> getEmpresas() {
        return empresaFacade.findAll();
    }

    public void guardarEmpresa() {
        if (this.textoBoton.equals("Guardar")) {
            empresaFacade.create(empresa);
        }
        if (this.textoBoton.equals("Modificar")) {
            empresaFacade.edit(empresa);
        }
        limpiarEmpresa();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.textoBoton, "Guardado Exitosamente"));
    }

    public void limpiarEmpresa() {
        empresa = new Empresa();
        empresa.setContribuyente(new Contribuyente());
        textoBoton = "Guardar";
    }

    public void actualizarEmpresa(Empresa empresa) {
        textoBoton = "Modificar";
        this.empresa = empresa;
    }

    public void eliminarEmpresa() {
        empresaFacade.remove(empresa);
        limpiarEmpresa();
    }

    //Empresa-Contribuyente
    public EmpresaContribuyente getEmpresaContribuyente() {
        return empresaContribuyente;
    }

    public void setEmpresaContribuyente(EmpresaContribuyente empresaContribuyente) {
        this.empresaContribuyente = empresaContribuyente;
    }

    public List<Empresa> completeTextRFCEC(String query) {
        List<Empresa> empTemp = empresaFacade.findEmpresaByRFC(query);
        return empTemp;
    }

    public List<EmpresaContribuyente> getEmpresasContribuyentes() {
        return empresaContribuyenteFacade.findAll();
    }

    public void guardarEmpresaContribuyente() {
        if (this.textoBoton.equals("Guardar")) {
            empresaContribuyenteFacade.create(empresaContribuyente);
        }
        if (this.textoBoton.equals("Modificar")) {
            empresaContribuyenteFacade.edit(empresaContribuyente);
        }
        limpiarEmpresaContribuyente();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.textoBoton, "Guardado Exitosamente"));
    }

    public void limpiarEmpresaContribuyente() {
        empresaContribuyente = new EmpresaContribuyente();
        empresaContribuyente.setEmpresa(new Empresa());
        empresaContribuyente.setContribuyente(new Contribuyente());
        textoBoton = "Guardar";
    }

    public void actualizarEmpresaContribuyente(EmpresaContribuyente empresaContribuyente) {
        textoBoton = "Modificar";
        this.empresaContribuyente = empresaContribuyente;
    }

    public void eliminarEmpresaContribuyente() {
        empresaContribuyenteFacade.remove(empresaContribuyente);
        limpiarEmpresaContribuyente();
    }

    //configuracion
    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public void setEmpresaForConfiguracion(Empresa empresa) {
        configuracion.setEmpresa(empresa);
    }

    public List<Configuracion> getConfiguraciones() {
        return configuracionFacade.findAll();
    }

    public void guardarConfiguracion() {
        if (this.textoBoton.equals("Guardar")) {
            configuracionFacade.create(configuracion);
        }
        if (this.textoBoton.equals("Modificar")) {
            configuracionFacade.edit(configuracion);
        }
        limpiarConfiguracion();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.textoBoton, "Guardado Exitosamente"));
    }

    public void limpiarConfiguracion() {
        configuracion = new Configuracion();
        configuracion.setEmpresa(new Empresa());
        textoBoton = "Guardar";
    }

    public void actualizarConfiguracion(Configuracion configuracion) {
        textoBoton = "Modificar";
        this.configuracion = configuracion;
    }

    public void eliminarConfiguracion() {
        configuracionFacade.remove(configuracion);
        limpiarConfiguracion();
    }

    //Persepcion
    public Percepcion getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(Percepcion percepcion) {
        this.percepcion = percepcion;
    }

    public List<Percepcion> getPercepciones() {
        return percepcionFacade.findAll();
    }

    public void guardarPercepcion() {
        if (this.textoBoton.equals("Guardar")) {
            percepcionFacade.create(percepcion);
        }
        if (this.textoBoton.equals("Modificar")) {
            percepcionFacade.edit(percepcion);
        }
        limpiarPercepcion();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.textoBoton, "Guardado Exitosamente"));
    }

    public void limpiarPercepcion() {
        percepcion = new Percepcion();
        textoBoton = "Guardar";
    }

    public void actualizarPercepcion(Percepcion percepcion) {
        textoBoton = "Modificar";
        this.percepcion = percepcion;
    }

    public void eliminarPercepcion() {
        percepcionFacade.remove(percepcion);
        limpiarPercepcion();
    }

    //Deduccion
    public Deduccion getDeduccion() {
        return deduccion;
    }

    public void setDeduccion(Deduccion deduccion) {
        this.deduccion = deduccion;
    }

    public List<Deduccion> getDeducciones() {
        return deduccionFacade.findAll();
    }

    public void guardarDeduccion() {

        if (this.textoBoton.equals("Guardar")) {
            deduccionFacade.create(deduccion);
        }
        if (this.textoBoton.equals("Modificar")) {
            deduccionFacade.edit(deduccion);
        }
        limpiarDeduccion();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.textoBoton, "Guardado Exitosamente"));
    }

    public void limpiarDeduccion() {
        deduccion = new Deduccion();
        textoBoton = "Guardar";
    }

    public void actualizarDeduccion(Deduccion deduccion) {
        textoBoton = "Modificar";
        this.deduccion = deduccion;
    }

    public void eliminarDeduccion() {
        deduccionFacade.remove(deduccion);
        limpiarDeduccion();
    }

    //DeduccionPercepcion
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
        percepcionesEmpleadoNomina = deduccionPercepcionFacade.findPercepcionesEmpleado(empleadoNomina);
        // deduccionesPercepcionesD =deduccionPercepcionFacade.findDeduccionEmpleado(empleado);
    }

    public void setEmpleadoPD(Empleado empleado) {
        this.empleado = empleado;
        deduccionesPercepcionesP = deduccionPercepcionFacade.findPercepcionesEmpleado(empleado);
        deduccionesPercepcionesD = deduccionPercepcionFacade.findDeduccionEmpleado(empleado);
    }

    public List<DeduccionPercepcion> getPercepcionesEmpleado() {
        return deduccionesPercepcionesP;
    }

    public List<DeduccionPercepcion> getDeduccionesEmpleado() {
        return deduccionesPercepcionesD;
    }

    public Percepcion getFirstPercepcion() {
        return new Percepcion();
    }

    public void setFirstPercepcion(Percepcion percepcion) {

    }

    public Deduccion getFirstDeduccion() {
        return new Deduccion();
    }

    public void setFirstDeduccion(Deduccion deduccion) {

    }

    public void guardarPercepcionEmpleado() {
        Integer temp = percepcion.getIdPercepcion();
        if (temp == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Seleccione una Percepción"));
        } else {
            DeduccionPercepcion dp = new DeduccionPercepcion();
            dp.setDeduccion(null);
            dp.setPercepcion(percepcion);
            dp.setEmpleado(empleado);
            dp.setCantidad(cantidad);

            DeduccionPercepcion pTemp = deduccionPercepcionFacade.getPercepcionEmpleado(dp);
            if (pTemp == null) {
                deduccionPercepcionFacade.create(dp);
                deduccionesPercepcionesP = deduccionPercepcionFacade.findPercepcionesEmpleado(empleado);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Atributo Registrado"));
            }
        }
    }

    public void guardarDeduccionEmpleado() {
        Integer temp = deduccion.getIdDeduccion();
        if (temp == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Seleccione una Percepción"));
        } else {
            DeduccionPercepcion dp = new DeduccionPercepcion();
            dp.setDeduccion(deduccion);
            dp.setPercepcion(null);
            dp.setEmpleado(empleado);
            dp.setCantidad(cantidad);
            if (deduccionPercepcionFacade.getDeduccionEmpleado(dp) == null) {
                deduccionPercepcionFacade.create(dp);
                deduccionesPercepcionesD = deduccionPercepcionFacade.findDeduccionEmpleado(empleado);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Atributo Registrado"));
            }
        }
    }

    public void actualizarDeduccionPercepcion(DeduccionPercepcion deduccionPercepcion) {
        textoBoton = "Modificar";
        this.deduccionPercepcion = deduccionPercepcion;
    }

    public void actualizarGuardarDeduccionPercepcion(DeduccionPercepcion deduccionPercepcion) {
        this.deduccionPercepcion = deduccionPercepcion;
        try {
            deduccionPercepcionFacade.edit(deduccionPercepcion);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void eliminarDeduccionPercepcion() {
        deduccionPercepcionFacade.remove(deduccionPercepcion);
        deduccionesPercepcionesP = deduccionPercepcionFacade.findPercepcionesEmpleado(empleado);
        deduccionesPercepcionesD = deduccionPercepcionFacade.findDeduccionEmpleado(empleado);
        limpiarConfiguracion();
    }

    public void limpiarDeduccionPercepcion() {
        deduccionPercepcion = new DeduccionPercepcion();
        textoBoton = "Guardar";
    }

    //nomina
    public List<Empleado> completeTextEmpleado(String query) {
        Empresa empresaLocal = (Empresa) this.recuperarParametroObject("empresaActual");
        //List<EmpresaContribuyente> valor = empresaContribuyenteFacade.findEmpleadoEmpresaa(query, empresaLocal);
        List<Empleado> valor = empleadoFacade.findEmpleadoEmpresa(query, empresaLocal);
        return valor;
    }

    public Empleado getEmpleadoNomina() {
        return empleadoNomina;
    }

    public void setEmpleadoNomina(Empleado empleadoNomina) {
        if (empleadoNomina == null) {
            this.empleadoNomina = new Empleado();
            percepcionesEmpleadoNomina=new ArrayList<>();
            deduccionesEmpleadoNomina=new ArrayList<>();
        } else {
            this.empleadoNomina = empleadoNomina;
            percepcionesEmpleadoNomina = deduccionPercepcionFacade.findPercepcionesEmpleado(empleadoNomina);
            deduccionesEmpleadoNomina = deduccionPercepcionFacade.findDeduccionEmpleado(empleadoNomina);
        }
    }

    public Empresa getEmpresaActual() {
        return (Empresa) this.recuperarParametroObject("empresaActual");
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public List<DeduccionPercepcion> getPercepcionesEmpleadoNomina() {
        /*
        List<DeduccionPercepcion> lista =new ArrayList<>();
        if (empleadoNomina != null)
           for(DeduccionPercepcion dep :empleadoNomina.getDeduccionPercepcionCollection()){   
               if (dep.getPercepcion() != null )
                  lista.add(dep);
           }
       
        return lista;*/
        return percepcionesEmpleadoNomina;
    }

    public void setPercepcionEmpleadoNomina(List<DeduccionPercepcion> percepcionesEmpleadoNomina) {
        this.percepcionesEmpleadoNomina = percepcionesEmpleadoNomina;
    }

    public List<DeduccionPercepcion> getDeduccionesEmpleadoNomina() {
        /*List<DeduccionPercepcion> lista =new ArrayList<>();
        if (empleadoNomina != null)
            for(DeduccionPercepcion dep :empleadoNomina.getDeduccionPercepcionCollection()){   
               if (dep.getDeduccion()!= null )
                  lista.add(dep);
           }
        return lista;*/
        return deduccionesEmpleadoNomina;
    }

    public void onRowEdit(RowEditEvent event) {
        DeduccionPercepcion dvTemp = ((DeduccionPercepcion) event.getObject());
        deduccionPercepcionFacade.edit(dvTemp);
        FacesMessage msg = new FacesMessage("Percepcion", dvTemp.getPercepcion().getConcepto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((DeduccionPercepcion) event.getObject()).getPercepcion().getConcepto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEditD(RowEditEvent event) {
        DeduccionPercepcion dvTemp = ((DeduccionPercepcion) event.getObject());
        deduccionPercepcionFacade.edit(dvTemp);
        FacesMessage msg = new FacesMessage("Deduccion", dvTemp.getDeduccion().getConcepto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancelD(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((DeduccionPercepcion) event.getObject()).getDeduccion().getConcepto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public DeduccionPercepcion getDeduccionPercepcionTemp() {
        return deduccionPercepcionTemp;
    }

    public void setDeduccionPercepcionTemp(DeduccionPercepcion deduccionPercepcionTemp) {
        this.deduccionPercepcionTemp = deduccionPercepcionTemp;
    }

}
