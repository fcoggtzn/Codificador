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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "contribuyente", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contribuyente.findAll", query = "SELECT c FROM Contribuyente c")
    , @NamedQuery(name = "Contribuyente.findByIdContribuyente", query = "SELECT c FROM Contribuyente c WHERE c.idContribuyente = :idContribuyente")
    , @NamedQuery(name = "Contribuyente.findByRfc", query = "SELECT c FROM Contribuyente c WHERE c.rfc = :rfc")})
public class Contribuyente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contribuyente")
    private Integer idContribuyente;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "rfc")
    private String rfc;
    @OneToMany(mappedBy = "contribuyente")
    private Collection<Empleado> empleadoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contribuyente")
    private Collection<EmpresaContribuyente> empresaContribuyenteCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "contribuyente")
    private Comprobante comprobante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contribuyente1")
    private Collection<Comprobante> comprobanteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contribuyente")
    private Collection<Empresa> empresaCollection;

    public Contribuyente() {
    }

    public Contribuyente(Integer idContribuyente) {
        this.idContribuyente = idContribuyente;
    }

    public Contribuyente(Integer idContribuyente, String nombre, String rfc) {
        this.idContribuyente = idContribuyente;
        this.nombre = nombre;
        this.rfc = rfc;
    }

    public Integer getIdContribuyente() {
        return idContribuyente;
    }

    public void setIdContribuyente(Integer idContribuyente) {
        this.idContribuyente = idContribuyente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    @XmlTransient
    public Collection<EmpresaContribuyente> getEmpresaContribuyenteCollection() {
        return empresaContribuyenteCollection;
    }

    public void setEmpresaContribuyenteCollection(Collection<EmpresaContribuyente> empresaContribuyenteCollection) {
        this.empresaContribuyenteCollection = empresaContribuyenteCollection;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    @XmlTransient
    public Collection<Comprobante> getComprobanteCollection() {
        return comprobanteCollection;
    }

    public void setComprobanteCollection(Collection<Comprobante> comprobanteCollection) {
        this.comprobanteCollection = comprobanteCollection;
    }

    @XmlTransient
    public Collection<Empresa> getEmpresaCollection() {
        return empresaCollection;
    }

    public void setEmpresaCollection(Collection<Empresa> empresaCollection) {
        this.empresaCollection = empresaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContribuyente != null ? idContribuyente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contribuyente)) {
            return false;
        }
        Contribuyente other = (Contribuyente) object;
        if ((this.idContribuyente == null && other.idContribuyente != null) || (this.idContribuyente != null && !this.idContribuyente.equals(other.idContribuyente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Contribuyente[ idContribuyente=" + idContribuyente + " ]";
    }
    
}
