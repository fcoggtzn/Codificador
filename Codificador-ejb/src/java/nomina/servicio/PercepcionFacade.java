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
import nomina.entidad.Percepcion;

/**
 *
 * @author ovante
 */
@Stateless
public class PercepcionFacade extends AbstractFacade<Percepcion> implements PercepcionFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PercepcionFacade() {
        super(Percepcion.class);
    }
    
    @Override
    public Percepcion getPercepcion(String concepto){
        Percepcion per=new Percepcion();
        List<Percepcion> query = em.createQuery("Select p from Percepcion p where p.concepto =:concepto").setParameter("concepto", concepto).setMaxResults(1).getResultList();
        if (!query.isEmpty()){
            per=query.get(0);
        }
        return per;
    }
    
    @Override
    public List<Percepcion> findPercepcionEmpleado(Integer empleado){
        List<Percepcion> query;
        query = em.createQuery("Select p from Percepcion p where p.deduccionPercepcionCollection.empleado.numEmpleado =:empleado").setParameter("empleado",empleado).getResultList();
        return query;
    }
    
}
