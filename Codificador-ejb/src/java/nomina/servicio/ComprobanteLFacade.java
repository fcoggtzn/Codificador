/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import ejb.CancelaCfdiEjb;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import nomina.entidad.Archivos;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;
import nomina.entidad.Folio;

/**
 *
 * @author ovante
 */
@Stateless
public class ComprobanteLFacade extends AbstractFacade<ComprobanteL> implements ComprobanteLFacadeLocal {

    @PersistenceContext(unitName = "Codificador-ejbPU")
    private EntityManager em;
    
    
    @EJB
    private FolioFacadeLocal folioFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComprobanteLFacade() {
        super(ComprobanteL.class);
    }
    
    @Override
    public ComprobanteL comprobanteBySFE(String serie, String folio, String rfc){
        Query q=em.createQuery("SELECT c FROM ComprobanteL c WHERE c.serie=:serie and c.folio=:folio and c.contribuyente.rfc=:rfc and c.estatus != -2");
        q.setParameter("serie", serie);
        q.setParameter("folio", folio);
        q.setParameter("rfc", rfc);
      //  q.setParameter("tipo", tipo);
        q.getResultList();
        if (q.getResultList().isEmpty()) {
            return new ComprobanteL();
        } else {
            return (ComprobanteL) q.getResultList().get(0);
        }
    }
    
    @Override
    public List<ComprobanteL> findComprobanteEmpleadoEmpresa(Empleado empleado, Empresa empresa){
        List<ComprobanteL> query;
        query = em.createQuery("Select c from ComprobanteL c where c.contribuyente.idContribuyente=:idEmpresa and c.contribuyente1.idContribuyente=:idEmpleado").setParameter("idEmpresa", empresa.getContribuyente().getIdContribuyente()).setParameter("idEmpleado", empleado.getContribuyente().getIdContribuyente()).getResultList();
        return query;
    }

    @Override
    public List<ComprobanteL> findComprobanteEmpresaContribuyente(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente) {
        Query query = null;
        if (contribuyente != null) {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa and c.contribuyente1=:contribuyente and  c.fecha  between :fechaInicio and  :fechaFin");
            query.setParameter("empresa", empresa.getContribuyente());
            query.setParameter("contribuyente", contribuyente);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        } else {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa and c.fecha  between :fechaInicio and  :fechaFin");
            query.setParameter("empresa", empresa.getContribuyente());

            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        }

        //.setParameter("idEmpresa", empresa.getContribuyente().getIdContribuyente()).setParameter("idEmpleado", empleado.getContribuyente().getIdContribuyente()).getResultList();
        return query.getResultList();
    }

    @Override
    public List<ComprobanteL> findComprobanteEmpresaContribuyenteConSaldo(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente) {
        
 Query query = null;
        if (contribuyente != null) {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa and c.contribuyente1=:contribuyente and c.saldo > 0 and c.tipo = 'I' and  c.fecha  between :fechaInicio and  :fechaFin ");
            query.setParameter("empresa", empresa.getContribuyente());
            query.setParameter("contribuyente", contribuyente);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        } else {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa  and c.saldo > 0  and c.tipo = 'I' and  c.fecha  between :fechaInicio and  :fechaFin");
            query.setParameter("empresa", empresa.getContribuyente());

            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        }

        //.setParameter("idEmpresa", empresa.getContribuyente().getIdContribuyente()).setParameter("idEmpleado", empleado.getContribuyente().getIdContribuyente()).getResultList();
        return query.getResultList();
    }

