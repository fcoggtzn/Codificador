/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;

/**
 *
 * @author ovante
 */
@Stateless
public class ComprobanteLFacade extends AbstractFacade<ComprobanteL> implements ComprobanteLFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComprobanteLFacade() {
        super(ComprobanteL.class);
    }
    
    @Override
    public ComprobanteL comprobanteBySFE(String serie, String folio, String rfc){
        Query q=em.createQuery("SELECT c FROM ComprobanteL c WHERE c.serie=:serie and c.folio=:folio and c.contribuyente.rfc=:rfc and c.estatus != -2");
        q.setParameter("serie", serie);
        q.setParameter("folio", folio);
        q.setParameter("rfc", rfc);
      //  q.setParameter("tipo", tipo);
        q.getResultList();
        if (q.getResultList().isEmpty()) {
            return new ComprobanteL();
        } else {
            return (ComprobanteL) q.getResultList().get(0);
        }
    }
    
    @Override
    public List<ComprobanteL> findComprobanteEmpleadoEmpresa(Empleado empleado, Empresa empresa){
        List<ComprobanteL> query;
        query = em.createQuery("Select c from ComprobanteL c where c.contribuyente.idContribuyente=:idEmpresa and c.contribuyente1.idContribuyente=:idEmpleado").setParameter("idEmpresa", empresa.getContribuyente().getIdContribuyente()).setParameter("idEmpleado", empleado.getContribuyente().getIdContribuyente()).getResultList();
        return query;
    }

    @Override
    public List<ComprobanteL> findComprobanteEmpresaContribuyente(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente) {
        Query query = null;
        if (contribuyente != null) {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa and c.contribuyente1=:contribuyente and  c.fecha >= :fechaInicio and c.fecha <= :fechaFin");
            query.setParameter("empresa", empresa.getContribuyente());
            query.setParameter("contribuyente", contribuyente);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        } else {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa and c.fecha >= :fechaInicio and c.fecha <= :fechaFin");
            query.setParameter("empresa", empresa.getContribuyente());

            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        }

        //.setParameter("idEmpresa", empresa.getContribuyente().getIdContribuyente()).setParameter("idEmpleado", empleado.getContribuyente().getIdContribuyente()).getResultList();
        return query.getResultList();
    }
    
}
