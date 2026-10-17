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
public class Documento {
    public static int adicionarDocumento( String nome, String tipo, String conteudo, String dataDocumento) throws SQLException {
        String codigo = "Insert into documento ( nome, tipo, conteudo, data_documento) values(?, ?, ?, ?)";
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo, Statement.RETURN_GENERATED_KEYS);
                ){
            statement.setString(1, nome);
            statement.setString(2, tipo);
            statement.setString(3,conteudo);
            statement.setString(4, dataDocumento);
            
            int linhas = statement.executeUpdate();
            if(linhas!=1){
                throw new SQLException("document nao adicionado ");  
            }
            try(ResultSet chaves = statement.getGeneratedKeys()){
                if(chaves.next()){
                    return chaves.getInt(1);
                }
                throw new SQLException("documento adicionado mas nao foi possivel obter o id");
            }
            
            
        }
       
    }
    public static void listarDocumentos(){
        String codigo="select * from documento";
        
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ResultSet resultado = statement.executeQuery()
                ){
                while(resultado.next()){
                String nome = resultado.getString("nome");
                String tipo = resultado.getString("tipo");
                String conteudo = resultado.getString("conteudo");
                String dataDocumento = resultado.getString("data_documento");
                
                System.out.print("Nome: " +nome+" ");
                System.out.print("tipo: " +tipo+" ");
                System.out.print("conteudo: " +conteudo+" ");
                System.out.println("data do documento: " +dataDocumento);
            }
        }catch (SQLException e){
            System.out.println("ERRO AO LISTAR os documentos");
        }
    }
    
    public static void eliminarDocumento(int id){
        String codigo ="Delete from documento where id=?";
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setInt(1,id);
            int linhas = statement.executeUpdate();
            if(linhas>0){
                System.out.println("Documento removido com sucesso.");
            }else{
                System.out.println("Documento nao removido .");
            }
        }catch (SQLException e){
            System.out.println("ERRO AO ELIMINAR OS REGISTROS DE ACESSO");
        }
    }
    public static void atualizarDocumento( int id, String nome, String tipo, String conteudo, String dataDocumento){
        String codigo = "update pessoa set nome=?, tipo=?, conteudo=?, data_documento=? where id=?";
        
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setString(1, nome);
            statement.setString(2, tipo);
            statement.setString(3,conteudo);
            statement.setString(4, dataDocumento);
            
            int linhas = statement.executeUpdate();
            if(linhas>0){
                System.out.println("Documento atualizado  com sucesso");  
            }else{
                System.out.println(" documento nao atualizado");
            } 
        }catch (SQLException e){
            System.out.println("ERRO AO ATUALIZAR OS documentos DE ACESSO");
        }
    }
}
