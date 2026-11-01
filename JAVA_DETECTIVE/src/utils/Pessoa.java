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
public class Pessoa {
    private int id;
    private int idade;
    private String descricao;
    private String nome;
    private ArrayList<String> eventos;
    
    public Pessoa(int id, int idade, String descricao, String nome){
        this.id=id;
        this.idade = idade;
        this.descricao=descricao;
        this.nome=nome;
        this.eventos= new ArrayList<>();
    }
    public String obterIdentificao(){
        return getNome();
    }
    public void adicionarEventos(String evento){
        getEventos().add(evento);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idade
     */
    public int getIdade() {
        return idade;
    }

    /**
     * @param idade the idade to set
     */
    public void setIdade(int idade) {
        this.idade = idade;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the eventos
     */
    public ArrayList<String> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(ArrayList<String> eventos) {
        this.eventos = eventos;
    }
}
