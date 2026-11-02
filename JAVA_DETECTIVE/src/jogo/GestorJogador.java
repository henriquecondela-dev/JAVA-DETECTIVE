package jogo;

import db.ConexaoMySQL;
import recursos.Pessoa;
import recursos.Jogador;
import recursos.ProgressoCaso;
import recursos.Etapa;
import java.sql.*;
import utils.Genero;
import java.util.List;
import java.util.ArrayList;
public class GestorJogador {

    public long criarJogador(String nome, int idade, Genero genero) throws SQLException {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if (idade <= 0 || idade > 120) {
            throw new IllegalArgumentException("Idade inválida.");
        }

        if (genero == null) {
            throw new IllegalArgumentException("Género obrigatório.");
        }

        try (Connection conexao = ConexaoMySQL.getConnection()) {

            conexao.setAutoCommit(false);

            try {
                long pessoaId = Pessoa.criarPessoa(
                        conexao,
                        nome.trim(),
                        idade,
                        genero.name(),
                        "Investigador"
                );

                long jogadorId = Jogador.criarJogador(
                        conexao,
                        pessoaId
                );

                conexao.commit();

                return jogadorId;

            } catch (SQLException | RuntimeException erro) {

                try {
                    conexao.rollback();
                } catch (SQLException falhaRollback) {
                    erro.addSuppressed(falhaRollback);
                }

                throw erro;
            }
        }
    }

    public utils.Jogador obterJogador(long jogadorId) throws SQLException {
        if (jogadorId <= 0) {
            throw new IllegalArgumentException(
                    "ID do jogador inválido."
            );
        }

        try (Connection conexao = ConexaoMySQL.getConnection()) {
            utils.Jogador jogador = recursos.Jogador.procurarJogadorPorId(conexao, jogadorId);

            if (jogador == null) {
                throw new IllegalArgumentException(
                        "Jogador não encontrado."
                );
            }

            return jogador;
        }
    }

    public utils.ProgressoCaso iniciarInvestigacao(long jogadorId, long casoId) throws SQLException {
        if (jogadorId <= 0 || casoId <= 0) {
            throw new IllegalArgumentException("Identificadores inválidos.");
        }

        try (Connection conexao = ConexaoMySQL.getConnection()) {

            conexao.setAutoCommit(false);

            try {
                // meio que estou a verificar se ja existe uma investicacao a decorer desse usuaio
                utils.ProgressoCaso progresso = ProgressoCaso.procurarProgresso(
                        conexao,
                        jogadorId,
                        casoId
                );

                if (progresso != null) {
                    conexao.commit();
                    return progresso;
                }

                long primeiraEtapaId = Etapa.obterPrimeiraEtapa(conexao, casoId);

                ProgressoCaso.criarProgresso(conexao, jogadorId, casoId, primeiraEtapaId);

                progresso = ProgressoCaso.procurarProgresso(conexao, jogadorId, casoId);

                if (progresso == null) {
                    throw new SQLException("Falha ao recuperar a investigação.");
                }

                conexao.commit();

                return progresso;

            } catch (SQLException | RuntimeException erro) {
                try {
                    conexao.rollback();
                } catch (SQLException falhaRollback) {
                    erro.addSuppressed(falhaRollback);
                }
                throw erro;
            }
        }
    }

    public utils.ProgressoCaso continuarInvestigacao(long jogadorId, long casoId) throws SQLException {
        if (jogadorId <= 0 || casoId <= 0) {
            throw new IllegalArgumentException("Identificadores inválidos.");
        }

        try (Connection conexao = ConexaoMySQL.getConnection()) {

            utils.ProgressoCaso progresso = ProgressoCaso.procurarProgresso(
                    conexao,
                    jogadorId,
                    casoId
            );

            if (progresso == null) {
                throw new IllegalStateException("Este jogador ainda não iniciou o caso.");
            }

            return progresso;
        }
    }

    public utils.Etapa obterEtapaAtual(long jogadorId, long casoId) throws SQLException {

        utils.ProgressoCaso progresso = continuarInvestigacao(jogadorId, casoId);

        long etapaId = progresso.getEtapaAtualId();

        try (Connection conexao = ConexaoMySQL.getConnection()) {

            utils.Etapa etapa = recursos.Etapa.procurarEtapaPorId(conexao, etapaId);

            if (etapa == null) {
                throw new IllegalStateException(
                        "Etapa actual não encontrada."
                );
            }

            return etapa;
        }
    }

    public utils.SessaoInvestigacao carregarSessao(long jogadorId, long casoId) throws SQLException {
        utils.Jogador jogador = obterJogador(jogadorId);
        utils.ProgressoCaso progresso = continuarInvestigacao(jogadorId, casoId);
        utils.Etapa etapa = obterEtapaAtual(jogadorId, casoId);
        return new utils.SessaoInvestigacao(
                jogador,
                progresso,
                etapa
        );
    }

    public utils.SessaoInvestigacao criarNovaInvestigacao(String nome, int idade, utils.Genero genero, long casoId
    ) throws SQLException {
        long jogadorId = criarJogador(nome, idade, genero);
        this.iniciarInvestigacao(jogadorId, casoId);
        return carregarSessao(
                jogadorId,
                casoId
        );
    }

    public List<utils.Jogador> listarJogadores()throws SQLException {
        try (Connection conexao= ConexaoMySQL.getConnection()) {
            return recursos.Jogador.listarJogadores(conexao);
        }
    }

}
