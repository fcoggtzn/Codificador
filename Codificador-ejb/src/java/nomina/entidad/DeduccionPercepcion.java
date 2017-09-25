/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.entidad;

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
@Table(name = "deduccion_percepcion", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeduccionPercepcion.findAll", query = "SELECT d FROM DeduccionPercepcion d")
    , @NamedQuery(name = "DeduccionPercepcion.findByIdaplicable", query = "SELECT d FROM DeduccionPercepcion d WHERE d.idaplicable = :idaplicable")
    , @NamedQuery(name = "DeduccionPercepcion.findByCantidad", query = "SELECT d FROM DeduccionPercepcion d WHERE d.cantidad = :cantidad")})
public class DeduccionPercepcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaplicable")
    private Integer idaplicable;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @JoinColumn(name = "deduccion_id_deduccion", referencedColumnName = "id_deduccion")
    @ManyToOne
    private Deduccion deduccion;
    @JoinColumn(name = "empleado_num_empleado", referencedColumnName = "num_empleado")
    @ManyToOne(optional = false)
    private Empleado empleado;
    @JoinColumn(name = "percepcion_id_percepcion", referencedColumnName = "id_percepcion")
    @ManyToOne
    private Percepcion percepcion;

    public DeduccionPercepcion() {
    }

    public DeduccionPercepcion(Integer idaplicable) {
        this.idaplicable = idaplicable;
    }

    public Integer getIdaplicable() {
        return idaplicable;
    }

    public void setIdaplicable(Integer idaplicable) {
        this.idaplicable = idaplicable;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Deduccion getDeduccion() {
        return deduccion;
    }

    public void setDeduccion(Deduccion deduccion) {
        this.deduccion = deduccion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Percepcion getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(Percepcion percepcion) {
        this.percepcion = percepcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaplicable != null ? idaplicable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeduccionPercepcion)) {
            return false;
        }
        DeduccionPercepcion other = (DeduccionPercepcion) object;
        if ((this.idaplicable == null && other.idaplicable != null) || (this.idaplicable != null && !this.idaplicable.equals(other.idaplicable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.DeduccionPercepcion[ idaplicable=" + idaplicable + " ]";
    }
    
}
