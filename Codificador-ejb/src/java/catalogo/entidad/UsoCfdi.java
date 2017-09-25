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
@Table(name = "uso_cfdi", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsoCfdi.findAll", query = "SELECT u FROM UsoCfdi u")
    , @NamedQuery(name = "UsoCfdi.findById", query = "SELECT u FROM UsoCfdi u WHERE u.id = :id")
    , @NamedQuery(name = "UsoCfdi.findByUsoCfdi", query = "SELECT u FROM UsoCfdi u WHERE u.usoCfdi = :usoCfdi")
    , @NamedQuery(name = "UsoCfdi.findByDescripcion", query = "SELECT u FROM UsoCfdi u WHERE u.descripcion = :descripcion")
    , @NamedQuery(name = "UsoCfdi.findByFisica", query = "SELECT u FROM UsoCfdi u WHERE u.fisica = :fisica")
    , @NamedQuery(name = "UsoCfdi.findByMoral", query = "SELECT u FROM UsoCfdi u WHERE u.moral = :moral")
    , @NamedQuery(name = "UsoCfdi.findByFechaInicio", query = "SELECT u FROM UsoCfdi u WHERE u.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "UsoCfdi.findByFechaFin", query = "SELECT u FROM UsoCfdi u WHERE u.fechaFin = :fechaFin")})
public class UsoCfdi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "uso_cfdi")
    private String usoCfdi;
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
    @Column(name = "fecha_inicio")
    private String fechaInicio;
    @Size(max = 255)
    @Column(name = "fecha_fin")
    private String fechaFin;

    public UsoCfdi() {
    }

    public UsoCfdi(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(String usoCfdi) {
        this.usoCfdi = usoCfdi;
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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
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
        if (!(object instanceof UsoCfdi)) {
            return false;
        }
        UsoCfdi other = (UsoCfdi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.UsoCfdi[ id=" + id + " ]";
    }
    
}
