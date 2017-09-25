/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Local;
import nomina.entidad.Deduccion;

/**
 *
 * @author ovante
 */
@Local
public interface DeduccionFacadeLocal {

    void create(Deduccion deduccion);

    void edit(Deduccion deduccion);

    void remove(Deduccion deduccion);

    Deduccion find(Object id);

    List<Deduccion> findAll();

    List<Deduccion> findRange(int[] range);

    int count();

    public Deduccion getDeduccion(String concepto);
    
}
