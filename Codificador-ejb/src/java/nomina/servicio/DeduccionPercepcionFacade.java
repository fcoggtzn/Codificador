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
import javax.persistence.Query;
import nomina.entidad.DeduccionPercepcion;
import nomina.entidad.Empleado;

/**
 *
 * @author ovante
 */
@Stateless
public class DeduccionPercepcionFacade extends AbstractFacade<DeduccionPercepcion> implements DeduccionPercepcionFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeduccionPercepcionFacade() {
        super(DeduccionPercepcion.class);
    }

    @Override
    public List<DeduccionPercepcion> findPercepcionesEmpleado(Empleado idEmpleado) {
        List<DeduccionPercepcion> lista;
        Query query = em.createQuery("SELECT d FROM DeduccionPercepcion d where  d.percepcion != NULL and d.empleado = :id");
        query.setParameter("id", idEmpleado);
        lista = query.getResultList();
        return lista;
    }

    @Override
    public List<DeduccionPercepcion> findDeduccionEmpleado(Empleado idEmpleado) {
        List<DeduccionPercepcion> query;
        query = em.createQuery("SELECT d FROM DeduccionPercepcion d where d.empleado = :id  and d.deduccion != null").setParameter("id", idEmpleado).getResultList();
        return query;
    }

    @Override
    public DeduccionPercepcion getDeduccionEmpleado(DeduccionPercepcion dp) {
        DeduccionPercepcion dedPer = new DeduccionPercepcion();
        List<DeduccionPercepcion> query;
        query = em.createQuery("Select d from DeduccionPercepcion d where d.empleado.numEmpleado =:ide and d.deduccion.idDeduccion =:idd").setParameter("ide", dp.getEmpleado().getNumEmpleado()).setParameter("idd", dp.getDeduccion().getIdDeduccion()).setMaxResults(1).getResultList();
        if (!query.isEmpty()) {
            dedPer = query.get(0);
        } else {
            dedPer = null;
        }
        return dedPer;
    }

    @Override
    public DeduccionPercepcion getPercepcionEmpleado(DeduccionPercepcion dp) {
        DeduccionPercepcion dedPer = new DeduccionPercepcion();
        List<DeduccionPercepcion> query = em.createQuery("Select p from DeduccionPercepcion p where p.empleado.numEmpleado =:ide and p.percepcion.idPercepcion =:idp").setParameter("ide", dp.getEmpleado().getNumEmpleado()).setParameter("idp", dp.getPercepcion().getIdPercepcion()).setMaxResults(1).getResultList();
        if (!query.isEmpty()) {
            dedPer = query.get(0);
        } else {
            dedPer = null;
        }
        return dedPer;
    }

}
