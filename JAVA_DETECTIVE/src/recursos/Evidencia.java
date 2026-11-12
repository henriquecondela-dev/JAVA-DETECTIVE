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
public class Evidencia {

    public static int adicionarEvidencia(String titulo, String descricao, Long documento_id, int importancia) throws SQLException {

        String sql = """
                INSERT INTO evidencia(titulo, descricao, documento_id,importancia,coletado)
                VALUES (?, ?, ?,?,?)
                """;//adicionei coletado

        try (Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, titulo);
            statement.setString(2, descricao);
            if (null == documento_id) {
                statement.setNull(3, java.sql.Types.BIGINT);
            } else {
                statement.setLong(3, documento_id);
            }
            statement.setInt(4, importancia);
            statement.setBoolean(5, false);//adicionei coletado

            int linhasAfetadas = statement.executeUpdate();
            //tippo se por acaso  nao for um alinha afectada
            if (linhasAfetadas != 1) {
                throw new SQLException("Não foi possível criar a evidencia.");
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

    public static List<utils.Evidencia> listarEvidenciasPorEtapa(
            Connection conexao,
            long etapaId
    ) throws SQLException {

        String sql = """
        SELECT
            e.id,
            e.titulo,
            e.descricao,
            e.documento_id,
            e.importancia
        FROM evidencia e
        INNER JOIN etapa_evidencia ee
            ON e.id = ee.evidencia_id
        WHERE ee.etapa_id = ?
        ORDER BY e.id
        """;

        List<utils.Evidencia> evidencias = new ArrayList<>();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, etapaId);

            try (ResultSet resposta = statement.executeQuery()) {

                while (resposta.next()) {

                    int importancia = resposta.getInt("importancia");

                    Long documentoId = resposta.getObject("documento_id",Long.class);
                    utils.Evidencia evidencia = new utils.Evidencia(
                            resposta.getLong("id"),
                            resposta.getString("titulo"),
                            resposta.getString("descricao"),
                            documentoId,
                            obterImportancia(importancia)
                    );

                    if (documentoId != null) {
                        utils.Documento documento= recursos.Documento.procurarDocumentoPorEvidencia(
                                        conexao,
                                        evidencia.getId()
                                );

                        evidencia.setDocumento(documento);
                    }

                    evidencias.add(evidencia);
                }
            }
        }

        return evidencias;
    }

    public static List<utils.Evidencia> listarEvidencia() throws SQLException {
        //adicionei coletado
        String sql = """
                SELECT id, titulo, descricao, documento_id, importancia
                FROM evidencia
                ORDER BY id
                """;

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql); ResultSet resposta = statement.executeQuery()) {
            List<utils.Evidencia> evidencias = new ArrayList<>();
            while (resposta.next()) {
                int num = resposta.getInt("importancia");
                utils.Evidencia ev = new utils.Evidencia(resposta.getLong("id"), resposta.getString("titulo"), resposta.getString("descricao"), resposta.getObject("documento_id", Long.class), obterImportancia(num));
                evidencias.add(ev);
                //imprimirEvidencia(resposta);
            }
            return evidencias;
        }
    }

    public static utils.Importancia obterImportancia(int num) {
        if (num == 1) {
            return utils.Importancia.BAIXA;
        } else if (num == 2) {
            return utils.Importancia.MEDIA;
        } else if (num == 3) {
            return utils.Importancia.ALTA;
        }
        return null;
    }

    public static utils.Evidencia buscarPorId(long id)
            throws SQLException {
        String sql = """
                SELECT id, titulo, descricao, documento_id,importancia,coletado
                FROM evidencia
                WHERE id = ?
                """;
        //adicionei coletado

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    int num = resposta.getInt("importancia");
                    utils.Evidencia evidencia = new utils.Evidencia(resposta.getLong("id"), resposta.getString("titulo"), resposta.getString("descricao"), resposta.getObject("documento_id", Long.class), obterImportancia(num));
                    return evidencia;
                    //imprimirEvidencia(resposta);
                }
                return null;
            }

        }
    }

    public static boolean atualizarEvidencia(long id, String titulo, String descricao, Long documento_id, int importancia) throws SQLException {

        String sql = """
                UPDATE evidencia
                SET titulo=?, descricao=?, documento_id=?, importancia=?
                WHERE id = ?
                """;

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, titulo);
            statement.setString(2, descricao);
            if (documento_id != null) {
                statement.setLong(3, documento_id);
            } else {
                statement.setNull(3, java.sql.Types.BIGINT);
            }
            statement.setInt(4, importancia);
            statement.setLong(5, id);

            return statement.executeUpdate() > 0;
        }
    }

    public static boolean eliminarEvidencia(long id) throws SQLException {

        String sql = "DELETE FROM evidencia WHERE id = ?";

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    private static void imprimirEvidencia(ResultSet resposta)
            throws SQLException {

        System.out.println(
                resposta.getLong("id") + " | "
                + resposta.getString("titulo") + " | "
                + resposta.getString("descricao") + " | "
                + resposta.getLong("documento_id") + " | "
                + resposta.getInt("importancia") + " | "
                + resposta.getBoolean("coletado")
        );
    }

    private static boolean estaColetada(long id) throws SQLException {
        String sql = """
                SELECT coletado
                FROM evidencia
                WHERE id = ?
                """;
        //adicionei coletado

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirEvidencia(resposta);
                    return true;
                }

                return false;
            }
        }
    }

}
