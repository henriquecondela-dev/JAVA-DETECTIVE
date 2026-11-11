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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static recursos.Evidencia.obterImportancia;

/**
 *
 * @author antonio-nuvunga
 */

public class ProgressoDesafio {
    
    public static boolean criarProgressoDesafio(long progressoCasoId, long evidenciaId, boolean concluido) throws SQLException{
        String codigo = "Insert into progresso_desafio (progresso_caso_id, desafio_id, concluido, data_conclusao) values(?, ?, ?, ?)";
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setLong(1, progressoCasoId);
            statement.setLong(2, evidenciaId);
            statement.setBoolean(3,concluido);
            if(concluido==false){
                statement.setTimestamp(4, null);
            }else{
                statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }
            int linhas = statement.executeUpdate();
            if(linhas>0){
                return true;
            }else{
                return false;
            }
        }
    }
    
    public static boolean concluirDesafio(long progresso_caso_id, long desafio_id) throws SQLException{
        String sql="update progresso_desafio set concluido=true, data_conclusao=? where desafio_id =? and progresso_caso_id=?";
        try(
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ){
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(2, desafio_id);
            statement.setLong(3, progresso_caso_id);
            int linhasAfectadas = statement.executeUpdate();
            if(linhasAfectadas>0){
                return true;
            }else{
                return false;
            }
        }
    }
    
    public static boolean verificarConclusao(long progresso_caso_id, long desafio_id) throws SQLException{
        String sql="select * from progresso_desafio where desafio_id=? and progresso_caso_id=?";
        try(
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ){
            statement.setLong(1, desafio_id);
            statement.setLong(2, progresso_caso_id);
            try (ResultSet resposta = statement.executeQuery()) {
                boolean coleta = false;
                if (resposta.next()) {
                    coleta = resposta.getBoolean("concluido");
                }
                return coleta;
            }
        }
    }
    
    public static List<String> buscarDesafiosPorEtapaId(long id) throws SQLException {
        String sql = """
                select etapa.id as 'Etapa ID', etapa.caso_id as 
                'Caso ID', desafioDeProgramacao.id as 
                'Desafio de Programacao ID', etapa.numero as 'Numero', 
                etapa.titulo as 'Titulo da Etapa', etapa.descricao as 
                'Descricao da Etapa', desafioDeProgramacao.titulo as 
                'Titulo do desafio de programacao', 
                desafioDeProgramacao.descricao as 'Descricao do desafio de programacao',
                desafioDeProgramacao.classe_esperada as 'Classe Esperada',
                desafioDeProgramacao.metodo_esperado as 'Metodo Esperado',
                from desafioDeProgramacao inner join etapa 
                on etapa.id = desafioDeProgramacao.etapa_id where etapa.id = ?;
                """;
        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            try (ResultSet resposta = statement.executeQuery()) {
                List<String> desafioDeProgramacao = new ArrayList<>();
                while (resposta.next()) {
                    String desafio = resposta.getLong("Etapa ID") + "|" + resposta.getLong("Desafio de Programacao ID") + "|" 
                            + resposta.getString("Titulo do desafio de programacao") + "|" + resposta.getString("Descricao do desafio de programacao") + "|" 
                            + resposta.getString("Classe Esperada") + "|"                   + resposta.getString("Metodo Esperado");
                    desafioDeProgramacao.add(desafio);
                    
                }
                return desafioDeProgramacao;
            }            
        }
    }
    
    public static int buscarNumeroDesafiosConcluidosPorEtapa(long id, long progresso_caso_id) throws SQLException {
        String sql = "select progresso_desafio.concluido from desafioDeProgramacao inner join "
                + "progresso_desafio on desafioDeProgramacao.id=progresso_desafio.desafio_id and concluido=true "
                + "inner join etapa on etapa.id= desafioDeProgramacao.etapa_id where etapa.id=? and progresso_desafio.progresso_caso_id=?;";
        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            statement.setLong(2, progresso_caso_id);
            try (ResultSet resposta = statement.executeQuery()) {
                int num = 0;
                while (resposta.next()) {
                    num++;
                }
                return num;
            }            
        }
    }
    
    public static int buscarNumeroDesafiosConcluidos(long progresso_caso_id) throws SQLException {
        String sql = "select * from progresso_desafio where concluido=true and progresso_caso_id=?";
        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, progresso_caso_id);
            try (ResultSet resposta = statement.executeQuery()) {
                int num  = 0;
                while (resposta.next()) {
                    num++;
                }
                return num;
            }            
        }
    }    
}
