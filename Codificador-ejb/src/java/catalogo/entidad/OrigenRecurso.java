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
@Table(name = "origen_recurso", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrigenRecurso.findAll", query = "SELECT o FROM OrigenRecurso o")
    , @NamedQuery(name = "OrigenRecurso.findById", query = "SELECT o FROM OrigenRecurso o WHERE o.id = :id")
    , @NamedQuery(name = "OrigenRecurso.findByOrigen", query = "SELECT o FROM OrigenRecurso o WHERE o.origen = :origen")
    , @NamedQuery(name = "OrigenRecurso.findByDescripcion", query = "SELECT o FROM OrigenRecurso o WHERE o.descripcion = :descripcion")})
public class OrigenRecurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "origen")
    private String origen;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    public OrigenRecurso() {
    }

    public OrigenRecurso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
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
        if (!(object instanceof OrigenRecurso)) {
            return false;
        }
        OrigenRecurso other = (OrigenRecurso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.OrigenRecurso[ id=" + id + " ]";
    }
    
}
