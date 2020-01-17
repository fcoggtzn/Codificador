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
import javax.persistence.Query;
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
    public Folio getFolioEmpresa(Empresa empresa,String tipo){
        Folio folio= new Folio();
        Query querySql = em.createQuery("Select f from Folio f where f.activo=TRUE and f.tipoComprobante =:tipo and f.empresa.idempresa=:empresa");
      //  Query querySql = em.createQuery("Select f from Folio f where f.activo=TRUE and  f.empresa.idempresa=:empresa");
          
      querySql.setParameter("empresa", empresa.getIdempresa());
      querySql.setParameter("tipo", tipo);

        List<Folio> query = querySql.setMaxResults(1).getResultList();
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
