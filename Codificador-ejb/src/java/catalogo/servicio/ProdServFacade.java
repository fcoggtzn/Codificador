/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.ProdServ;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ovante
 */
@Stateless
public class ProdServFacade extends AbstractFacade<ProdServ> implements ProdServFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProdServFacade() {
        super(ProdServ.class);
    }
    
    @Override
    public List<ProdServ> findProdServ(String desc){
        List<ProdServ> query;
        query = em.createQuery("Select p from ProdServ p where p.descripcion LIKE :desc").setParameter("desc", "%" + desc + "%").getResultList();
        return query;
    }
    
}
