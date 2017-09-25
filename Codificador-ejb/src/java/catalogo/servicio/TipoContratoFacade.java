/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.TipoContrato;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ovante
 */
@Stateless
public class TipoContratoFacade extends AbstractFacade<TipoContrato> implements TipoContratoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoContratoFacade() {
        super(TipoContrato.class);
    }
    
    @Override
    public TipoContrato getTipoContratoByTC(String TipoContrato){
        Query q=(Query) em.createQuery("SELECT t FROM TipoContrato t WHERE t.tipoContrato =:tc");
        q.setParameter("tc", TipoContrato);
        return (TipoContrato) q.getResultList().get(0);
    }
    
    @Override
    public TipoContrato getTipoContratoByDesc(String desc){
        Query q=(Query) em.createQuery("SELECT t FROM TipoContrato t WHERE t.descripcion =:tc");
        q.setParameter("tc", desc);
        return (TipoContrato) q.getResultList().get(0);
    }
    
    @Override
    public TipoContrato getPrimerTipoContrato(){
        Query q=(Query) em.createQuery("SELECT  t FROM TipoContrato t");
        return (TipoContrato) q.getResultList().get(0);
    }
    
}
