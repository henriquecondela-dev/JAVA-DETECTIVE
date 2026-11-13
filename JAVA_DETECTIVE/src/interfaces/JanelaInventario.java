import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

class Evidencia {

    private int id;
    private String nome;
    private String descricao;
    private String caminhoImagem;

    public Evidencia(int id, String nome, String descricao, String caminhoImagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoImagem = caminhoImagem;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }
}

interface OuvinteEvidencias {
    void evidenciaSelecionada(Evidencia evidencia);
}

class PainelInventario extends JPanel {

    private int totalSlots = 8;

    private Color fundo = new Color(35, 23, 17);
    private Color painel = new Color(48, 32, 24);
    private Color borda = new Color(105, 76, 55);
    private Color texto = new Color(235, 218, 195);
    private Color selecionado = new Color(130, 91, 60);

    private List<Evidencia> evidencias;
    private Evidencia evidenciaSelecionada;

    private JPanel grade;
    private JLabel lblNome;
    private JTextArea txtDescricao;

    private OuvinteEvidencias ouvinte;

    public PainelInventario() {

        evidencias = new ArrayList<>();

        setLayout(new BorderLayout(15, 15));
        setBackground(fundo);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        adicionarTopo();
        adicionarGrade();
        adicionarDescricao();

        atualizarGrade();
    }

    private void adicionarTopo() {

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(fundo);

        JLabel titulo = new JLabel("INVENTARIO DE EVIDENCIAS");
        titulo.setForeground(texto);
        titulo.setFont(new Font("Georgia", Font.BOLD, 24));

        JButton sair = new JButton("X");
        sair.setForeground(texto);
        sair.setBackground(painel);
        sair.setFocusPainted(false);
        sair.setBorder(new LineBorder(borda));

        sair.addActionListener(e -> {
            Window janela = SwingUtilities.getWindowAncestor(this);

            if (janela != null) {
                janela.dispose();
            }
        });

        topo.add(titulo, BorderLayout.WEST);
        topo.add(sair, BorderLayout.EAST);

        add(topo, BorderLayout.NORTH);
    }

    private void adicionarGrade() {

        grade = new JPanel(new GridLayout(2, 4, 12, 12));
        grade.setBackground(fundo);

        add(grade, BorderLayout.CENTER);
    }

