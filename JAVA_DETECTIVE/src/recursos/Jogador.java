package recursos;

import java.sql.*;

public class Jogador {

    public static long criarJogador(
            Connection conexao,
            long pessoaId
    ) throws SQLException {

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
}