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
import nomina.entidad.Empresa;

/**
 *
 * @author ovante
 */
@Stateless
public class EmpresaFacade extends AbstractFacade<Empresa> implements EmpresaFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaFacade() {
        super(Empresa.class);
    }
    
    @Override
    public List<Empresa> findEmpresaByRFC(String rfc){
        List<Empresa> query;
        query = em.createQuery("Select e from Empresa e where e.contribuyente.rfc LIKE :rfc").setParameter("rfc", "%" + rfc + "%").getResultList();
        return query;
    }
    
    @Override
    public Empresa getEmpresa(String rfc){
        Empresa emp= new Empresa();
        List<Empresa> query = em.createQuery("Select e from Empresa e where e.contribuyente.rfc =:rfc").setParameter("rfc", rfc).setMaxResults(1).getResultList();
        if (!query.isEmpty()){
            emp=query.get(0);
        }
        return emp;
    }
    
}
