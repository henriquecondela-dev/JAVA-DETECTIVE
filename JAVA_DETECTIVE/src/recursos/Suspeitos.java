/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import db.*;
import java.sql.*;

/**
 *
 * @author henrique-dev
 */

public class Suspeitos {
    public static void  listarSuspeitos(){
        try(Connection conexao= ConexaoMySQL.getConnection();
            Statement statement=conexao.createStatement();
            ResultSet resposta=statement.executeQuery("SELECT * FROM suspeito ")
        ){
            while (resposta.next()){
                System.out.println(
                        resposta.getString("id")+" | "+
                        resposta.getString("pessoa_id")+" | "+
                        resposta.getString("motivo")+"\n"
                );
            }
        }catch(SQLException e){
            System.out.println("erro ao se conectar com o mysql!");
        }
    }
    
}
