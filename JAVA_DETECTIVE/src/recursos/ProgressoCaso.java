/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

/**
 *
 * @author henrique-dev
 */

import java.sql.*;

public class ProgressoCaso {

    public static utils.ProgressoCaso procurarProgresso(
            Connection conexao,
            long jogadorId,
            long casoId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    jogador_id,
                    caso_id,
                    etapa_atual_id,
                    pontuacao
                FROM progressoCaso
                WHERE jogador_id = ?
                  AND caso_id = ?
                """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, jogadorId);
            stmt.setLong(2, casoId);

            try (ResultSet resultado = stmt.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                return new utils.ProgressoCaso(
                        resultado.getLong("id"),
                        resultado.getLong("jogador_id"),
                        resultado.getLong("caso_id"),
                        resultado.getLong("etapa_atual_id"),
                        resultado.getInt("pontuacao")
                );
            }
        }
    }

    public static long criarProgresso( Connection conexao, long jogadorId,long casoId, long etapaId) 
            throws SQLException {
        String sql = """
                INSERT INTO progressoCaso
                    (jogador_id, caso_id, etapa_atual_id)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement stmt = conexao.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS )) {
            stmt.setLong(1, jogadorId);
            stmt.setLong(2, casoId);
            stmt.setLong(3, etapaId);

            if (stmt.executeUpdate() != 1) {
                throw new SQLException( "Não foi possível criar o progresso.");
            }

            try (ResultSet resultado = stmt.getGeneratedKeys()) {

                if (resultado.next()) {
                    return resultado.getLong(1);
                }
            }
        }

        throw new SQLException( "ID do progresso não encontrado.");
    }
    
    public static void atualizarEtapaAtual(Connection conexao, long progressoId, long etapaId) throws SQLException {
        String sql = """
                update progressoCaso
                     set etapa_atual_id =?
                     where id=?
                """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, etapaId);
            stmt.setLong(2, progressoId);

            if (stmt.executeUpdate() != 1) {
                throw new SQLException( "Não foi possível atualizar a etapa.");
            }
        }
    }
    
    public static utils.ProgressoCaso procurarProgressoPorId(Connection conexao, long progressoId) throws SQLException {
        String sql = """
                SELECT id, jogador_id, caso_id, etapa_atual_id, pontuacao
                FROM progressoCaso
                WHERE id = ?;
                """;

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, progressoId);

            try (ResultSet resposta = statement.executeQuery()) {

                if (resposta.next()) {
                    return new utils.ProgressoCaso(
                        resposta.getLong("id"),
                        resposta.getLong("jogador_id"),
                        resposta.getLong("caso_id"),
                        resposta.getLong("etapa_atual_id"),
                        resposta.getInt("pontuacao")
                );
                }
                return null;
            }
        }
    }
    
    public static boolean atualizarPontuacao(Connection conexao, long progressoId, int pontuacao) throws SQLException {

        if (progressoId <= 0) {
            throw new IllegalArgumentException("ID do progresso inválido.");
        }

        if (pontuacao < 0 || pontuacao > 100) {
            throw new IllegalArgumentException(
                    "A pontuação deve estar entre 0 e 100."
            );
        }

        String sql = """
                UPDATE progressoCaso
                SET pontuacao = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, pontuacao);
            statement.setLong(2, progressoId);

            return statement.executeUpdate() > 0;
        }
    }
    
}
