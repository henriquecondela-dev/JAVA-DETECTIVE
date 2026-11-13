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

    public utils.Etapa obterEtapaCompletaAtual(long jogadorId, long casoId) throws SQLException {

        utils.ProgressoCaso progresso
                = continuarInvestigacao(jogadorId, casoId);

        try (Connection conexao = ConexaoMySQL.getConnection()) {

            utils.Etapa etapa = recursos.Etapa.procurarEtapaCompletaPorId(conexao, progresso.getEtapaAtualId());

            if (etapa == null) {
                throw new IllegalStateException(
                        "Etapa actual não encontrada."
                );
            }

            return etapa;
        }
    }

    public utils.SessaoInvestigacao carregarSessaoCompleta(long jogadorId, long casoId) throws SQLException {
        utils.SessaoInvestigacao sessao = carregarSessao(jogadorId, casoId);

        if (sessao == null) {
            return null;
        }
        utils.Etapa etapaCompleta = obterEtapaCompletaAtual(jogadorId, casoId);

        if (etapaCompleta == null) {
            return null;
        }
        return new utils.SessaoInvestigacao(
                sessao.getJogador(),
                sessao.getProgresso(),
                etapaCompleta
        );
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

                long primeiraEtapaId = recursos.Etapa.obterPrimeiraEtapa(conexao, casoId);

                ProgressoCaso.criarProgresso(conexao, jogadorId, casoId, primeiraEtapaId);

                progresso = ProgressoCaso.procurarProgresso(conexao, jogadorId, casoId);

                if (progresso == null) {
                    throw new SQLException("Falha ao recuperar a investigação.");
                }

                long progressoCasoId = progresso.getId();
                List<Long> idsEvidencias = recursos.ProgressoEvidencia.buscarIdsEvidenciasPorEtapaId(conexao, primeiraEtapaId);

                for (long evidenciaId : idsEvidencias) {
                    boolean criado = recursos.ProgressoEvidencia.criarProgressoEvidencia(conexao, progressoCasoId, evidenciaId, false);
                    if (!criado) {
                        throw new SQLException("Erro ao inicializar progresso de evidencia.");
                    }
                }

                List<Long> idsDesafios = recursos.ProgressoDesafio.buscarIdsDesafiosPorEtapaId(conexao, primeiraEtapaId);
                for (long desafioId : idsDesafios) {
                    boolean criado = recursos.ProgressoDesafio.criarProgressoDesafio(conexao, progressoCasoId, desafioId, false);

                    if (!criado) {
                        throw new SQLException("Erro ao inicializar progresso de desafio.");
                    }
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
                throw new IllegalStateException("Etapa actual não encontrada.");
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

    public List<utils.Jogador> listarJogadores() throws SQLException {
        try (Connection conexao = ConexaoMySQL.getConnection()) {
            return recursos.Jogador.listarJogadores(conexao);
        }
    }

    public boolean podeAvancarEtapa(Connection conexao, long progressoId) throws SQLException {
        try {
            utils.ProgressoCaso progresso = ProgressoCaso.procurarProgressoPorId(conexao, progressoId);

            if (progresso == null) {
                throw new IllegalArgumentException("Progresso nao encontrado!");
            }

            long etapaAtual = progresso.getEtapaAtualId();
            long nrEvColectadas = recursos.ProgressoEvidencia.buscarNumeroEvidenciasColectadasPorEtapa(conexao, etapaAtual, progressoId);

            utils.Etapa etapa = recursos.Etapa.procurarEtapaPorId(conexao, etapaAtual);

            if (etapa == null) {
                throw new IllegalArgumentException("Etapa nao encontrada!");
            }

            int minimoEvidencias = etapa.getMinimoEvidencias();
            utils.ProgressoDesafio desafio = recursos.ProgressoDesafio.buscarProgressoDesafioPorEtapaId(conexao, etapaAtual, progressoId);
            boolean conclusao = desafio.estadoConclusao();
            boolean estadoGeral = false;
            if (nrEvColectadas < minimoEvidencias || conclusao == false) {
                estadoGeral = false;
            } else {
                estadoGeral = true;
            }
            return estadoGeral;
        } catch (SQLException | RuntimeException erro) {
            try {
                conexao.rollback();
            } catch (SQLException falhaRollback) {
                erro.addSuppressed(falhaRollback);
            }
            throw erro;
        }
    }

    public TipoAvanco avancarEtapa(long progressoId) throws SQLException {
        try (Connection conexao = ConexaoMySQL.getConnection()) {
            conexao.setAutoCommit(false);
            if (podeAvancarEtapa(conexao, progressoId)) {
                try {
                    utils.ProgressoCaso progresso = ProgressoCaso.procurarProgressoPorId(conexao, progressoId);

                    if (progresso == null) {
                        throw new IllegalArgumentException("Progresso nao encontrado!");
                    }

                    int etapaAtualId = (int) progresso.getEtapaAtualId();

                    utils.Etapa etapaAtual = recursos.Etapa.procurarEtapaPorId(conexao, etapaAtualId);

                    if (etapaAtual == null) {
                        throw new IllegalArgumentException("Etapa atual nao encontrada!");
                    }

                    utils.Etapa seguinte = recursos.Etapa.identificarEtapaSeguinte(conexao, progresso.getCasoId(), etapaAtual.getNumero());

                    boolean existeEtapaSeguinte = (seguinte == null) ? false : true;

                    if (existeEtapaSeguinte) {
                        recursos.ProgressoCaso.atualizarEtapaAtual(conexao, progresso.getId(), seguinte.getId());

                        List<utils.Evidencia> evidencias = recursos.ProgressoEvidencia.buscarEvidenciasPorEtapa(conexao, seguinte.getId());

                        for (utils.Evidencia evidencia : evidencias) {
                            boolean criado = recursos.ProgressoEvidencia.criarProgressoEvidencia(conexao, progressoId, evidencia.getId(), false);
                            if (!criado) {
                                throw new SQLException("Erro ao inicializar prohresso de evidencia!");
                            }
                        }

                        List<utils.DesafioDeProgramacao> desafios = recursos.ProgressoDesafio.buscarDesafiosPorEtapaId(conexao, seguinte.getId());

                        for (utils.DesafioDeProgramacao desafio : desafios) {
                            boolean criado = recursos.ProgressoDesafio.criarProgressoDesafio(conexao, progressoId, desafio.getId(), false);
                            if (!criado) {
                                throw new SQLException("Erro ao inicializar prohresso de desafio!");
                            }
                        }
                        //Avanço que ser
                        conexao.commit();
                        return TipoAvanco.AVANCO;
                    } else {
                        //Estado que representa a conclusao do caso
                        conexao.rollback();
                        return TipoAvanco.CONCLUIDO;
                    }
                } catch (SQLException | RuntimeException erro) {
                    try {
                        conexao.rollback();
                    } catch (SQLException falhaRollback) {
                        erro.addSuppressed(falhaRollback);
                    }
                    throw erro;
                }
            } else {
                //Representaçá~o de requisits não cumpridosã
                conexao.rollback();
                return TipoAvanco.REQUISITOS_NAO_CUMPRIDOS;
            }
        }
    }

}
