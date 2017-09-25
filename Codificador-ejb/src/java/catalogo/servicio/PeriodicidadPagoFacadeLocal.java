/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.PeriodicidadPago;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface PeriodicidadPagoFacadeLocal {

    void create(PeriodicidadPago periodicidadPago);

    void edit(PeriodicidadPago periodicidadPago);

    void remove(PeriodicidadPago periodicidadPago);

    PeriodicidadPago find(Object id);

    List<PeriodicidadPago> findAll();

    List<PeriodicidadPago> findRange(int[] range);

    int count();

    public PeriodicidadPago getPeriodicidadByPer(String period);

    public PeriodicidadPago getPeriodicidadByDesc(String desc);

    public PeriodicidadPago getPrimerPeriodicidad();
    
}
