/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.RiesgoPuesto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface RiesgoPuestoFacadeLocal {

    void create(RiesgoPuesto riesgoPuesto);

    void edit(RiesgoPuesto riesgoPuesto);

    void remove(RiesgoPuesto riesgoPuesto);

    RiesgoPuesto find(Object id);

    List<RiesgoPuesto> findAll();

    List<RiesgoPuesto> findRange(int[] range);

    int count();

    public RiesgoPuesto RiesgoPuestoByCve(String rp);

    public RiesgoPuesto RiesgoPuestoByDesc(String desc);

    public RiesgoPuesto primerRiesgoPuesto();
    
}
