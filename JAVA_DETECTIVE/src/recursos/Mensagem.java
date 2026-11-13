/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import db.*;
import java.sql.*;

/**
 *
 * @author antonio-nuvunga
 */
public class Mensagem {

    public static int adicionarMensagem(String conteudo) throws SQLException {
        String sql = "Insert into mensagem (conteudo) values(?)";

        try (Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, conteudo);

            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas != 1) {
                throw new SQLException("Erro: Não foi possível criar a mensagem.");
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

    public static void listarMensagens() throws SQLException {
        String sql = "Select * from mensagem";

        try (Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql); ResultSet conteudo = statement.executeQuery()) {
            boolean encontrou = false;

            while (conteudo.next()) {
                imprimirMensagens(conteudo);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhuma mensagem encontrada.");
            }
        }
    }

    public static boolean eliminarMensagem(long id) throws SQLException {
        boolean eliminada = false;
        String sql = "Delete from mensagem where id=?";

        try (Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);

            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas != 1) {
                throw new SQLException("Erro: Não foi possível eliminar a mensagem!");
            } else {
                eliminada = true;
            }
        }
        return eliminada;

    }

    public static boolean atualizarMensagem(long id, String conteudo) throws SQLException {
        boolean atualizou = false;
        String sql = "Update mensagem set conteudo=? where id=?";

        try (Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, conteudo);
            statement.setLong(2, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas != 1) {
                throw new SQLException("Erro: Não foi possível atualizar a mensagem!");
            } else {
                atualizou = true;
            }
        }

        return atualizou;
    }

    public static boolean buscarPorId(long id) throws SQLException {
        String sql = " SELECT id, conteudo FROM mensagem WHERE id = ? ";

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirMensagens(resposta);
                    return true;
                }
                return false;
            }
        }
    }

    public static utils.Mensagem procurarMensagemPorEtapa(
            Connection conexao,
            long etapaId
    ) throws SQLException {

        String sql = """
        SELECT m.id, m.conteudo
        FROM mensagem m
        INNER JOIN etapa_mensagem em
            ON em.mensagem_id = m.id
        WHERE em.etapa_id = ?
        """;

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, etapaId);

            try (ResultSet resposta = statement.executeQuery()) {

                if (resposta.next()) {
                    return new utils.Mensagem(
                            resposta.getLong("id"),
                            resposta.getString("conteudo")
                    );
                }
            }
        }

        return null;
    }

    public static void imprimirMensagens(ResultSet result) throws SQLException {
        System.out.println("ID: " + result.getLong("id") + "|"
                + "Conteudo: " + result.getString("conteudo"));
    }

}
