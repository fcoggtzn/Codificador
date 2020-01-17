/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import servicio.entidad.Operador;

/**
 *
 * @author ovante
 */
@Stateless
public class OperadorFacade extends AbstractFacade<Operador> implements OperadorFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperadorFacade() {
        super(Operador.class);
    }

    @Override
    public Operador findOperador(String value) {
        List<Operador> operadores = this.findOperadors(value);
        Operador operador;
        if (operadores.size() > 0 ){
            operador = operadores.get(0);
        }
        else
        {operador = null;}
        return operador;
    }
    
     @Override
    public List<Operador> findOperadors(String value) {
        List<Operador> operadores = em.createQuery("Select c from Operador c where c.alias LIKE :alias or c.nombre like :alias").setParameter("alias", "%" + value + "%").getResultList();
       
        return operadores;
    }
    
}
