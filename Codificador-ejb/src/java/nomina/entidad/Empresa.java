/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina.entidad;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ovante
 */
@Entity
@Table(name = "empresa", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")
    , @NamedQuery(name = "Empresa.findByIdempresa", query = "SELECT e FROM Empresa e WHERE e.idempresa = :idempresa")
    , @NamedQuery(name = "Empresa.findByEmpresa", query = "SELECT e FROM Empresa e WHERE e.empresa = :empresa")
    , @NamedQuery(name = "Empresa.findByDescripcion", query = "SELECT e FROM Empresa e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Empresa.findByRegimenFiscal", query = "SELECT e FROM Empresa e WHERE e.regimenFiscal = :regimenFiscal")
    , @NamedQuery(name = "Empresa.findByRegistroPatronal", query = "SELECT e FROM Empresa e WHERE e.registroPatronal = :registroPatronal")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idempresa")
    private Integer idempresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "empresa")
    private String empresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "regimen_fiscal")
    private String regimenFiscal;
    @Size(max = 255)
    @Column(name = "registro_patronal")
    private String registroPatronal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<EmpresaContribuyente> empresaContribuyenteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<Configuracion> configuracionCollection;
    @JoinColumn(name = "contribuyente_id_contribuyente", referencedColumnName = "id_contribuyente")
    @ManyToOne(optional = false)
    private Contribuyente contribuyente;

    public Empresa() {
    }

    public Empresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public Empresa(Integer idempresa, String empresa, String descripcion) {
        this.idempresa = idempresa;
        this.empresa = empresa;
        this.descripcion = descripcion;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(String regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }

    @XmlTransient
    public Collection<EmpresaContribuyente> getEmpresaContribuyenteCollection() {
        return empresaContribuyenteCollection;
    }

    public void setEmpresaContribuyenteCollection(Collection<EmpresaContribuyente> empresaContribuyenteCollection) {
        this.empresaContribuyenteCollection = empresaContribuyenteCollection;
    }

    @XmlTransient
    public Collection<Configuracion> getConfiguracionCollection() {
        return configuracionCollection;
    }

    public void setConfiguracionCollection(Collection<Configuracion> configuracionCollection) {
        this.configuracionCollection = configuracionCollection;
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
        hash += (idempresa != null ? idempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.idempresa == null && other.idempresa != null) || (this.idempresa != null && !this.idempresa.equals(other.idempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Empresa[ idempresa=" + idempresa + " ]";
    }
    
}
