/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.apache.commons.ssl.PKCS8Key;

/**
 *
 * @author franciscogutierrez
 */
@Stateless
public class Firma implements FirmaLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public byte[] firmar(byte[] cadenaOriginal, String rfc) {
        /*falta el proceso de obtener Certificados y Sellos del rfc en cuestion */
        try {
            
            ClassLoader loader = Firma.class.getClassLoader(); 
           
       
            BufferedInputStream bis = null;
            InputStream keyResource = loader.getResourceAsStream("resources/"+rfc+"/"+rfc+".key");

            bis = new BufferedInputStream(keyResource);

            byte[] privKeyBytes = new byte[keyResource.available()];
            bis.read(privKeyBytes);
            bis.close();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
               PKCS8Key pkcs8;
            pkcs8 = new PKCS8Key(privKeyBytes, "12345678a".toCharArray());
        PrivateKey privKey = pkcs8.getPrivateKey();       
        
        
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privKey);
            signature.update(cadenaOriginal);
            return signature.sign();
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e) {
            System.out.println(e);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(Firma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
    
