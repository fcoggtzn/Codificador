/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import catalogo.entidad.ProdServ;
import catalogo.servicio.ProdServFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ovante
 */
@Named(value = "facturaController")
@SessionScoped
public class facturaController implements Serializable {
     @EJB
     ProdServFacadeLocal prodServFacade;
     
     private ProdServ prodServ;
     private List<ProdServ> allProdServ;
     private String busquedaProdServ="No existe en el catálogo";

    /**
     * Creates a new instance of facturaController
     */
    public facturaController() {
        
    }
    
    @PostConstruct
    public void init() {
        prodServ=new ProdServ();
        allProdServ=new ArrayList<>();
        allProdServ=prodServFacade.findProdServ(busquedaProdServ);
    }

    public ProdServ getProdServ() {
        return prodServ;
    }

    public void setProdServ(ProdServ prodServ) {
        this.prodServ = prodServ;
    }

    public String getBusquedaProdServ() {
        return busquedaProdServ;
    }

    public void setBusquedaProdServ(String busquedaProdServ) {
        this.busquedaProdServ = busquedaProdServ;
        allProdServ=prodServFacade.findProdServ(busquedaProdServ);
    }
    
    public List<ProdServ> getAllProdServ(){
        
        return allProdServ;
    }
    
    public void onRowSelect(SelectEvent event) {
        this.prodServ=(ProdServ) event.getObject();
        FacesMessage msg = new FacesMessage("", ((ProdServ) event.getObject()).getDescripcion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void redireccionar(String page) throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect(page);
    }
    
}