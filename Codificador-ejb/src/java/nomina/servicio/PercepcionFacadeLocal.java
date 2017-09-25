/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Local;
import nomina.entidad.Percepcion;

/**
 *
 * @author ovante
 */
@Local
public interface PercepcionFacadeLocal {

    void create(Percepcion percepcion);

    void edit(Percepcion percepcion);

    void remove(Percepcion percepcion);

    Percepcion find(Object id);

    List<Percepcion> findAll();

    List<Percepcion> findRange(int[] range);

    int count();

    public Percepcion getPercepcion(String concepto);

    public List<Percepcion> findPercepcionEmpleado(Integer empleado);

}
