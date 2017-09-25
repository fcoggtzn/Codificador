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
@Table(name = "impuesto", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Impuesto.findAll", query = "SELECT i FROM Impuesto i")
    , @NamedQuery(name = "Impuesto.findById", query = "SELECT i FROM Impuesto i WHERE i.id = :id")
    , @NamedQuery(name = "Impuesto.findByCodigo", query = "SELECT i FROM Impuesto i WHERE i.codigo = :codigo")
    , @NamedQuery(name = "Impuesto.findByDescripcion", query = "SELECT i FROM Impuesto i WHERE i.descripcion = :descripcion")
    , @NamedQuery(name = "Impuesto.findByRetencion", query = "SELECT i FROM Impuesto i WHERE i.retencion = :retencion")
    , @NamedQuery(name = "Impuesto.findByTraslado", query = "SELECT i FROM Impuesto i WHERE i.traslado = :traslado")
    , @NamedQuery(name = "Impuesto.findByLocalFederal", query = "SELECT i FROM Impuesto i WHERE i.localFederal = :localFederal")
    , @NamedQuery(name = "Impuesto.findByEntidadAplica", query = "SELECT i FROM Impuesto i WHERE i.entidadAplica = :entidadAplica")})
public class Impuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "retencion")
    private String retencion;
    @Size(max = 255)
    @Column(name = "traslado")
    private String traslado;
    @Size(max = 255)
    @Column(name = "local_federal")
    private String localFederal;
    @Size(max = 255)
    @Column(name = "entidad_aplica")
    private String entidadAplica;

    public Impuesto() {
    }

    public Impuesto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRetencion() {
        return retencion;
    }

    public void setRetencion(String retencion) {
        this.retencion = retencion;
    }

    public String getTraslado() {
        return traslado;
    }

    public void setTraslado(String traslado) {
        this.traslado = traslado;
    }

    public String getLocalFederal() {
        return localFederal;
    }

    public void setLocalFederal(String localFederal) {
        this.localFederal = localFederal;
    }

    public String getEntidadAplica() {
        return entidadAplica;
    }

    public void setEntidadAplica(String entidadAplica) {
        this.entidadAplica = entidadAplica;
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
        if (!(object instanceof Impuesto)) {
            return false;
        }
        Impuesto other = (Impuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.Impuesto[ id=" + id + " ]";
    }
    
}
