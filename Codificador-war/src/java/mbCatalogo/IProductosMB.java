/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbCatalogo;

import factura.entidad.Producto;
import factura.servicio.ProductoFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import tools.Tab;

/**
 *
 * @author ovante
 */
@Named(value = "iProductosMB")
@SessionScoped
public class IProductosMB implements Serializable {

    @EJB
    private ProductoFacadeLocal productoFacade;
    private String busqueda; 
    
    private List<Producto> productos;
    private Producto producto;
    
    private List<Tab> tabs;
    private String textoBoton;
    
    private String[] tabString = new String[]{"-","A","B","C","D","E","F","G","H","I","J"
                                  ,"K","L","M","N","O","P","Q","R","S","T"
                                  ,"U","V","X","Y","Z"};

    /**
     * Creates a new instance of iProductosMB
     */
    @PostConstruct
    public void initState(){
        producto = new Producto();
        producto.setIdproducto(0);
        busqueda="A";
        productos=this.productoFacade.getListaProductos(busqueda);
        tabs = new ArrayList<>();
        for (String i:tabString){
        tabs.add(new Tab(i,i));
        }
        textoBoton="Nuevo";
    }
    
    
    public IProductosMB() {
        
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
    public void grabar(){
        try{
            this.productoFacade.create(producto);
        }
        catch(Exception e){
            this.productoFacade.edit(producto);
        }
         String busqueda= this.busqueda;
        this.initState();
        this.busqueda= busqueda;
        productos=this.productoFacade.getListaProductos(busqueda);
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public String getTextoBoton() {
        return textoBoton;
    }

    public void setTextoBoton(String textoBoton) {
        this.textoBoton = textoBoton;
    }
    
    
    public void onChageBusqueda(){
         FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    String paramIndex = paramMap.get("activeIndex");
  
    System.out.println("Active index changed to " + tabString[new Integer(paramIndex)]);
    this.busqueda = tabString[new Integer(paramIndex)];
    productos=this.productoFacade.getListaProductos(busqueda);

        
    }
    
    public void edit(Producto e){
        this.producto = e;
        this.textoBoton="Editar";
    }
    
    
    public void borrar(Producto e){
        Producto find = this.productoFacade.find(e.getIdproducto());
        this.productoFacade.remove(find);
        String busqueda= this.busqueda;
        this.initState();
        this.busqueda= busqueda;
        productos=this.productoFacade.getListaProductos(busqueda);

    }
}
