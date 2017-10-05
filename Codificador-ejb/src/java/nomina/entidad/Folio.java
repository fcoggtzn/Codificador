/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.entidad;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "folio", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Folio.findAll", query = "SELECT f FROM Folio f")
    , @NamedQuery(name = "Folio.findByIdfolio", query = "SELECT f FROM Folio f WHERE f.idfolio = :idfolio")
    , @NamedQuery(name = "Folio.findByFolio", query = "SELECT f FROM Folio f WHERE f.folio = :folio")
    , @NamedQuery(name = "Folio.findBySerie", query = "SELECT f FROM Folio f WHERE f.serie = :serie")
    , @NamedQuery(name = "Folio.findByActivo", query = "SELECT f FROM Folio f WHERE f.activo = :activo")})
public class Folio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfolio")
    private Integer idfolio;
    @Column(name = "folio")
    private BigInteger folio;
    @Size(max = 45)
    @Column(name = "serie")
    private String serie;
    @Column(name = "activo")
    private Short activo;
    @JoinColumn(name = "empresa_idempresa", referencedColumnName = "idempresa")
    @ManyToOne(optional = false)
    private Empresa empresa;

    public Folio() {
    }

    public Folio(Integer idfolio) {
        this.idfolio = idfolio;
    }

    public Integer getIdfolio() {
        return idfolio;
    }

    public void setIdfolio(Integer idfolio) {
        this.idfolio = idfolio;
    }

    public BigInteger getFolio() {
        return folio;
    }

    public void setFolio(BigInteger folio) {
        this.folio = folio;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfolio != null ? idfolio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Folio)) {
            return false;
        }
        Folio other = (Folio) object;
        if ((this.idfolio == null && other.idfolio != null) || (this.idfolio != null && !this.idfolio.equals(other.idfolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Folio[ idfolio=" + idfolio + " ]";
    }
    
}
