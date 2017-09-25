/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "comprobante_impuesto", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComprobanteImpuesto.findAll", query = "SELECT c FROM ComprobanteImpuesto c")
    , @NamedQuery(name = "ComprobanteImpuesto.findByIdcomprobanteImpuesto", query = "SELECT c FROM ComprobanteImpuesto c WHERE c.idcomprobanteImpuesto = :idcomprobanteImpuesto")
    , @NamedQuery(name = "ComprobanteImpuesto.findByTipoImpuesto", query = "SELECT c FROM ComprobanteImpuesto c WHERE c.tipoImpuesto = :tipoImpuesto")
    , @NamedQuery(name = "ComprobanteImpuesto.findByCantidad", query = "SELECT c FROM ComprobanteImpuesto c WHERE c.cantidad = :cantidad")})
public class ComprobanteImpuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomprobante_impuesto")
    private Integer idcomprobanteImpuesto;
    @Column(name = "tipo_impuesto")
    private Integer tipoImpuesto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @JoinColumn(name = "comprobante_id_comprobante", referencedColumnName = "id_comprobante")
    @ManyToOne(optional = false)
    private Comprobante comprobante;

    public ComprobanteImpuesto() {
    }

    public ComprobanteImpuesto(Integer idcomprobanteImpuesto) {
        this.idcomprobanteImpuesto = idcomprobanteImpuesto;
    }

    public Integer getIdcomprobanteImpuesto() {
        return idcomprobanteImpuesto;
    }

    public void setIdcomprobanteImpuesto(Integer idcomprobanteImpuesto) {
        this.idcomprobanteImpuesto = idcomprobanteImpuesto;
    }

    public Integer getTipoImpuesto() {
        return tipoImpuesto;
    }

    public void setTipoImpuesto(Integer tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomprobanteImpuesto != null ? idcomprobanteImpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprobanteImpuesto)) {
            return false;
        }
        ComprobanteImpuesto other = (ComprobanteImpuesto) object;
        if ((this.idcomprobanteImpuesto == null && other.idcomprobanteImpuesto != null) || (this.idcomprobanteImpuesto != null && !this.idcomprobanteImpuesto.equals(other.idcomprobanteImpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.ComprobanteImpuesto[ idcomprobanteImpuesto=" + idcomprobanteImpuesto + " ]";
    }
    
}
