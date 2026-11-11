/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;
import db.*;
import java.sql.*;
/**
 *
 * @author antonio-nuvunga
 */
public class Evento {
    public static int adicionarEvento(String descricao,String data_hora,Long local_id) throws SQLException {

        String sql = """
                INSERT INTO evento(descricao, data_hora, local_id)
                VALUES (?, ?, ?)
                """;

        try (Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, descricao);
            statement.setString(2, data_hora);
            statement.setLong(3, local_id);

            int linhasAfetadas = statement.executeUpdate();
            //tippo se por acaso  nao for um alinha afectada
            if (linhasAfetadas != 1) {
                throw new SQLException("Não foi possível criar o evento.");
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
    public static void listarEvento() throws SQLException {

        String sql = """
                SELECT id, descricao,data_hora,local_id
                FROM evento
                ORDER BY id
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()
        ) {
            boolean encontrou = false;

            while (resposta.next()) {
                imprimirEvento(resposta);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhuma evento registada.");
            }
        }
    }
    
    
    public static boolean buscarPorId(long id)
            throws SQLException {
        String sql = """
                SELECT id, titulo, descricao, data_hora,local_id
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
                    imprimirEvento(resposta);
                    return true;
                }

                return false;
            }
        }
    }
    
    //burcarPorLocalizacao
    //buscarPorPessoa
    
     public static boolean atualizarEvento(long id,String descricao,String data_hora,Long local_id) throws SQLException {

        String sql = """
                UPDATE evidencia
                SET descricao=?, data_hora=?, local_id=?
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setString(1, descricao);
            statement.setString(2, data_hora);
            statement.setLong(3, local_id);
            statement.setLong(4,id);

            return statement.executeUpdate() > 0;
        }
    }
     
    public static boolean eliminarEvento(long id) throws SQLException{
        boolean eliminado = false;
        String sql = "Delete from evento where id=?";
        try(Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(sql))
        {
            statement.setLong(1, id);
            int linhasAfetadas = statement.executeUpdate();
            if(linhasAfetadas==1) eliminado= true;
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
        return eliminado;
    }
     
    private static void imprimirEvento(ResultSet resposta)
            throws SQLException {

        System.out.println(
                resposta.getLong("id")+" | "
                + resposta.getString("descricao")+" | "
                + resposta.getString("data_hora")+" | "
                + resposta.getLong("local_id")+" | "
        );
    }
    
}
    
    
 
