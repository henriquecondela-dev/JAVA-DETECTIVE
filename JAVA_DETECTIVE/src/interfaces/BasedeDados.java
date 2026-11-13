import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BasedeDados extends JFrame {
    
    static final Color fundo = new Color(18, 12, 8);
    static final Color barra = new Color(27, 18, 12);
    static final Color painel = new Color(30, 19, 12);
    static final Color areaTabela = new Color(23, 15, 10);
    static final Color cabecalho = new Color(39, 25, 17);
    static final Color linhaAlternada = new Color(32, 21, 14);
    static final Color borda = new Color(60, 41, 28);
    static final Color texto = new Color(226, 211, 190);
    static final Color selecao = new Color(105, 70, 43);

    static final String fonte = "Georgia";

    private final String[] nomes = {
        "pessoas",
        "registos_entrada",
        "objetos",
        "mensagens"
    };

    private final String[][] colunas = {
        {"id", "nome", "idade", "profissao", "ultimo_local"},
        {"id", "pessoa", "data", "hora", "local"},
        {"id", "nome", "categoria", "proprietario", "local"},
        {"id", "remetente", "destinatario", "mensagem", "data"}
    };

    private final List<JButton> botoes = new ArrayList<>();

    private final JLabel lblTitulo = new JLabel();

    private final DefaultTableModel modelo = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modelo);

    public BasedeDados() {

        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.setBackground(fundo);
        painelPrincipal.setBorder(
            BorderFactory.createLineBorder(borda)
        );

        painelPrincipal.add(criarBarraSuperior(), BorderLayout.NORTH);
        painelPrincipal.add(criarMenuLateral(), BorderLayout.WEST);
        painelPrincipal.add(criarAreaPrincipal(), BorderLayout.CENTER);

        setContentPane(painelPrincipal);

        selecionarTabela(0);
    }

    private JPanel criarBarraSuperior() {

        JPanel painelTopo = new JPanel(new BorderLayout());

        painelTopo.setBackground(barra);
        painelTopo.setPreferredSize(new Dimension(0, 55));

        painelTopo.setBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, borda)
        );

        JLabel lblBase = new JLabel(
            "Base de Dados",
            new Icone(Icone.Tipo.BASE_DADOS, texto),
            SwingConstants.LEFT
        );

        lblBase.setForeground(texto);
        lblBase.setFont(new Font(fonte, Font.BOLD, 20));
        lblBase.setIconTextGap(12);

        lblBase.setBorder(
            BorderFactory.createEmptyBorder(0, 18, 0, 0)
        );

        painelTopo.add(lblBase, BorderLayout.WEST);

        JButton btnFechar = new JButton("X");

        btnFechar.setForeground(texto);
        btnFechar.setBackground(barra);
        btnFechar.setFont(new Font(fonte, Font.BOLD, 18));
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setPreferredSize(new Dimension(55, 55));

        btnFechar.addActionListener(e -> dispose());

        painelTopo.add(btnFechar, BorderLayout.EAST);

        return painelTopo;
    }

    // se tiver algum problema no menu lateral podes alteral aqui Tony!
    private JPanel criarMenuLateral() {

        JPanel painelMenu = new JPanel();

        painelMenu.setLayout(
            new BoxLayout(painelMenu, BoxLayout.Y_AXIS)
        );

        painelMenu.setBackground(painel);
        painelMenu.setPreferredSize(new Dimension(240, 0));

        painelMenu.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, borda),
                BorderFactory.createEmptyBorder(20, 10, 15, 10)
            )
        );

        Icone.Tipo[] tipos = {
            Icone.Tipo.PESSOA,
            Icone.Tipo.ENTRADA,
            Icone.Tipo.OBJETO,
            Icone.Tipo.MENSAGEM
        };

        for (int i = 0; i < nomes.length; i++) {

            final int indice = i;

            JButton btn = new JButton(
                nomes[i],
                new Icone(tipos[i], texto)
            );

            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setIconTextGap(14);
            btn.setForeground(texto);
            btn.setBackground(painel);
            btn.setFont(new Font(fonte, Font.PLAIN, 18));

            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);

            btn.setBorder(
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
            );

            btn.setAlignmentX(Component.LEFT_ALIGNMENT);

            btn.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 52)
            );

            btn.addActionListener(
                e -> selecionarTabela(indice)
            );

            botoes.add(btn);

            painelMenu.add(btn);
            painelMenu.add(Box.createVerticalStrut(8));
        }

        return painelMenu;
    }

    // aqui fica a tabela entao fica facil modificar caso vc precise tony
    private JPanel criarAreaPrincipal() {

        JPanel painelConteudo = new JPanel(new BorderLayout(0, 18));

        painelConteudo.setBackground(fundo);

        painelConteudo.setBorder(
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        lblTitulo.setForeground(texto);
        lblTitulo.setFont(new Font(fonte, Font.BOLD, 23));

        painelConteudo.add(lblTitulo, BorderLayout.NORTH);

        configurarTabela();

        JScrollPane scroll = new JScrollPane(tabela);

        scroll.setBorder(
            BorderFactory.createLineBorder(borda)
        );

        scroll.getViewport().setBackground(areaTabela);

        painelConteudo.add(scroll, BorderLayout.CENTER);

        return painelConteudo;
    }

    private void configurarTabela() {

    tabela.setRowHeight(38);

    tabela.setBackground(areaTabela);
    tabela.setForeground(texto);
    tabela.setFont(new Font(fonte, Font.PLAIN, 16));

    tabela.setGridColor(borda);
    tabela.setSelectionBackground(selecao);
    tabela.setSelectionForeground(Color.WHITE);

    tabela.setShowVerticalLines(false);
    tabela.setFillsViewportHeight(true);

    tabela.getTableHeader().setBackground(cabecalho);
    tabela.getTableHeader().setForeground(texto);
    tabela.getTableHeader().setFont(new Font(fonte, Font.BOLD, 17));
    tabela.getTableHeader().setPreferredSize(new Dimension(0, 43));

    tabela.getTableHeader().setReorderingAllowed(false);
    tabela.getTableHeader().setOpaque(true);
    }
    private void selecionarTabela(int indice) {

        for (int i = 0; i < botoes.size(); i++) {

            JButton btn = botoes.get(i);

            boolean selecionado = i == indice;

            btn.setBackground(
                selecionado ? selecao : painel
            );

            btn.setContentAreaFilled(selecionado);
        }

        String nomeTabela = nomes[indice];

        lblTitulo.setText("Tabela: " + nomeTabela);

        modelo.setRowCount(0);

        modelo.setColumnIdentifiers(colunas[indice]);
    }

    
    static class Icone implements Icon {

        enum Tipo {
            BASE_DADOS, PESSOA, ENTRADA, OBJETO, MENSAGEM
        }

        private final Tipo tipo;
        private final Color cor;

        Icone(Tipo tipo, Color cor) {
            this.tipo = tipo;
            this.cor = cor;
        }

        @Override
        public int getIconWidth() {
            return 20;
        }

        @Override
        public int getIconHeight() {
            return 20;
        }

        @Override
        public void paintIcon(
            Component componente,
            Graphics grafico,
            int x,
            int y
        ) {

            Graphics2D g = (Graphics2D) grafico.create();

            g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g.translate(x, y);
            g.setColor(cor);
            g.setStroke(new BasicStroke(1.8f));

            switch (tipo) {

                case BASE_DADOS:
                    g.drawOval(3, 2, 14, 6);
                    g.drawLine(3, 5, 3, 15);
                    g.drawLine(17, 5, 17, 15);
                    g.drawArc(3, 9, 14, 6, 180, 180);
                    g.drawArc(3, 14, 14, 6, 180, 180);
                    break;

                case PESSOA:
                    g.drawOval(7, 2, 7, 7);
                    g.drawArc(3, 11, 15, 12, 0, 180);
                    break;

                case ENTRADA:
                    g.drawRect(3, 2, 9, 15);
                    g.drawLine(9, 10, 18, 10);
                    g.drawLine(14, 7, 18, 10);
                    g.drawLine(14, 13, 18, 10);
                    break;

                case OBJETO:
                    g.drawRect(3, 5, 14, 11);
                    g.drawLine(3, 10, 17, 10);
                    g.drawLine(10, 5, 10, 10);
                    break;

                case MENSAGEM:
                    g.drawRect(2, 4, 17, 12);
                    g.drawLine(2, 4, 10, 11);
                    g.drawLine(19, 4, 10, 11);
                    break;
            }

            g.dispose();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new BasedeDados().setVisible(true);
        });
    }
}