/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factura.servicio;

import java.util.List;
import javax.ejb.Local;
import factura.entidad.ImpuestoP;

/**
 *
 * @author ovante
 */
@Local
public interface ImpuestoPFacadeLocal {

    void create(ImpuestoP impuestoP);

    void edit(ImpuestoP impuestoP);

    void remove(ImpuestoP impuestoP);

    ImpuestoP find(Object id);

    List<ImpuestoP> findAll();

    List<ImpuestoP> findRange(int[] range);

    int count();
    
}
