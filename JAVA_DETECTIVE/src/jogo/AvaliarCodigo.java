/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogo;

/**
 *
 * @author antonio-nuvunga
 */

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class AvaliarCodigo{

    public static boolean avaliar(String nomeClasse,String assinatura,String codigo) throws IOException, InterruptedException {

        if (nomeClasse == null || assinatura == null || codigo == null) {
            return false;
        }

        String metodo = obterNomeMetodo(assinatura);

        if (metodo == null || !java.util.regex.Pattern.compile(
                "\\bclass\\s+" + java.util.regex.Pattern.quote(nomeClasse) + "\\b"
        ).matcher(codigo).find()) {
            return false;
        }

        String metodoEsperado = switch (nomeClasse) {
            case "ReconstrucaoNoite" -> "estaNaJanelaDoCrime";
            case "AnaliseDepoimentos" -> "verificarContradicao";
            case "InvestigacaoFinanceira" -> "contarTransferenciasSuspeitas";
            case "AnaliseAcessos" -> "existeRegisto";
            case "AcusacaoFinal" -> "verificarCulpado";
            default -> null;
        };

        if (!metodo.equals(metodoEsperado)) {
            return false;
        }

        Path pasta = Files.createTempDirectory("avaliacao_java_");

        try {
            Path ficheiroClasse = pasta.resolve(nomeClasse + ".java");
            Path ficheiroTeste = pasta.resolve("TesteDesafio.java");

            // O import permite utilizar ArrayList no desafio 4.
            Files.writeString(
                    ficheiroClasse,
                    "import java.util.*;\n" + codigo
            );

            Files.writeString(ficheiroTeste, criarCodigoTeste());

            JavaCompiler compilador = ToolProvider.getSystemJavaCompiler();

            if (compilador == null) {
                throw new IOException(
                        "JDK não encontrado. É necessário executar a aplicação com um JDK."
                );
            }

            int resultado = compilador.run(
                    null,
                    null,
                    null,
                    "-d", pasta.toString(),
                    ficheiroClasse.toString(),
                    ficheiroTeste.toString()
            );

            if (resultado != 0) {
                System.out.println("ERRO: A compilação do código submetido falhou.");
                return false;
            }
            String java = Path.of(
                    System.getProperty("java.home"),
                    "bin",
                    "java"
            ).toString();

            Process processo = new ProcessBuilder(
                    java,
                    "-cp", pasta.toString(),
                    "TesteDesafio",
                    nomeClasse
            ).redirectErrorStream(true).start();

            boolean terminou = processo.waitFor(8, TimeUnit.SECONDS);

            if (!terminou) {
                processo.destroyForcibly();
                return false;
            }

            String saida = new String(
                    processo.getInputStream().readAllBytes()
            ).trim();
            System.out.println("Resultado detalhado da avaliação:");
            System.out.println(saida);

            System.out.println("Saída do TesteDesafio: " + saida);
            System.out.println("Código de saída: " + processo.exitValue());

            return processo.exitValue() == 0
                    && saida.equals("APROVADO");

        } finally {
            try (var caminhos = Files.walk(pasta)) {
                caminhos.sorted(Comparator.reverseOrder())
                        .forEach(caminho -> {
                            try {
                                Files.deleteIfExists(caminho);
                            } catch (IOException ignored) {
                            }
                        });
            }
        }
    }

    private static String obterNomeMetodo(String assinatura) {
        int abertura = assinatura.indexOf('(');
        if (abertura < 0) {
            return null;
        }

        String antesDosParametros = assinatura.substring(0, abertura).trim();
        String[] partes = antesDosParametros.split("\\s+");

        return partes.length >= 2
                ? partes[partes.length - 1]
                : null;
    }

    private static String criarCodigoTeste() {
        return """
            import java.lang.reflect.*;
            import java.util.*;

            public class TesteDesafio {

                static void verificar(
                        Object instancia,
                        Method metodo,
                        Object[][] entradas,
                        Object[] esperados
                ) throws Exception {

                    for (int i = 0; i < entradas.length; i++) {
                        Object resultado = metodo.invoke(
                                instancia, entradas[i]
                        );

                        if (!esperados[i].equals(resultado)) {
                            throw new Exception("Teste falhou");
                        }
                    }
                }

                static Object criarInstancia(Class<?> classe)
                        throws Exception {

                    if (classe.getSimpleName().equals("AnaliseDepoimentos")) {
                        Constructor<?> construtor = classe.getDeclaredConstructor(
                                String.class, String.class, String.class
                        );
                        construtor.setAccessible(true);
                        return construtor.newInstance(
                                "António", "Biblioteca", "22:15"
                        );
                    }

                    Constructor<?> construtor = classe.getDeclaredConstructor();
                    construtor.setAccessible(true);
                    return construtor.newInstance();
                }

                public static void main(String[] args) {
                    try {
                        String nome = args[0];
                        Class<?> classe = Class.forName(nome);

                        switch (nome) {

                            case "ReconstrucaoNoite" -> {
                                Object obj = criarInstancia(classe);
                                Method m = classe.getDeclaredMethod(
                                        "estaNaJanelaDoCrime",
                                        int.class, int.class
                                );
                                m.setAccessible(true);

                                verificar(obj, m,
                                    new Object[][]{
                                        {22, 14}, {22, 15}, {22, 20},
                                        {22, 30}, {22, 31}
                                    },
                                    new Object[]{
                                        false, true, true, true, false
                                    }
                                );
                            }

                            case "AnaliseDepoimentos" -> {
                                Object obj = criarInstancia(classe);
                                Method m = classe.getDeclaredMethod(
                                        "verificarContradicao",
                                        String.class, String.class
                                );
                                m.setAccessible(true);

                                verificar(obj, m,
                                    new Object[][]{
                                        {"22:15", "22:15"},
                                        {"22:15", "22:20"},
                                        {" 22:15 ", "22:15"}
                                    },
                                    new Object[]{false, true, false}
                                );
                            }

                            case "InvestigacaoFinanceira" -> {
                                Object obj = criarInstancia(classe);
                                Method m = classe.getDeclaredMethod(
                                        "contarTransferenciasSuspeitas",
                                        double[].class
                                );
                                m.setAccessible(true);

                                verificar(obj, m,
                                    new Object[][]{
                                        {(Object) new double[]{
                                            50000, 120000, 200000, 100000
                                        }},
                                        {(Object) new double[]{}}
                                    },
                                    new Object[]{2, 0}
                                );
                            }

                            case "AnaliseAcessos" -> {
                                Object obj = criarInstancia(classe);
                                Method m = classe.getDeclaredMethod(
                                        "existeRegisto",
                                        ArrayList.class, String.class
                                );
                                m.setAccessible(true);

                                ArrayList<String> horarios =
                                        new ArrayList<>(
                                            List.of("22:08", "22:15", "23:00")
                                        );

                                verificar(obj, m,
                                    new Object[][]{
                                        {horarios, "22:8"},
                                        {horarios, "22:15"},
                                        {horarios, "21:00"}
                                    },
                                    new Object[]{true, true, false}
                                );
                            }

                            case "AcusacaoFinal" -> {
                                Constructor<?> construtor =
                                        classe.getDeclaredConstructor(String.class);
                                construtor.setAccessible(true);

                                Object antonio = construtor.newInstance("António");
                                Object henrique = construtor.newInstance("Henrique");

                                Method m = classe.getDeclaredMethod(
                                        "verificarCulpado"
                                );
                                m.setAccessible(true);

                                if (!Boolean.TRUE.equals(m.invoke(antonio))
                                        || !Boolean.FALSE.equals(m.invoke(henrique))) {
                                    throw new Exception("Teste falhou");
                                }
                            }

                            default -> throw new Exception("Desafio desconhecido");
                        }

                        System.out.println("APROVADO");

                    } catch (Throwable erro) {
                        erro.printStackTrace();
                        System.out.println("REPROVADO");
                        System.exit(1);
                    }
                }
            }
            """;
    }
}
