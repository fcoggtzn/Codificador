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
@Table(name = "riesgo_puesto", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RiesgoPuesto.findAll", query = "SELECT r FROM RiesgoPuesto r")
    , @NamedQuery(name = "RiesgoPuesto.findById", query = "SELECT r FROM RiesgoPuesto r WHERE r.id = :id")
    , @NamedQuery(name = "RiesgoPuesto.findByRiesgoPuesto", query = "SELECT r FROM RiesgoPuesto r WHERE r.riesgoPuesto = :riesgoPuesto")
    , @NamedQuery(name = "RiesgoPuesto.findByDescripcion", query = "SELECT r FROM RiesgoPuesto r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RiesgoPuesto.findByFechaInicio", query = "SELECT r FROM RiesgoPuesto r WHERE r.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "RiesgoPuesto.findByFechaFin", query = "SELECT r FROM RiesgoPuesto r WHERE r.fechaFin = :fechaFin")})
public class RiesgoPuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "riesgo_puesto")
    private String riesgoPuesto;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "fecha_inicio")
    private String fechaInicio;
    @Size(max = 255)
    @Column(name = "fecha_fin")
    private String fechaFin;

    public RiesgoPuesto() {
    }

    public RiesgoPuesto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRiesgoPuesto() {
        return riesgoPuesto;
    }

    public void setRiesgoPuesto(String riesgoPuesto) {
        this.riesgoPuesto = riesgoPuesto;
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
        if (!(object instanceof RiesgoPuesto)) {
            return false;
        }
        RiesgoPuesto other = (RiesgoPuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.RiesgoPuesto[ id=" + id + " ]";
    }
    
}
