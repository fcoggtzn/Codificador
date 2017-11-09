/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factura.servicio;

import java.util.List;
import javax.ejb.Local;
import factura.entidad.CategoriaImpuesto;

/**
 *
 * @author ovante
 */
@Local
public interface CategoriaImpuestoFacadeLocal {

    void create(CategoriaImpuesto categoriaImpuesto);

    void edit(CategoriaImpuesto categoriaImpuesto);

    void remove(CategoriaImpuesto categoriaImpuesto);

    CategoriaImpuesto find(Object id);

    List<CategoriaImpuesto> findAll();

    List<CategoriaImpuesto> findRange(int[] range);

    int count();
    
}
