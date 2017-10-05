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
import javax.persistence.Lob;
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
@Table(name = "archivos", catalog = "sole3", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Archivos.findAll", query = "SELECT a FROM Archivos a")
    , @NamedQuery(name = "Archivos.findByIdarchivos", query = "SELECT a FROM Archivos a WHERE a.idarchivos = :idarchivos")
    , @NamedQuery(name = "Archivos.findByNombre", query = "SELECT a FROM Archivos a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Archivos.findByTipo", query = "SELECT a FROM Archivos a WHERE a.tipo = :tipo")})
public class Archivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarchivos")
    private Integer idarchivos;
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Lob
    @Column(name = "contenido")
    private byte[] contenido;
    @JoinColumn(name = "comprobante_id_comprobante", referencedColumnName = "id_comprobante")
    @ManyToOne(optional = false)
    private ComprobanteL comprobanteL;

    public Archivos() {
    }

    public Archivos(Integer idarchivos) {
        this.idarchivos = idarchivos;
    }

    public Integer getIdarchivos() {
        return idarchivos;
    }

    public void setIdarchivos(Integer idarchivos) {
        this.idarchivos = idarchivos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public ComprobanteL getComprobanteL() {
        return comprobanteL;
    }

    public void setComprobanteL(ComprobanteL comprobanteL) {
        this.comprobanteL = comprobanteL;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idarchivos != null ? idarchivos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Archivos)) {
            return false;
        }
        Archivos other = (Archivos) object;
        if ((this.idarchivos == null && other.idarchivos != null) || (this.idarchivos != null && !this.idarchivos.equals(other.idarchivos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nomina.entidad.Archivos[ idarchivos=" + idarchivos + " ]";
    }
    
}
