/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.Unidad;
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
public class UnidadFacade extends AbstractFacade<Unidad> implements UnidadFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnidadFacade() {
        super(Unidad.class);
    }
    
    @Override
    public List<Unidad> findUnidades(String desc){
        List<Unidad> query;
        Query setParameter = em.createQuery("Select u from Unidad u where u.descripcion LIKE :desc or u.claveUnidad like :desc ").setParameter("desc", "%" + desc + "%");
        setParameter.setMaxResults(20);
        return setParameter.getResultList();
    }
    
    public Unidad findUnidadID(String desc){
        List<Unidad> query;
        Query setParameter = em.createQuery("Select u from Unidad u where u.claveUnidad = :desc ").setParameter("desc",  desc );
        setParameter.setMaxResults(1);
        List resultList = setParameter.getResultList();
        if (resultList.size() ==1 ){
        return (Unidad) setParameter.getResultList().get(0);
        }
        return null;
    }
    
}
