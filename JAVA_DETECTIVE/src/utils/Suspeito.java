/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Maunze
 */
public class Suspeito extends Pessoa {
    private String motivo;
    private double nivelSuspeita;
    Alibi alibi;
    
    public Suspeito (int id, int idade, String descricao, String nome, String motivo, double nivelSuspeita, Alibi alibi){
        super(id, idade, descricao, nome);
        this.motivo = motivo;
        this.nivelSuspeita=nivelSuspeita;
        this.alibi= alibi;
    }
    
    public void atualizarSuspeita(double nivel){
        nivelSuspeita = nivel;
    }
    
    public Alibi obterAlibi(){
        return alibi;
    }
}
