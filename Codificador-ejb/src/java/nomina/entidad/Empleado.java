/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.entidad;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "empleado", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")
    , @NamedQuery(name = "Empleado.findByNumEmpleado", query = "SELECT e FROM Empleado e WHERE e.numEmpleado = :numEmpleado")
    , @NamedQuery(name = "Empleado.findByCurp", query = "SELECT e FROM Empleado e WHERE e.curp = :curp")
    , @NamedQuery(name = "Empleado.findByNumseguroSocial", query = "SELECT e FROM Empleado e WHERE e.numseguroSocial = :numseguroSocial")
    , @NamedQuery(name = "Empleado.findByTipoContrato", query = "SELECT e FROM Empleado e WHERE e.tipoContrato = :tipoContrato")
    , @NamedQuery(name = "Empleado.findByFechaInicio", query = "SELECT e FROM Empleado e WHERE e.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Empleado.findByAntiguedad", query = "SELECT e FROM Empleado e WHERE e.antiguedad = :antiguedad")
    , @NamedQuery(name = "Empleado.findByClaveEntFed", query = "SELECT e FROM Empleado e WHERE e.claveEntFed = :claveEntFed")
    , @NamedQuery(name = "Empleado.findByTipoRegimen", query = "SELECT e FROM Empleado e WHERE e.tipoRegimen = :tipoRegimen")
    , @NamedQuery(name = "Empleado.findByPeriodicidadPago", query = "SELECT e FROM Empleado e WHERE e.periodicidadPago = :periodicidadPago")
    , @NamedQuery(name = "Empleado.findByRiesgoPuesto", query = "SELECT e FROM Empleado e WHERE e.riesgoPuesto = :riesgoPuesto")
    , @NamedQuery(name = "Empleado.findBySalarioDiarioIntegrado", query = "SELECT e FROM Empleado e WHERE e.salarioDiarioIntegrado = :salarioDiarioIntegrado")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_empleado")
    private Integer numEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "curp")
    private String curp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "num_seguroSocial")
    private String numseguroSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipo_contrato")
    private String tipoContrato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "antiguedad")
    private String antiguedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "clave_ent_fed")
    private String claveEntFed;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipo_regimen")
    private String tipoRegimen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "periodicidad_pago")
    private String periodicidadPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "riesgo_puesto")
    private String riesgoPuesto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "salario_diario_integrado")
    private double salarioDiarioIntegrado;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "empleado")
    private Collection<DeduccionPercepcion> deduccionPercepcionCollection;
    @JoinColumn(name = "contribuyente_id_contribuyente", referencedColumnName = "id_contribuyente")
    @ManyToOne
    private Contribuyente contribuyente;

    public Empleado() {
    }

    public Empleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public Empleado(Integer numEmpleado, String curp, String numseguroSocial, String tipoContrato, Date fechaInicio, String antiguedad, String claveEntFed, String tipoRegimen, String periodicidadPago, String riesgoPuesto, double salarioDiarioIntegrado) {
        this.numEmpleado = numEmpleado;
        this.curp = curp;
        this.numseguroSocial = numseguroSocial;
        this.tipoContrato = tipoContrato;
        this.fechaInicio = fechaInicio;
        this.antiguedad = antiguedad;
        this.claveEntFed = claveEntFed;
        this.tipoRegimen = tipoRegimen;
        this.periodicidadPago = periodicidadPago;
        this.riesgoPuesto = riesgoPuesto;
        this.salarioDiarioIntegrado = salarioDiarioIntegrado;
    }

    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNumseguroSocial() {
        return numseguroSocial;
    }

    public void setNumseguroSocial(String numseguroSocial) {
        this.numseguroSocial = numseguroSocial;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getClaveEntFed() {
        return claveEntFed;
    }

    public void setClaveEntFed(String claveEntFed) {
        this.claveEntFed = claveEntFed;
    }

    public String getTipoRegimen() {
        return tipoRegimen;
    }

    public void setTipoRegimen(String tipoRegimen) {
        this.tipoRegimen = tipoRegimen;
    }

    public String getPeriodicidadPago() {
        return periodicidadPago;
    }

    public void setPeriodicidadPago(String periodicidadPago) {
        this.periodicidadPago = periodicidadPago;
    }

    public String getRiesgoPuesto() {
        return riesgoPuesto;
    }

    public void setRiesgoPuesto(String riesgoPuesto) {
        this.riesgoPuesto = riesgoPuesto;
    }

    public double getSalarioDiarioIntegrado() {
        return salarioDiarioIntegrado;
    }

    public void setSalarioDiarioIntegrado(double salarioDiarioIntegrado) {
        this.salarioDiarioIntegrado = salarioDiarioIntegrado;
    }

    @XmlTransient
    public Collection<DeduccionPercepcion> getDeduccionPercepcionCollection() {
        return deduccionPercepcionCollection;
    }

    public void setDeduccionPercepcionCollection(Collection<DeduccionPercepcion> deduccionPercepcionCollection) {
        this.deduccionPercepcionCollection = deduccionPercepcionCollection;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numEmpleado != null ? numEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.numEmpleado == null && other.numEmpleado != null) || (this.numEmpleado != null && !this.numEmpleado.equals(other.numEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Empleado[ numEmpleado=" + numEmpleado + " ]";
    }
    
}
