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
}
