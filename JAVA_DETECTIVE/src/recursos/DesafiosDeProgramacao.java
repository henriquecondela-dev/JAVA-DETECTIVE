/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author henrique-dev
 */
package recursos;

import db.ConexaoMySQL;
import java.sql.*;

public class DesafiosDeProgramacao {

    // CREATE 
    public static long criarDesafio(
            String titulo,
            long casoId,
            String descricao,
            long etapaId,
            String classeEsperada,
            String metodoEsperado
    ) throws SQLException {

        String sql = """
                INSERT INTO desafioDeProgramacao (
                    titulo,
                    caso_id,
                    descricao,
                    etapa_id,
                    classe_esperada,
                    metodo_esperado,
                    concluido
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, titulo);
            statement.setLong(2, casoId);
            statement.setString(3, descricao);
            statement.setLong(4, etapaId);
            statement.setString(5, classeEsperada);
            statement.setString(6, metodoEsperado);
            statement.setBoolean(7, false);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas != 1) {
                throw new SQLException("Não foi possível criar o desafio.");
            }

            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    return chaves.getLong(1);
                }

                throw new SQLException(
                        "O INSERT foi executado, mas não foi possível obter o ID."
                );
            }
        }
    }

    // READ 
    public static void listarDesafios() throws SQLException {

        String sql = """
                SELECT id, titulo, caso_id, descricao, etapa_id,
                       classe_esperada, metodo_esperado, concluido
                FROM desafioDeProgramacao
                ORDER BY id
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resposta = statement.executeQuery()
        ) {
            boolean encontrou = false;

            while (resposta.next()) {
                imprimirDesafio(resposta);
                encontrou = true;
            }

            if (!encontrou) {
                System.out.println("Nenhum desafio registado.");
            }
        }
    }

    // READ 
    public static boolean buscarDesafioPorId(long id)
            throws SQLException {

        String sql = """
                SELECT id, titulo, caso_id, descricao, etapa_id,
                       classe_esperada, metodo_esperado, concluido
                FROM desafioDeProgramacao
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resposta = statement.executeQuery()) {
                if (resposta.next()) {
                    imprimirDesafio(resposta);
                    return true;
                }

                return false;
            }
        }
    }

    // UPDATE 
    public static boolean atualizarDesafio(
            long id,
            String titulo,
            long casoId,
            String descricao,
            long etapaId,
            String classeEsperada,
            String metodoEsperado
    ) throws SQLException {

        String sql = """
                UPDATE desafioDeProgramacao
                SET titulo = ?,
                    caso_id = ?,
                    descricao = ?,
                    etapa_id = ?,
                    classe_esperada = ?,
                    metodo_esperado = ?,
                WHERE id = ?
                """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setString(1, titulo);
            statement.setLong(2, casoId);
            statement.setString(3, descricao);
            statement.setLong(4, etapaId);
            statement.setString(5, classeEsperada);
            statement.setString(6, metodoEsperado);
            statement.setLong(7, id);

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE 
    public static boolean eliminarDesafio(long id)
            throws SQLException {

        String sql = "DELETE FROM desafioDeProgramacao WHERE id = ?";

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    // Auxiliar 
    private static void imprimirDesafio(ResultSet resposta)
            throws SQLException {

        System.out.println(
                "ID: " + resposta.getLong("id")
                + "\nTítulo: " + resposta.getString("titulo")
                + "\nCaso ID: " + resposta.getLong("caso_id")
                + "\nDescrição: " + resposta.getString("descricao")
                + "\nEtapa ID: " + resposta.getLong("etapa_id")
                + "\nClasse esperada: " + resposta.getString("classe_esperada")
                + "\nMétodo esperado: " + resposta.getString("metodo_esperado")
                + "\nConcluído: " + resposta.getObject("concluido")
                + "\n--------------------------------"
        );
    }
}
