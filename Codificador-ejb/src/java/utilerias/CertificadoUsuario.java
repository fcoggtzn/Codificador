/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franciscogutierrez
 */
public class CertificadoUsuario  implements Serializable {
    private byte[] llave;
    private byte[] certificado;
    private char[] clave;

    public byte[] getLlave() {
        return llave;
    }

    public void setLlave(byte[] llave) {
        this.llave = llave;
    }

    public byte[] getCertificado() {
        return certificado;
    }

    public void setCertificado(byte[] certificado) {
        this.certificado = certificado;
    }

    public char[] getClave() {
        return clave;
    }

    public void setClave(char[] clave) {
        this.clave = clave;
    }
    
     public String getBase64Llave() {
        return Base64.encode(llave);
    }

   

    public String getBase64Certificado() {
        return Base64.encode(certificado);
    }

   

  public CertificadoUsuario(String rfc){
        try {
            BufferedInputStream bis ;
            InputStream keyResource = CertificadoUsuario.class.getResourceAsStream("../resources/" + rfc + "/" + rfc + ".key");
            bis = new BufferedInputStream(keyResource);
            llave = new byte[keyResource.available()];
            bis.read(llave);
            bis.close();
            keyResource = CertificadoUsuario.class.getResourceAsStream("../resources/" + rfc + "/" + rfc + ".cer");
            bis = new BufferedInputStream(keyResource);
            certificado = new byte[keyResource.available()];
            bis.read(certificado);
            bis.close();
           
           
            clave = getClave(rfc).toCharArray();
            
        } catch (IOException ex) {
            Logger.getLogger(CertificadoUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
  }    
  
  private String getClave(String rfc) {
        StringBuilder result = new StringBuilder("");
        
        File file = new File( CertificadoUsuario.class.getResource("../resources/" + rfc + "/clave.txt").getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();}
      
}
