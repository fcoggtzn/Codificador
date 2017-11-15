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
@Table(name = "categoria", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c")
    , @NamedQuery(name = "Categoria.findByIdcategoria", query = "SELECT c FROM Categoria c WHERE c.idcategoria = :idcategoria")
    , @NamedQuery(name = "Categoria.findByClaveProductoServicio", query = "SELECT c FROM Categoria c WHERE c.claveProductoServicio = :claveProductoServicio")
    , @NamedQuery(name = "Categoria.findByClaveUnidad", query = "SELECT c FROM Categoria c WHERE c.claveUnidad = :claveUnidad")
    , @NamedQuery(name = "Categoria.findByUnidad", query = "SELECT c FROM Categoria c WHERE c.unidad = :unidad")
    , @NamedQuery(name = "Categoria.findByInventariable", query = "SELECT c FROM Categoria c WHERE c.inventariable = :inventariable")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcategoria")
    private Integer idcategoria;
    @Size(max = 45)    
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)    
    @Column(name = "clave_producto_servicio")
    private String claveProductoServicio;
    @Size(max = 45)
    @Column(name = "clave_unidad")
    private String claveUnidad;
    @Size(max = 255)
    @Column(name = "unidad")
    private String unidad;
    @Column(name = "inventariable")
    private Short inventariable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private Collection<Producto> productoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private Collection<CategoriaImpuesto> categoriaImpuestoCollection;

    public Categoria() {
    }

    public Categoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getClaveProductoServicio() {
        return claveProductoServicio;
    }

    public void setClaveProductoServicio(String claveProductoServicio) {
        this.claveProductoServicio = claveProductoServicio;
    }

    public String getClaveUnidad() {
        return claveUnidad;
    }

    public void setClaveUnidad(String claveUnidad) {
        this.claveUnidad = claveUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Short getInventariable() {
        return inventariable;
    }

    public void setInventariable(Short inventariable) {
        this.inventariable = inventariable;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
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
        hash += (idcategoria != null ? idcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.idcategoria == null && other.idcategoria != null) || (this.idcategoria != null && !this.idcategoria.equals(other.idcategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Categoria[ idcategoria=" + idcategoria + " ]";
    }
    
}
