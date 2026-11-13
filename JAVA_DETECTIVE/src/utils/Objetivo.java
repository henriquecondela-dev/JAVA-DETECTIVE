/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author henrique-dev
 */
public class Objetivo {

    private long id;
    private long etapaId;
    private String descricao;
    private int minimoRelacoesCodigo;
    private boolean concluido;

    public Objetivo() {
    }

    public Objetivo( long id,long etapaId,String descricao,int minimoRelacoesCodigo, boolean concluido ) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "Descrição do objectivo obrigatória."
            );
        }

        if (minimoRelacoesCodigo < 0) {
            throw new IllegalArgumentException(
                    "O mínimo de relações não pode ser negativo."
            );
        }

        this.id = id;
        this.etapaId = etapaId;
        this.descricao = descricao;
        this.minimoRelacoesCodigo = minimoRelacoesCodigo;
        this.concluido = concluido;
    }

    public long getId() {
        return id;
    }

    public long getEtapaId() {
        return etapaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getMinimoRelacoesCodigo() {
        return minimoRelacoesCodigo;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
}
