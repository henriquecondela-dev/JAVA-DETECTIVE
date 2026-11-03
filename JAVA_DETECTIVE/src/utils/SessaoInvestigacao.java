/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author henrique-dev
 */

public class SessaoInvestigacao {

    private final Jogador jogador;
    private final ProgressoCaso progresso;
    private final Etapa etapaAtual;

    public SessaoInvestigacao(Jogador jogador,ProgressoCaso progresso, Etapa etapaAtual ) {
        if (jogador == null ||progresso == null || etapaAtual == null) {
            throw new IllegalArgumentException("A sessão não pode conter dados nulos.");
        }

        if (jogador.getJogadorId()!= progresso.getJogadorId()) {
            throw new IllegalArgumentException("O progresso não pertence ao jogador.");
        }

        if (etapaAtual.getId()!= progresso.getEtapaAtualId()) {
            throw new IllegalArgumentException("A etapa não corresponde ao progresso.");
        }
        this.jogador = jogador;
        this.progresso = progresso;
        this.etapaAtual = etapaAtual;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public ProgressoCaso getProgresso() {
        return progresso;
    }
    public Etapa getEtapaAtual() {
        return etapaAtual;
    }
    public String obterResumo() {
        return "Investigador: " + jogador.getNome()
                + "\nEtapa: " + etapaAtual.getNumero()
                + "\nTítulo: " + etapaAtual.getTitulo();
    }
}
