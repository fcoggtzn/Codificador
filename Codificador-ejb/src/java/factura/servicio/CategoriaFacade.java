/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factura.servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import factura.entidad.Categoria;
import java.util.List;
import javax.persistence.Query;
import nomina.servicio.AbstractFacade;

/**
 *
 * @author ovante
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> implements CategoriaFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public Categoria findID(String value) {
        Query query;
        query = em.createQuery("Select c from Categoria c where c.idcategoria = :desc"); 
        query.setParameter("desc", new Integer(value));
        return (Categoria) query.getSingleResult();
    }
    
}
