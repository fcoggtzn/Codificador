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
@Table(name = "patente_aduana", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatenteAduana.findAll", query = "SELECT p FROM PatenteAduana p")
    , @NamedQuery(name = "PatenteAduana.findById", query = "SELECT p FROM PatenteAduana p WHERE p.id = :id")
    , @NamedQuery(name = "PatenteAduana.findByPatenteAduana", query = "SELECT p FROM PatenteAduana p WHERE p.patenteAduana = :patenteAduana")
    , @NamedQuery(name = "PatenteAduana.findByInicioVigencia", query = "SELECT p FROM PatenteAduana p WHERE p.inicioVigencia = :inicioVigencia")
    , @NamedQuery(name = "PatenteAduana.findByFinVigencia", query = "SELECT p FROM PatenteAduana p WHERE p.finVigencia = :finVigencia")})
public class PatenteAduana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "patente_aduana")
    private String patenteAduana;
    @Size(max = 255)
    @Column(name = "inicio_vigencia")
    private String inicioVigencia;
    @Size(max = 255)
    @Column(name = "fin_vigencia")
    private String finVigencia;

    public PatenteAduana() {
    }

    public PatenteAduana(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatenteAduana() {
        return patenteAduana;
    }

    public void setPatenteAduana(String patenteAduana) {
        this.patenteAduana = patenteAduana;
    }

    public String getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(String inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public String getFinVigencia() {
        return finVigencia;
    }

    public void setFinVigencia(String finVigencia) {
        this.finVigencia = finVigencia;
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
        if (!(object instanceof PatenteAduana)) {
            return false;
        }
        PatenteAduana other = (PatenteAduana) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.PatenteAduana[ id=" + id + " ]";
    }
    
}
