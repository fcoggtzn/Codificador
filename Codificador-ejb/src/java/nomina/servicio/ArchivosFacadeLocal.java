/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Local;
import nomina.entidad.Archivos;

/**
 *
 * @author ovante
 */
@Local
public interface ArchivosFacadeLocal {

    void create(Archivos archivos);

    void edit(Archivos archivos);

    void remove(Archivos archivos);

    Archivos find(Object id);

    List<Archivos> findAll();

    List<Archivos> findRange(int[] range);

    int count();
    
}
