/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.Impuesto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ovante
 */
@Stateless
public class ImpuestoFacade extends AbstractFacade<Impuesto> implements ImpuestoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImpuestoFacade() {
        super(Impuesto.class);
    }
    
    @Override
     public List<Impuesto> findImpuestos(String desc){
        List<Impuesto> query;
        Query setParameter = em.createQuery("Select i from Impuesto i where i.codigo LIKE :desc or i.descripcion like :desc ").setParameter("desc", "%" + desc + "%");
        setParameter.setMaxResults(20);
        return setParameter.getResultList();
    }
     @Override
     public Impuesto findImpuesto(String desc){
        List<Impuesto> findImpuestos = findImpuestos(desc);
        
        if (findImpuestos.size() == 1 ){
            return findImpuestos.get(0);
        }
        return null;
    }
     
}
