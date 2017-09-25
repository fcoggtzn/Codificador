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
@Table(name = "moneda", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Moneda.findAll", query = "SELECT m FROM Moneda m")
    , @NamedQuery(name = "Moneda.findById", query = "SELECT m FROM Moneda m WHERE m.id = :id")
    , @NamedQuery(name = "Moneda.findByMoneda", query = "SELECT m FROM Moneda m WHERE m.moneda = :moneda")
    , @NamedQuery(name = "Moneda.findByDescripcionj", query = "SELECT m FROM Moneda m WHERE m.descripcionj = :descripcionj")
    , @NamedQuery(name = "Moneda.findByDecimales", query = "SELECT m FROM Moneda m WHERE m.decimales = :decimales")
    , @NamedQuery(name = "Moneda.findByVariacion", query = "SELECT m FROM Moneda m WHERE m.variacion = :variacion")})
public class Moneda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "moneda")
    private String moneda;
    @Size(max = 255)
    @Column(name = "descripcionj")
    private String descripcionj;
    @Size(max = 255)
    @Column(name = "decimales")
    private String decimales;
    @Size(max = 255)
    @Column(name = "variacion")
    private String variacion;

    public Moneda() {
    }

    public Moneda(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getDescripcionj() {
        return descripcionj;
    }

    public void setDescripcionj(String descripcionj) {
        this.descripcionj = descripcionj;
    }

    public String getDecimales() {
        return decimales;
    }

    public void setDecimales(String decimales) {
        this.decimales = decimales;
    }

    public String getVariacion() {
        return variacion;
    }

    public void setVariacion(String variacion) {
        this.variacion = variacion;
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
        if (!(object instanceof Moneda)) {
            return false;
        }
        Moneda other = (Moneda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.Moneda[ id=" + id + " ]";
    }
    
}
