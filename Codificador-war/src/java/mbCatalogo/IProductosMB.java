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
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author ovante
 */
@Named(value = "iProductosMB")
@SessionScoped
public class IProductosMB implements Serializable {

    @EJB
    private ProductoFacadeLocal productoFacade;
    
    private List<Producto> productos;
    private Producto producto;

    /**
     * Creates a new instance of iProductosMB
     */
    public IProductosMB() {
        producto = new Producto();
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
    
    
    
    
}
