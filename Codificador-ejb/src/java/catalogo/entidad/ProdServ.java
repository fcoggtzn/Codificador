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
@Table(name = "prod_serv", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProdServ.findAll", query = "SELECT p FROM ProdServ p")
    , @NamedQuery(name = "ProdServ.findById", query = "SELECT p FROM ProdServ p WHERE p.id = :id")
    , @NamedQuery(name = "ProdServ.findByClaveProdServ", query = "SELECT p FROM ProdServ p WHERE p.claveProdServ = :claveProdServ")
    , @NamedQuery(name = "ProdServ.findByDescripcion", query = "SELECT p FROM ProdServ p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "ProdServ.findByFechaInicioVigencia", query = "SELECT p FROM ProdServ p WHERE p.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "ProdServ.findByFechaFinVigencia", query = "SELECT p FROM ProdServ p WHERE p.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "ProdServ.findByIvaTrasladado", query = "SELECT p FROM ProdServ p WHERE p.ivaTrasladado = :ivaTrasladado")
    , @NamedQuery(name = "ProdServ.findByIepsTrasladado", query = "SELECT p FROM ProdServ p WHERE p.iepsTrasladado = :iepsTrasladado")
    , @NamedQuery(name = "ProdServ.findByComplemento", query = "SELECT p FROM ProdServ p WHERE p.complemento = :complemento")})
public class ProdServ implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "clave_prod_serv")
    private String claveProdServ;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "fecha_inicio_vigencia")
    private String fechaInicioVigencia;
    @Size(max = 255)
    @Column(name = "fecha_fin_vigencia")
    private String fechaFinVigencia;
    @Size(max = 255)
    @Column(name = "iva_trasladado")
    private String ivaTrasladado;
    @Size(max = 255)
    @Column(name = "ieps_trasladado")
    private String iepsTrasladado;
    @Size(max = 255)
    @Column(name = "complemento")
    private String complemento;

    public ProdServ() {
    }

    public ProdServ(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClaveProdServ() {
        return claveProdServ;
    }

    public void setClaveProdServ(String claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getIvaTrasladado() {
        return ivaTrasladado;
    }

    public void setIvaTrasladado(String ivaTrasladado) {
        this.ivaTrasladado = ivaTrasladado;
    }

    public String getIepsTrasladado() {
        return iepsTrasladado;
    }

    public void setIepsTrasladado(String iepsTrasladado) {
        this.iepsTrasladado = iepsTrasladado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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
        if (!(object instanceof ProdServ)) {
            return false;
        }
        ProdServ other = (ProdServ) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.ProdServ[ id=" + id + " ]";
    }
    
}
