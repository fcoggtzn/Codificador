/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilerias;

import java.text.DecimalFormat;

/**
 *
 * @author francisco
 */
public class NumeroALetras {    
    private long  parteEntera;  //para la parte entera
    private String parteCentavos; //para los centavos
    private String letras ="";

    public NumeroALetras(double numero){
        DecimalFormat twoDForm = new DecimalFormat("#.##");
	 numero=Double.valueOf(twoDForm.format(numero));

        parteEntera = (long)numero;
        parteCentavos = (long)Math.round((numero - parteEntera)*100 ) +"";
        if(parteCentavos.length() == 1)
             parteCentavos ="0"+parteCentavos;

        // letras = millones(parteEntera);
    }

    public void millones(long numero){
        int millones = (((int) (Math.floor(numero) % 100000000 )))/1000000;
        centenas(millones);
        if (millones == 1){
            letras +=" millon";
        }
        else
         if (millones != 0){
            letras +=" millones ";
         }
        miles(numero);
        
    }

    public void miles(long numero){
      int miles = (((int) (Math.floor(numero) % 1000000 )))/1000;
      switch (miles){
            case 1:letras +=" mil";break;
            case 100:letras +=" cien mil";break;
           default:
                    if (miles !=0) {
                    centenas(miles);
                    letras +=" mil";}
                    break;
      }
      centenas(numero);
    }

    public void centenas(long numero){
        if (numero !=100){
            int centenas = (((int) (Math.floor(numero) % 1000 )))/100;
            switch (centenas){
            case 1:letras+=" ciento" ;break;
            case 2:letras+=" doscientos" ;break;
            case 3:letras+=" trescientos" ;break;
            case 4:letras+=" cuatrocientos" ;break;
            case 5:letras+=" quinientos" ;break;
            case 6:letras+=" seiscientos" ;break;
            case 7:letras+=" setecientos" ;break;
            case 8:letras+=" ochocientos" ;break;
            case 9:letras+=" novecientos" ;break;
            }
        
             decenas(numero);
        }
        else
        {
            letras+=" cien";
        }


    }


    public void decenas(long numero){
             int unidades = (int) (Math.floor(numero) % 10 );
         int decenas = (((int) (Math.floor(numero) % 100 ))) /10;
      switch (decenas){
        case 1: switch(unidades){
                          case 0: letras +=" diez";unidades(unidades);break;
                          case 6: letras += " dieciséis";break;
                          case 7:letras +=" diecisiete" ;break;
                          case 8:letras +=" dieciocho";break;
                          case 9: letras +=" diez y nueve";break;
                          case 1: letras+=" once";break;
                          case 2: letras+=" doce";break;
                          case 3: letras+=" trece";break;
                          case 4: letras+=" catorce";break;
                          case 5: letras+=" quince";break;


                      };break;
        case 2: letras +=" veinte";
        if (unidades != 0){
                letras +=" y";
                unidades(unidades);
            }
            ;
            break;
        case 3: letras +=" treinta";
        if (unidades != 0){
                letras +=" y";
                unidades(unidades);
            }
            ;
            break;
        case 4: letras +=" cuarenta";
        if (unidades != 0){
                letras +=" y";
                unidades(unidades);
            }
            ;
            break;
        case 5: letras +=" cincuenta";
        if (unidades != 0){
                letras +=" y";
                unidades(unidades);
            }
            ;
            break;
        case 6: letras +=" sesenta";
        if (unidades != 0){
                letras +=" y";
                unidades(unidades);
            }
            ;
            break;
        case 7: letras +=" setenta";
        if (unidades != 0){
                letras +=" y";
                unidades(unidades);
            }
            ;
            break;
        case 8: letras += " ochenta";
        if (unidades != 0){
                letras +=" y";
                unidades(unidades);
            }
            ;
            break;
        case 9: letras += " noventa";
               if (unidades != 0){
                letras +=" y";
                unidades(unidades);
            }
            ;
            break;
        case 0:
                unidades(unidades);
            ;
            break;
      }
    }



    public void unidades(long numero){
        int num = (int) (Math.floor(numero) % 10 );
      switch (num){        
        case 1: letras +=" un";break;
        case 2: letras +=" dos";break;
        case 3: letras +=" tres";break;
        case 4: letras +=" cuatro";break;
        case 5: letras +=" cinco";break;
        case 6: letras +=" seis";break;
        case 7: letras +=" siete";break;
        case 8: letras += " ocho"; break;
        case 9: letras += " nueve"; break;        
      }        
    }


    @Override
    public String toString(){
        letras="";
        millones(parteEntera);
        letras +=" pesos con ";
        //decenas (parteCentavos);
        letras += parteCentavos+ "/100 centavos  M.N";
        return letras.toUpperCase();
    }


}
