/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.Estado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ovante
 */
@Stateless
public class EstadoFacade extends AbstractFacade<Estado> implements EstadoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoFacade() {
        super(Estado.class);
    }
    
    @Override
    public Estado getEstadoByCve(String clave){
        Query q=(Query) em.createQuery("SELECT e FROM Estado e WHERE e.codigo =:cve");
        q.setParameter("cve", clave);
        return (Estado) q.getResultList().get(0);
    }
    
    @Override
    public Estado getEstadoByEstado(String edo){
        Query q=(Query) em.createQuery("SELECT e FROM Estado e WHERE e.estado =:edo");
        q.setParameter("edo", edo);
        return (Estado) q.getResultList().get(0);
    }
    
    @Override
    public Estado getPrimerEstado(){
        Query q=(Query) em.createQuery("SELECT e FROM Estado e");
        return (Estado) q.getResultList().get(0);
    }
    
}
