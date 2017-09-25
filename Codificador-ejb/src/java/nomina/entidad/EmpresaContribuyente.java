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
@Table(name = "empresa_contribuyente", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaContribuyente.findAll", query = "SELECT e FROM EmpresaContribuyente e")
    , @NamedQuery(name = "EmpresaContribuyente.findByIdempresaContribuyente", query = "SELECT e FROM EmpresaContribuyente e WHERE e.idempresaContribuyente = :idempresaContribuyente")})
public class EmpresaContribuyente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idempresa_contribuyente")
    private Integer idempresaContribuyente;
    @JoinColumn(name = "contribuyente_id_contribuyente", referencedColumnName = "id_contribuyente")
    @ManyToOne(optional = false)
    private Contribuyente contribuyente;
    @JoinColumn(name = "empresa_idempresa", referencedColumnName = "idempresa")
    @ManyToOne(optional = false)
    private Empresa empresa;

    public EmpresaContribuyente() {
    }

    public EmpresaContribuyente(Integer idempresaContribuyente) {
        this.idempresaContribuyente = idempresaContribuyente;
    }

    public Integer getIdempresaContribuyente() {
        return idempresaContribuyente;
    }

    public void setIdempresaContribuyente(Integer idempresaContribuyente) {
        this.idempresaContribuyente = idempresaContribuyente;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
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
        hash += (idempresaContribuyente != null ? idempresaContribuyente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaContribuyente)) {
            return false;
        }
        EmpresaContribuyente other = (EmpresaContribuyente) object;
        if ((this.idempresaContribuyente == null && other.idempresaContribuyente != null) || (this.idempresaContribuyente != null && !this.idempresaContribuyente.equals(other.idempresaContribuyente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.EmpresaContribuyente[ idempresaContribuyente=" + idempresaContribuyente + " ]";
    }
    
}
