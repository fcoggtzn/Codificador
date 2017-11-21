/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.MetodoPago;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface MetodoPagoFacadeLocal {

    void create(MetodoPago metodoPago);

    void edit(MetodoPago metodoPago);

    void remove(MetodoPago metodoPago);

    MetodoPago find(Object id);

    List<MetodoPago> findAll();

    List<MetodoPago> findRange(int[] range);

    int count();

    public MetodoPago findbyID(String busca);
    public List<MetodoPago> findCombo(String busca);
    
}
