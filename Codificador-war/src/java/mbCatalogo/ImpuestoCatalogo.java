/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbCatalogo;

import catalogo.entidad.Impuesto;
import catalogo.servicio.ImpuestoFacadeLocal;
import factura.entidad.ImpuestoP;
import factura.servicio.ImpuestoPFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author ovante
 */
@Named(value = "impuestoCatalogo")
@SessionScoped
public class ImpuestoCatalogo implements Serializable {

    @EJB
    private ImpuestoFacadeLocal impuestoFacade; //Catalogo de impuestos del Sat

    @EJB
    private ImpuestoPFacadeLocal impuestoPFacade;

    private List<Impuesto> impuestos;
    private Impuesto impuesto;
    private List<ImpuestoP> impuestosP;
    private List<ImpuestoP> impuestoSeleccionado; 
    /* impuestos para producto */
    private ImpuestoP impuestoP;

    /**
     * Creates a new instance of ImpuestoCatalogo
     */
    @PostConstruct
    public void initState() {
        impuestos = this.impuestoFacade.findAll();
        impuestosP = this.impuestoPFacade.findAll();
        impuestoP = new ImpuestoP();        
        impuestoP.setIdimpuesto(0);
        impuestoP.setCantidad(0.0);
        impuestoP.setPorciento(0.0);
        impuesto= null;
        this.impuestoSeleccionado = null;
    }

    public ImpuestoCatalogo() {

    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
        if (this.impuesto != null) {
            if (impuestoP == null) {
                impuestoP = new ImpuestoP();
                impuestoP.setIdimpuesto(0);
            }
            
            impuestoP.setImpuesto(impuesto.getCodigo());
            impuestoP.setDescripcion(impuesto.getDescripcion());
            System.out.println("Impuesto seleccionado " + impuesto.getDescripcion());

        }
    }

    public List<ImpuestoP> getImpuestosP() {
      //  System.out.println(impuestosP.size()+ "en la lista");
        return impuestosP;
    }

    public void setImpuestosP(List<ImpuestoP> impuestosP) {
        this.impuestosP = impuestosP;
    }

    public ImpuestoP getImpuestoP() {
        return impuestoP;
    }

    public void setImpuestoP(ImpuestoP impuestoP) {
        this.impuestoP = impuestoP;
        System.out.println(impuestoP.getDescripcion());
    }

    public List<Impuesto> buscaEnSat(String query) {
        this.impuestos = this.impuestoFacade.findImpuestos(query);
        return impuestos;
    }

    public void editar(ImpuestoP cual) {
        this.impuestoP = cual;        
        this.impuesto = this.impuestoFacade.findImpuesto(cual.getImpuesto());

    }

    public void borrar(ImpuestoP cual) {

        this.impuestoPFacade.remove(cual);
        this.initState();
        

    }

    public void guardar() {
        if (impuestoP.getIdimpuesto() == 0) {
            this.impuestoPFacade.create(impuestoP);
        } else {
            this.impuestoPFacade.edit(impuestoP);

        }
        this.initState();

    }

    public List<ImpuestoP> getImpuestoSeleccionado() {
        return impuestoSeleccionado;
    }

    public void setImpuestoSeleccionado(List<ImpuestoP> impuestoSeleccionado) {
        this.impuestoSeleccionado = impuestoSeleccionado;
    /*    if (this.impuestoSeleccionado.size() > 0 )
            this.impuestoP = this.impuestoSeleccionado.get(0);*/
    }
    
      

}
