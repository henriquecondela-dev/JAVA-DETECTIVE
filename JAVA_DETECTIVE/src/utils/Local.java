/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.util.ArrayList;
/**
 *
 * @author Maunze
 */
public class Local {
   private int id;
   private String nome;
   private String descricao;
   private String tipo;
   ArrayList<String> eventos;
   
   public Local(int id, String nome, String descricao, String tipo){
       this.id= id;
       this.nome= nome;
       this.descricao = descricao;
       this.tipo= tipo;
       eventos = new ArrayList<>();
   }
   
   public void adicionarEvento(String evento){
       eventos.add(evento);
   }
   public ArrayList<String> obterEventos(){
       return eventos;
   }
}
