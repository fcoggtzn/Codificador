/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factura.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "categoria_impuesto", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaImpuesto.findAll", query = "SELECT c FROM CategoriaImpuesto c")
    , @NamedQuery(name = "CategoriaImpuesto.findByIdcategoriaImpuesto", query = "SELECT c FROM CategoriaImpuesto c WHERE c.idcategoriaImpuesto = :idcategoriaImpuesto")
    , @NamedQuery(name = "CategoriaImpuesto.findByTraslado", query = "SELECT c FROM CategoriaImpuesto c WHERE c.traslado = :traslado")})
public class CategoriaImpuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcategoria_impuesto")
    private Integer idcategoriaImpuesto;
    @Column(name = "traslado")
    private boolean traslado;
    @JoinColumn(name = "categoria_idcategoria", referencedColumnName = "idcategoria")
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "impuestoP_idimpuesto", referencedColumnName = "idimpuesto")
    @ManyToOne(optional = false)
    private ImpuestoP impuestoP;

    public CategoriaImpuesto() {
    }

    public CategoriaImpuesto(Integer idcategoriaImpuesto) {
        this.idcategoriaImpuesto = idcategoriaImpuesto;
    }

    public Integer getIdcategoriaImpuesto() {
        return idcategoriaImpuesto;
    }

    public void setIdcategoriaImpuesto(Integer idcategoriaImpuesto) {
        this.idcategoriaImpuesto = idcategoriaImpuesto;
    }

    public boolean getTraslado() {
        return traslado;
    }

    public void setTraslado(boolean traslado) {
        this.traslado = traslado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ImpuestoP getImpuestoP() {
        return impuestoP;
    }

    public void setImpuestoP(ImpuestoP impuestoP) {
        this.impuestoP = impuestoP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcategoriaImpuesto != null ? idcategoriaImpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaImpuesto)) {
            return false;
        }
        CategoriaImpuesto other = (CategoriaImpuesto) object;
        if ((this.idcategoriaImpuesto == null && other.idcategoriaImpuesto != null) || (this.idcategoriaImpuesto != null && !this.idcategoriaImpuesto.equals(other.idcategoriaImpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.CategoriaImpuesto[ idcategoriaImpuesto=" + idcategoriaImpuesto + " ]";
    }
    
}
