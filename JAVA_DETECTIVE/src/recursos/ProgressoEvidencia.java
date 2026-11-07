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

import java.time.LocalDateTime;

/**
 *
 * @author antonio-nuvunga
 */
public class ProgressoEvidencia {
    
    public static void criarProgressoEvidencia(long progressoCasoId, long evidenciaId, boolean coletada, LocalDateTime dataColeta) throws SQLException{
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
                statement.setTimestamp(4, Timestamp.valueOf(dataColeta));
            }
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
    }
}
