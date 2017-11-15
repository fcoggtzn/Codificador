/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.ProdServ;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ovante
 */
@Local
public interface ProdServFacadeLocal {

    void create(ProdServ prodServ);

    void edit(ProdServ prodServ);

    void remove(ProdServ prodServ);

    ProdServ find(Object id);

    List<ProdServ> findAll();

    List<ProdServ> findRange(int[] range);

    int count();

    public List<ProdServ> findProdServ(String desc);

    public ProdServ findProdServID(String desc);
    
}
