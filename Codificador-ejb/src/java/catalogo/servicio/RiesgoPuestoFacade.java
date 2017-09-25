/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.RiesgoPuesto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ovante
 */
@Stateless
public class RiesgoPuestoFacade extends AbstractFacade<RiesgoPuesto> implements RiesgoPuestoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RiesgoPuestoFacade() {
        super(RiesgoPuesto.class);
    }
    
    @Override
    public RiesgoPuesto RiesgoPuestoByCve(String rp){
        Query q=(Query) em.createQuery("SELECT r FROM RiesgoPuesto r WHERE r.riesgoPuesto =:rp");
        q.setParameter("rp", rp);
        return (RiesgoPuesto) q.getResultList().get(0);
    }
    
    @Override
    public RiesgoPuesto RiesgoPuestoByDesc(String desc){
        Query q=(Query) em.createQuery("SELECT r FROM RiesgoPuesto r WHERE r.descripcion =:desc");
        q.setParameter("desc", desc);
        return (RiesgoPuesto) q.getResultList().get(0);
    }
    
    @Override
    public RiesgoPuesto primerRiesgoPuesto(){
        Query q=(Query) em.createQuery("SELECT r FROM RiesgoPuesto r");
        return (RiesgoPuesto) q.getResultList().get(0);
    }
}
