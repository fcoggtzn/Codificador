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
@Table(name = "tasa_cuota", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TasaCuota.findAll", query = "SELECT t FROM TasaCuota t")
    , @NamedQuery(name = "TasaCuota.findById", query = "SELECT t FROM TasaCuota t WHERE t.id = :id")
    , @NamedQuery(name = "TasaCuota.findByRangoFijo", query = "SELECT t FROM TasaCuota t WHERE t.rangoFijo = :rangoFijo")
    , @NamedQuery(name = "TasaCuota.findByValorMinimo", query = "SELECT t FROM TasaCuota t WHERE t.valorMinimo = :valorMinimo")
    , @NamedQuery(name = "TasaCuota.findByValorMaximo", query = "SELECT t FROM TasaCuota t WHERE t.valorMaximo = :valorMaximo")
    , @NamedQuery(name = "TasaCuota.findByImpuesto", query = "SELECT t FROM TasaCuota t WHERE t.impuesto = :impuesto")
    , @NamedQuery(name = "TasaCuota.findByFactor", query = "SELECT t FROM TasaCuota t WHERE t.factor = :factor")
    , @NamedQuery(name = "TasaCuota.findByTraslado", query = "SELECT t FROM TasaCuota t WHERE t.traslado = :traslado")
    , @NamedQuery(name = "TasaCuota.findByRetencion", query = "SELECT t FROM TasaCuota t WHERE t.retencion = :retencion")})
public class TasaCuota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "rango_fijo")
    private String rangoFijo;
    @Size(max = 255)
    @Column(name = "valor_minimo")
    private String valorMinimo;
    @Size(max = 255)
    @Column(name = "valor_maximo")
    private String valorMaximo;
    @Size(max = 255)
    @Column(name = "impuesto")
    private String impuesto;
    @Size(max = 255)
    @Column(name = "factor")
    private String factor;
    @Size(max = 255)
    @Column(name = "traslado")
    private String traslado;
    @Size(max = 255)
    @Column(name = "retencion")
    private String retencion;

    public TasaCuota() {
    }

    public TasaCuota(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRangoFijo() {
        return rangoFijo;
    }

    public void setRangoFijo(String rangoFijo) {
        this.rangoFijo = rangoFijo;
    }

    public String getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(String valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public String getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getTraslado() {
        return traslado;
    }

    public void setTraslado(String traslado) {
        this.traslado = traslado;
    }

    public String getRetencion() {
        return retencion;
    }

    public void setRetencion(String retencion) {
        this.retencion = retencion;
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
        if (!(object instanceof TasaCuota)) {
            return false;
        }
        TasaCuota other = (TasaCuota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.TasaCuota[ id=" + id + " ]";
    }
    
}
