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
@Table(name = "forma_pago", catalog = "catalogoSat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormaPago.findAll", query = "SELECT f FROM FormaPago f")
    , @NamedQuery(name = "FormaPago.findById", query = "SELECT f FROM FormaPago f WHERE f.id = :id")
    , @NamedQuery(name = "FormaPago.findByFormaPago", query = "SELECT f FROM FormaPago f WHERE f.formaPago = :formaPago")
    , @NamedQuery(name = "FormaPago.findByDescripcion", query = "SELECT f FROM FormaPago f WHERE f.descripcion = :descripcion")
    , @NamedQuery(name = "FormaPago.findByBancarizado", query = "SELECT f FROM FormaPago f WHERE f.bancarizado = :bancarizado")
    , @NamedQuery(name = "FormaPago.findByNumeroOperacion", query = "SELECT f FROM FormaPago f WHERE f.numeroOperacion = :numeroOperacion")
    , @NamedQuery(name = "FormaPago.findByRfcEmisorOrdenante", query = "SELECT f FROM FormaPago f WHERE f.rfcEmisorOrdenante = :rfcEmisorOrdenante")
    , @NamedQuery(name = "FormaPago.findByCuentaOrdenante", query = "SELECT f FROM FormaPago f WHERE f.cuentaOrdenante = :cuentaOrdenante")
    , @NamedQuery(name = "FormaPago.findByPatronCuentaOrdenante", query = "SELECT f FROM FormaPago f WHERE f.patronCuentaOrdenante = :patronCuentaOrdenante")
    , @NamedQuery(name = "FormaPago.findByRfcEmisorBeneficiario", query = "SELECT f FROM FormaPago f WHERE f.rfcEmisorBeneficiario = :rfcEmisorBeneficiario")
    , @NamedQuery(name = "FormaPago.findByCuentaBeneficiario", query = "SELECT f FROM FormaPago f WHERE f.cuentaBeneficiario = :cuentaBeneficiario")
    , @NamedQuery(name = "FormaPago.findByPatronCuentaBeneficiario", query = "SELECT f FROM FormaPago f WHERE f.patronCuentaBeneficiario = :patronCuentaBeneficiario")
    , @NamedQuery(name = "FormaPago.findByTipoCadenaPago", query = "SELECT f FROM FormaPago f WHERE f.tipoCadenaPago = :tipoCadenaPago")
    , @NamedQuery(name = "FormaPago.findByBancoEmisorCuentaOrdenanteExtranjero", query = "SELECT f FROM FormaPago f WHERE f.bancoEmisorCuentaOrdenanteExtranjero = :bancoEmisorCuentaOrdenanteExtranjero")})
public class FormaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "forma_pago")
    private String formaPago;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "bancarizado")
    private String bancarizado;
    @Size(max = 255)
    @Column(name = "numero_operacion")
    private String numeroOperacion;
    @Size(max = 255)
    @Column(name = "rfc_emisor_ordenante")
    private String rfcEmisorOrdenante;
    @Size(max = 255)
    @Column(name = "cuenta_ordenante")
    private String cuentaOrdenante;
    @Size(max = 255)
    @Column(name = "patron_cuenta_ordenante")
    private String patronCuentaOrdenante;
    @Size(max = 255)
    @Column(name = "rfc_emisor_beneficiario")
    private String rfcEmisorBeneficiario;
    @Size(max = 255)
    @Column(name = "cuenta_beneficiario")
    private String cuentaBeneficiario;
    @Size(max = 255)
    @Column(name = "patron_cuenta_beneficiario")
    private String patronCuentaBeneficiario;
    @Size(max = 255)
    @Column(name = "tipo_cadena_pago")
    private String tipoCadenaPago;
    @Size(max = 255)
    @Column(name = "banco_emisor_cuenta_ordenante_extranjero")
    private String bancoEmisorCuentaOrdenanteExtranjero;

    public FormaPago() {
    }

    public FormaPago(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBancarizado() {
        return bancarizado;
    }

    public void setBancarizado(String bancarizado) {
        this.bancarizado = bancarizado;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public String getRfcEmisorOrdenante() {
        return rfcEmisorOrdenante;
    }

    public void setRfcEmisorOrdenante(String rfcEmisorOrdenante) {
        this.rfcEmisorOrdenante = rfcEmisorOrdenante;
    }

    public String getCuentaOrdenante() {
        return cuentaOrdenante;
    }

    public void setCuentaOrdenante(String cuentaOrdenante) {
        this.cuentaOrdenante = cuentaOrdenante;
    }

    public String getPatronCuentaOrdenante() {
        return patronCuentaOrdenante;
    }

    public void setPatronCuentaOrdenante(String patronCuentaOrdenante) {
        this.patronCuentaOrdenante = patronCuentaOrdenante;
    }

    public String getRfcEmisorBeneficiario() {
        return rfcEmisorBeneficiario;
    }

    public void setRfcEmisorBeneficiario(String rfcEmisorBeneficiario) {
        this.rfcEmisorBeneficiario = rfcEmisorBeneficiario;
    }

    public String getCuentaBeneficiario() {
        return cuentaBeneficiario;
    }

    public void setCuentaBeneficiario(String cuentaBeneficiario) {
        this.cuentaBeneficiario = cuentaBeneficiario;
    }

    public String getPatronCuentaBeneficiario() {
        return patronCuentaBeneficiario;
    }

    public void setPatronCuentaBeneficiario(String patronCuentaBeneficiario) {
        this.patronCuentaBeneficiario = patronCuentaBeneficiario;
    }

    public String getTipoCadenaPago() {
        return tipoCadenaPago;
    }

    public void setTipoCadenaPago(String tipoCadenaPago) {
        this.tipoCadenaPago = tipoCadenaPago;
    }

    public String getBancoEmisorCuentaOrdenanteExtranjero() {
        return bancoEmisorCuentaOrdenanteExtranjero;
    }

    public void setBancoEmisorCuentaOrdenanteExtranjero(String bancoEmisorCuentaOrdenanteExtranjero) {
        this.bancoEmisorCuentaOrdenanteExtranjero = bancoEmisorCuentaOrdenanteExtranjero;
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
        if (!(object instanceof FormaPago)) {
            return false;
        }
        FormaPago other = (FormaPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.entidad.FormaPago[ id=" + id + " ]";
    }
    
}
