/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.RegimenFiscal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface RegimenFiscalFacadeLocal {

    void create(RegimenFiscal regimenFiscal);

    void edit(RegimenFiscal regimenFiscal);

    void remove(RegimenFiscal regimenFiscal);

    RegimenFiscal find(Object id);

    List<RegimenFiscal> findAll();

    List<RegimenFiscal> findRange(int[] range);

    int count();

    public RegimenFiscal findbyID(String busca);
    
}
