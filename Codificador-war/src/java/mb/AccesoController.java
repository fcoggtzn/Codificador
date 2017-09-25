/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import nomina.entidad.Configuracion;
import nomina.servicio.ConfiguracionFacadeLocal;

/**
 *
 * @author ovante
 */
@Named(value = "accesoController")
@SessionScoped
public class AccesoController extends BaseController implements Serializable  {
    @EJB ConfiguracionFacadeLocal configuracionFacade;
    
    private Configuracion configuracion;

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }
    
    public AccesoController() {
        configuracion= new Configuracion();
    }
    
    public void redireccionar(String page) throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect(page);
    }
    
    public String loginSesion() throws IOException{
        configuracion=configuracionFacade.getConfiguracion(configuracion.getLogin(),configuracion.getPassword());
        if (!configuracion.equals(new Configuracion())){
            //return "index.xhtml";
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            session.setAttribute("configuracion", configuracion);
            session.setAttribute("usuario", configuracion.getLogin());
            session.setAttribute("empresa", configuracion.getEmpresa().getEmpresa());
            session.setAttribute("empresaActual", configuracion.getEmpresa());
            //FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            return "index";
        }else{
            
            return "error";
            //FacesContext.getCurrentInstance().getExternalContext().redirect("errorLogin.xhtml");
        }
    }
    
    public String logoutAmbito() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        configuracion=new Configuracion();
        return "/faces/Login.xhtml";

    }
    
    public String nombreUsuario() {
        if (this.recuperarParametroObject("usuario") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("Login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AccesoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "";
        } else {
            String temp = this.recuperarParametroObject("usuario").toString();
            return temp;
        }
    }
    
    public String empresaUsuario() {
        if (this.recuperarParametroObject("empresa") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("Login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AccesoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "";
        } else {
            String temp = this.recuperarParametroObject("empresa").toString();
            return temp;
        }
    }
    
}
