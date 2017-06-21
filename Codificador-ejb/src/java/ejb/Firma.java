/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import javax.ejb.Stateless;

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
           
         /*   String certPath = "mycert.pem";
            File pubCertFile = new File(certPath);*/
            BufferedInputStream bis = null;
      //      InputStream certResource = loader.getResourceAsStream("../../resources/"+rfc+"/"+rfc+".cer");
            InputStream keyResource = loader.getResourceAsStream("resources/"+rfc+"/"+rfc+".key");

            /*
                // bis = new BufferedInputStream(new FileInputStream(pubCertFile));
                bis = new BufferedInputStream(certResource);
            
            CertificateFactory certFact = null;
            Certificate cert = null;
            try {
                certFact = CertificateFactory.getInstance("X.509");
                cert = certFact.generateCertificate(bis);
            } catch (CertificateException e) {
                throw new Exception("Could not instantiate cert", e);
            }
            bis.close();
            ArrayList<Certificate> certs = new ArrayList<Certificate>();
            certs.add(cert);
*/
            /*String keyPath = "mykey.pem";
            File privKeyFile = new File(keyPath);*/
            /*try {
                bis = new BufferedInputStream(new FileInputStream(privKeyFile));
              
            } catch (FileNotFoundException e) {
                throw new Exception("Could not locate keyfile at '" + keyPath + "'", e);
            }*/
            bis = new BufferedInputStream(keyResource);

          /*  byte[] privKeyBytes = new byte[(int) privKeyFile.length()];*/
            byte[] privKeyBytes = new byte[keyResource.available()];
            bis.read(privKeyBytes);
            bis.close();
            KeyFactory keyFactory;
            //= KeyFactory.getInstance("RSA");
            
            KeySpec ks;
            
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(KeyBytes); 
            RSAPrivateKey privKey; 
             
            try {
                keyFactory = KeyFactory.getInstance("RSA");
                privateKey = keyFactory.generatePrivate(keySpec);
                algorithm = keyFactory.getAlgorithm();
                //algorithm = "RSA";
                //publicKey = keyFactory.generatePublic(keySpec);
            } catch (InvalidKeySpecException excep1) {
                try {
                    KeyFactory keyFactory = KeyFactory.getInstance("DSA");
                    privateKey = keyFactory.generatePrivate(keySpec);
                    algorithm = keyFactory.getAlgorithm();
                    //publicKey = keyFactory.generatePublic(keySpec);
                } catch (InvalidKeySpecException excep2) {

                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    privateKey = keyFactory.generatePrivate(keySpec);

                } // inner catch
            }
            
            
            
            ks = new PKCS8EncodedKeySpec(privKeyBytes);
            
            
            
                                
            privKey = (RSAPrivateKey) keyFactory.generatePrivate(ks);
            

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privKey);
            signature.update(cadenaOriginal);
            return signature.sign();
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e) {
            System.out.println(e);
        }
        return null;
    }
}
    
