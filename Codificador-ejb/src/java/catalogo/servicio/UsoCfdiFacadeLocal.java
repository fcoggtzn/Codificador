/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.UsoCfdi;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface UsoCfdiFacadeLocal {

    void create(UsoCfdi usoCfdi);

    void edit(UsoCfdi usoCfdi);

    void remove(UsoCfdi usoCfdi);

    UsoCfdi find(Object id);

    List<UsoCfdi> findAll();

    List<UsoCfdi> findRange(int[] range);

    int count();

    public UsoCfdi findId(String value);

    public List<UsoCfdi> findCombo(String value);
    
}
