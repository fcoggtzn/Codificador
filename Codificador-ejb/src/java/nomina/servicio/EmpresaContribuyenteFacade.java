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
import nomina.entidad.EmpresaContribuyente;

/**
 *
 * @author ovante
 */
@Stateless
public class EmpresaContribuyenteFacade extends AbstractFacade<EmpresaContribuyente> implements EmpresaContribuyenteFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaContribuyenteFacade() {
        super(EmpresaContribuyente.class);
    }
    
    @Override
    public List<EmpresaContribuyente> findEmpleadoEmpresaa(String nombre, String empresa){
        List<EmpresaContribuyente> query;
        query = em.createQuery("Select e from EmpresaContribuyente e where e.contribuyente.nombre LIKE :nombre and e.empresa.empresa =:empresa").setParameter("nombre", "%" + nombre + "%").setParameter("empresa", empresa).getResultList();
        return query;
    }
    
    @Override
    public EmpresaContribuyente getEmpleado(String nombre){
        EmpresaContribuyente emp= new EmpresaContribuyente();
        List<EmpresaContribuyente> query = em.createQuery("Select e from EmpresaContribuyente e where e.contribuyente.nombre =:nombre").setParameter("nombre", nombre).setMaxResults(1).getResultList();
        if (!query.isEmpty()){
            emp=query.get(0);
        }
        return emp;
    }
    
}
