/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.sql.*;
import db.*;

/**
 *
 * @author antonio-nuvunga
 */
public class Acusacao {
    public static int adicionarAcusacao(long jogador_id, long caso_id, long suspeito_acusado_id, boolean resultado) throws SQLException{
        String sql = "Insert into acusacao (jogador_id, caso_id, suspeito_acusado_id, resultado) values(?, ?, ?, ?)";
        
        try(Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            statement.setLong(1, jogador_id);
            statement.setLong(2, caso_id);
            statement.setLong(3, suspeito_acusado_id);
            statement.setBoolean(4, resultado);
            
            int linhasAfetadas = statement.executeUpdate();
            if(linhasAfetadas!=1){
                throw new SQLException("Erro: Nao foi possivel adicionar a acusacao!");
            }
            
            try(ResultSet chaves = statement.getGeneratedKeys()){
                if(chaves.next()){
                    return chaves.getInt(1);
                }
                
                throw new SQLException(
                        "O INSERT foi executado, mas não foi possível obter o ID."
                );
            }
        }
    }
    
    public static void listarAcusacao() throws SQLException {

        String sql = "select * from acusacao";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()
        ) {
            boolean encontrou = false;

            while (resposta.next()) {
                imprimirAcusacao(resposta);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhuma acusacao registada.");
            }
        }
    }
    
    
    
    public static boolean buscarPorId(long id)
            throws SQLException {
        String sql = """
                SELECT id, jogador_id, caso_id, suspeito_acusado_id, resultado, data_acusacao
                FROM acusacao
                WHERE id = ?
                """;
      
        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirAcusacao(resposta);
                    return true;
                }

                return false;
            }
        }
    }
    
    public static boolean atualizarAcusacao(long id, long jogador_id, long caso_id, Long suspeito_acusado_id, boolean resultado) throws SQLException {

        String sql = """
                UPDATE acusacao
                SET jogador_id=?, caso_id=?, suspeito_acusado_id=?, resultado=?
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, jogador_id);
            statement.setLong(2, caso_id);
            statement.setLong(3, suspeito_acusado_id);
            statement.setBoolean(4,resultado);
            statement.setLong(5,id);

            return statement.executeUpdate() > 0;
        }
    }
    
    public static boolean eliminarAcusacao(long id) throws SQLException {

        String sql = "DELETE FROM acusacao WHERE id = ?";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    private static void imprimirAcusacao(ResultSet resposta) throws SQLException {

        System.out.println(
                resposta.getLong("id")+" | "
                + resposta.getLong("jogador_id")+" | "
                + resposta.getLong("caso_id")+" | "
                + resposta.getLong("suspeito_acusado_id")+" | "
                + resposta.getInt("resultado")+" | "
                + resposta.getString("data_acusacao")
        );
    }
    
    public static boolean verificarAcusacao(long suspeito_id) throws SQLException {

        String sql = """
                SELECT pessoa.nome
                FROM suspeito
                INNER JOIN pessoa ON suspeito.pessoa_id = pessoa.id
                WHERE suspeito.id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, suspeito_id);

            try (ResultSet resposta = statement.executeQuery()) {

                if (resposta.next()) {
                    String nome = resposta.getString("nome");
                    
                    if (nome != null) {
                        String primeiroNome = nome.trim().split("\\s+")[0];

                        return primeiroNome.equalsIgnoreCase("Antonio");
                    }

                    return false;
                }

                return false;
            }
        }
    }
    
    public static boolean efectuarAcusacao(long jogador_id,long caso_id, long suspeito_acusado_id) throws SQLException {

        boolean resultado = verificarAcusacao(suspeito_acusado_id);

        adicionarAcusacao(jogador_id, caso_id, suspeito_acusado_id, resultado);

        return resultado;
    }
    
}
