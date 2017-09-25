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
import nomina.entidad.Contribuyente;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;
import nomina.entidad.EmpresaContribuyente;

/**
 *
 * @author ovante
 */
@Stateless
public class EmpleadoFacade extends AbstractFacade<Empleado> implements EmpleadoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoFacade() {
        super(Empleado.class);
    }
    
    @Override
    public List<Empleado> findEmpleadoEmpresa(String nombre, Empresa empresa){
        List<Empleado> lista;
        
        //Query query = em.createNativeQuery("SELECT cont.*  FROM empresa em join empresa_contribuyente emc on (em.idempresa = emc.empresa_idempresa) join contribuyente cont on (emc.idempresa_contribuyente and cont.id_contribuyente)  where em.idempresa = idempresa and cont.nombre like :nombre " , Contribuyente.class);
        Query query = em.createQuery(" SELECT ee  FROM Empresa em join em.empresaContribuyenteCollection emc join emc.contribuyente cont join cont.empleadoCollection  ee    where em = :empresa and cont.nombre like :nombre ");
        query.setParameter("empresa", empresa);
        query.setParameter("nombre", "%" + nombre + "%");
        lista = query.getResultList();
        
        /*for (EmpresaContribuyente empCont :empresa.getEmpresaContribuyenteCollection()){
        if (empCont.getContribuyente().getNombre().indexOf(nombre) > 0){
        Object[] toArray = empCont.getContribuyente().getEmpleadoCollection();
        return (Empleado) toArray[0];
        }
        }*/
        //query = em.createQuery("Select e from Empleado e  joind where e.contribuyente.nombre LIKE :nombre and empresa").setParameter("nombre", "%" + nombre + "%").getResultList();
        
        
        return lista;
    }
    
    @Override
    public Empleado getEmpleado(String nombre){
        Empleado emp= new Empleado();
        List<Empleado> query = em.createQuery("Select e from Empleado e where e.contribuyente.nombre =:nombre").setParameter("nombre", nombre).setMaxResults(1).getResultList();
        if (!query.isEmpty()){
            emp=query.get(0);
        }
        return emp;
    }
    
    
}
