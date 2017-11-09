/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factura.entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "impuestoP", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImpuestoP.findAll", query = "SELECT i FROM ImpuestoP i")
    , @NamedQuery(name = "ImpuestoP.findByIdimpuesto", query = "SELECT i FROM ImpuestoP i WHERE i.idimpuesto = :idimpuesto")
    , @NamedQuery(name = "ImpuestoP.findByImpuesto", query = "SELECT i FROM ImpuestoP i WHERE i.impuesto = :impuesto")
    , @NamedQuery(name = "ImpuestoP.findByDescripcion", query = "SELECT i FROM ImpuestoP i WHERE i.descripcion = :descripcion")
    , @NamedQuery(name = "ImpuestoP.findByPorciento", query = "SELECT i FROM ImpuestoP i WHERE i.porciento = :porciento")})
public class ImpuestoP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idimpuesto")
    private Integer idimpuesto;
    @Size(max = 45)
    @Column(name = "impuesto")
    private String impuesto;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porciento")
    private Double porciento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "impuestoP")
    private Collection<CategoriaImpuesto> categoriaImpuestoCollection;

    public ImpuestoP() {
    }

    public ImpuestoP(Integer idimpuesto) {
        this.idimpuesto = idimpuesto;
    }

    public Integer getIdimpuesto() {
        return idimpuesto;
    }

    public void setIdimpuesto(Integer idimpuesto) {
        this.idimpuesto = idimpuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPorciento() {
        return porciento;
    }

    public void setPorciento(Double porciento) {
        this.porciento = porciento;
    }

    @XmlTransient
    public Collection<CategoriaImpuesto> getCategoriaImpuestoCollection() {
        return categoriaImpuestoCollection;
    }

    public void setCategoriaImpuestoCollection(Collection<CategoriaImpuesto> categoriaImpuestoCollection) {
        this.categoriaImpuestoCollection = categoriaImpuestoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idimpuesto != null ? idimpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImpuestoP)) {
            return false;
        }
        ImpuestoP other = (ImpuestoP) object;
        if ((this.idimpuesto == null && other.idimpuesto != null) || (this.idimpuesto != null && !this.idimpuesto.equals(other.idimpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.ImpuestoP[ idimpuesto=" + idimpuesto + " ]";
    }
    
}
