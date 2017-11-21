/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.FormaPago;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ovante
 */
@Stateless
public class FormaPagoFacade extends AbstractFacade<FormaPago> implements FormaPagoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormaPagoFacade() {
        super(FormaPago.class);
    }
    
    
    @Override
     public FormaPago findbyID(String busca){
        Query query = em.createQuery("SELECT f FROM FormaPago f WHERE f.formaPago "
                + "= :busca ");
        query.setParameter("busca",busca);
        FormaPago retorno = null;
        try{
            retorno = (FormaPago) query.getSingleResult();
        } catch (Exception e){
            System.out.println("Sin forma de pago");
        }
        return retorno;
    }
    
       @Override
     public List<FormaPago> findCombo(String busca){
        Query query = em.createQuery("SELECT f FROM FormaPago f WHERE f.formaPago "
                + "like :busca or f.descripcion like :busca ");
        query.setParameter("busca","%"+busca+"%");
        query.setMaxResults(10);
        List<FormaPago> retorno = null;
        try{
            retorno =  query.getResultList();
        } catch (Exception e){
            System.out.println("Sin lista de pagos");
        }
        return retorno;
    }
    
}
