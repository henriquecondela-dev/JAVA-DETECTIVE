/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import db.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author antonio-nuvunga
 */
public class Evidencia {
    
    public static int adicionarEvidencia(String titulo,String descricao,int documento_id,int importancia) throws SQLException {

        String sql = """
                INSERT INTO evidencia(titulo, descricao, documento_id,importancia)
                VALUES (?, ?, ?,?)
                """;

        try (Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, titulo);
            statement.setString(2, descricao);
            statement.setInt(3, documento_id);
            statement.setInt(4,importancia);

            int linhasAfetadas = statement.executeUpdate();
            //tippo se por acaso  nao for um alinha afectada
            if (linhasAfetadas != 1) {
                throw new SQLException("Não foi possível criar a evidencia.");
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
    
    public static void listarEvidencia() throws SQLException {

        String sql = """
                SELECT id, titulo, descricao, documento_id,importancia
                FROM evidencia
                ORDER BY id
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()
        ) {
            boolean encontrou = false;

            while (resposta.next()) {
                imprimirEvidencia(resposta);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhuma evidencia registada.");
            }
        }
    }
    
    
    
    public static boolean buscarPorId(int id)
            throws SQLException {
        String sql = """
                SELECT id, titulo, descricao, documento_id,importancia
                FROM evidencia
                WHERE id = ?
                """;
      
        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirEvidencia(resposta);
                    return true;
                }

                return false;
            }
        }
    }
    
    public static boolean atualizarEvidencia(int id,String titulo,String descricao,int documento_id,int importancia) throws SQLException {

        String sql = """
                UPDATE evidencia
                SET titulo=?, descricao=?, documento_id=?, importancia=?
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setString(1, titulo);
            statement.setString(2, descricao);
            statement.setInt(3, documento_id);
            statement.setInt(4,importancia);
            statement.setInt(5,id);

            return statement.executeUpdate() > 0;
        }
    }
    
    public static boolean eliminarEvidencia(int id)
            throws SQLException {

        String sql = "DELETE FROM evidencia WHERE id = ?";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    private static void imprimirEvidencia(ResultSet resposta)
            throws SQLException {

        System.out.println(
                resposta.getInt("id")+" | "
                + resposta.getString("titulo")+" | "
                + resposta.getString("descricao")+" | "
                + resposta.getInt("documento_id")+" | "
                + resposta.getInt("importancia")
        );
    }
    
}
            

