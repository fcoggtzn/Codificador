/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factura.servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import factura.entidad.ImpuestoP;
import nomina.servicio.AbstractFacade;

/**
 *
 * @author ovante
 */
@Stateless
public class ImpuestoPFacade extends AbstractFacade<ImpuestoP> implements ImpuestoPFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImpuestoPFacade() {
        super(ImpuestoP.class);
    }
    
}
