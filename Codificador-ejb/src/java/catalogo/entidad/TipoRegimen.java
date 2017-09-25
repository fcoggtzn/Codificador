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
@Table(name = "tipo_regimen", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoRegimen.findAll", query = "SELECT t FROM TipoRegimen t")
    , @NamedQuery(name = "TipoRegimen.findById", query = "SELECT t FROM TipoRegimen t WHERE t.id = :id")
    , @NamedQuery(name = "TipoRegimen.findByTipoRegimen", query = "SELECT t FROM TipoRegimen t WHERE t.tipoRegimen = :tipoRegimen")
    , @NamedQuery(name = "TipoRegimen.findByDescripcion", query = "SELECT t FROM TipoRegimen t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "TipoRegimen.findByFechaInicio", query = "SELECT t FROM TipoRegimen t WHERE t.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "TipoRegimen.findByFechaFin", query = "SELECT t FROM TipoRegimen t WHERE t.fechaFin = :fechaFin")})
public class TipoRegimen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "tipo_regimen")
    private String tipoRegimen;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "fecha_inicio")
    private String fechaInicio;
    @Size(max = 255)
    @Column(name = "fecha_fin")
    private String fechaFin;

    public TipoRegimen() {
    }

    public TipoRegimen(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoRegimen() {
        return tipoRegimen;
    }

    public void setTipoRegimen(String tipoRegimen) {
        this.tipoRegimen = tipoRegimen;
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
        if (!(object instanceof TipoRegimen)) {
            return false;
        }
        TipoRegimen other = (TipoRegimen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.TipoRegimen[ id=" + id + " ]";
    }
    
}
