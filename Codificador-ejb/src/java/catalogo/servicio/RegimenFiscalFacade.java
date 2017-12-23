/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.RegimenFiscal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ovante
 */
@Stateless
public class RegimenFiscalFacade extends AbstractFacade<RegimenFiscal> implements RegimenFiscalFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
       @Override
     public RegimenFiscal findbyID(String busca){
        Query query = em.createQuery("SELECT r FROM RegimenFiscal r WHERE r.regimenFiscal "
                + "= :busca ");
        query.setParameter("busca",busca);
        RegimenFiscal retorno = null;
        try{
            retorno = (RegimenFiscal) query.getSingleResult();
        } catch (Exception e){
            System.out.println("Sin  RegimenFiscal");
        }
        return retorno;
    }

    public RegimenFiscalFacade() {
        super(RegimenFiscal.class);
    }
    
}
