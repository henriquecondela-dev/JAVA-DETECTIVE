package jogo;

import db.ConexaoMySQL;
import recursos.Pessoa;
import recursos.Jogador;

import java.sql.*;

public class GestorJogador {

    public long criarJogador( String nome, int idade,  String genero ) throws SQLException {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if (idade <= 0 || idade > 120) {
            throw new IllegalArgumentException("Idade inválida.");
        }

        if (!"M".equals(genero) && !"F".equals(genero)) {
            throw new IllegalArgumentException("Género inválido.");
        }

        try (Connection conexao = ConexaoMySQL.getConnection()) {

            conexao.setAutoCommit(false);

            try {
                long pessoaId = Pessoa.criarPessoa(
                        conexao,
                        nome.trim(),
                        idade,
                        genero,
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
}
