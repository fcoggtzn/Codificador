/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Local;
import nomina.entidad.DeduccionPercepcion;
import nomina.entidad.Empleado;

/**
 *
 * @author ovante
 */
@Local
public interface DeduccionPercepcionFacadeLocal {

    void create(DeduccionPercepcion deduccionPercepcion);

    void edit(DeduccionPercepcion deduccionPercepcion);

    void remove(DeduccionPercepcion deduccionPercepcion);

    DeduccionPercepcion find(Object id);

    List<DeduccionPercepcion> findAll();

    List<DeduccionPercepcion> findRange(int[] range);

    int count();

    public List<DeduccionPercepcion> findPercepcionesEmpleado(Empleado idEmpleado);

    public List<DeduccionPercepcion> findDeduccionEmpleado(Empleado idEmpleado);

    public DeduccionPercepcion getDeduccionEmpleado(DeduccionPercepcion dp);

    public DeduccionPercepcion getPercepcionEmpleado(DeduccionPercepcion dp);
    
}
