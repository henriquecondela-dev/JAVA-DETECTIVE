/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import db.ConexaoMySQL;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static recursos.Evidencia.obterImportancia;

/**
 *
 * @author antonio-nuvunga
 */
public class ProgressoEvidencia {
    
    public static boolean criarProgressoEvidencia(long progressoCasoId, long evidenciaId, boolean coletada) throws SQLException{
        String codigo = "Insert into progresso_evidencia (progresso_caso_id, evidencia_id, coletada, data_coleta) values(?, ?, ?, ?)";
        try(
                Connection conexao = ConexaoMySQL.getConnection();
                PreparedStatement statement = conexao.prepareStatement(codigo);
                ){
            statement.setLong(1, progressoCasoId);
            statement.setLong(2, evidenciaId);
            statement.setBoolean(3,coletada);
            if(coletada==false){
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
    
    public static boolean colectarEvidencia(long progresso_caso_id, long evidencia_id) throws SQLException{
        String sql="update progresso_evidencia set coletada=true, data_coleta=? where evidencia_id =? and progresso_caso_id=?";
        try(
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ){
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(2, evidencia_id);
            statement.setLong(3, progresso_caso_id);
            int linhasAfectadas = statement.executeUpdate();
            if(linhasAfectadas>0){
                return true;
            }else{
                return false;
            }
        }
    }
    
    public static boolean verificarColecta(long progresso_caso_id, long evidencia_id) throws SQLException{
        String sql="select * from progresso_evidencia where evidencia_id =? and progresso_caso_id=?";
        try(
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ){
            statement.setLong(1, evidencia_id);
            statement.setLong(2, progresso_caso_id);
            try (ResultSet resposta = statement.executeQuery()) {
                boolean coleta = false;
                if (resposta.next()) {
                    coleta = resposta.getBoolean("coletada");
                }
                return coleta;
            }
        }
    }
    
    public static List<String> buscarEvidenciasPorEtapaId(long id) throws SQLException {
        String sql = """
                select etapa.id as 'Etapa ID', etapa.caso_id as 'Caso ID', evidencia.id as 'Evidencia ID', etapa.numero as 'Numero', 
                etapa.titulo as 'Titulo da Etapa', etapa.descricao as 'Descricao da Etapa', evidencia.titulo as 'Titulo da Evidencia',
                evidencia.descricao as 'Descricao da Evidencia', evidencia.documento_id as 'Documento ID', 
                evidencia.importancia as 'Importancia'
                from etapa_evidencia inner join etapa on etapa.id = etapa_evidencia.etapa_id
                inner join evidencia on evidencia.id = etapa_evidencia.evidencia_id where etapa.id = ?;
                """;
        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            try (ResultSet resposta = statement.executeQuery()) {
                List<String> evidenciasPorEtapa = new ArrayList<>();
                while (resposta.next()) {
                    int num = resposta.getInt("Importancia");
                    String evidencia = resposta.getLong("Etapa ID") + "|" + resposta.getLong("Evidencia ID") + "|" 
                            + resposta.getString("Titulo da Evidencia") + "|" + resposta.getString("Descricao da Evidencia") + "|" 
                            + resposta.getObject("Documento ID", Long.class) + "|" + obterImportancia(num);
                    evidenciasPorEtapa.add(evidencia);
                    
                }
                return evidenciasPorEtapa;
            }            
        }
    }
    
    public static int buscarNumeroEvidenciasColectadasPorEtapa(long id, long progresso_caso_id) throws SQLException {
        String sql = "select progresso_evidencia.coletada from evidencia inner join "
                + "progresso_evidencia on evidencia.id=progresso_evidencia.evidencia_id and coletada=true "
                + "inner join etapa_evidencia on etapa_evidencia.evidencia_id = evidencia.id where etapa_evidencia.etapa_id=? and progresso_evidencia.progresso_caso_id=?;";
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
    
    public static int buscarNumeroEvidenciasColectadas(long progresso_caso_id) throws SQLException {
        String sql = "select * from progresso_evidencia where coletada=true and progresso_caso_id=?";
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
