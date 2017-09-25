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
@Table(name = "tipo_incapacidad", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoIncapacidad.findAll", query = "SELECT t FROM TipoIncapacidad t")
    , @NamedQuery(name = "TipoIncapacidad.findById", query = "SELECT t FROM TipoIncapacidad t WHERE t.id = :id")
    , @NamedQuery(name = "TipoIncapacidad.findByTipoIncapacidad", query = "SELECT t FROM TipoIncapacidad t WHERE t.tipoIncapacidad = :tipoIncapacidad")
    , @NamedQuery(name = "TipoIncapacidad.findByDescripcion", query = "SELECT t FROM TipoIncapacidad t WHERE t.descripcion = :descripcion")})
public class TipoIncapacidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "tipo_incapacidad")
    private String tipoIncapacidad;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    public TipoIncapacidad() {
    }

    public TipoIncapacidad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoIncapacidad() {
        return tipoIncapacidad;
    }

    public void setTipoIncapacidad(String tipoIncapacidad) {
        this.tipoIncapacidad = tipoIncapacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof TipoIncapacidad)) {
            return false;
        }
        TipoIncapacidad other = (TipoIncapacidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.TipoIncapacidad[ id=" + id + " ]";
    }
    
}
