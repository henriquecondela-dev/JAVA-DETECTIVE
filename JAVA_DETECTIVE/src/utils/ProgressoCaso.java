/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author henrique-dev
 */
public class ProgressoCaso {

    private long id;
    private long jogadorId;
    private long casoId;
    private long etapaAtualId;
    private int pontuacao;

    public ProgressoCaso(
            long id,
            long jogadorId,
            long casoId,
            long etapaAtualId,
            int pontuacao
    ) {
        this.id = id;
        this.jogadorId = jogadorId;
        this.casoId = casoId;
        this.etapaAtualId = etapaAtualId;
        this.pontuacao = pontuacao;
    }

    public long getId() {
        return id;
    }

    public long getJogadorId() {
        return jogadorId;
    }

    public long getCasoId() {
        return casoId;
    }

    public long getEtapaAtualId() {
        return etapaAtualId;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setEtapaAtualId(long etapaAtualId) {
        if (etapaAtualId <= 0) {
            throw new IllegalArgumentException( "ID da etapa inválido." );
        }

        this.etapaAtualId = etapaAtualId;
    }

    public void setPontuacao(int pontuacao) {
        if (pontuacao < 0) {
            throw new IllegalArgumentException( "A pontuação não pode ser negativa.");
        }

        this.pontuacao = pontuacao;
    }
}

