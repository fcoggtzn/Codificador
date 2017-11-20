/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.Unidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface UnidadFacadeLocal {

    void create(Unidad unidad);

    void edit(Unidad unidad);

    void remove(Unidad unidad);

    Unidad find(Object id);

    List<Unidad> findAll();

    List<Unidad> findRange(int[] range);

    int count();

    public List<Unidad> findUnidades(String desc);

    public Unidad findUnidadId(String desc);
    
}
