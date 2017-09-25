/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nomina.entidad.Deduccion;

/**
 *
 * @author ovante
 */
@Stateless
public class DeduccionFacade extends AbstractFacade<Deduccion> implements DeduccionFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeduccionFacade() {
        super(Deduccion.class);
    }
    
    @Override
    public Deduccion getDeduccion(String concepto){
        Deduccion ded=new Deduccion();
        List<Deduccion> query = em.createQuery("Select d from Deduccion d where d.concepto =:concepto").setParameter("concepto", concepto).setMaxResults(1).getResultList();
        if (!query.isEmpty()){
            ded=query.get(0);
        }
        return ded;
    }
    
}
