/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import nomina.entidad.Archivos;
import nomina.entidad.ComprobanteL;
import nomina.entidad.Contribuyente;
import nomina.entidad.Empleado;
import nomina.entidad.Empresa;

/**
 *
 * @author ovante
 */
@Local
public interface ComprobanteLFacadeLocal {

    void create(ComprobanteL comprobante);

    void edit(ComprobanteL comprobante);

    void remove(ComprobanteL comprobante);

    ComprobanteL find(Object id);

    List<ComprobanteL> findAll();

    List<ComprobanteL> findRange(int[] range);

    int count();

    public ComprobanteL comprobanteBySFE(String serie, String folio, String rfc);

    public List<ComprobanteL> findComprobanteEmpleadoEmpresa(Empleado empleado, Empresa empresa);

    public List<ComprobanteL> findComprobanteEmpresaContribuyente(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente);

    public List<ComprobanteL> findComprobanteEmpresaContribuyenteConSaldo(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente);

    public void crearPago(ComprobanteL entity);

    public List<ComprobanteL> findComprobanteEmpresaContribuyenteSinSaldo(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente);

    public ComprobanteL findByUUID(String UUID);

    public Archivos findXML(ComprobanteL comprobanteX);

    public Archivos findPDF(ComprobanteL comprobanteX);

    public List<ComprobanteL> findComprobanteEmpresaContribuyente(Empresa empresa, Date fechaInicio, Date fechaFin, Contribuyente contribuyente, String tipo);

    public void bonifica(nomina.entidad.Archivos findXML);
    
}