    /**
     *
     * @param entity
     */
    public void crearPago(ComprobanteL cfdi) {
        Empresa empresaTemp = (Empresa) this.recuperarParametroObject("empresaActual");
        Folio folioEmpresa = folioFacade.getFolioEmpresa(empresaTemp,"P");
        folioFacade.folioInc(folioEmpresa);
        cfdi.setEstatus(1); //siempre aplicados
        cfdi.setSerie(folioEmpresa.getSerie());
        cfdi.setFolio(folioEmpresa.getFolio().toString());
        
        super.create(cfdi); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected Object recuperarParametroObject(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object retorno = session.getAttribute(parametro);
        return retorno;
    }

    @Override
    public List<ComprobanteL> findComprobanteEmpresaContribuyenteSinSaldo(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente) {
 
 Query query = null;
        if (contribuyente != null) {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa and c.contribuyente1=:contribuyente and ((c.saldo = 0 and c.tipo = 'I') or c.tipo='P' ) and  c.fecha  between :fechaInicio and  :fechaFin ");
            query.setParameter("empresa", empresa.getContribuyente());
            query.setParameter("contribuyente", contribuyente);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        } else {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa  and ((c.saldo = 0  and c.tipo = 'I') or c.tipo='P' )  and  c.fecha  between :fechaInicio and  :fechaFin");
            query.setParameter("empresa", empresa.getContribuyente());

            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        }

        //.setParameter("idEmpresa", empresa.getContribuyente().getIdContribuyente()).setParameter("idEmpleado", empleado.getContribuyente().getIdContribuyente()).getResultList();
        return query.getResultList();
    }

    @Override
    public ComprobanteL findByUUID(String UUID) {
        Query q=em.createQuery("SELECT c FROM ComprobanteL c WHERE c.uuid = :uuid");
        q.setParameter("uuid", UUID);        
      
        q.getResultList();
        if (q.getResultList().isEmpty()) {
            return new ComprobanteL();
        } else {
            return (ComprobanteL) q.getResultList().get(0);
        }
    }

    @Override
    public Archivos findXML(ComprobanteL comprobanteX) {
        Archivos regreso = null;
       for(Archivos archivo: comprobanteX.getArchivosCollection())
       {
           if (archivo.getTipo().equals("XML")){
               regreso = archivo;
               break;
           }
       }
      return regreso;
    }

    @Override
    public Archivos findPDF(ComprobanteL comprobanteX) {
        Archivos regreso = null;
       for(Archivos archivo: comprobanteX.getArchivosCollection())
       {
           if (archivo.getTipo().equals("PDF")){
               regreso = archivo;
               break;
           }
       }
      return regreso;
    }

    @Override
    public List<ComprobanteL> findComprobanteEmpresaContribuyente(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente, String tipo) {
       Query query = null;
        if (contribuyente != null) {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa and c.contribuyente1=:contribuyente and c.tipo =:tipo and  c.fecha  between :fechaInicio and  :fechaFin");
            query.setParameter("empresa", empresa.getContribuyente());
            query.setParameter("tipo", tipo);
            query.setParameter("contribuyente", contribuyente);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        } else {
            query = em.createQuery("Select c from ComprobanteL c where c.contribuyente=:empresa and c.tipo =:tipo and c.fecha  between :fechaInicio and  :fechaFin");
            query.setParameter("empresa", empresa.getContribuyente());
            query.setParameter("tipo", tipo);

            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        }

        //.setParameter("idEmpresa", empresa.getContribuyente().getIdContribuyente()).setParameter("idEmpleado", empleado.getContribuyente().getIdContribuyente()).getResultList();
        return query.getResultList();
    }

    @Override
    public void bonifica(nomina.entidad.Archivos findXML) {
        /* tiene problemas con librerias XMLCalendar
         try {
                   JAXBContext jaxbContext = JAXBContext.newInstance(sat.Comprobante.class,sat.Pagos.class);
                   Unmarshaller umarsh = jaxbContext.createUnmarshaller();   
                  
                   ByteArrayInputStream input = new ByteArrayInputStream(findXML.getContenido());
                   sat.Comprobante comprobanteSat = (sat.Comprobante) umarsh.unmarshal(input);
                    System.out.println(comprobanteSat.getComplemento().get(0).getAny().get(0));
                   sat.Pagos pago = ( sat.Pagos)comprobanteSat.getComplemento().get(0).getAny().get(0); 
                   for (sat.Pagos.Pago pg :pago.getPago()){
                       for (sat.Pagos.Pago.DoctoRelacionado documento: pg.getDoctoRelacionado()){
                           ComprobanteL findByUUID = findByUUID(documento.getIdDocumento()); 
                           findByUUID.setSaldo(documento.getImpPagado().doubleValue() + findByUUID.getSaldo().doubleValue());
                           edit(findByUUID);
                       }
                   }

                   
               } catch (JAXBException ex) {
                   Logger.getLogger(CancelaCfdiEjb.class.getName()).log(Level.SEVERE, null, ex);
               }
*/
         int i=0;
        for (String sgm:new String(findXML.getContenido()).split("IdDocumento")){
            if (i++ > 0){
              // lo que es el Documento, System.out.println(sgm.substring(2, 38));
              ComprobanteL findByUUID = findByUUID(sgm.substring(2, 38)); 
               int idPago=sgm.indexOf("ImpPagado=");
                double pago = new Double(sgm.substring(idPago+11,sgm.indexOf("\" ", idPago)));
            
              
               findByUUID.setSaldo(pago + findByUUID.getSaldo().doubleValue());
                           edit(findByUUID);
            }
        }

    }

    
}
