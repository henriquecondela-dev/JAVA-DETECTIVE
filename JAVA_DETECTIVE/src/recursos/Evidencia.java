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
    
    public static int adicionarEvidencia(String titulo, String descricao, Long documento_id,int importancia) throws SQLException {

        String sql = """
                INSERT INTO evidencia(titulo, descricao, documento_id,importancia,coletado)
                VALUES (?, ?, ?,?,?)
                """;//adicionei coletado

        try (Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, titulo);
            statement.setString(2, descricao);
            statement.setLong(3, documento_id);
            statement.setInt(4,importancia);
            statement.setBoolean(5,false);//adicionei coletado

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
            //adicionei coletado
        String sql = """
                SELECT id, titulo, descricao, documento_id,importancia,coletado
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
    
    
    
    public static boolean buscarPorId(long id)
            throws SQLException {
        String sql = """
                SELECT id, titulo, descricao, documento_id,importancia,coletado
                FROM evidencia
                WHERE id = ?
                """;
                //adicionei coletado
      
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
    
    public static boolean atualizarEvidencia(long id,String titulo,String descricao,Long documento_id,int importancia,boolean coletado) throws SQLException {

        String sql = """
                UPDATE evidencia
                SET titulo=?, descricao=?, documento_id=?, importancia=?, coletado=?
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            
            statement.setString(1, titulo);
            statement.setString(2, descricao);
            statement.setLong(3, documento_id);
            statement.setInt(4,importancia);
            statement.setBoolean(5,coletado);
            statement.setLong(6,id);

            return statement.executeUpdate() > 0;
        }
    }
    
    public static boolean eliminarEvidencia(long id)throws SQLException {

        String sql = "DELETE FROM evidencia WHERE id = ?";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    private static void imprimirEvidencia(ResultSet resposta)
            throws SQLException {

        System.out.println(
                resposta.getLong("id")+" | "
                + resposta.getString("titulo")+" | "
                + resposta.getString("descricao")+" | "
                + resposta.getLong("documento_id")+" | "
                + resposta.getInt("importancia")+" | "
                +resposta.getBoolean("coletado")
        );
    }
    
    
    private static boolean estaColetada(long id) throws SQLException{
        String sql = """
                SELECT coletado
                FROM evidencia
                WHERE id = ?
                """;
                //adicionei coletado
      
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

    
}
            

