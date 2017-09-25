/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.entidad;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "comprobante", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comprobante.findAll", query = "SELECT c FROM Comprobante c")
    , @NamedQuery(name = "Comprobante.findByIdComprobante", query = "SELECT c FROM Comprobante c WHERE c.idComprobante = :idComprobante")
    , @NamedQuery(name = "Comprobante.findBySerie", query = "SELECT c FROM Comprobante c WHERE c.serie = :serie")
    , @NamedQuery(name = "Comprobante.findByFolio", query = "SELECT c FROM Comprobante c WHERE c.folio = :folio")
    , @NamedQuery(name = "Comprobante.findByTipo", query = "SELECT c FROM Comprobante c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Comprobante.findByTotal", query = "SELECT c FROM Comprobante c WHERE c.total = :total")
    , @NamedQuery(name = "Comprobante.findByFecha", query = "SELECT c FROM Comprobante c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Comprobante.findByEstatus", query = "SELECT c FROM Comprobante c WHERE c.estatus = :estatus")
    , @NamedQuery(name = "Comprobante.findBySubtotal", query = "SELECT c FROM Comprobante c WHERE c.subtotal = :subtotal")
    , @NamedQuery(name = "Comprobante.findByImpuesto", query = "SELECT c FROM Comprobante c WHERE c.impuesto = :impuesto")})
public class Comprobante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comprobante")
    private Integer idComprobante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "folio")
    private String folio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "estatus")
    private Integer estatus;
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "impuesto")
    private Double impuesto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comprobante")
    private Collection<ComprobanteImpuesto> comprobanteImpuestoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comprobante")
    private Collection<Archivos> archivosCollection;
    @JoinColumn(name = "emisor", referencedColumnName = "id_contribuyente")
    @OneToOne(optional = false)
    private Contribuyente contribuyente;
    @JoinColumn(name = "receptor", referencedColumnName = "id_contribuyente")
    @ManyToOne(optional = false)
    private Contribuyente contribuyente1;

    public Comprobante() {
    }

    public Comprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public Comprobante(Integer idComprobante, String serie, String folio, String tipo) {
        this.idComprobante = idComprobante;
        this.serie = serie;
        this.folio = folio;
        this.tipo = tipo;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    @XmlTransient
    public Collection<ComprobanteImpuesto> getComprobanteImpuestoCollection() {
        return comprobanteImpuestoCollection;
    }

    public void setComprobanteImpuestoCollection(Collection<ComprobanteImpuesto> comprobanteImpuestoCollection) {
        this.comprobanteImpuestoCollection = comprobanteImpuestoCollection;
    }

    @XmlTransient
    public Collection<Archivos> getArchivosCollection() {
        return archivosCollection;
    }

    public void setArchivosCollection(Collection<Archivos> archivosCollection) {
        this.archivosCollection = archivosCollection;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public Contribuyente getContribuyente1() {
        return contribuyente1;
    }

    public void setContribuyente1(Contribuyente contribuyente1) {
        this.contribuyente1 = contribuyente1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComprobante != null ? idComprobante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comprobante)) {
            return false;
        }
        Comprobante other = (Comprobante) object;
        if ((this.idComprobante == null && other.idComprobante != null) || (this.idComprobante != null && !this.idComprobante.equals(other.idComprobante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Comprobante[ idComprobante=" + idComprobante + " ]";
    }
    
}
