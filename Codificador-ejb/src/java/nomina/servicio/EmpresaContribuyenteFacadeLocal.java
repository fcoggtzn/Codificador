/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Local;
import nomina.entidad.EmpresaContribuyente;

/**
 *
 * @author ovante
 */
@Local
public interface EmpresaContribuyenteFacadeLocal {

    void create(EmpresaContribuyente empresaContribuyente);

    void edit(EmpresaContribuyente empresaContribuyente);

    void remove(EmpresaContribuyente empresaContribuyente);

    EmpresaContribuyente find(Object id);

    List<EmpresaContribuyente> findAll();

    List<EmpresaContribuyente> findRange(int[] range);

    int count();

    public List<EmpresaContribuyente> findEmpleadoEmpresaa(String nombre, String empresa);

    public EmpresaContribuyente getEmpleado(String nombre);
    
}
