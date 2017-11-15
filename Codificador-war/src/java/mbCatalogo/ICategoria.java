/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbCatalogo;

import catalogo.entidad.ProdServ;
import catalogo.servicio.ProdServFacadeLocal;
import factura.entidad.Categoria;
import factura.servicio.CategoriaFacadeLocal;
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
@Named(value = "iCategoria")
@SessionScoped
public class ICategoria implements Serializable {

    @EJB
    private CategoriaFacadeLocal categoriaFacade;

    @EJB
    private ProdServFacadeLocal prodServFacade;
    
    private Categoria categoria;
    private List<Categoria> categorias;
    private List<ProdServ> productoServicios;
    private ProdServ prodServ;
    private String textoBoton;

    /**
     * Creates a new instance of ICatalogo
     */
    public ICategoria() {
    }
    
    @PostConstruct
    public void inicializa(){
        
     
        productoServicios = this.prodServFacade.findProdServ("");
       
        categorias = this.categoriaFacade.findAll();
        textoBoton="Guardar";
        if (categorias.size() == 0) {
            categoria = new Categoria();
        } else {
            categoria =categorias.get(0);
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<ProdServ> getProductoServicios() {
        return productoServicios;
    }

    public void setProductoServicios(List<ProdServ> productoServicios) {
        this.productoServicios = productoServicios;
    }

    public String getTextoBoton() {
        return textoBoton;
    }

    public void setTextoBoton(String textoBoton) {
        this.textoBoton = textoBoton;
    }
    
    public List<ProdServ> buscaEnSat(String query) {
        this.productoServicios = this.prodServFacade.findProdServ(query);      
        return productoServicios;
    }
    
    public void guardar(){
        categoria.setClaveProductoServicio(this.prodServ.getClaveProdServ());
        System.out.println(categoria.getClaveProductoServicio());
    }

    public ProdServ getProdServ() {
        return prodServ;
    }

    public void setProdServ(ProdServ prodServ) {
        this.prodServ = prodServ;
    }
    
    
    
    
}
