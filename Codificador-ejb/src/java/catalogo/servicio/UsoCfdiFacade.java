/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.UsoCfdi;
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
public class UsoCfdiFacade extends AbstractFacade<UsoCfdi> implements UsoCfdiFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsoCfdiFacade() {
        super(UsoCfdi.class);
    }

    @Override
    public UsoCfdi findId(String value) {
     
        Query setParameter = em.createQuery("Select u from UsoCfdi u where u.usoCfdi = :desc ").setParameter("desc", value);
        setParameter.setMaxResults(1);
        List resultList = setParameter.getResultList();
        if (resultList.size() == 1) {
            return (UsoCfdi) setParameter.getResultList().get(0);
        }
        return null;
    }

    @Override
    public List<UsoCfdi> findCombo(String value) {
       
        Query query = em.createQuery("Select u from UsoCfdi u where u.usoCfdi like :desc or u.descripcion  like :desc ");
               query.setParameter("desc", "%"+value+"%");
        query.setMaxResults(10);
        List resultList = query.getResultList();
        
        return resultList;
    }

}
