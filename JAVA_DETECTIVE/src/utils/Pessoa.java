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

    private long id;
    private int idade;
    private String descricao;
    private String nome;
    private ArrayList<String> eventos;

    public Pessoa(long id, int idade, String descricao, String nome) {
        this.id = id;
        this.idade = idade;
        this.descricao=descricao;
        if(nome==null || nome.isBlank()){
          throw new IllegalArgumentException("Título de etapa obrigatório!");
        }
        this.nome=nome;
        this.eventos= new ArrayList<>();
    }

    public void adicionarEventos(String evento){
        getEventos().add(evento);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String obterIdentificao() {
        return getNome();
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
        if(nome==null || nome.isBlank()){
          throw new IllegalArgumentException("Título de etapa obrigatório!");
        }
        this.nome = nome;
    }

    /**
     * @return the eventos
     */
    public ArrayList<String> getEventos() {
        return eventos;
    }

}
