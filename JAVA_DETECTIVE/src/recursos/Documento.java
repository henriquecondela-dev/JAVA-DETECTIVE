/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import db.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Documento {

    public static int adicionarDocumento(String nome, String tipo, String conteudo, String dataDocumento) throws SQLException {
        String codigo = "Insert into documento ( nome, tipo, conteudo, data_documento) values(?, ?, ?, ?)";
        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(codigo, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, nome);
            statement.setString(2, tipo);
            statement.setString(3, conteudo);
            statement.setString(4, dataDocumento);

            int linhas = statement.executeUpdate();
            if (linhas != 1) {
                throw new SQLException("document nao adicionado ");
            }
            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    return chaves.getInt(1);
                }
                throw new SQLException("documento adicionado mas nao foi possivel obter o id");
            }

        }

    }

    public static void listarDocumentos() {
        String codigo = "select * from documento";

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(codigo); ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                String nome = resultado.getString("nome");
                String tipo = resultado.getString("tipo");
                String conteudo = resultado.getString("conteudo");
                String dataDocumento = resultado.getString("data_documento");

                System.out.print("Nome: " + nome + " ");
                System.out.print("tipo: " + tipo + " ");
                System.out.print("conteudo: " + conteudo + " ");
                System.out.println("data do documento: " + dataDocumento);
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO LISTAR os documentos");
        }
    }

    public static void eliminarDocumento(long id) {
        String codigo = "Delete from documento where id=?";
        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(codigo);) {
            statement.setLong(1, id);
            int linhas = statement.executeUpdate();
            if (linhas > 0) {
                System.out.println("Documento removido com sucesso.");
            } else {
                System.out.println("Documento nao removido .");
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO ELIMINAR OS REGISTROS DE ACESSO");
        }
    }

    public static void atualizarDocumento(long id, String nome, String tipo, String conteudo, String dataDocumento) {
        String codigo = "update pessoa set nome=?, tipo=?, conteudo=?, data_documento=? where id=?";

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(codigo);) {
            statement.setString(1, nome);
            statement.setString(2, tipo);
            statement.setString(3, conteudo);
            statement.setString(4, dataDocumento);
            statement.setLong(5, id);

            int linhas = statement.executeUpdate();
            if (linhas > 0) {
                System.out.println("Documento atualizado  com sucesso");
            } else {
                System.out.println(" documento nao atualizado");
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO ATUALIZAR OS documentos DE ACESSO");
        }
    }

    public static List<utils.Documento> listarDocumentosPorEtapa(
            Connection conexao,
            long etapaId
    ) throws SQLException {

        String sql = """
        SELECT DISTINCT
            d.id,
            d.nome,
            d.tipo,
            d.conteudo,
            d.data_documento
        FROM documento d
        INNER JOIN evidencia e
            ON e.documento_id = d.id
        INNER JOIN etapa_evidencia ee
            ON ee.evidencia_id = e.id
        WHERE ee.etapa_id = ?
        ORDER BY d.id
        """;

        List<utils.Documento> documentos = new ArrayList<>();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, etapaId);

            try (ResultSet resposta = statement.executeQuery()) {

                while (resposta.next()) {

                    java.sql.Date dataSQL
                            = resposta.getDate("data_documento");

                    java.time.LocalDate dataDocumento
                            = dataSQL != null ? dataSQL.toLocalDate() : null;

                    utils.Documento documento = new utils.Documento(
                            resposta.getLong("id"),
                            resposta.getString("nome"),
                            resposta.getString("tipo"),
                            resposta.getString("conteudo"),
                            dataDocumento
                    );

                    documentos.add(documento);
                }
            }
        }

        return documentos;
    }

    public static utils.Documento procurarDocumentoPorEvidencia(
            Connection conexao,
            long evidenciaId
    ) throws SQLException {

        String sql = """
        SELECT
            d.id,
            d.nome,
            d.tipo,
            d.conteudo,
            d.data_documento
        FROM evidencia e
        INNER JOIN documento d
            ON e.documento_id = d.id
        WHERE e.id = ?
        """;

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, evidenciaId);

            try (ResultSet resposta = statement.executeQuery()) {

                if (resposta.next()) {

                    java.sql.Date dataSQL
                            = resposta.getDate("data_documento");

                    java.time.LocalDate dataDocumento
                            = dataSQL != null ? dataSQL.toLocalDate() : null;

                    return new utils.Documento(
                            resposta.getLong("id"),
                            resposta.getString("nome"),
                            resposta.getString("tipo"),
                            resposta.getString("conteudo"),
                            dataDocumento
                    );
                }
            }
        }

        return null;
    }
}
