/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nomina.entidad.Empresa;
import nomina.entidad.Folio;

/**
 *
 * @author ovante
 */
@Stateless
public class FolioFacade extends AbstractFacade<Folio> implements FolioFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FolioFacade() {
        super(Folio.class);
    }
    
    @Override
    public Folio getFolioEmpresa(Empresa empresa){
        Folio folio= new Folio();
        List<Folio> query = em.createQuery("Select f from Folio f where f.activo=TRUE and f.empresa.idempresa=:empresa").setParameter("empresa", empresa.getIdempresa()).setMaxResults(1).getResultList();
        if (!query.isEmpty()){
            folio=query.get(0);
        }
        return folio;
    }
    
    @Override
    public void folioInc(Folio folio){
        folio.setFolio(folio.getFolio().add(BigInteger.ONE));
        em.merge(folio);
        
    }
    
}
