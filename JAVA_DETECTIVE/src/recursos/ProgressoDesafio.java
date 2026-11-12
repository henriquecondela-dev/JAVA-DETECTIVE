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
    
    public static boolean criarProgressoDesafio(Connection conexao, long progressoCasoId, long desafioId, boolean concluido) throws SQLException{
        // 1. Verificar se o progresso já existe
        String sqlVerificar ="select id from progresso_desafio where progresso_caso_id = ? and desafio_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sqlVerificar)) {

            stmt.setLong(1, progressoCasoId);
            stmt.setLong(2, desafioId);

            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    return true;
                }
            }
        }
        
        String codigo = "Insert into progresso_desafio (progresso_caso_id, desafio_id, concluido, data_conclusao) values(?, ?, ?, ?)";
        try(PreparedStatement statement = conexao.prepareStatement(codigo)){
            statement.setLong(1, progressoCasoId);
            statement.setLong(2, desafioId);
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
    
    public static List<utils.DesafioDeProgramacao> buscarDesafiosPorEtapaId(Connection conexao, long id) throws SQLException {
        String sql = """
                select etapa.id as 'Etapa ID', 
                etapa.caso_id as 'Caso ID', 
                desafioDeProgramacao.id as 'Desafio de Programacao ID', 
                desafioDeProgramacao.titulo as 'Titulo do desafio de programacao', 
                desafioDeProgramacao.descricao as 'Descricao do desafio de programacao',
                desafioDeProgramacao.classe_esperada as 'Classe Esperada',
                desafioDeProgramacao.metodo_esperado as 'Metodo Esperado'
                from desafioDeProgramacao inner join etapa 
                on etapa.id = desafioDeProgramacao.etapa_id where etapa.id = ?;
                """;
        try (PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            try (ResultSet resposta = statement.executeQuery()) {
                List<utils.DesafioDeProgramacao> desafioDeProgramacao = new ArrayList<>();
                while (resposta.next()) {
                    utils.DesafioDeProgramacao desafio = new utils.DesafioDeProgramacao(resposta.getLong("Desafio de Programacao ID"), resposta.getLong("Caso ID"), resposta.getLong("Etapa ID") , resposta.getString("Titulo do desafio de programacao"), resposta.getString("'Descricao do desafio de programacao"), resposta.getString("Classe Esperada"), resposta.getString("Metodo Esperado"));
                    desafioDeProgramacao.add(desafio);
                }
                return desafioDeProgramacao;
            }            
        }
    }
    
    public static List<Long> buscarIdsDesafiosPorEtapaId(Connection conexao, long id) throws SQLException {
        String sql = """
                select etapa.id as 'Etapa ID', etapa.caso_id as 
                'Caso ID', desafioDeProgramacao.id as 
                'Desafio de Programacao ID'
                from desafioDeProgramacao inner join etapa 
                on etapa.id = desafioDeProgramacao.etapa_id where etapa.id = ?;
                """;
        try (PreparedStatement statement = conexao.prepareStatement(sql)){
            statement.setLong(1, id);
            try (ResultSet resposta = statement.executeQuery()) {
                List<Long> ids = new ArrayList<>();
                while (resposta.next()) {
                    ids.add(resposta.getLong("Desafio de Programacao ID"));
                    
                }
                return ids;
            }            
        }
    }
    
    public static utils.ProgressoDesafio buscarProgressoDesafioPorEtapaId(Connection conexao, long etapaId, long progressoId) throws SQLException {
        String sql = """
                SELECT pd.progresso_caso_id,
                       pd.desafio_id,
                       pd.concluido,
                       pd.data_conclusao
                FROM progresso_desafio pd
                JOIN desafioDeProgramacao d
                    ON d.id = pd.desafio_id
                WHERE d.etapa_id = ?
                  AND pd.progresso_caso_id = ?
                """;
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, etapaId);
            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    Timestamp ts = resposta.getTimestamp("data_conclusao");
                    LocalDateTime dataConclusao = (ts != null) ? ts.toLocalDateTime() : null;
                    return new utils.ProgressoDesafio(
                            resposta.getLong("progresso_caso_id"),
                            resposta.getLong("desafio_id"),
                            resposta.getBoolean("concluido"),
                            dataConclusao
                    );
                }
                return null;
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
