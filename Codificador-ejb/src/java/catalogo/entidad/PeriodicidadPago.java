/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "periodicidad_pago", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodicidadPago.findAll", query = "SELECT p FROM PeriodicidadPago p")
    , @NamedQuery(name = "PeriodicidadPago.findById", query = "SELECT p FROM PeriodicidadPago p WHERE p.id = :id")
    , @NamedQuery(name = "PeriodicidadPago.findByPeriodicidadPago", query = "SELECT p FROM PeriodicidadPago p WHERE p.periodicidadPago = :periodicidadPago")
    , @NamedQuery(name = "PeriodicidadPago.findByDescripcion", query = "SELECT p FROM PeriodicidadPago p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "PeriodicidadPago.findByFechaIni", query = "SELECT p FROM PeriodicidadPago p WHERE p.fechaIni = :fechaIni")
    , @NamedQuery(name = "PeriodicidadPago.findByFechaFin", query = "SELECT p FROM PeriodicidadPago p WHERE p.fechaFin = :fechaFin")})
public class PeriodicidadPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "periodicidad_pago")
    private String periodicidadPago;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "fecha_ini")
    private String fechaIni;
    @Size(max = 255)
    @Column(name = "fecha_fin")
    private String fechaFin;

    public PeriodicidadPago() {
    }

    public PeriodicidadPago(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPeriodicidadPago() {
        return periodicidadPago;
    }

    public void setPeriodicidadPago(String periodicidadPago) {
        this.periodicidadPago = periodicidadPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodicidadPago)) {
            return false;
        }
        PeriodicidadPago other = (PeriodicidadPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.PeriodicidadPago[ id=" + id + " ]";
    }
    
}
