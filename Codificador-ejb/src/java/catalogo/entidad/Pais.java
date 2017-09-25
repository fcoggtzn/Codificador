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
@Table(name = "pais", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")
    , @NamedQuery(name = "Pais.findById", query = "SELECT p FROM Pais p WHERE p.id = :id")
    , @NamedQuery(name = "Pais.findByPais", query = "SELECT p FROM Pais p WHERE p.pais = :pais")
    , @NamedQuery(name = "Pais.findByDescripcion", query = "SELECT p FROM Pais p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Pais.findByFormatoCp", query = "SELECT p FROM Pais p WHERE p.formatoCp = :formatoCp")
    , @NamedQuery(name = "Pais.findByFormatoRegistroIdentidadTributaria", query = "SELECT p FROM Pais p WHERE p.formatoRegistroIdentidadTributaria = :formatoRegistroIdentidadTributaria")
    , @NamedQuery(name = "Pais.findByValidacionRefistroIdentidadTributaria", query = "SELECT p FROM Pais p WHERE p.validacionRefistroIdentidadTributaria = :validacionRefistroIdentidadTributaria")
    , @NamedQuery(name = "Pais.findByAgrupaciones", query = "SELECT p FROM Pais p WHERE p.agrupaciones = :agrupaciones")})
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "pais")
    private String pais;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "formato_cp")
    private String formatoCp;
    @Size(max = 255)
    @Column(name = "formato_registro_identidad_tributaria")
    private String formatoRegistroIdentidadTributaria;
    @Size(max = 255)
    @Column(name = "validacion_refistro_identidad_tributaria")
    private String validacionRefistroIdentidadTributaria;
    @Size(max = 255)
    @Column(name = "agrupaciones")
    private String agrupaciones;

    public Pais() {
    }

    public Pais(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFormatoCp() {
        return formatoCp;
    }

    public void setFormatoCp(String formatoCp) {
        this.formatoCp = formatoCp;
    }

    public String getFormatoRegistroIdentidadTributaria() {
        return formatoRegistroIdentidadTributaria;
    }

    public void setFormatoRegistroIdentidadTributaria(String formatoRegistroIdentidadTributaria) {
        this.formatoRegistroIdentidadTributaria = formatoRegistroIdentidadTributaria;
    }

    public String getValidacionRefistroIdentidadTributaria() {
        return validacionRefistroIdentidadTributaria;
    }

    public void setValidacionRefistroIdentidadTributaria(String validacionRefistroIdentidadTributaria) {
        this.validacionRefistroIdentidadTributaria = validacionRefistroIdentidadTributaria;
    }

    public String getAgrupaciones() {
        return agrupaciones;
    }

    public void setAgrupaciones(String agrupaciones) {
        this.agrupaciones = agrupaciones;
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
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.Pais[ id=" + id + " ]";
    }
    
}
