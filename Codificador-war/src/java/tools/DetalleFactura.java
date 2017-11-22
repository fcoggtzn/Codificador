/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import factura.entidad.Producto;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author ovante
 */
public class DetalleFactura implements Serializable  {
    private Producto producto ;
    private double cantidad=1.0;
    private double porDescuento=0.0;
    private double cantidadDescuento=0.0;
    private double importe=0.0;
    private double valorUnitario=0.0;
    private String descripcion;
   
 public DetalleFactura(){
    
 }
    
    

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (producto != null){
        this.valorUnitario=producto.getValorUnitario();
        }
        else 
        {
            this.valorUnitario = 0.0;
            this.porDescuento=0.0;
            this.cantidadDescuento=0.0;
        }
       calculos();
       
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        if (cantidad >= 0){
        this.cantidad = cantidad;
        calculos();
        }else{
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se admiten cantidades negativas"));

        }
    }

    
    public Double getPorDescuento() {
        return porDescuento;
    }

    public void setPorDescuento(Double porDescuento) {
        if(porDescuento < 100){
            this.porDescuento = porDescuento;
            calculos();
        }else
        {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Descuento maximo 99.9%"));


        }
        
    }

    public double getCantidadDescuento() {
        return cantidadDescuento;
    }

    public void setCantidadDescuento(double cantidadDescuento) {
       
            if (valorUnitario*cantidad < cantidadDescuento){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Descuento mayor a la cantidad"));
            }
            else
            {
                if (cantidadDescuento > 0) {
                    this.cantidadDescuento = cantidadDescuento;
               //     this.porDescuento = (this.cantidadDescuento * 100) / (valorUnitario * cantidad);
                } else {
                    cantidadDescuento = 0.0;
                    porDescuento = 0.0;
                }
                calculos();
            }

      
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
    
    private void calculos(){
         importe = valorUnitario * cantidad ;
        
         //cantidadDescuento = valorUnitario*cantidad * porDescuento/100; 
         if (cantidadDescuento > importe ) {
             cantidadDescuento =0;
         }
        
         importe = valorUnitario * cantidad - cantidadDescuento;
        
         
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
     
    
    
}
