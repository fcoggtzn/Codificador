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
@Table(name = "tipo_comprobante", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoComprobante.findAll", query = "SELECT t FROM TipoComprobante t")
    , @NamedQuery(name = "TipoComprobante.findById", query = "SELECT t FROM TipoComprobante t WHERE t.id = :id")
    , @NamedQuery(name = "TipoComprobante.findByTipoComprobante", query = "SELECT t FROM TipoComprobante t WHERE t.tipoComprobante = :tipoComprobante")
    , @NamedQuery(name = "TipoComprobante.findByDescripcion", query = "SELECT t FROM TipoComprobante t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "TipoComprobante.findByValorMaximo", query = "SELECT t FROM TipoComprobante t WHERE t.valorMaximo = :valorMaximo")})
public class TipoComprobante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 254)
    @Column(name = "tipo_comprobante")
    private String tipoComprobante;
    @Size(max = 254)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 254)
    @Column(name = "valor_maximo")
    private String valorMaximo;

    public TipoComprobante() {
    }

    public TipoComprobante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        this.valorMaximo = valorMaximo;
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
        if (!(object instanceof TipoComprobante)) {
            return false;
        }
        TipoComprobante other = (TipoComprobante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.TipoComprobante[ id=" + id + " ]";
    }
    
}
