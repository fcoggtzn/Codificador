/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.PeriodicidadPago;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ovante
 */
@Stateless
public class PeriodicidadPagoFacade extends AbstractFacade<PeriodicidadPago> implements PeriodicidadPagoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodicidadPagoFacade() {
        super(PeriodicidadPago.class);
    }
    
    @Override
    public PeriodicidadPago getPeriodicidadByPer(String period){
        Query q=(Query) em.createQuery("SELECT p FROM PeriodicidadPago p WHERE p.periodicidadPago =:per");
        q.setParameter("per", period);
        return (PeriodicidadPago) q.getResultList().get(0);
    }
    
    @Override
    public PeriodicidadPago getPeriodicidadByDesc(String desc){
        Query q=(Query) em.createQuery("SELECT p FROM PeriodicidadPago p WHERE p.descripcion =:desc");
        q.setParameter("desc", desc);
        return (PeriodicidadPago) q.getResultList().get(0);
    }
    
    @Override
    public PeriodicidadPago getPrimerPeriodicidad(){
        Query q=(Query) em.createQuery("SELECT p FROM PeriodicidadPago p");
        return (PeriodicidadPago) q.getResultList().get(0);
    }
    
}
