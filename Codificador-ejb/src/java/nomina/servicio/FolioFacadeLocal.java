/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Local;
import nomina.entidad.Empresa;
import nomina.entidad.Folio;

/**
 *
 * @author ovante
 */
@Local
public interface FolioFacadeLocal {

    void create(Folio folio);

    void edit(Folio folio);

    void remove(Folio folio);

    Folio find(Object id);

    List<Folio> findAll();

    List<Folio> findRange(int[] range);

    int count();

    public Folio getFolioEmpresa(Empresa empresa);

    public void folioInc(Folio folio);
    
}
