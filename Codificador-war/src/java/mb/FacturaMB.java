/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import factura.entidad.Producto;
import factura.servicio.ProductoFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import nomina.entidad.Contribuyente;
import tools.DetalleFactura;

/**
 *
 * @author ovante
 */
@Named(value = "facturaMB")
@SessionScoped
public class FacturaMB implements Serializable {

    @EJB
    private ProductoFacadeLocal productoFacade;
    private Contribuyente contribuyente;
    private String usoDeCFDI;
    private String textoBoton;
    private List<DetalleFactura> detallesDeFactura;
    private DetalleFactura detalleFactura;

    /**
     * Creates a new instance of FacturaMB
     */
    @PostConstruct
    public void initState(){
                textoBoton="Generar CFDI";
                detallesDeFactura= new ArrayList<DetalleFactura>();
                detalleFactura = new DetalleFactura();

    }
    
    
    public FacturaMB() {
        textoBoton="Generar CFDI";
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public String getUsoDeCFDI() {
        return usoDeCFDI;
    }

    public void setUsoDeCFDI(String usoDeCFDI) {
        this.usoDeCFDI = usoDeCFDI;
    }

    public String getTextoBoton() {
        return textoBoton;
    }

    public void setTextoBoton(String textoBoton) {
        this.textoBoton = textoBoton;
    }
    
    public void guardar(){
        System.out.println("Guardar");
    }
    public void limpiar(){
        System.out.println("Limpiar");
    }

    public List<DetalleFactura> getDetallesDeFactura() {
        return detallesDeFactura;
    }

    public void setDetallesDeFactura(List<DetalleFactura> detallesDeFactura) {
        this.detallesDeFactura = detallesDeFactura;
    }

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }
    
    public void quitarDetalle(DetalleFactura detalleFactura){
        this.detallesDeFactura.remove(detalleFactura);
    }
    
    public void agregarDetalle(){
        this.detallesDeFactura.add(detalleFactura);
        detalleFactura = new DetalleFactura();
    }
     
    
    public List<Producto> completaProductos(String query){
        
        return this.productoFacade.getListaProductosCombo(query);
    }
    
}
