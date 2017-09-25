/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Local;
import nomina.entidad.Contribuyente;

/**
 *
 * @author ovante
 */
@Local
public interface ContribuyenteFacadeLocal {

    void create(Contribuyente contribuyente);

    void edit(Contribuyente contribuyente);

    void remove(Contribuyente contribuyente);

    Contribuyente find(Object id);

    List<Contribuyente> findAll();

    List<Contribuyente> findRange(int[] range);

    int count();

    public List<Contribuyente> findcontribuyentesByRFC(String rfc);

    public Contribuyente getContribuyente(String rfc);
    
}
