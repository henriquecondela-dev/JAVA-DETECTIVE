package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.*;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class EditorJava extends JPanel {

    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(EditorJava.class.getName());

    private utils.DesafioDeProgramacao desafio;

    private JTextArea areaEnunciado;
    private JTextArea numerosLinha;

    private JButton btnVoltar;
    private JButton btnExecutar;

    private Runnable acaoVoltar;

    private JTextArea areaDeCodigo;
    private JTextPane terminal;

    private JPanel topo;
    private JPanel painelCodigo;
    private JPanel painelTerminal;

    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;

    private long progressoCasoId;

    private JLabelPlaceholder placeholder;

    public EditorJava() {

        inicializarComponentes();

        configurarNumerosLinha();

        configurarLayoutDividido();
    }

    public EditorJava(
            utils.DesafioDeProgramacao desafio,
            long progressoCasoId,
            Runnable acaoVoltar
    ) {

        this();

        if (desafio == null) {
            throw new IllegalArgumentException(
                    "O desafio não pode ser nulo."
            );
        }

        if (progressoCasoId <= 0) {
            throw new IllegalArgumentException(
                    "O progresso da investigação é inválido."
            );
        }

        this.desafio = desafio;
        this.progressoCasoId = progressoCasoId;
        this.acaoVoltar = acaoVoltar;

        areaEnunciado.setText(desafio.getDescricao());
        areaEnunciado.setCaretPosition(0);

        topo.add(btnVoltar, BorderLayout.EAST);

        revalidate();
        repaint();
    }

    private void inicializarComponentes() {

        Color fundo = new Color(20, 20, 20);
        Color dourado = new Color(216, 189, 145);

        // Cabeçalho
        topo = new JPanel(new BorderLayout());
        topo.setBackground(new Color(30, 25, 20));
        topo.setPreferredSize(new Dimension(100, 65));

        PainelFundo fundoTopo
                = new PainelFundo("/imagens/ack.jpeg");

        fundoTopo.setLayout(new BorderLayout());

        javax.swing.JLabel simbolo
                = new javax.swing.JLabel("</>");

        simbolo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        simbolo.setForeground(Color.WHITE);

        javax.swing.JLabel titulo
                = new javax.swing.JLabel("Editor de Código");

        titulo.setFont(new Font("Serif", Font.PLAIN, 20));
        titulo.setForeground(Color.WHITE);

        JPanel identificacao
                = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        identificacao.setOpaque(false);
        identificacao.add(simbolo);
        identificacao.add(titulo);

        fundoTopo.add(identificacao, BorderLayout.CENTER);

        topo.add(fundoTopo, BorderLayout.CENTER);

        btnVoltar = new JButton("Voltar ao Jogo");

        btnVoltar.setFocusPainted(false);
        btnVoltar.setBackground(new Color(75, 55, 35));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Serif", Font.BOLD, 14));

        btnVoltar.addActionListener(e -> {

            if (acaoVoltar != null) {
                acaoVoltar.run();
            }

        });

        // Enunciado
        areaEnunciado = new JTextArea();

        areaEnunciado.setEditable(false);
        areaEnunciado.setLineWrap(true);
        areaEnunciado.setWrapStyleWord(true);

        areaEnunciado.setFont(
                new Font("Serif", Font.PLAIN, 17)
        );

        areaEnunciado.setBackground(new Color(42, 29, 18));
        areaEnunciado.setForeground(new Color(240, 220, 180));

        areaEnunciado.setBorder(
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        );

        areaDeCodigo = new JTextArea();

        areaDeCodigo.setBackground(Color.BLACK);
        areaDeCodigo.setForeground(new Color(242, 242, 242));

        areaDeCodigo.setFont(
                new Font("Monospaced", Font.PLAIN, 15)
        );

        areaDeCodigo.setTabSize(4);

        areaDeCodigo.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        jScrollPane1 = new JScrollPane(areaDeCodigo);

        jScrollPane1.setBorder(
                BorderFactory.createLineBorder(
                        new Color(65, 65, 65)
                )
        );

        painelCodigo = new JPanel(new BorderLayout());
        painelCodigo.add(jScrollPane1, BorderLayout.CENTER);

        terminal = new javax.swing.JTextPane();

        terminal.setEditable(false);
        terminal.setBackground(new Color(15, 15, 15));
        terminal.setForeground(new Color(220, 220, 220));

        terminal.setFont(
                new Font("Monospaced", Font.BOLD, 14)
        );

        terminal.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        jScrollPane2 = new JScrollPane(terminal);

        jScrollPane2.setBorder(
                BorderFactory.createLineBorder(
                        new Color(65, 65, 65)
                )
        );

        painelTerminal = new JPanel(new BorderLayout());
        painelTerminal.add(jScrollPane2, BorderLayout.CENTER);

        // Botão executar
        btnExecutar = new JButton("Executar");

        btnExecutar.setBackground(new Color(46, 125, 50));
        btnExecutar.setForeground(Color.WHITE);
        btnExecutar.setFocusPainted(false);

        btnExecutar.addActionListener(
                this::btnExecutarActionPerformed
        );

        setBackground(fundo);
        setLayout(new BorderLayout());

    }

    private void configurarLayoutDividido() {

        Color dourado = new Color(216, 189, 145);

        JScrollPane scrollEnunciado
                = new JScrollPane(areaEnunciado);

        scrollEnunciado.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(dourado),
                        "Enunciado do Desafio",
                        0,
                        0,
                        new Font("Serif", Font.BOLD, 15),
                        dourado
                )
        );

        JPanel painelBotao
                = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        painelBotao.setBackground(Color.BLACK);
        painelBotao.add(btnExecutar);

        JPanel painelExecucao
                = new JPanel(new BorderLayout(0, 5));

        painelExecucao.setBackground(Color.BLACK);

        painelExecucao.add(
                painelBotao,
                BorderLayout.NORTH
        );

        painelExecucao.add(
                painelTerminal,
                BorderLayout.CENTER
        );

        JSplitPane divisaoEditor
                = new JSplitPane(
                        JSplitPane.VERTICAL_SPLIT,
                        painelCodigo,
                        painelExecucao
                );

        divisaoEditor.setResizeWeight(0.70);
        divisaoEditor.setDividerSize(6);
        divisaoEditor.setContinuousLayout(true);

        JSplitPane divisaoPrincipal
                = new JSplitPane(
                        JSplitPane.VERTICAL_SPLIT,
                        scrollEnunciado,
                        divisaoEditor
                );

        divisaoPrincipal.setResizeWeight(0.30);
        divisaoPrincipal.setDividerSize(7);
        divisaoPrincipal.setContinuousLayout(true);

        add(topo, BorderLayout.NORTH);
        add(divisaoPrincipal, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {

            divisaoPrincipal.setDividerLocation(0.30);
            divisaoEditor.setDividerLocation(0.70);

        });

    }

    private void configurarNumerosLinha() {

        numerosLinha = new JTextArea();

        numerosLinha.setEditable(false);
        numerosLinha.setFocusable(false);

        numerosLinha.setBackground(new Color(65, 65, 65));
        numerosLinha.setForeground(new Color(200, 200, 200));

        numerosLinha.setFont(areaDeCodigo.getFont());

        numerosLinha.setLineWrap(false);
        numerosLinha.setWrapStyleWord(false);

        numerosLinha.setMargin(areaDeCodigo.getMargin());

        atualizarNumerosLinha();

        jScrollPane1.setRowHeaderView(numerosLinha);

        areaDeCodigo.getDocument().addDocumentListener(
                new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarNumerosLinha();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarNumerosLinha();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atualizarNumerosLinha();
            }

        });

    }

    private void atualizarNumerosLinha() {

        int totalLinhas = areaDeCodigo.getLineCount();

        StringBuilder numeros = new StringBuilder();

        for (int i = 1; i <= totalLinhas; i++) {
            numeros.append(i).append("\n");
        }

        numerosLinha.setText(numeros.toString());

    }

    private void escreverTerminal(
            String mensagem,
            Color cor
    ) {

        StyledDocument documento
                = terminal.getStyledDocument();

        SimpleAttributeSet estilo
                = new SimpleAttributeSet();

        StyleConstants.setForeground(estilo, cor);

        try {

            documento.insertString(
                    documento.getLength(),
                    mensagem + "\n",
                    estilo
            );

            terminal.setCaretPosition(
                    documento.getLength()
            );

        } catch (BadLocationException e) {

            e.printStackTrace();

        }

    }

    private void mostrarMensagem(
            String tipo,
            String mensagem
    ) {

        switch (tipo) {

            case "INFO":
                escreverTerminal(
                        "[INFO] " + mensagem,
                        Color.CYAN
                );
                break;

            case "SUCCESS":
                escreverTerminal(
                        "[SUCCESS] " + mensagem,
                        Color.GREEN
                );
                break;

            case "ERROR":
                escreverTerminal(
                        "[ERROR] " + mensagem,
                        Color.RED
                );
                break;

            case "WARNING":
                escreverTerminal(
                        "[WARNING] " + mensagem,
                        Color.ORANGE
                );
                break;

            case "OUTPUT":
                escreverTerminal(
                        mensagem,
                        Color.WHITE
                );
                break;

        }

    }

    private void btnExecutarActionPerformed(
            java.awt.event.ActionEvent evt
    ) {

        terminal.setText("");

        String codigo = areaDeCodigo.getText();

        if (codigo.isBlank()) {
            mostrarMensagem(
                    "WARNING",
                    "Escreva a sua solução antes de submeter."
            );
            return;
        }

        if (desafio == null || progressoCasoId <= 0) {
            mostrarMensagem(
                    "ERROR",
                    "Não foi possível identificar o desafio ou a investigação."
            );
            return;
        }

        btnExecutar.setEnabled(false);

        mostrarMensagem(
                "INFO",
                "A submeter a solução..."
        );

        mostrarMensagem(
                "INFO",
                "A verificar os requisitos do desafio."
        );

        SwingWorker<Boolean, Void> executor = new SwingWorker<>() {

            @Override
            protected Boolean doInBackground() throws Exception {

                return jogo.GestorDesafios.submeterSolucao(
                        progressoCasoId,
                        desafio.getId(),
                        codigo
                );
            }

            @Override
            protected void done() {

                try {

                    boolean aprovado = get();

                    if (aprovado) {

                        mostrarMensagem(
                                "SUCCESS",
                                "Parabéns! Desafio concluído."
                        );

                        mostrarMensagem(
                                "SUCCESS",
                                "A tua solução foi validada e o progresso foi guardado."
                        );

                    } else {

                        mostrarMensagem(
                                "ERROR",
                                "A solução não passou nos testes."
                        );

                        mostrarMensagem(
                                "WARNING",
                                "Revê o código e tenta novamente."
                        );
                    }

                } catch (Exception ex) {

                    mostrarMensagem(
                            "ERROR",
                            "Não foi possível avaliar a solução: "
                            + ex.getMessage()
                    );

                    logger.log(
                            java.util.logging.Level.SEVERE,
                            "Erro na submissão do desafio.",
                            ex
                    );

                } finally {

                    btnExecutar.setEnabled(true);
                }
            }
        };

        executor.execute();
    }

    public class PainelFundo extends JPanel {

        private final Image fundo;

        public PainelFundo(String caminho) {

            java.net.URL url
                    = getClass().getResource(caminho);

            System.out.println(
                    "Imagem encontrada: " + url
            );

            fundo = (url != null)
                    ? new ImageIcon(url).getImage()
                    : null;

        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            if (fundo != null) {

                g.drawImage(
                        fundo,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );

            }

        }

    }

    /**
     * Teste isolado do painel. Na aplicação, a TelaPrincipal irá incorporá-lo.
     */
    public static void main(String args[]) {

        SwingUtilities.invokeLater(() -> {

            JFrame janela = new JFrame("Editor de Código");

            janela.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            janela.setContentPane(new EditorJava());

            janela.setSize(1100, 750);

            janela.setLocationRelativeTo(null);

            janela.setVisible(true);

        });

    }

    // Classe auxiliar sem utilização funcional.
    private class JLabelPlaceholder {
    }

}
