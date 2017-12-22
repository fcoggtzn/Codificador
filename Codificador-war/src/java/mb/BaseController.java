/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
        Logger.getLogger(FacturaMB.class.getName()).log(Level.WARNING, null, mensaje);

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
        Logger.getLogger(FacturaMB.class.getName()).log(Level.INFO, null, mensaje);

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

    public void handleFileUpload(FileUploadEvent event) {
        InputStream isrX = null;
        try {
            UploadedFile file = event.getFile();
            isrX = file.getInputstream();
            File archivo = new File("/Users/ovante/GlassFish_Server_Produccion/glassfish/domains/domain1/docroot/upload/"+file.getFileName());
            OutputStream outStream = new FileOutputStream(archivo);

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = isrX.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.close();
            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                isrX.close();
            } catch (IOException ex) {
                Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
