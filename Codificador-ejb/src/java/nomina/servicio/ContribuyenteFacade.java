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
import nomina.entidad.Contribuyente;

/**
 *
 * @author ovante
 */
@Stateless
public class ContribuyenteFacade extends AbstractFacade<Contribuyente> implements ContribuyenteFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContribuyenteFacade() {
        super(Contribuyente.class);
    }
    
    @Override
    public List<Contribuyente> findcontribuyentesByRFC(String rfc){
        List<Contribuyente> query;
        query = em.createQuery("Select c from Contribuyente c where c.rfc LIKE :rfc").setParameter("rfc", "%" + rfc + "%").getResultList();
        return query;
    }
    
    @Override
    public Contribuyente getContribuyente(String rfc){
        Contribuyente contr= new Contribuyente();
        List<Contribuyente> query = em.createQuery("Select c from Contribuyente c where c.rfc =:rfc").setParameter("rfc", rfc).setMaxResults(1).getResultList();
        if (!query.isEmpty()){
            contr=query.get(0);
        }
        return contr;
    }
    
}
