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
@Table(name = "tipo_otro_pago", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoOtroPago.findAll", query = "SELECT t FROM TipoOtroPago t")
    , @NamedQuery(name = "TipoOtroPago.findById", query = "SELECT t FROM TipoOtroPago t WHERE t.id = :id")
    , @NamedQuery(name = "TipoOtroPago.findByTipoOtroPago", query = "SELECT t FROM TipoOtroPago t WHERE t.tipoOtroPago = :tipoOtroPago")
    , @NamedQuery(name = "TipoOtroPago.findByDescripcion", query = "SELECT t FROM TipoOtroPago t WHERE t.descripcion = :descripcion")})
public class TipoOtroPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "tipo_otro_pago")
    private String tipoOtroPago;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    public TipoOtroPago() {
    }

    public TipoOtroPago(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoOtroPago() {
        return tipoOtroPago;
    }

    public void setTipoOtroPago(String tipoOtroPago) {
        this.tipoOtroPago = tipoOtroPago;
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
        if (!(object instanceof TipoOtroPago)) {
            return false;
        }
        TipoOtroPago other = (TipoOtroPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.TipoOtroPago[ id=" + id + " ]";
    }
    
}
