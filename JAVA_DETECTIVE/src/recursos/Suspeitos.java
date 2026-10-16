package recursos;

import db.ConexaoMySQL;
import java.sql.*;

public class Suspeitos {

    // esse e o um create
    public static long criarSuspeito(int pessoaId,int casoId,String motivo
    ) throws SQLException {

        String sql = """
                INSERT INTO suspeito (pessoa_id, caso_id, motivo)
                VALUES (?, ?, ?)
                """;

        try (Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setLong(1, pessoaId);
            statement.setLong(2, casoId);
            statement.setString(3, motivo);

            int linhasAfetadas = statement.executeUpdate();
            //tippo se por acaso  nao for um alinha afectada
            if (linhasAfetadas != 1) {
                throw new SQLException("Não foi possível criar o suspeito.");
            }
            
            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    return chaves.getLong(1);
                }

                throw new SQLException(
                        "O INSERT foi executado, mas não foi possível obter o ID."
                );
            }
        }
    }

    // READ — lista todos os suspeitos, ......
    public static void listarSuspeitos() throws SQLException {

        String sql = """
                SELECT id, pessoa_id, caso_id, motivo
                FROM suspeito
                ORDER BY id
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()
        ) {
            boolean encontrou = false;

            while (resposta.next()) {
                imprimirSuspeito(resposta);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhum suspeito registado.");
            }
        }
    }

    // READ — procura pelo ID da tabela suspeito., mas depois imprime o tal suspeito
    public static boolean buscarSuspeitoPorId(long id)
            throws SQLException {
        String sql = """
                SELECT id, pessoa_id, caso_id, motivo
                FROM suspeito
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirSuspeito(resposta);
                    return true;
                }

                return false;
            }
        }
    }

    // UPDATE — altera os dados do suspeito identificado pelo ID.
    public static boolean atualizarSuspeito(
            long id,
            long pessoaId,
            long casoId,
            String motivo
    ) throws SQLException {

        String sql = """
                UPDATE suspeito
                SET pessoa_id = ?, caso_id = ?, motivo = ?
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, pessoaId);
            statement.setLong(2, casoId);
            statement.setString(3, motivo);
            statement.setLong(4, id);

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE — elimina o registo da tabela suspeito.
    public static boolean eliminarSuspeito(long id)
            throws SQLException {

        String sql = "DELETE FROM suspeito WHERE id = ?";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    // esse e auxilir tipo para evitar repetir malta system em cada metdod.
    private static void imprimirSuspeito(ResultSet resposta)
            throws SQLException {

        System.out.println(
                resposta.getLong("id") + " | "
                + resposta.getLong("pessoa_id") + " | "
                + resposta.getLong("caso_id") + " | "
                + resposta.getString("motivo")
        );
    }
}