/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "servicio_translado.servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s")
    , @NamedQuery(name = "Servicio.findByIdservicio", query = "SELECT s FROM Servicio s WHERE s.idservicio = :idservicio")
    , @NamedQuery(name = "Servicio.findByFolio", query = "SELECT s FROM Servicio s WHERE s.folio = :folio")
    , @NamedQuery(name = "Servicio.findByNotaDeArrastre", query = "SELECT s FROM Servicio s WHERE s.notaDeArrastre = :notaDeArrastre")
    , @NamedQuery(name = "Servicio.findByFecha", query = "SELECT s FROM Servicio s WHERE s.fecha = :fecha")
    , @NamedQuery(name = "Servicio.findByTransladode", query = "SELECT s FROM Servicio s WHERE s.transladode = :transladode")
    , @NamedQuery(name = "Servicio.findByTransladoa", query = "SELECT s FROM Servicio s WHERE s.transladoa = :transladoa")
    , @NamedQuery(name = "Servicio.findByVehiculo", query = "SELECT s FROM Servicio s WHERE s.vehiculo = :vehiculo")
    , @NamedQuery(name = "Servicio.findByColor", query = "SELECT s FROM Servicio s WHERE s.color = :color")
    , @NamedQuery(name = "Servicio.findByPlacas", query = "SELECT s FROM Servicio s WHERE s.placas = :placas")
    , @NamedQuery(name = "Servicio.findByClienteRfc", query = "SELECT s FROM Servicio s WHERE s.clienteRfc = :clienteRfc")
    , @NamedQuery(name = "Servicio.findByTotalAPagar", query = "SELECT s FROM Servicio s WHERE s.totalAPagar = :totalAPagar")
    , @NamedQuery(name = "Servicio.findByEstatus", query = "SELECT s FROM Servicio s WHERE s.estatus = :estatus")
    , @NamedQuery(name = "Servicio.findByFormaDePago", query = "SELECT s FROM Servicio s WHERE s.formaDePago = :formaDePago")
    , @NamedQuery(name = "Servicio.findByEsfactura", query = "SELECT s FROM Servicio s WHERE s.esfactura = :esfactura")
    , @NamedQuery(name = "Servicio.findByIdContribuyente", query = "SELECT s FROM Servicio s WHERE s.idContribuyente = :idContribuyente")})
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idservicio")
    private Integer idservicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "folio")
    private String folio;
    @Size(max = 45)
    @Column(name = "nota_de_arrastre")
    private String notaDeArrastre;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 45)
    @Column(name = "transladode")
    private String transladode;
    @Size(max = 45)
    @Column(name = "transladoa")
    private String transladoa;
    @Size(max = 45)
    @Column(name = "vehiculo")
    private String vehiculo;
    @Size(max = 45)
    @Column(name = "color")
    private String color;
    @Size(max = 45)
    @Column(name = "placas")
    private String placas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cliente_rfc")
    private String clienteRfc;
    @Column(name = "total_a_pagar")
    private Double totalAPagar;
    @Column(name = "estatus")
    private Integer estatus;
    @Column(name = "forma_de_pago")
    private Integer formaDePago;
    @Column(name = "esfactura")
    private Integer esfactura;
    @Lob
    @Size(max = 65535)
    @Column(name = "notas")
    private String notas;
    @Column(name = "id_contribuyente")
    private Integer idContribuyente;
    @JoinColumn(name = "id_operador", referencedColumnName = "id_operador")
    @ManyToOne
    private Operador idOperador;

    public Servicio() {
    }

    public Servicio(Integer idservicio) {
        this.idservicio = idservicio;
    }

    public Servicio(Integer idservicio, String folio, String clienteRfc) {
        this.idservicio = idservicio;
        this.folio = folio;
        this.clienteRfc = clienteRfc;
    }

    public Integer getIdservicio() {
        return idservicio;
    }

    public void setIdservicio(Integer idservicio) {
        this.idservicio = idservicio;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNotaDeArrastre() {
        return notaDeArrastre;
    }

    public void setNotaDeArrastre(String notaDeArrastre) {
        this.notaDeArrastre = notaDeArrastre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTransladode() {
        return transladode;
    }

    public void setTransladode(String transladode) {
        this.transladode = transladode;
    }

    public String getTransladoa() {
        return transladoa;
    }

    public void setTransladoa(String transladoa) {
        this.transladoa = transladoa;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public String getClienteRfc() {
        return clienteRfc;
    }

    public void setClienteRfc(String clienteRfc) {
        this.clienteRfc = clienteRfc;
    }

    public Double getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(Double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(Integer formaDePago) {
        this.formaDePago = formaDePago;
    }

    public Integer getEsfactura() {
        return esfactura;
    }

    public void setEsfactura(Integer esfactura) {
        this.esfactura = esfactura;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getIdContribuyente() {
        return idContribuyente;
    }

    public void setIdContribuyente(Integer idContribuyente) {
        this.idContribuyente = idContribuyente;
    }

    public Operador getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Operador idOperador) {
        this.idOperador = idOperador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idservicio != null ? idservicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.idservicio == null && other.idservicio != null) || (this.idservicio != null && !this.idservicio.equals(other.idservicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.Servicio[ idservicio=" + idservicio + " ]";
    }
    
}
