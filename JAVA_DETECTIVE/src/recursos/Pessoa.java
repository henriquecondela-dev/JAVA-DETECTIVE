/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import db.*;
import java.sql.*;

/**
 *
 * @author gercia-dev
 */
public class Pessoa {

    //o metodo criar pessoas
    public static void criarPessoa(String nome, int idade, String genero, String descricao) {

        String codigo = "insert into pessoa (nome, idade, genero, descricao) value(?, ?, ?, ?)";

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(codigo);) {
            statement.setString(1, nome);
            statement.setInt(2, idade);
            statement.setString(3, genero);
            statement.setString(4, descricao);

            int linhas = statement.executeUpdate();
            if (linhas == 1) {
                System.out.println("Pessoa adicionada com sucesso");
            } else {
                System.out.println("Pessoa nao adicionada");
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO LISTAR AS PESSOAS");
        }
    }

    public static long criarPessoa(
            Connection conexao,
            String nome,
            int idade,
            String genero,
            String descricao
    ) throws SQLException {

        String sql = """
            INSERT INTO pessoa (nome, idade, genero, descricao)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = conexao.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {

            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.setString(3, genero);
            stmt.setString(4, descricao);

            int linhas = stmt.executeUpdate();

            if (linhas != 1) {
                throw new SQLException("Erro ao criar pessoa.");
            }

            try (ResultSet resultado = stmt.getGeneratedKeys()) {

                if (resultado.next()) {
                    return resultado.getLong(1);
                }

                throw new SQLException("ID da pessoa não encontrado.");
            }
        }
    }

    //o metodo listar pessoas 
    public static void listarPessoas() {
        String codigo = "select * from pessoa";

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(codigo); ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                String nome = resultado.getString("nome");
                int idade = resultado.getInt("idade");
                String genero = resultado.getString("Genero");
                String descricao = resultado.getString("Descricao");

                System.out.print("Nome: " + nome + " ");
                System.out.print("Idade: " + idade + " ");
                System.out.print("genero: " + genero + " ");
                System.out.println("descricao: " + descricao);
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO LISTAR AS PESSOAS");
        }
    }

    public static void eliminarPessoas(int id) {
        String codigo = "Delete from pessoa where id=?";
        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(codigo);) {
            statement.setInt(1, id);
            int linhas = statement.executeUpdate();
            if (linhas == 1) {
                System.out.println("Pessoa removida com sucesso.");
            } else {
                System.out.println("Pessoa removida com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO LISTAR AS PESSOAS");
        }
    }

    public static void atualizar(int id, String nome, int idade, String genero, String descricao) {
        String codigo = "update pessoa set nome=?, idade=?, genero=?, descricao=? where id=?";

        try (
                Connection conexao = ConexaoMySQL.getConnection(); PreparedStatement statement = conexao.prepareStatement(codigo);) {
            statement.setString(1, nome);
            statement.setInt(2, idade);
            statement.setString(3, genero);
            statement.setString(4, descricao);

            int linhas = statement.executeUpdate();
            if (linhas > 0) {
                System.out.println("Pessoa atualizada com sucesso");
            } else {
                System.out.println("Pessoa nao atualizad");
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO atualizar AS PESSOAS");
        }
    }
}
