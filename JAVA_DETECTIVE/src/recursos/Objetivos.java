

/**
 *
 * @author henrique-dev
 */
package recursos;

import db.ConexaoMySQL;
import java.sql.*;

public class Objetivos {

    // CREATE 
    public static long criarObjetivo(
            long etapaId,
            String descricao
    ) throws SQLException {

        // Os campos minimo_relacoes_codigo e concluido
        // recebem os valores DEFAULT definidos na tabela.
        String sql = """
                INSERT INTO objetivo (etapa_id, descricao)
                VALUES (?, ?)
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setLong(1, etapaId);
            statement.setString(2, descricao);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas != 1) {
                throw new SQLException(
                        "Não foi possível criar o objetivo."
                );
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

    // READ 
    public static void listarObjetivos() throws SQLException {

        String sql = """
                SELECT id, etapa_id, descricao,
                       minimo_relacoes_codigo, concluido
                FROM objetivo
                ORDER BY id
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()
        ) {
            boolean encontrou = false;

            while (resposta.next()) {
                imprimirObjetivo(resposta);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhum objetivo registado.");
            }
        }
    }

    // READ 
    public static boolean buscarObjetivoPorId(long id)
            throws SQLException {

        String sql = """
                SELECT id, etapa_id, descricao,
                       minimo_relacoes_codigo, concluido
                FROM objetivo
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirObjetivo(resposta);
                    return true;
                }

                return false;
            }
        }
    }

    // UPDATE 
    public static boolean atualizarObjetivo(
            long id,
            long etapaId,
            String descricao,
            int minimoRelacoesCodigo,
            boolean concluido
    ) throws SQLException {

        String sql = """
                UPDATE objetivo
                SET etapa_id = ?,
                    descricao = ?,
                    minimo_relacoes_codigo = ?,
                    concluido = ?
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, etapaId);
            statement.setString(2, descricao);
            statement.setInt(3, minimoRelacoesCodigo);
            statement.setBoolean(4, concluido);
            statement.setLong(5, id);

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE 
    public static boolean eliminarObjetivo(long id)
            throws SQLException {

        String sql = "DELETE FROM objetivo WHERE id = ?";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    // Auxiliar 
    private static void imprimirObjetivo(ResultSet resposta)
            throws SQLException {

        System.out.println(
                "ID: " + resposta.getLong("id")
                + "\nEtapa ID: " + resposta.getLong("etapa_id")
                + "\nDescrição: " + resposta.getString("descricao")
                + "\nMínimo de relações de código: "
                + resposta.getObject("minimo_relacoes_codigo")
                + "\nConcluído: "
                + resposta.getObject("concluido")
                + "\n--------------------------------"
        );
    }
}