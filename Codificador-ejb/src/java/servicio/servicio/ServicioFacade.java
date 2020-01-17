/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import servicio.entidad.Servicio;

/**
 *
 * @author ovante
 */
@Stateless
public class ServicioFacade extends AbstractFacade<Servicio> implements ServicioFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServicioFacade() {
        super(Servicio.class);
    }
    
    @Override
    public List<Servicio> getRangoFecha(Date fechaInicio, Date fechaFin){
        Query query = em.createQuery("Select s from Servicio s where s.fecha >= :fechaInicio and s.fecha<= :fechaFin");
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin",fechaFin);
        return query.getResultList();
        
    }
    
}
