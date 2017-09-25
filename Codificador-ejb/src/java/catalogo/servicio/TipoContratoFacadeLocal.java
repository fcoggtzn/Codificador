/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.TipoContrato;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface TipoContratoFacadeLocal {

    void create(TipoContrato tipoContrato);

    void edit(TipoContrato tipoContrato);

    void remove(TipoContrato tipoContrato);

    TipoContrato find(Object id);

    List<TipoContrato> findAll();

    List<TipoContrato> findRange(int[] range);

    int count();

    public TipoContrato getTipoContratoByTC(String TipoContrato);

    public TipoContrato getTipoContratoByDesc(String desc);

    public TipoContrato getPrimerTipoContrato();
    
}
