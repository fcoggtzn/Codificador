/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "deduccion", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deduccion.findAll", query = "SELECT d FROM Deduccion d")
    , @NamedQuery(name = "Deduccion.findByIdDeduccion", query = "SELECT d FROM Deduccion d WHERE d.idDeduccion = :idDeduccion")
    , @NamedQuery(name = "Deduccion.findByTipoDeduccion", query = "SELECT d FROM Deduccion d WHERE d.tipoDeduccion = :tipoDeduccion")
    , @NamedQuery(name = "Deduccion.findByTipoClave", query = "SELECT d FROM Deduccion d WHERE d.tipoClave = :tipoClave")
    , @NamedQuery(name = "Deduccion.findByConcepto", query = "SELECT d FROM Deduccion d WHERE d.concepto = :concepto")})
public class Deduccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_deduccion")
    private Integer idDeduccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "tipo_deduccion")
    private String tipoDeduccion;
    @Size(max = 3)
    @Column(name = "tipo_clave")
    private String tipoClave;
    @Size(max = 45)
    @Column(name = "concepto")
    private String concepto;
    @OneToMany(mappedBy = "deduccion")
    private Collection<DeduccionPercepcion> deduccionPercepcionCollection;

    public Deduccion() {
    }

    public Deduccion(Integer idDeduccion) {
        this.idDeduccion = idDeduccion;
    }

    public Deduccion(Integer idDeduccion, String tipoDeduccion) {
        this.idDeduccion = idDeduccion;
        this.tipoDeduccion = tipoDeduccion;
    }

    public Integer getIdDeduccion() {
        return idDeduccion;
    }

    public void setIdDeduccion(Integer idDeduccion) {
        this.idDeduccion = idDeduccion;
    }

    public String getTipoDeduccion() {
        return tipoDeduccion;
    }

    public void setTipoDeduccion(String tipoDeduccion) {
        this.tipoDeduccion = tipoDeduccion;
    }

    public String getTipoClave() {
        return tipoClave;
    }

    public void setTipoClave(String tipoClave) {
        this.tipoClave = tipoClave;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @XmlTransient
    public Collection<DeduccionPercepcion> getDeduccionPercepcionCollection() {
        return deduccionPercepcionCollection;
    }

    public void setDeduccionPercepcionCollection(Collection<DeduccionPercepcion> deduccionPercepcionCollection) {
        this.deduccionPercepcionCollection = deduccionPercepcionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeduccion != null ? idDeduccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deduccion)) {
            return false;
        }
        Deduccion other = (Deduccion) object;
        if ((this.idDeduccion == null && other.idDeduccion != null) || (this.idDeduccion != null && !this.idDeduccion.equals(other.idDeduccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Deduccion[ idDeduccion=" + idDeduccion + " ]";
    }
    
}
