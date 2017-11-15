/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbCatalogo;

import catalogo.entidad.Impuesto;
import catalogo.servicio.ImpuestoFacadeLocal;
import factura.servicio.ImpuestoPFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
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
    private String buscaImpuesto;
     

    /**
     * Creates a new instance of ImpuestoCatalogo
     */
    public ImpuestoCatalogo() {
        impuestos = this.impuestoFacade.findAll();
        
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
    }

    public String getBuscaImpuesto() {
        return buscaImpuesto;
    }

    public void setBuscaImpuesto(String buscaImpuesto) {
        this.buscaImpuesto = buscaImpuesto;
    }
    
    
    
}
