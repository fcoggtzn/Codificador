/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbCatalogo;

import catalogo.entidad.Impuesto;
import catalogo.entidad.ProdServ;
import catalogo.entidad.Unidad;
import catalogo.servicio.ImpuestoFacadeLocal;
import catalogo.servicio.ProdServFacadeLocal;
import catalogo.servicio.UnidadFacadeLocal;
import factura.entidad.Categoria;
import factura.entidad.CategoriaImpuesto;
import factura.entidad.ImpuestoP;
import factura.servicio.CategoriaFacadeLocal;
import factura.servicio.CategoriaImpuestoFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author ovante
 */
@Named(value = "iCategoria")
@SessionScoped
public class ICategoria implements Serializable {

    @EJB
    private CategoriaImpuestoFacadeLocal categoriaImpuestoFacade;

    @EJB
    private ImpuestoFacadeLocal impuestoFacade;

    @EJB
    private UnidadFacadeLocal unidadFacade;

    @EJB
    private CategoriaFacadeLocal categoriaFacade;

    @EJB
    private ProdServFacadeLocal prodServFacade;
    
    
    
    

    private Categoria categoria;
    private List<Categoria> categorias;
    private List<ProdServ> productoServicios;
    private ProdServ prodServ;
    private String textoBoton;
    private List<Unidad> unidades;
    private Unidad unidad;
    private List<Impuesto> impuestos;
    
    @ManagedProperty(value="#{impuestoCatalogo.impuestosP}") 
     ImpuestoP impuestoP;

    /**
     * Creates a new instance of ICatalogo
     */
    public ICategoria() {
    }

    @PostConstruct
    public void inicializa() {
       limpiar();
        if (categorias.isEmpty()) {
            categoria = new Categoria();
        } else {
            setCategoria(categorias.get(0));
        }
    }
    
    public void limpiar(){
        categoria = new Categoria();
        categoria.setIdcategoria(0);
         
      

        productoServicios = this.prodServFacade.findProdServ("");
        unidades = this.unidadFacade.findUnidades("");
        impuestos = this.impuestoFacade.findAll();

        categorias = this.categoriaFacade.findAll();
        textoBoton = "Nuevo";
    }
    
    

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.textoBoton = "Editar";
        this.categoria = categoria;
        this.prodServ= this.prodServFacade.findProdServID(categoria.getClaveProductoServicio());
        this.unidad = this.unidadFacade.findUnidadId(categoria.getClaveUnidad());
        
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

    public void guardar() {
       
        try{
            categoriaImpuestoFacade.borrarImpuesto(categoria);
            categoriaFacade.edit(categoria);
            System.out.println("editando");
        }catch(Exception e){
            categoriaFacade.create(categoria);
        }
        inicializa();
    }

    public ProdServ getProdServ() {
        return prodServ;
    }

    public void setProdServ(ProdServ prodServ) {
        this.prodServ = prodServ;
        if (prodServ != null) {
            categoria.setClaveProductoServicio(this.prodServ.getClaveProdServ());
        }
    }

    public List<Unidad> buscaUnidadEnSat(String query) {
        this.unidades = this.unidadFacade.findUnidades(query);
        return unidades;
    }

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
        if (unidad != null) {
            
        categoria.setClaveUnidad(unidad.getClaveUnidad());
        categoria.setUnidad(unidad.getNombre());
        }
    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }
    
    public void borrar(Categoria categoria){
        this.categoriaFacade.remove(categoria);
        inicializa();
    }
    
    public void editar(Categoria categoria){
        setCategoria(categoria);
        this.textoBoton="Editar";
    }
    
    
        public void agregarImpuestoT(ImpuestoP impuestoP) {
        agregarImpuestoP(impuestoP, false);
    }

    public void agregarImpuestoR(ImpuestoP impuestoP) {
        agregarImpuestoP(impuestoP, true);
    }
    
    public void agregarImpuestoP(ImpuestoP impuestoP,boolean traslado){
        CategoriaImpuesto catImp= new  CategoriaImpuesto();
        catImp.setCategoria(categoria);
        catImp.setIdcategoriaImpuesto(0);
        catImp.setImpuestoP(impuestoP);
        catImp.setTraslado(traslado);
          categoria.getCategoriaImpuestoCollection().add(catImp);
    }
    
     public void borrarImpuesto(CategoriaImpuesto catImp) {
         categoria.getCategoriaImpuestoCollection().remove(catImp);
         
    }
     
    // private CategoriaImpuesto impuestoCategoria()

}
