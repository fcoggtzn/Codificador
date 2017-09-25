/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nomina.entidad.Configuracion;

/**
 *
 * @author ovante
 */
@Stateless
public class ConfiguracionFacade extends AbstractFacade<Configuracion> implements ConfiguracionFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiguracionFacade() {
        super(Configuracion.class);
    }
    
    @Override
    public Configuracion getConfiguracion(String login, String pass) {
        Query q = (Query) em.createQuery("SELECT c FROM Configuracion c WHERE c.login =:login and c.password=:pass");
        q.setParameter("login", login);
        q.setParameter("pass", pass);
        q.getResultList();
        if (q.getResultList().isEmpty()) {
            return new Configuracion();
        } else {
            return (Configuracion) q.getResultList().get(0);
        }
    }
    
}
