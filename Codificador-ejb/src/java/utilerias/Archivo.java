/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franciscogutierrez
 */
public class Archivo {
    
    
    public byte[] leerBytes(String ficheroOriginal) {
        try {
            // Se abre el fichero original para lectura
            FileInputStream fileInput = new FileInputStream(ficheroOriginal);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);


            // Bucle para leer de un fichero y escribir en el otro.
            byte[] array = new byte[2500];
            int leidos = bufferedInput.read(array);
            byte[] retorno = new byte[leidos];
            System.arraycopy(array, 0, retorno, 0, leidos);

            // Cierre de los ficheros
            bufferedInput.close();
            return retorno;
        } catch (Exception e) {
        }
        return null;
    }
    
    public void grabarQRCode(String file,String codigo) {
        OutputStream out = null;
        try {
           

            out = new FileOutputStream(file);
            out.write(Base64.getDecoder().decode(codigo));
            out.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
    public String LeerString(String archivo) {
        BufferedReader in = null;
        String cadena = "";
        String s = "";
        try {
            File file = new File(archivo);

            //in = new BufferedReader(new FileReader(file));
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            s = in.readLine();
            while (s != null) {
                cadena += s;
                s = in.readLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //     comprobante.setCadenaOriginal(cadena); fijar cadena original
        return cadena;
    }
}
