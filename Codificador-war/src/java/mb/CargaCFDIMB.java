/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.CargarCFDILocal;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import sat.Comprobante;
import timbre.TimbreFiscalDigital;

/**
 *
 * @author ovante
 */
@Named(value = "cargaCFDIMB")
@SessionScoped
public class CargaCFDIMB implements Serializable {

    @EJB
    private CargarCFDILocal cargarCFDI;
    String factura;
    Comprobante comprobante;
     

    /**
     * Creates a new instance of CargaCFDIMB
     */
    public CargaCFDIMB() {
    }
    
    
     public void handleFileUpload(FileUploadEvent event) throws IOException {
        FacesMessage message = new FacesMessage("Archivo ", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        InputStream inputstream = event.getFile().getInputstream();
        factura = readFile(inputstream);
         comprobante = cargarCFDI.loadComprobante(factura);
        
        
    }
     
     
      //Read file content into string with - Files.lines(Path path, Charset cs)
 
    private static String readFile(InputStream in) 
    {
        String contents = "";
      try{
        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        
        while (result != -1) {
            
            
          

            buf.write((byte) result);
            result = bis.read();
        }

            return buf.toString("UTF-8").replace("\uFEFF", "");

        } catch (IOException ex) {
            Logger.getLogger(CargaCFDIMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contents;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }
    
    public TimbreFiscalDigital getTimbre(){
          TimbreFiscalDigital timbre = null;
          try{
            timbre = (TimbreFiscalDigital)comprobante.getComplemento().get(0).getAny().get(0);
          }catch (Exception e){
              
          }
            return timbre;
    }
    
    public void cargar(ActionEvent e){
           FacesMessage message = new FacesMessage("Comprobante Cargado a BD");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
    

    
}
