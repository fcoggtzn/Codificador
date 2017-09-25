/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.TipoRegimen;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface TipoRegimenFacadeLocal {

    void create(TipoRegimen tipoRegimen);

    void edit(TipoRegimen tipoRegimen);

    void remove(TipoRegimen tipoRegimen);

    TipoRegimen find(Object id);

    List<TipoRegimen> findAll();

    List<TipoRegimen> findRange(int[] range);

    int count();

    public TipoRegimen getTipoRegimenByRF(String rf);

    public TipoRegimen getTipoRegimenByDescripcion(String des);

    public TipoRegimen getPrimerTipoRegimen();
    
}
