/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author FranciscoJavier
 */
@ManagedBean
@SessionScoped
public class BaseController {

    /**
     * Creates a new instance of BaseController
     */
    public BaseController() {
    }
    
    /**
     * Obtiene la fecha y hora actual del sistema
     *
     * @author I.S.C. Francisco Javier Esparza Lopez
     * @return
     */
    public Date getDateNow() {
        Date now = null;
        Calendar currentDate = Calendar.getInstance();
        now = currentDate.getTime();
        return now;
    }
    
    public void subirParametro(String etiqueta, Object valor) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute(etiqueta, valor);
    }
    
    protected Object recuperarParametroObject(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object retorno = session.getAttribute(parametro);
        return retorno;
    }
    
    /**
     * Añade un mensaje de error a la jeraquia de componetes de la página JSF
     *
     * @param mensaje: mensaje de error a desplegar
     */
    public void msgError(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + mensaje, mensaje));
    }

    /**
     * Añade un mensaje arbitrario a la jeraquia de componetes de la página JSF
     *
     * @param titulo: Titulo del Mensaje a desplegar
     * @param mensaje: Mensaje a desplegar
     */
    public void msgOk(String titulo, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(titulo, mensaje));
    }

    /**
     * Añade un mensaje arbitrario a la jeraquia de componetes de la página JSF
     *
     * @param titulo: Titulo del Mensaje a desplegar
     * @param mensaje: Mensaje a desplegar
     */
    public void msgWarning(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", mensaje));
    }
    
}
