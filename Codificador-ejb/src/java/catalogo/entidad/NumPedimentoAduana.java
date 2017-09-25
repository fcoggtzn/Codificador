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
@Table(name = "num_pedimento_aduana", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NumPedimentoAduana.findAll", query = "SELECT n FROM NumPedimentoAduana n")
    , @NamedQuery(name = "NumPedimentoAduana.findById", query = "SELECT n FROM NumPedimentoAduana n WHERE n.id = :id")
    , @NamedQuery(name = "NumPedimentoAduana.findByAduana", query = "SELECT n FROM NumPedimentoAduana n WHERE n.aduana = :aduana")
    , @NamedQuery(name = "NumPedimentoAduana.findByPatente", query = "SELECT n FROM NumPedimentoAduana n WHERE n.patente = :patente")
    , @NamedQuery(name = "NumPedimentoAduana.findByEjercicio", query = "SELECT n FROM NumPedimentoAduana n WHERE n.ejercicio = :ejercicio")
    , @NamedQuery(name = "NumPedimentoAduana.findByCantidad", query = "SELECT n FROM NumPedimentoAduana n WHERE n.cantidad = :cantidad")})
public class NumPedimentoAduana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "aduana")
    private String aduana;
    @Size(max = 255)
    @Column(name = "patente")
    private String patente;
    @Size(max = 255)
    @Column(name = "ejercicio")
    private String ejercicio;
    @Size(max = 255)
    @Column(name = "cantidad")
    private String cantidad;

    public NumPedimentoAduana() {
    }

    public NumPedimentoAduana(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAduana() {
        return aduana;
    }

    public void setAduana(String aduana) {
        this.aduana = aduana;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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
        if (!(object instanceof NumPedimentoAduana)) {
            return false;
        }
        NumPedimentoAduana other = (NumPedimentoAduana) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.NumPedimentoAduana[ id=" + id + " ]";
    }
    
}
