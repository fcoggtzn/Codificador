/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.servicio;

import catalogo.entidad.MetodoPago;
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
public class MetodoPagoFacade extends AbstractFacade<MetodoPago> implements MetodoPagoFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetodoPagoFacade() {
        super(MetodoPago.class);
    }
    
      
    @Override
     public MetodoPago findbyID(String busca){
        Query query = em.createQuery("SELECT m FROM MetodoPago m WHERE m.metodoPago "
                + "= :busca ");
        query.setParameter("busca",busca);
        MetodoPago retorno = null;
        try{
            retorno = (MetodoPago) query.getSingleResult();
        } catch (Exception e){
            System.out.println("Sin  Metodo Pago");
        }
        return retorno;
    }
    
      @Override
     public List<MetodoPago> findCombo(String busca){
        Query query = em.createQuery("SELECT m FROM MetodoPago m WHERE  m.descripcion "
                + "like :busca or m.metodoPago like :busca ");
        query.setParameter("busca","%"+busca+"%");
        query.setMaxResults(10);
        List<MetodoPago> retorno = null;
        try{
            retorno =  query.getResultList();
        } catch (Exception e){
            System.out.println("Sin lista de Metodos de Pago");
        }
        return retorno;
    }
    
}
