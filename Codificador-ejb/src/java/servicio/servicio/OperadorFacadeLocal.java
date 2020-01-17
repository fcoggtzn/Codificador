/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.servicio;

import java.util.List;
import javax.ejb.Local;
import servicio.entidad.Operador;

/**
 *
 * @author ovante
 */
@Local
public interface OperadorFacadeLocal {

    void create(Operador operador);

    void edit(Operador operador);

    void remove(Operador operador);

    Operador find(Object id);

    List<Operador> findAll();

    List<Operador> findRange(int[] range);

    int count();

    public Operador findOperador(String value);

    public List<Operador> findOperadors(String value);
    
}
