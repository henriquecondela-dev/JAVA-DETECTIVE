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
import java.sql.*;

public class Localizacao {
    public static int adicionarLocalizacao(String nome) throws SQLException {

        String sql = """
                INSERT INTO localizacao(nome)
                VALUES (?)
                """;

        try (Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, nome);
      

            int linhasAfetadas = statement.executeUpdate();
            //tippo se por acaso  nao for um alinha afectada
            if (linhasAfetadas != 1) {
                throw new SQLException("Não foi possível adicionar a localizacao");
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
    
    public static void listarLocalizacao() throws SQLException {

        String sql = """
                SELECT id, nome
                FROM localizacao
                ORDER BY id
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()
        ) {
            boolean encontrou = false;

            while (resposta.next()) {
                imprimirLocalizacao(resposta);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhuma localização registada.");
            }
        }
    }
    
    public static boolean buscarPorId(long id)
            throws SQLException {
        String sql = """
                SELECT id, nome
                FROM localizacao
                WHERE id = ?
                """;
      
        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirLocalizacao(resposta);
                    return true;
                }

                return false;
            }
        }
    }
    
     public static boolean atualizarLocalizacao(long id,String nome) throws SQLException {

        String sql = """
                UPDATE localizacao
                SET nome=?
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setString(1, nome);
            statement.setLong(2, id);
            
            return statement.executeUpdate() > 0;
        }
    }
     
    public static boolean eliminarLocalizacao(long id)
            throws SQLException {

        String sql = "DELETE FROM localizacao WHERE id = ?";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    private static void imprimirLocalizacao(ResultSet resposta)
            throws SQLException {

        System.out.println(
                resposta.getLong("id")+" | "
                + resposta.getString("nome")
        );
    }
}


