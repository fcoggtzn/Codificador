/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import servicio.entidad.Operador;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import servicio.servicio.OperadorFacadeLocal;

/**
 *
 * @author ovante
 */
@Named(value = "operadorMB")
@SessionScoped
public class OperadorMB implements Serializable {

    @EJB
    private OperadorFacadeLocal operadorFacade;
    private Operador operador; 
    private List<Operador> operadores;
    
@PostConstruct
    public void inicializa(){
        operadores = this.operadorFacade.findAll();
         operador= new Operador();
        operador.setIdOperador(0);
    }
    /**
     * Creates a new instance of OperadorMB
     */
    public OperadorMB() {
       operador = new Operador();
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public String getTextoBoton() {
        String textoBoton;
        textoBoton = "Nuevo";
        if (operador.getIdOperador() != 0)
            textoBoton ="Guardar";
        return textoBoton;
    }

    
    
    public void limpiar(){
       this.inicializa();
    }
    
    public void guardar(){
        if (operador.getIdOperador() == 0)
            this.operadorFacade.create(operador);
        else 
            this.operadorFacade.edit(operador);
        this.inicializa();
               

    }
    
    
    public void borrar(Operador operador){
        this.operadorFacade.remove(operador);
        this.inicializa();
    }
    
    

    public List<Operador> getOperadores() {
        return operadores;
    }

    public void setOperadores(List<Operador> operadores) {
        this.operadores = operadores;
    }
    
    public List<Operador> completeTexto(String value){
        return this.operadorFacade.findOperadors(value);
    }
    
    
    
}