    private void adicionarDescricao() {

        JPanel painelDescricao = new JPanel(new BorderLayout(10, 10)) {

            Image imagem = null;

            {
                java.net.URL caminho = getClass().getResource("/imagens/fundo.jpeg");

                if (caminho != null) {
                    imagem = new ImageIcon(caminho).getImage();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (imagem != null) {
                    g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        painelDescricao.setOpaque(false);
        painelDescricao.setBorder(new EmptyBorder(15, 15, 15, 15));
        painelDescricao.setPreferredSize(new Dimension(250, 0));

        JLabel titulo = new JLabel("DETALHES");
        titulo.setForeground(texto);
        titulo.setFont(new Font("Georgia", Font.BOLD, 19));

        lblNome = new JLabel("Nenhuma evidencia selecionada");
        lblNome.setForeground(texto);
        lblNome.setFont(new Font("Georgia", Font.BOLD, 16));

        txtDescricao = new JTextArea();
        txtDescricao.setText("Selecione uma evidencia para consultar os seus detalhes.");
        txtDescricao.setFont(new Font("Georgia", Font.PLAIN, 15));
        txtDescricao.setForeground(texto);
        txtDescricao.setBackground(new Color(35, 23, 17, 180));
        txtDescricao.setOpaque(false);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtDescricao);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        JPanel informacoes = new JPanel(new BorderLayout(10, 10));
        informacoes.setOpaque(false);

        informacoes.add(lblNome, BorderLayout.NORTH);
        informacoes.add(scroll, BorderLayout.CENTER);

        painelDescricao.add(titulo, BorderLayout.NORTH);
        painelDescricao.add(informacoes, BorderLayout.CENTER);

        add(painelDescricao, BorderLayout.EAST);
    }

    private void atualizarGrade() {

        grade.removeAll();

        for (int i = 0; i < totalSlots; i++) {

            if (i < evidencias.size()) {
                adicionarEvidencia(evidencias.get(i));
            } else {
                adicionarEspacoVazio();
            }
        }

        grade.revalidate();
        grade.repaint();
    }

    private void adicionarEvidencia(Evidencia evidencia) {

        JButton botao = new JButton(evidencia.getNome());

        botao.setBackground(painel);
        botao.setForeground(texto);
        botao.setFont(new Font("Georgia", Font.PLAIN, 15));
        botao.setFocusPainted(false);

        botao.setVerticalTextPosition(SwingConstants.BOTTOM);
        botao.setHorizontalTextPosition(SwingConstants.CENTER);

        String caminho = evidencia.getCaminhoImagem();

        if (caminho != null) {

            java.net.URL imagem = getClass().getResource(caminho);

            if (imagem != null) {

                ImageIcon icone = new ImageIcon(imagem);

                Image imagemRedimensionada = icone.getImage().getScaledInstance(
                        65, 65, Image.SCALE_SMOOTH
                );

                botao.setIcon(new ImageIcon(imagemRedimensionada));
            }
        }

        if (evidencia == evidenciaSelecionada) {
            botao.setBorder(new LineBorder(selecionado, 3));
        } else {
            botao.setBorder(new LineBorder(borda, 1));
        }

        botao.addActionListener(e -> selecionarEvidencia(evidencia));

        grade.add(botao);
    }

    private void adicionarEspacoVazio() {

        JButton botao = new JButton("+");

        botao.setBackground(painel);
        botao.setForeground(borda);
        botao.setFont(new Font("Georgia", Font.BOLD, 30));
        botao.setBorder(new LineBorder(borda, 1));
        botao.setFocusPainted(false);
        botao.setEnabled(false);

        grade.add(botao);
    }

    private void selecionarEvidencia(Evidencia evidencia) {

        evidenciaSelecionada = evidencia;

        lblNome.setText(evidencia.getNome());
        txtDescricao.setText(evidencia.getDescricao());

        atualizarGrade();

        if (ouvinte != null) {
            ouvinte.evidenciaSelecionada(evidencia);
        }
    }

    public void adicionar(Evidencia evidencia) {

        if (evidencias.size() < totalSlots) {

            evidencias.add(evidencia);
            atualizarGrade();
        }
    }

    public void definirEvidencias(List<Evidencia> lista) {

        evidencias.clear();

        for (int i = 0; i < lista.size() && i < totalSlots; i++) {
            evidencias.add(lista.get(i));
        }

        evidenciaSelecionada = null;

        lblNome.setText("Nenhuma evidencia selecionada");
        txtDescricao.setText("Selecione uma evidencia para consultar os seus detalhes.");

        atualizarGrade();
    }

    public void limpar() {

        evidencias.clear();
        evidenciaSelecionada = null;

        lblNome.setText("Nenhuma evidencia selecionada");
        txtDescricao.setText("Selecione uma evidencia para consultar os seus detalhes.");

        atualizarGrade();
    }

    public void setOuvinte(OuvinteEvidencias ouvinte) {
        this.ouvinte = ouvinte;
    }
}

public class JanelaInventario extends JFrame {

    public JanelaInventario() {

        setTitle("Inventario de Evidencias");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        PainelInventario inventario = new PainelInventario();

        inventario.adicionar(new Evidencia(
                1,
                "Documento",
                "Documento encontrado durante a investigacao.",
                "/imagens/documento.png"
        ));

        inventario.adicionar(new Evidencia(
                2,
                "Fotografia",
                "Fotografia encontrada na cena do crime.",
                "/imagens/fotografia.png"
        ));

        inventario.adicionar(new Evidencia(
                3,
                "Chave",
                "Uma chave encontrada perto do local.",
                "/imagens/chave.png"
        ));

        add(inventario);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JanelaInventario janela = new JanelaInventario();
            janela.setVisible(true);
        });
    }
}