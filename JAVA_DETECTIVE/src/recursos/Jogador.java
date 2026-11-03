package recursos;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Jogador {

    public static long criarJogador(Connection conexao, long pessoaId) throws SQLException {
        String sql = """
                INSERT INTO jogador (pessoa_id)
                VALUES (?)
                """;

        try (PreparedStatement stmt = conexao.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {

            stmt.setLong(1, pessoaId);

            if (stmt.executeUpdate() != 1) {
                throw new SQLException("Erro ao criar jogador.");
            }

            try (ResultSet resultado = stmt.getGeneratedKeys()) {

                if (resultado.next()) {
                    return resultado.getLong(1);
                }

                throw new SQLException("ID do jogador não encontrado.");
            }
        }
    }

    public static utils.Jogador procurarJogadorPorId(Connection conexao, long jogadorId) throws SQLException {
        String sql = """
            SELECT
                j.id AS jogador_id,
                j.etapa_atual,
                j.pontuacao,
                p.id AS pessoa_id,
                p.nome,
                p.idade,
                p.descricao
            FROM jogador j
            INNER JOIN pessoa p
                ON j.pessoa_id = p.id
            WHERE j.id = ?
            """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, jogadorId);
            try (ResultSet resultado = stmt.executeQuery()) {
                if (!resultado.next()) {
                    return null;
                }

                long pessoaId = resultado.getLong("pessoa_id");
                long idJogador = resultado.getLong("jogador_id");

                String nome = resultado.getString("nome");
                int idade = resultado.getInt("idade");
                String descricao = resultado.getString("descricao");

                int etapaAtual = resultado.getInt("etapa_atual");
                int pontuacao = resultado.getInt("pontuacao");

                return new utils.Jogador(
                        pessoaId,
                        idJogador,
                        nome,
                        idade,
                        descricao,
                        etapaAtual,
                        pontuacao
                );
            }
        }
    }

    public static List<utils.Jogador> listarJogadores(Connection conexao) throws SQLException {

        List<utils.Jogador> jogadores = new ArrayList<>();

        String sql = """
            SELECT
                j.id AS jogador_id,
                j.etapa_atual,
                j.pontuacao,
                p.id AS pessoa_id,
                p.nome,
                p.idade,
                p.descricao
            FROM jogador j
            INNER JOIN pessoa p
                ON j.pessoa_id = p.id
            ORDER BY p.nome ASC
            """;

        try (PreparedStatement stmt
                = conexao.prepareStatement(sql); ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {

                utils.Jogador jogador = new utils.Jogador(
                        resultado.getLong("pessoa_id"),
                        resultado.getLong("jogador_id"),
                        resultado.getString("nome"),
                        resultado.getInt("idade"),
                        resultado.getString("descricao"),
                        resultado.getInt("etapa_atual"),
                        resultado.getInt("pontuacao")
                );

                jogadores.add(jogador);
            }
        }

        return jogadores;
    }

}
