/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.IOException;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface CancelaCfdiEjbLocal {

    public void cancela(String UUID) throws IOException;
    
}
