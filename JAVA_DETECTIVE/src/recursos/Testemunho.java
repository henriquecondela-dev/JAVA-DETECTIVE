/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

/**
 *
 * @author antonio-nuvunga
 */
import db.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author clarson
 */
public class Testemunho {
    
    public static int adicionarTestemunho(long testemunha_id, String descricao, String data_hora
    ) throws SQLException {

        String sql = """
                INSERT INTO testemunho (testemunha_id, descricao, data_hora)
                VALUES (?, ?, ?)
                """;

        try (Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setLong(1, testemunha_id);
            statement.setString(2, descricao);
            statement.setString(3, data_hora);

            int linhasAfetadas = statement.executeUpdate();
            //tippo se por acaso  nao for um alinha afectada
            if (linhasAfetadas != 1) {
                throw new SQLException("Não foi possível criar o testemunho.");
            }
            
            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    return chaves.getInt(1);
                }

                throw new SQLException(
                        "O INSERT foi executado, mas não foi possível obter o ID."
                );
            }
        }
    }

    // READ — lista todos os testemunhos, ......
    public static void listarTestemunhos() throws SQLException {

        String sql = """
                SELECT id, testemunha_id, descricao, data_hora
                FROM testemunho
                ORDER BY id
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()
        ) {
            boolean encontrou = false;

            while (resposta.next()) {
                imprimirTestemunho(resposta);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhum testemunho registado.");
            }
        }
    }

    // READ — procura pelo ID da tabela testemunho., mas depois imprime o tal suspeito
    public static boolean buscarTestemunhoPorId(long id)
            throws SQLException {
        String sql = """
                SELECT id, testemunha_id, descricao, data_hora
                FROM testemunho
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirTestemunho(resposta);
                    return true;
                }

                return false;
            }
        }
    }

    // UPDATE — altera os dados do testemunho identificado pelo ID.
    public static boolean atualizarTestemunho(long id, long testemunha_id, String descricao, String data_hora) throws SQLException {
        String sql = """
                UPDATE testemunho
                SET testemunha_id = ?, descricao = ?, data_hota = ?
                WHERE id = ?
                """;
        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            statement.setLong(2, testemunha_id);
            statement.setString(3, descricao);
            statement.setString(4, data_hora);
            statement.setLong(5, id);

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE — elimina o registo da tabela testemunho.
    public static boolean eliminarTestemunho(long id)
            throws SQLException {

        String sql = "DELETE FROM testemunho WHERE id = ?";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    // esse e auxilir tipo para evitar repetir malta system em cada metdod.
    private static void imprimirTestemunho(ResultSet resposta)
            throws SQLException {

        System.out.println(
                resposta.getLong("id") + " | "
                + resposta.getInt("testemunha_id") + " | "
                + resposta.getString("descricao") + " | "
                + resposta.getString("data_hora")
        );
    }
    
}
