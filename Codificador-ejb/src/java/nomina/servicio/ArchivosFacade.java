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
import nomina.entidad.Archivos;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;

/**
 *
 * @author ovante
 */
@Stateless
public class ArchivosFacade extends AbstractFacade<Archivos> implements ArchivosFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivosFacade() {
        super(Archivos.class);
    }
    
    @Override
     public List<Archivos> findArchivoEmpleadoEmpresa(Empleado empleado, Empresa empresa){
        List<Archivos> query;
        query = em.createQuery("Select a from Archivos a where a.comprobanteL.contribuyente.idContribuyente=:idEmpresa and a.comprobanteL.contribuyente1.idContribuyente=:idEmpleado").setParameter("idEmpresa", empresa.getContribuyente().getIdContribuyente()).setParameter("idEmpleado", empleado.getContribuyente().getIdContribuyente()).getResultList();
        return query;
    }
    
}
