/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogo;

/**
 *
 * @author antonio-nuvunga
 */
import db.ConexaoMySQL;
import recursos.ProgressoDesafio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GestorDesafios {

    public static boolean submeterSolucao(long progressoCasoId, long desafioId, String codigo ) throws Exception {

        if (progressoCasoId <= 0 || desafioId <= 0
                || codigo == null || codigo.isBlank()) {
            return false;
        }

        String nomeClasse;
        String assinatura;

        // Obter os dados do desafio.
        String sql = """
            SELECT classe_esperada, metodo_esperado
            FROM desafioDeProgramacao
            WHERE id = ?
            """;

        try (
            Connection conexao = ConexaoMySQL.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql)
        ) {
            statement.setLong(1, desafioId);

            try (ResultSet resultado = statement.executeQuery()) {

                if (!resultado.next()) {
                    return false;
                }

                nomeClasse = resultado.getString("classe_esperada");
                assinatura = resultado.getString("metodo_esperado");
            }
        }

        // Se já estiver concluído, não precisamos de avaliar novamente.
        if (ProgressoDesafio.verificarConclusao(
                progressoCasoId, desafioId)) {
            return true;
        }

        // Garantir que existe um registo de progresso.
        try (Connection conexao = ConexaoMySQL.getConnection()) {
            ProgressoDesafio.criarProgressoDesafio(
                    conexao,
                    progressoCasoId,
                    desafioId,
                    false
            );
        }

        // Avaliar o código submetido.
        boolean aprovado = AvaliarCodigo.avaliar(
                nomeClasse,
                assinatura,
                codigo
        );

        if (!aprovado) {
            return false;
        }

        // Registar a conclusão individual.
        return ProgressoDesafio.concluirDesafio(
                progressoCasoId,
                desafioId
        );
    }
}

