package interfaces;

import java.awt.*;
import javax.swing.*;

public class TelaJogador extends JFrame {

    private JTextField txtNome;
    private JTextField txtIdade;

    private JRadioButton rbMasculino;
    private JRadioButton rbFeminino;

    private final Color castanho = new Color(75, 48, 29);
    private final Color creme = new Color(239, 222, 191);

    public TelaJogador() {

        setTitle("Identificação do Jogador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        PainelFundo1 fundo = new PainelFundo1(
                "/imagens/fundoPapel.jpg"
        );

        fundo.setLayout(new GridBagLayout());
        setContentPane(fundo);

        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(
                new BoxLayout(painel, BoxLayout.Y_AXIS)
        );

        JLabel titulo = new JLabel(
                "Bem-vindo, Investigador!"
        );

        titulo.setFont(
                new Font("Serif", Font.BOLD, 44)
        );

        titulo.setForeground(castanho);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descricao = new JLabel(
                "<html><div style='text-align:center;width:850px;'>"
                + "Neste jogo vais resolver casos analisando pistas, "
                + "consultando dados e usando lógica."
                + "</div></html>"
        );

        descricao.setFont(
                new Font("Serif", Font.PLAIN, 23)
        );

        descricao.setForeground(castanho);
        descricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Antes de começar, conta-me sobre ti:"
        );

        subtitulo.setFont(
                new Font("Serif", Font.PLAIN, 26)
        );

        subtitulo.setForeground(castanho);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));
        painel.add(descricao);
        painel.add(Box.createVerticalStrut(35));
        painel.add(subtitulo);
        painel.add(Box.createVerticalStrut(45));

        txtNome = criarCampo();

        painel.add(criarLinha("O teu nome:", txtNome));
        painel.add(Box.createVerticalStrut(25));

        txtIdade = criarCampo();

        painel.add(criarLinha("Idade:", txtIdade));
        painel.add(Box.createVerticalStrut(25));

        rbMasculino = criarRadio("Masculino");
        rbFeminino = criarRadio("Feminino");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbMasculino);
        grupo.add(rbFeminino);

        JPanel generos = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 25, 0)
        );

        generos.setOpaque(false);
        generos.add(rbMasculino);
        generos.add(rbFeminino);

        painel.add(criarLinha("Género:", generos));
        painel.add(Box.createVerticalStrut(45));

        JButton btnContinuar = new JButton("CONTINUAR");

        btnContinuar.setFont(
                new Font("Serif", Font.BOLD, 25)
        );

        btnContinuar.setBackground(castanho);
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setFocusPainted(false);

        btnContinuar.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        btnContinuar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        btnContinuar.setPreferredSize(
                new Dimension(280, 65)
        );

        btnContinuar.setMaximumSize(
                new Dimension(280, 65)
        );

        painel.add(btnContinuar);
        btnContinuar.addActionListener(e -> iniciarInvestigacao());

        fundo.add(painel);
    }

    private void iniciarInvestigacao() {
        String nome = txtNome.getText().trim();
        String textoIdade = txtIdade.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, introduz o teu nome.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );
            txtNome.requestFocus();
            return;
        }
        int idade;
        try {
            idade = Integer.parseInt(textoIdade);

            if (idade <= 0 || idade > 120) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(
                    this,
                    "Introduz uma idade válida.",
                    "Idade inválida",
                    JOptionPane.WARNING_MESSAGE
            );
            txtIdade.requestFocus();
            return;
        }
        utils.Genero genero;
        if (rbMasculino.isSelected()) {
            genero = utils.Genero.M;

        } else if (rbFeminino.isSelected()) {
            genero = utils.Genero.F;

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona o teu género.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        try {
            jogo.GestorJogador gestor = new jogo.GestorJogador();
            long casoId = 1L;
            utils.SessaoInvestigacao sessaoInicial= gestor.criarNovaInvestigacao(
                            nome,
                            idade,
                            genero,
                            casoId
                    );

            if (sessaoInicial == null) {
                throw new IllegalStateException(
                        "Não foi possível iniciar a investigação."
                );
            }

            long jogadorId = sessaoInicial.getJogador().getJogadorId();
            utils.SessaoInvestigacao sessao= gestor.carregarSessaoCompleta(jogadorId, casoId);

            if (sessao == null || sessao.getEtapaAtual() == null) {
                throw new IllegalStateException(
                        "Não foi possível carregar a etapa inicial."
                );
            }
            TelaPrincipal tela = new TelaPrincipal(sessao);
            tela.setVisible(true);
            this.dispose();
        } catch (java.sql.SQLException | IllegalStateException erro) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ocorreu um erro ao iniciar a investigação:\n"
                    + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            erro.printStackTrace();
        }
    }

    private JTextField criarCampo() {

        JTextField campo = new JTextField();

        campo.setFont(
                new Font("Serif", Font.PLAIN, 23)
        );

        campo.setBackground(creme);
        campo.setForeground(castanho);

        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(130, 94, 58), 2
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 12, 5, 12
                        )
                )
        );

        campo.setPreferredSize(
                new Dimension(420, 52)
        );

        return campo;
    }

    private JRadioButton criarRadio(String texto) {

        JRadioButton radio = new JRadioButton(texto);

        radio.setFont(
                new Font("Serif", Font.PLAIN, 22)
        );

        radio.setForeground(castanho);
        radio.setOpaque(false);

        return radio;
    }

    private JPanel criarLinha(
            String texto,
            JComponent componente
    ) {

        JPanel linha = new JPanel(
                new BorderLayout(20, 0)
        );

        linha.setOpaque(false);

        linha.setMaximumSize(
                new Dimension(650, 60)
        );

        JLabel label = new JLabel(texto);

        label.setFont(
                new Font("Serif", Font.BOLD, 23)
        );

        label.setForeground(castanho);

        label.setPreferredSize(
                new Dimension(160, 55)
        );

        linha.add(label, BorderLayout.WEST);
        linha.add(componente, BorderLayout.CENTER);

        return linha;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TelaJogador tela = new TelaJogador();
            tela.setVisible(true);

        });
    }
}
