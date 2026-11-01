/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;
import db.*;
import java.sql.*;
/**
 *
 * @author Administrator
 */
public class Alibi {
   
  public static void adicionarAlibi(long id_suspeito ,String descricao, String inicio, String fim){
        
        String codigo = "insert into alibi (id_suspeito, descricao, inicio, fim) values(?, ?, ?, ?)";
        
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setLong(1, id_suspeito);
            statement.setString(2, descricao);
            statement.setString(3,inicio);
            statement.setString(4, fim);
            
            int linhas = statement.executeUpdate();
            if(linhas==1){
                System.out.println("Alibi  adicionado com sucesso");  
            }else{
                System.out.println("Alibi nao adicionado");
            }
        }catch (SQLException e){
            System.out.println("ERRO AO adicionar os alibis");
        }
    }
  public static void listarAlibi(){
        String codigo="select* from alibi";
        
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ResultSet resultado = statement.executeQuery()
                ){
                while(resultado.next()){
                long suspeito = resultado.getInt("suspeito_id");
                String descricao = resultado.getString("descricao");
                String inicio= resultado.getString("inicio");
                String fim= resultado.getString("fim");
                
                System.out.print("Nome: " +suspeito+" ");
                System.out.print("tipo: " +descricao+" ");
                System.out.print("conteudo: " +inicio+" ");
                System.out.println("data do documento: " +fim);
            }
        }catch (SQLException e){
            System.out.println("ERRO AO LISTAR os alibis"+ e.getMessage());
        }
    }
  public static void eliminarAlibi(long id){
        String codigo ="Delete from alibi where id=?";
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setLong(1,id);
            int linhas = statement.executeUpdate();
            if(linhas>0){
                System.out.println("alibi removido com sucesso.");
            }else{
                System.out.println("alibi nao removido .");
            }
        }catch (SQLException e){
            System.out.println("ERRO AO ELIMINAR O alibi");
        }
    }
  
  public static void atualizar(long id, long id_suspeito ,String descricao, String inicio, String fim){
        String codigo = "update alibi set id_suspeito=?, descricao=?, inicio=?, fim=? where id=?";
        
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setLong(1, id_suspeito);
            statement.setString(2, descricao);
            statement.setString(3,inicio);
            statement.setString(4, fim);
            statement.setLong(5, id);
            
            int linhas = statement.executeUpdate();
            if(linhas>0){
                System.out.println("alibi atualizado  com sucesso");  
            }else{
                System.out.println(" alibi nao atualizado");
            } 
        }catch (SQLException e){
            System.out.println("ERRO AO ATUALIZAR OS alibi");
        }
    }
    
}
