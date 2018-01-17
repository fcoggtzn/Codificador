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
import factura.entidad.CategoriaImpuesto;
import factura.entidad.Producto;
import factura.servicio.ProductoFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.datatype.DatatypeConfigurationException;
import nomina.entidad.Contribuyente;
import org.primefaces.context.RequestContext;
import sat.CTipoDeComprobante;
import tools.DetalleFactura;
import tools.FacturaXML;

/**
 *
 * @author ovante
 */
@Named(value = "facturaMB")
@SessionScoped
public class FacturaMB extends BaseController implements Serializable {

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
    private UsoCfdi usoCfdi;
    private FormaPago formaPago;
    private MetodoPago metodoPago;
    private String referencia;
    private boolean esPagado;
    private String message;
    private String facturaRuta;
    private Double subTotalFactura;
    private Double totalFactura;
    private Double totalFacturaImpuestosTrasladado;
    private Double totalFacturaImpuestosRetenido;
    private boolean errorProducto;
    private String cfdiRelacionado;

    /**
     * Creates a new instance of FacturaMB
     */
    @PostConstruct
    public void initState() {
        clean();
    }

    private void clean() {
        textoBoton = "Generar CFDI";
        detallesDeFactura = new ArrayList<DetalleFactura>();
        detalleFactura = new DetalleFactura();
        esPagado = true;
        this.metodoPago = null;
        this.formaPago = null;
        this.referencia = "";
        this.contribuyente = null;
        this.usoCfdi = null;
        subTotalFactura = 0.0;
        totalFactura = 0.0;
        totalFacturaImpuestosTrasladado = 0.0;
        totalFacturaImpuestosRetenido = 0.0;
    }
    
    
    public Double getSubTotalFactura(){
        errorProducto=false;
        subTotalFactura = 0.0;
        totalFactura = 0.0;
        totalFacturaImpuestosTrasladado = 0.0;
        totalFacturaImpuestosRetenido = 0.0;
        
        for(DetalleFactura df:detallesDeFactura){
            
            subTotalFactura += df.getImporte();
            if (df.getProducto() != null ){
            for(CategoriaImpuesto impPrd:  df.getProducto().getCategoria().getCategoriaImpuestoCollection()){
                if (impPrd.getTraslado()){
                    totalFacturaImpuestosTrasladado += impPrd.getImpuestoP().getPorciento()*df.getImporte();
                         totalFacturaImpuestosTrasladado += impPrd.getImpuestoP().getCantidad()*df.getCantidad();
                }else{ 
                    totalFacturaImpuestosRetenido += impPrd.getImpuestoP().getPorciento()*df.getImporte();
                    totalFacturaImpuestosRetenido += impPrd.getImpuestoP().getCantidad()*df.getCantidad();
                }
                    
            }}
            else {
                errorProducto=true;
            }
           totalFactura = subTotalFactura + totalFacturaImpuestosTrasladado - totalFacturaImpuestosRetenido;
        }
        return subTotalFactura;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public Double getTotalFacturaImpuestosTrasladado() {
        return totalFacturaImpuestosTrasladado;
    }

    public Double getTotalFacturaImpuestosRetenido() {
        return totalFacturaImpuestosRetenido;
    }
    
    

    public FacturaMB() {
        textoBoton = "Generar CFDI";
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

    public void guardar() {

        if (contribuyente == null) {
            this.msgError("Timbrando sin contriuyente");

        } else if (usoCfdi == null) {
            this.msgError("Error en uso de CFDI");
        } else if (detallesDeFactura.size() <= 0) {
            this.msgError("No tiene detalle de facturación");

        } else if (errorProducto) {
            this.msgError("Detalle de producto sin producto");

        } else if (formaPago == null) {
            this.msgError("No tiene Forma de Pago");
        } else if (referencia.isEmpty()) {
            this.msgError("S/N");
        } else if (metodoPago == null) {
            this.msgError("No tiene Metodo de Pago");
        } else {
            try {
                FacturaXML facturarXML;
                facturarXML = new FacturaXML(contribuyente, usoCfdi, detallesDeFactura,
                        formaPago, referencia, metodoPago, esPagado);
          
                 facturaRuta = facturarXML.generaCFDI(CTipoDeComprobante.I,this.cfdiRelacionado);                 
                this.msgOk("Comprobante grabado", "Comprobante grabado");
                clean();
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Codificador-war/faces/factura/iFacturaView.xhtml");
              
            } catch (Exception ex) {
            
               
                 this.msgError(ex.getMessage());
            }
        
        }
      RequestContext requestContext = RequestContext.getCurrentInstance();
      requestContext.execute("PF('statusDialog').hide()");
    }
    
    
     public void notaCredito() {

        if (contribuyente == null) {
            this.msgError("Timbrando sin contriuyente");

        } else if (usoCfdi == null) {
            this.msgError("Error en uso de CFDI");
        } else if (detallesDeFactura.size() <= 0) {
            this.msgError("No tiene detalle de facturación");

        } else if (errorProducto) {
            this.msgError("Detalle de producto sin producto");

        } else if (formaPago == null) {
            this.msgError("No tiene Forma de Pago");
        } else if (referencia.isEmpty()) {
            this.msgError("S/N");
        } else if (metodoPago == null) {
            this.msgError("No tiene Metodo de Pago");
        } else {
            try {
                FacturaXML facturarXML;
                facturarXML = new FacturaXML(contribuyente, usoCfdi, detallesDeFactura,
                        formaPago, referencia, metodoPago, esPagado);
          
                 facturaRuta = facturarXML.generaCFDI(CTipoDeComprobante.E,this.cfdiRelacionado);                 
                this.msgOk("Comprobante grabado", "Comprobante grabado");
                clean();
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Codificador-war/faces/factura/iFacturaView.xhtml");
              
            } catch (Exception ex) {
            
               
                 this.msgError(ex.getMessage());
            }
        
        }
      RequestContext requestContext = RequestContext.getCurrentInstance();
      requestContext.execute("PF('statusDialog').hide()");
    }

    public void limpiar() {
        this.clean();
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

    public void quitarDetalle(DetalleFactura detalleFactura) {
        this.detallesDeFactura.remove(detalleFactura);
    }

    public void agregarDetalle() {
        this.detallesDeFactura.add(detalleFactura);
        detalleFactura = new DetalleFactura();
    }

    public List<Producto> completaProductos(String query) {

        return this.productoFacade.getListaProductosCombo(query);
    }

    public List<UsoCfdi> completaUsos(String query) {
        return this.usoCfdiFacade.findCombo(query);
    }

    public List<FormaPago> completaFormaPago(String query) {
        return this.formaPagoFacade.findCombo(query);
    }

    public List<MetodoPago> completaMetodoPago(String query) {
        return this.metodoPagoFacade.findCombo(query);
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
        if (formaPago != null && formaPago.getBancarizado().toUpperCase().equals("OPCIONAL")) {
            this.esPagado = false;
        } else {
            this.esPagado = false;

        }
    }

    public String getCfdiRelacionado() {
        return cfdiRelacionado;
    }

    public void setCfdiRelacionado(String cfdiRelacionado) {
        this.cfdiRelacionado = cfdiRelacionado;
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

    public String getPatron() {
        String patron = "^[a-zA-Z0-9[:space:]]*$";
        try {
            patron = formaPago.getPatronCuentaOrdenante();
            if (formaPago.getPatronCuentaOrdenante().toUpperCase().equals("OPCIONAL")
                    || formaPago.getPatronCuentaOrdenante().toUpperCase().equals("NO")) {
                patron = "^[a-zA-Z0-9[:space:]]*$";
            }
        } catch (Exception e) {
            //   System.out.println("Sin forma de pago");
        }
        return patron;
    }

    public boolean isEsPagado() {
        return esPagado;
    }

    public void setEsPagado(boolean esPagado) {
        this.esPagado = esPagado;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Successful", "Your message: " + message));
        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }

    public String getFacturaRuta() {
        return facturaRuta;
    }

    public void setFacturaRuta(String facturaRuta) {
        this.facturaRuta = facturaRuta;
    }
    
    
    

}
