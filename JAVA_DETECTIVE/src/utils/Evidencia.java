/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author antonio-nuvunga
 */
public class Evidencia {
    private int id;
    private String titulo;
    private String descricao;
    private Documento documento;
    private Importancia importancia;
    private boolean coletada=false;
    
    public void marcarComoColetada(int id){
        if(this.coletada==false){
            this.coletada=true;
        }
        else{
            System.out.println("Essa evidencia já foi coletada");
        }    
    }
    
    public boolean estaColetada(){
        if(this.coletada==true){
            return true;
        }
        return false;
    }

    public String estadoColeta(){
        if(this.estaColetada()==true){
            return "Evidencia já colectada";
        }
        return "Evidência não colectada";
    }
}
