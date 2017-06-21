/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import com.sun.xml.wss.impl.misc.Base64;
import ejb.FirmaLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author franciscogutierrez
 */
@Named(value = "pruebaFirma")
@RequestScoped
public class PruebaFirma {

    @EJB
    private FirmaLocal firma;
 
    /**
     * Creates a new instance of PruebaFirma
     */
    public PruebaFirma() {
        
    }
    
    public String getFirma(){
        byte[] firmar = firma.firmar("VivaMexico".getBytes(),"TME960709LR2");
        return Base64.encode(firmar);
    }    
}
