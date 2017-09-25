/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.TipoRegimen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ovante
 */
@Stateless
public class TipoRegimenFacade extends AbstractFacade<TipoRegimen> implements TipoRegimenFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoRegimenFacade() {
        super(TipoRegimen.class);
    }
    
    @Override
    public TipoRegimen getTipoRegimenByRF(String rf){
        Query q=(Query) em.createQuery("SELECT r FROM TipoRegimen r WHERE r.tipoRegimen =:rf");
        q.setParameter("rf", rf);
        return (TipoRegimen) q.getResultList().get(0);
    }
    
    @Override
    public TipoRegimen getTipoRegimenByDescripcion(String des){
        Query q=(Query) em.createQuery("SELECT r FROM TipoRegimen r WHERE r.descripcion =:rf");
        q.setParameter("rf", des);
        return (TipoRegimen) q.getResultList().get(0);
    }
    
    @Override
    public TipoRegimen getPrimerTipoRegimen(){
        Query q=(Query) em.createQuery("SELECT r FROM TipoRegimen r");
        return (TipoRegimen) q.getResultList().get(0);
    }
    
}
