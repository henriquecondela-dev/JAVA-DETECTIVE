/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;


import db.*;
import java.sql.*;

/**
 *
 * @author gerciamaunze-dev
 */
public class RegistoAcesso {
  
    
    public static void adicionarRegistroAcesso(String descricao, String dataHora, String tipoAcesso, long local_id, long pessoa_id){
       String codigo = "Insert into registrodeacesso ( descricao, data_hora, tipo_acesso, local_id, pessoa_id) values(?, ?, ?, ?, ?)";
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setString(1, descricao);
            statement.setString(2, dataHora);
            statement.setString(3,tipoAcesso);
            statement.setLong(4, local_id);
            statement.setLong(5, pessoa_id);
            
            int linhas = statement.executeUpdate();
            if(linhas>0){
                System.out.println("Registro de acesso adicionad com sucesso");  
            }else{
                System.out.println("Registro de acesso nao adicionado");
            }
        }catch (SQLException e){
            System.out.println("ERRO AO ADICIONAR OS REGISTROS DE ACESSO"+ e.getMessage());
        }
    }
    
    public static void listarRegistroAcesso(){
        String codigo = " Select * from registrodeacesso";
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ResultSet resultado = statement.executeQuery()
                ){
                while(resultado.next()){
                String descricao= resultado.getString("descricao");
                String dataHora = resultado.getString("data_hora");
                String tipoAcesso = resultado.getString("tipo_acesso");
                long local_id = resultado.getInt("local_id");
                long pessoa_id = resultado.getInt("pessoa_id");
                
                System.out.print("Nome: " +descricao+" ");
                System.out.print("Idade: " +dataHora+" ");
                System.out.print("genero: " +tipoAcesso+" ");
                System.out.println("descricao: " +local_id+" ");
                System.out.println("Pessoa id "+ pessoa_id);
            }
        }catch (SQLException e){
            System.out.println("ERRO AO LISTAR OS REGISTROS DE ACESSO"+ e.getMessage());
        }
    }
    
    public static void eliminarRegistroAcesso(long id){
        String codigo ="Delete from registrodeacesso where id=?";
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setLong(1,id);
            int linhas = statement.executeUpdate();
            if(linhas>0){
                System.out.println("Registro de acesso removido com sucesso.");
            }else{
                System.out.println("Registro de acesso nao removido .");
            }
        }catch (SQLException e){
            System.out.println("ERRO AO ELIMINAR OS REGISTROS DE ACESSO");
        }
    }
    public static void atualizarRegistroAcesso(long id, String descricao, String dataHora, String tipoAcesso, long local_id, long pessoa_id){
        String codigo = "update pessoa set descricao=?, data_hora=?, tipo_acesso=?, local_id=?, pessoa_id=? where id=?";
        
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setString(1, descricao);
            statement.setString(2, dataHora);
            statement.setString(3,tipoAcesso);
            statement.setLong(4, local_id);
            statement.setLong(5, pessoa_id);
            
            int linhas = statement.executeUpdate();
            if(linhas>0){
                System.out.println("registro de acesso adicionada com sucesso");  
            }else{
                System.out.println(" registro de acesso nao adicionadO");
            } 
        }catch (SQLException e){
            System.out.println("ERRO AO ATUALIZAR OS REGISTROS DE ACESSO"+ e.getMessage());
        }
    }
}
