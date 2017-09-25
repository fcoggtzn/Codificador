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
@Table(name = "regimen_fiscal", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegimenFiscal.findAll", query = "SELECT r FROM RegimenFiscal r")
    , @NamedQuery(name = "RegimenFiscal.findById", query = "SELECT r FROM RegimenFiscal r WHERE r.id = :id")
    , @NamedQuery(name = "RegimenFiscal.findByRegimenFiscal", query = "SELECT r FROM RegimenFiscal r WHERE r.regimenFiscal = :regimenFiscal")
    , @NamedQuery(name = "RegimenFiscal.findByDescripcion", query = "SELECT r FROM RegimenFiscal r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RegimenFiscal.findByFisica", query = "SELECT r FROM RegimenFiscal r WHERE r.fisica = :fisica")
    , @NamedQuery(name = "RegimenFiscal.findByMoral", query = "SELECT r FROM RegimenFiscal r WHERE r.moral = :moral")
    , @NamedQuery(name = "RegimenFiscal.findByFechaInicioVigencia", query = "SELECT r FROM RegimenFiscal r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RegimenFiscal.findByFechaFinVigencia", query = "SELECT r FROM RegimenFiscal r WHERE r.fechaFinVigencia = :fechaFinVigencia")})
public class RegimenFiscal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "regimen_fiscal")
    private String regimenFiscal;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "fisica")
    private String fisica;
    @Size(max = 255)
    @Column(name = "moral")
    private String moral;
    @Size(max = 255)
    @Column(name = "fecha_inicio_vigencia")
    private String fechaInicioVigencia;
    @Size(max = 255)
    @Column(name = "fecha_fin_vigencia")
    private String fechaFinVigencia;

    public RegimenFiscal() {
    }

    public RegimenFiscal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(String regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFisica() {
        return fisica;
    }

    public void setFisica(String fisica) {
        this.fisica = fisica;
    }

    public String getMoral() {
        return moral;
    }

    public void setMoral(String moral) {
        this.moral = moral;
    }

    public String getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(String fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public String getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(String fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
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
        if (!(object instanceof RegimenFiscal)) {
            return false;
        }
        RegimenFiscal other = (RegimenFiscal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.RegimenFiscal[ id=" + id + " ]";
    }
    
}
