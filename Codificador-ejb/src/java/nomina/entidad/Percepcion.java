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
@Table(name = "percepcion", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Percepcion.findAll", query = "SELECT p FROM Percepcion p")
    , @NamedQuery(name = "Percepcion.findByIdPercepcion", query = "SELECT p FROM Percepcion p WHERE p.idPercepcion = :idPercepcion")
    , @NamedQuery(name = "Percepcion.findByTipoPercepcion", query = "SELECT p FROM Percepcion p WHERE p.tipoPercepcion = :tipoPercepcion")
    , @NamedQuery(name = "Percepcion.findByTipoClave", query = "SELECT p FROM Percepcion p WHERE p.tipoClave = :tipoClave")
    , @NamedQuery(name = "Percepcion.findByConcepto", query = "SELECT p FROM Percepcion p WHERE p.concepto = :concepto")})
public class Percepcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_percepcion")
    private Integer idPercepcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "tipo_percepcion")
    private String tipoPercepcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "tipo_clave")
    private String tipoClave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "concepto")
    private String concepto;
    @OneToMany(mappedBy = "percepcion")
    private Collection<DeduccionPercepcion> deduccionPercepcionCollection;

    public Percepcion() {
    }

    public Percepcion(Integer idPercepcion) {
        this.idPercepcion = idPercepcion;
    }

    public Percepcion(Integer idPercepcion, String tipoPercepcion, String tipoClave, String concepto) {
        this.idPercepcion = idPercepcion;
        this.tipoPercepcion = tipoPercepcion;
        this.tipoClave = tipoClave;
        this.concepto = concepto;
    }

    public Integer getIdPercepcion() {
        return idPercepcion;
    }

    public void setIdPercepcion(Integer idPercepcion) {
        this.idPercepcion = idPercepcion;
    }

    public String getTipoPercepcion() {
        return tipoPercepcion;
    }

    public void setTipoPercepcion(String tipoPercepcion) {
        this.tipoPercepcion = tipoPercepcion;
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
        hash += (idPercepcion != null ? idPercepcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Percepcion)) {
            return false;
        }
        Percepcion other = (Percepcion) object;
        if ((this.idPercepcion == null && other.idPercepcion != null) || (this.idPercepcion != null && !this.idPercepcion.equals(other.idPercepcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Percepcion[ idPercepcion=" + idPercepcion + " ]";
    }
    
}
