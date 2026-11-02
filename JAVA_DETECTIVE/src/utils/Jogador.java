/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author henrique-dev
 */

public class Jogador extends Pessoa {

    private long jogadorId;
    private int pontuacao;
    private int etapaAtual;

    public Jogador(
            long pessoaId,
            long jogadorId,
            String nome,
            int idade,
            String descricao,
            int etapaAtual,
            int pontuacao
    ) {

        super(pessoaId, idade, descricao, nome);

        this.jogadorId = jogadorId;
        this.etapaAtual = etapaAtual;
        this.pontuacao = pontuacao;
    }

   
    public long getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(long jogadorId) {
        this.jogadorId = jogadorId;
    }


    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    // Número da etapa actual
    public int getEtapaAtual() {
        return etapaAtual;
    }

    public void setEtapaAtual(int etapaAtual) {
        this.etapaAtual = etapaAtual;
    }
    
    @Override
    public String obterIdentificao() {
        return "Investigador: " + getNome();
    }
}

