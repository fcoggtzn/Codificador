/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import catalogo.entidad.FormaPago;
import catalogo.entidad.MetodoPago;
import catalogo.entidad.UsoCfdi;
import catalogo.servicio.FormaPagoFacadeLocal;
import catalogo.servicio.MetodoPagoFacadeLocal;
import catalogo.servicio.UsoCfdiFacadeLocal;
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
    private MetodoPagoFacadeLocal metodoPagoFacade;

    @EJB
    private FormaPagoFacadeLocal formaPagoFacade;

    @EJB
    private UsoCfdiFacadeLocal usoCfdiFacade;

    @EJB
    private ProductoFacadeLocal productoFacade;
    private Contribuyente contribuyente;
    private String usoDeCFDI;
    private String textoBoton;
    private List<DetalleFactura> detallesDeFactura;
    private DetalleFactura detalleFactura;
    private List<UsoCfdi> usosCFDI;
    private UsoCfdi usoCfdi;
    private FormaPago formaPago;
    private List<FormaPago> formasPago;
    private MetodoPago metodoPago;
    private String referencia;

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
    
    
    public List<UsoCfdi> completaUsos(String query){        
        return this.usoCfdiFacade.findCombo(query);
    }

    public List<UsoCfdi> getUsosCFDI() {
        return usosCFDI;
    }
    
    
    public List<FormaPago> completaFormaPago(String query){        
        return this.formaPagoFacade.findCombo(query);
    }
 public List<MetodoPago> completaMetodoPago(String query){        
        return this.metodoPagoFacade.findCombo(query);
    }
    public void setUsosCFDI(List<UsoCfdi> usosCFDI) {
        this.usosCFDI = usosCFDI;
        
    }

    public UsoCfdi getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(UsoCfdi usoCfdi) {
        this.usoCfdi = usoCfdi;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public List<FormaPago> getFormasPago() {
        return formasPago;
    }

    public void setFormasPago(List<FormaPago> formasPago) {
        this.formasPago = formasPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    
    public String getPatron(){
        String patron = "^[a-zA-Z0-9[:space:]]*$" ;
        try{
        patron = formaPago.getPatronCuentaOrdenante();
        if (formaPago.getPatronCuentaOrdenante().toUpperCase().equals("OPCIONAL") || 
                formaPago.getPatronCuentaOrdenante().toUpperCase().equals("NO")){
            patron = "^[a-zA-Z0-9[:space:]]*$";
        }
        }catch(Exception e){
            System.out.println("Sin forma de pago");
        }
        return patron;
    }
    
    
    
    
}
