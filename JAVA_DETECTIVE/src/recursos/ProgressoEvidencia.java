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
    
    public static boolean criarProgressoEvidencia(Connection conexao, long progressoCasoId, long evidenciaId, boolean coletada) throws SQLException {

        // 1. Verificar se o progresso já existe
        String sqlVerificar = """
                        SELECT progresso_caso_id
                        FROM progresso_evidencia
                        WHERE progresso_caso_id = ?
                          AND evidencia_id = ?
                        """;

        try (PreparedStatement stmtVerificar = conexao.prepareStatement(sqlVerificar)) {

            stmtVerificar.setLong(1, progressoCasoId);
            stmtVerificar.setLong(2, evidenciaId);

            try (ResultSet resultado = stmtVerificar.executeQuery()) {

                if (resultado.next()) {
                    return true;
                }
            }
        }

        //if nao existe progresso caso evidencia associado aqueles id evidencia e progresso
        String sql = """
                INSERT INTO progresso_evidencia
                    (progresso_caso_id, evidencia_id, coletada, data_coleta)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, progressoCasoId);
            stmt.setLong(2, evidenciaId);
            stmt.setBoolean(3, coletada);

            if(coletada==false){
                stmt.setTimestamp(4, null);
            }else{
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }

            return stmt.executeUpdate() == 1;
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
    
    public static List<utils.Evidencia> buscarEvidenciasPorEtapa(Connection conexao, long etapaId) throws SQLException {
        String sql = """
                select evidencia.id as 'Evidencia ID', 
                evidencia.titulo as 'Titulo da Evidencia',
                evidencia.descricao as 'Descricao da Evidencia', 
                evidencia.documento_id as 'Documento ID', 
                evidencia.importancia as 'Importancia'
                from etapa_evidencia 
                inner join etapa on etapa.id = etapa_evidencia.etapa_id
                inner join evidencia on evidencia.id = etapa_evidencia.evidencia_id where etapa.id = ?;
                """;
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, etapaId);
            try (ResultSet resposta = statement.executeQuery()) {
                List<utils.Evidencia> evidenciasPorEtapa = new ArrayList<>();
                while (resposta.next()) {
                    int num = resposta.getInt("Importancia");
                    long idEvidencia = resposta.getLong("Evidencia ID");
                    String titulo = resposta.getString("Titulo da Evidencia");
                    String descricao = resposta.getString("Descricao da Evidencia");
                    Long documentoId = resposta.getObject("Documento ID", Long.class);
                     utils.Importancia importa = obterImportancia(num);
                     utils.Evidencia evidencia = new utils.Evidencia(idEvidencia, titulo, descricao, documentoId, importa);
                    evidenciasPorEtapa.add(evidencia);
                    
                }
                return evidenciasPorEtapa;
            }            
        }
    }
    
    public static List<Long> buscarIdsEvidenciasPorEtapaId(Connection conexao, long id) throws SQLException {
        String sql = """
                select etapa.id as 'Etapa ID', etapa.caso_id as 'Caso ID', evidencia.id as 'Evidencia ID'
                from etapa_evidencia inner join etapa on etapa.id = etapa_evidencia.etapa_id
                inner join evidencia on evidencia.id = etapa_evidencia.evidencia_id where etapa.id = ?;
                """;
        try (PreparedStatement statement = conexao.prepareStatement(sql)){
            statement.setLong(1, id);
            try (ResultSet resposta = statement.executeQuery()) {
                List<Long> ids = new ArrayList<>();
                while (resposta.next()) {
                    long idEvidencia = resposta.getLong("Evidencia ID");
                    ids.add(idEvidencia);
                }
                return ids;
            }            
        }
    }
    
    public static int buscarNumeroEvidenciasColectadasPorEtapa(Connection conexao, long id, long progresso_caso_id) throws SQLException {
        String sql = "select progresso_evidencia.coletada from evidencia inner join "
                + "progresso_evidencia on evidencia.id=progresso_evidencia.evidencia_id and coletada=true "
                + "inner join etapa_evidencia on etapa_evidencia.evidencia_id = evidencia.id where etapa_evidencia.etapa_id=? and progresso_evidencia.progresso_caso_id=?;";
        try (PreparedStatement statement = conexao.prepareStatement(sql)){
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
    
    public static double calcularPontuacaoEvidencias(
        Connection conexao, long etapaId, long progressoCasoId) throws SQLException {

        String sql = """
                SELECT
                    SUM(CASE
                        WHEN e.importancia = 1 THEN 1
                        WHEN e.importancia = 2 THEN 2
                        WHEN e.importancia = 3 THEN 3
                        ELSE 0
                    END) AS peso_total,

                    SUM(CASE
                        WHEN pe.coletada = TRUE THEN
                            CASE
                                WHEN e.importancia = 1 THEN 1
                                WHEN e.importancia = 2 THEN 2
                                WHEN e.importancia = 3 THEN 3
                                ELSE 0
                            END
                        ELSE 0
                    END) AS peso_coletado

                FROM etapa_evidencia ee
                INNER JOIN evidencia e
                    ON e.id = ee.evidencia_id
                LEFT JOIN progresso_evidencia pe
                    ON pe.evidencia_id = e.id
                    AND pe.progresso_caso_id = ?

                WHERE ee.etapa_id = ?
                """;

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, progressoCasoId);
            statement.setLong(2, etapaId);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    double pesoTotal = resultado.getDouble("peso_total");
                    double pesoColetado = resultado.getDouble("peso_coletado");

                    if (pesoTotal == 0) {
                        return 0;
                    }

                    return 15.0 * pesoColetado / pesoTotal;
                }
            }
        }

        return 0;
    }
    
    public static List<utils.Evidencia> buscarArquivoEvidencias(Connection conexao, long progressoCasoId) throws SQLException {

        String sql = """
                SELECT
                    e.id,
                    e.titulo,
                    e.descricao,
                    e.documento_id,
                    e.importancia
                FROM progresso_evidencia pe
                INNER JOIN evidencia e
                    ON e.id = pe.evidencia_id
                WHERE pe.progresso_caso_id = ?
                  AND pe.coletada = TRUE
                ORDER BY pe.data_coleta ASC
                """;

        List<utils.Evidencia> evidencias = new ArrayList<>();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, progressoCasoId);

            try (ResultSet resultado = statement.executeQuery()) {

                while (resultado.next()) {

                    int importancia = resultado.getInt("importancia");

                    utils.Evidencia evidencia = new utils.Evidencia(
                            resultado.getLong("id"),
                            resultado.getString("titulo"),
                            resultado.getString("descricao"),
                            resultado.getObject("documento_id", Long.class),
                            obterImportancia(importancia)
                    );

                    evidencias.add(evidencia);
                }
            }
        }

        return evidencias;
    }
}
