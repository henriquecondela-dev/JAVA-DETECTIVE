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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antonio-nuvunga
 */
public class Etapa {

    public static int criarEtapa(long caso_id, int numero, String titulo, String descricao, int minimo_evidencias) throws SQLException {

        String sql = """
                INSERT INTO etapa(caso_id, numero, titulo, descricao, minimo_evidencias)
                VALUES (?, ?, ?,?,?)
                """;

        try (Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setLong(1, caso_id);
            statement.setInt(2, numero);
            statement.setString(3, titulo);
            statement.setString(4, descricao);
            statement.setInt(5, minimo_evidencias);//adicionei coletado

            int linhasAfetadas = statement.executeUpdate();
            //tippo se por acaso  nao for um alinha afectada
            if (linhasAfetadas != 1) {
                throw new SQLException("Não foi possível criar a etapa.");
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
    
    public List<utils.Etapa> listarEtapas() throws SQLException {

        String sql = """
                SELECT id, caso_id, numero , titulo, descricao, desbloqueada, concluida, minimo_evidencias
                FROM etapa
                ORDER BY id
                """;

        try (Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()) {
            boolean encontrou = false;
            List<utils.Etapa> etapas = new ArrayList<>();
            while (resposta.next()) {                
                utils.Etapa etapa = new utils.Etapa(resposta.getLong("id"), resposta.getLong("caso_id"), resposta.getInt("numero"), resposta.getInt("minimo_evidencias"), resposta.getString("titulo"), resposta.getString("descricao"), resposta.getString("objectivo"));
                 etapas.add(etapa);
                //imprimirEtapa(resposta);
            }

            if (!encontrou) {
                throw new SQLException("Nenhuma etapa registada.");
            }
            return etapas;
        }
    }
    
    
    public static utils.Etapa buscarPorId(long id)throws SQLException {
        String sql = """
                SELECT id, caso_id, numero , titulo, descricao, desbloqueada, concluida, minimo_evidencias
                FROM etapa
                WHERE id = ?
                """;

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    utils.Etapa etapa = new utils.Etapa(resposta.getLong("id"), resposta.getLong("caso_id"), resposta.getInt("numero"), resposta.getInt("minimo_evidencias"), resposta.getString("titulo"), resposta.getString("descricao"), resposta.getString("objectivo"));
                    return etapa;
                    //imprimirEtapa(resposta);
                }

                return null;
            }
        }
    }

    //burcarPorLocalizacao
    //buscarPorPessoa
    public static boolean atualizarEtapa(long id, long caso_id, int numero, String titulo, String descricao, int minimo_evidencias) throws SQLException {

        String sql = """
                UPDATE etapa
                SET caso_id=?, numero=?, titulo=?, descricao=?, minimo_evidencias=?
                WHERE id = ?
                """;

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, caso_id);
            statement.setLong(2, numero);
            statement.setString(3, titulo);
            statement.setString(4, descricao);
            statement.setInt(5, minimo_evidencias);
            statement.setLong(6, id);

            return statement.executeUpdate() > 0;
        }
    }

    public static boolean eliminarEtapa(long id) throws SQLException {
        boolean eliminado = false;
        String sql = "Delete from etapa where id=?";
        try (Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);
            int linhasAfetadas = statement.executeUpdate();
            if(linhasAfetadas==1) eliminado= true;
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return eliminado;
    }

    private static void imprimirEtapa(ResultSet resposta)
            throws SQLException {

        System.out.println(
                resposta.getLong("id") + " | "
                + resposta.getLong("caso_id") + " | "
                + resposta.getInt("numero") + " | "
                + resposta.getString("titulo") + " | "
                + resposta.getString("descricao") + " | "
                + resposta.getBoolean("desbloqueada") + " | "
                + resposta.getBoolean("concluida") + " | "
                + resposta.getInt("minimo_evidencias") + " | "
        );
    }

    //e ai pessoal, kk, henrique aqui.. adicionei um novo metodo aqui tou a precisar dele aqui ..
    public static long obterPrimeiraEtapa(Connection conexao, long casoId) throws SQLException {
        String sql = """
            SELECT id
            FROM etapa
            WHERE caso_id = ?
            ORDER BY numero ASC
            LIMIT 1
            """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, casoId);

            try (ResultSet resultado = stmt.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getLong("id");
                }
            }
        }

        throw new SQLException("O caso não possui etapas.");
    }

    public static utils.Etapa procurarEtapaPorId( Connection conexao,long etapaId) throws SQLException {
        String sql = """
            SELECT
                id,
                numero,
                titulo,
                descricao,
                desbloqueada,
                concluida,
                minimo_evidencias
            FROM etapa
            WHERE id = ?
            """;

        try (PreparedStatement stmt= conexao.prepareStatement(sql)) {

            stmt.setLong(1, etapaId);

            try (ResultSet resultado = stmt.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                return new utils.Etapa(
                        Math.toIntExact(resultado.getLong("id")),
                        resultado.getInt("numero"),
                        resultado.getInt("minimo_evidencias"),
                        resultado.getString("titulo"),
                        resultado.getString("descricao"),
                        null,
                        resultado.getBoolean("desbloqueada"),
                        resultado.getBoolean("concluida")
                );
            }
        }
    }

}
