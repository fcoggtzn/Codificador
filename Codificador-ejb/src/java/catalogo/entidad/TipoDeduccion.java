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
@Table(name = "tipo_deduccion", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDeduccion.findAll", query = "SELECT t FROM TipoDeduccion t")
    , @NamedQuery(name = "TipoDeduccion.findById", query = "SELECT t FROM TipoDeduccion t WHERE t.id = :id")
    , @NamedQuery(name = "TipoDeduccion.findByTipoDeduccion", query = "SELECT t FROM TipoDeduccion t WHERE t.tipoDeduccion = :tipoDeduccion")
    , @NamedQuery(name = "TipoDeduccion.findByDescripcion", query = "SELECT t FROM TipoDeduccion t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "TipoDeduccion.findByFechaInicio", query = "SELECT t FROM TipoDeduccion t WHERE t.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "TipoDeduccion.findByFechaFin", query = "SELECT t FROM TipoDeduccion t WHERE t.fechaFin = :fechaFin")})
public class TipoDeduccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "tipo_deduccion")
    private String tipoDeduccion;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "fecha_inicio")
    private String fechaInicio;
    @Size(max = 255)
    @Column(name = "fecha_fin")
    private String fechaFin;

    public TipoDeduccion() {
    }

    public TipoDeduccion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDeduccion() {
        return tipoDeduccion;
    }

    public void setTipoDeduccion(String tipoDeduccion) {
        this.tipoDeduccion = tipoDeduccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
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
        if (!(object instanceof TipoDeduccion)) {
            return false;
        }
        TipoDeduccion other = (TipoDeduccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.TipoDeduccion[ id=" + id + " ]";
    }
    
}
