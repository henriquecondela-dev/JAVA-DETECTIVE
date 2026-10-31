package utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andrea
 */
public class Local {
    private int id;
    private String nome;
    
    
    
    public boolean adicionarEvento(){
        
    }
    
    public boolean obterEvento(String id){
        try{   
            int num=Integer.parseInt(id);
            
        }catch(NumberFormatException e){
        
        }
        
    }

    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }
    
    
    
    
}
