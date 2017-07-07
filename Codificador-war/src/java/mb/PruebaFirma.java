/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.CrearCFDILocal;
import ejb.FirmaLocal;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author franciscogutierrez
 */
@Named(value = "pruebaFirma")
@RequestScoped
public class PruebaFirma {

    @EJB
    private CrearCFDILocal crearCFDI;

    @EJB
    private FirmaLocal firma;
     
 
    /**
     * Creates a new instance of PruebaFirma
     */
    public PruebaFirma() {
        
    }
    
    public String getFirma(){
        String firmar = firma.firmar("VivaMexico","TME960709LR2");
        try {
            this.crearCFDI.crear();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PruebaFirma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(PruebaFirma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(PruebaFirma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PruebaFirma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return firmar;
    }    
}
