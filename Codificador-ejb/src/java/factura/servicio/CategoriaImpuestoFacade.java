/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factura.servicio;

import factura.entidad.Categoria;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import factura.entidad.CategoriaImpuesto;
import javax.persistence.Query;
import nomina.servicio.AbstractFacade;

/**
 *
 * @author ovante
 */
@Stateless
public class CategoriaImpuestoFacade extends AbstractFacade<CategoriaImpuesto> implements CategoriaImpuestoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaImpuestoFacade() {
        super(CategoriaImpuesto.class);
    }

    @Override
    public void borrarImpuesto(Categoria categoria) {
        Query query = em.createQuery("DELETE FROM CategoriaImpuesto c WHERE c.categoria = :categoria ");
        query.setParameter("categoria", categoria);
        query.executeUpdate();
    }
    
}
