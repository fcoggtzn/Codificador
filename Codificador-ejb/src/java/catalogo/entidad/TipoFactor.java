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
@Table(name = "tipo_factor", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoFactor.findAll", query = "SELECT t FROM TipoFactor t")
    , @NamedQuery(name = "TipoFactor.findById", query = "SELECT t FROM TipoFactor t WHERE t.id = :id")
    , @NamedQuery(name = "TipoFactor.findByTipoFactor", query = "SELECT t FROM TipoFactor t WHERE t.tipoFactor = :tipoFactor")})
public class TipoFactor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "tipo_factor")
    private String tipoFactor;

    public TipoFactor() {
    }

    public TipoFactor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoFactor() {
        return tipoFactor;
    }

    public void setTipoFactor(String tipoFactor) {
        this.tipoFactor = tipoFactor;
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
        if (!(object instanceof TipoFactor)) {
            return false;
        }
        TipoFactor other = (TipoFactor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.TipoFactor[ id=" + id + " ]";
    }
    
}
