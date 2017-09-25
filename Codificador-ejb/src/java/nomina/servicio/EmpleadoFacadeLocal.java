/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Local;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;

/**
 *
 * @author ovante
 */
@Local
public interface EmpleadoFacadeLocal {

    void create(Empleado empleado);

    void edit(Empleado empleado);

    void remove(Empleado empleado);

    Empleado find(Object id);

    List<Empleado> findAll();

    List<Empleado> findRange(int[] range);

    int count();   

    public Empleado getEmpleado(String nombre);

    public List<Empleado> findEmpleadoEmpresa(String nombre, Empresa empresa);

}
