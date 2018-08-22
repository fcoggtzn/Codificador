/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;
import sat.Comprobante;

/**
 *
 * @author ovante
 */
@Local
public interface CargarCFDILocal {

    public Comprobante loadComprobante(String cfdi);
    
}
