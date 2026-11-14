package interfaces;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class Evidencia {

    private final long id;
    private final String nome;
    private final String descricao;
    private final String caminhoImagem;

    public Evidencia(int id, String nome, String descricao, String caminhoImagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoImagem = caminhoImagem;
    }

    public long getId() {
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

    private static final int TOTAL_SLOTS = 8;

    private static final Color FUNDO = new Color(30, 25, 20);
    private static final Color SLOT_CHEIO = new Color(70, 52, 36);
    private static final Color SLOT_VAZIO = new Color(20, 15, 10);
    private static final Color BORDA = new Color(60, 50, 40);
    private static final Color DESTAQUE = new Color(230, 190, 120);

    private final List<Evidencia> evidencias = new ArrayList<>();

    private Evidencia selecionada;
    private OuvinteEvidencias ouvinte;

    private JPanel grade;
    private JLabel lblNomeItem;
    private JTextArea txtDetalhes;

    public PainelInventario() {

        setLayout(new BorderLayout(15, 15));
        setBackground(FUNDO);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(criarTopo(), BorderLayout.NORTH);
        add(criarGrade(), BorderLayout.CENTER);
        add(criarDescricao(), BorderLayout.EAST);

        atualizarGrade();
        mostrarDetalhes(null);
    }

    private JPanel criarTopo() {

        JPanel topo = new JPanel(new BorderLayout());

        topo.setBackground(new Color(45, 32, 23));
        topo.setBorder(new EmptyBorder(12, 15, 12, 15));
        topo.setPreferredSize(new Dimension(0, 55));

        JLabel titulo = new JLabel("INVENTÁRIO / EVIDÊNCIAS");

        titulo.setFont(new Font("Serif", Font.BOLD, 20));
        titulo.setForeground(new Color(230, 190, 120));

        JButton btnSair = new JButton("✕");

        btnSair.setFont(new Font("Arial", Font.BOLD, 18));
        btnSair.setForeground(Color.WHITE);
        btnSair.setBackground(new Color(45, 32, 23));
        btnSair.setFocusPainted(false);
        btnSair.setBorderPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.setPreferredSize(new Dimension(40, 35));

        btnSair.addActionListener(e -> {

            Window janela = SwingUtilities.getWindowAncestor(this);

            if (janela != null) {
                janela.dispose();
            }
        });

        topo.add(titulo, BorderLayout.WEST);
        topo.add(btnSair, BorderLayout.EAST);

        return topo;
    }

    private JPanel criarGrade() {

        grade = new JPanel(new GridLayout(2, 4, 10, 10));

        grade.setOpaque(false);

        grade.setBorder(BorderFactory.createLineBorder(BORDA));

        return grade;
    }

    private JPanel criarDescricao() {

        JPanel painel = new JPanel(new BorderLayout(0, 10)) {

            private Image imagemFundo;

            {
                URL url = getClass().getResource("/imagens/fundo.jpeg");

                if (url != null) {
                    imagemFundo = new ImageIcon(url).getImage();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                if (imagemFundo != null) {
                    g.drawImage(imagemFundo, 0, 0,
                            getWidth(), getHeight(), this);
                }
            }
        };

        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));
        painel.setPreferredSize(new Dimension(280, 0));

        lblNomeItem = new JLabel(" ");

        lblNomeItem.setFont(new Font("Serif", Font.BOLD, 18));
        lblNomeItem.setForeground(Color.BLACK);

        txtDetalhes = new JTextArea();

        txtDetalhes.setFont(new Font("Serif", Font.ITALIC, 14));
        txtDetalhes.setOpaque(false);
        txtDetalhes.setEditable(false);
        txtDetalhes.setForeground(Color.BLACK);
        txtDetalhes.setLineWrap(true);
        txtDetalhes.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtDetalhes);

        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        painel.add(lblNomeItem, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private void atualizarGrade() {

        grade.removeAll();

        for (int i = 0; i < TOTAL_SLOTS; i++) {

            if (i < evidencias.size()) {

                grade.add(criarSlotCheio(evidencias.get(i)));

            } else {

                grade.add(criarSlotVazio());
            }
        }

        grade.revalidate();
        grade.repaint();
    }

    private JButton criarSlotCheio(final Evidencia e) {

        JButton slot = new JButton(e.getNome());

        slot.setBackground(SLOT_CHEIO);
        slot.setForeground(Color.WHITE);
        slot.setFocusPainted(false);
        slot.setOpaque(true);
        slot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon icone = carregarIcone(e.getCaminhoImagem());

        if (icone != null) {

            slot.setIcon(icone);

            slot.setVerticalTextPosition(SwingConstants.BOTTOM);
            slot.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        boolean ehSelecionada = selecionada != null
                && selecionada.getId() == e.getId();

        slot.setBorder(new LineBorder(
                ehSelecionada ? DESTAQUE : BORDA, 2
        ));

        slot.addActionListener(ev -> selecionar(e));

        return slot;
    }

    private JButton criarSlotVazio() {

        JButton slot = new JButton();

        slot.setEnabled(false);
        slot.setBackground(SLOT_VAZIO);
        slot.setBorder(new LineBorder(BORDA, 1));
        slot.setOpaque(true);

        return slot;
    }

    private ImageIcon carregarIcone(String caminho) {

        if (caminho == null) {
            return null;
        }

        URL url = getClass().getResource(caminho);

        if (url == null) {
            return null;
        }

        Image img = new ImageIcon(url).getImage()
                .getScaledInstance(56, 56, Image.SCALE_SMOOTH);

        return new ImageIcon(img);
    }

    private void selecionar(Evidencia e) {

        selecionada = e;

        mostrarDetalhes(e);
        atualizarGrade();

        if (ouvinte != null) {
            ouvinte.evidenciaSelecionada(e);
        }
    }

    private void mostrarDetalhes(Evidencia e) {

        if (e == null) {

            lblNomeItem.setText(evidencias.isEmpty()
                    ? "Sem evidências"
                    : "Seleciona uma evidência");

            txtDetalhes.setText(evidencias.isEmpty()
                    ? "Ainda não recolheste nenhuma evidência."
                    : "Clica num item para ver os detalhes.");

        } else {

            lblNomeItem.setText(e.getNome());

            txtDetalhes.setText(e.getDescricao());

            txtDetalhes.setCaretPosition(0);
        }
    }

    private void naInterface(Runnable tarefa) {

        if (SwingUtilities.isEventDispatchThread()) {
            tarefa.run();
        } else {
            SwingUtilities.invokeLater(tarefa);
        }
    }

    public void setOuvinte(OuvinteEvidencias ouvinte) {
        this.ouvinte = ouvinte;
    }

    public void adicionar(final Evidencia nova) {

        naInterface(() -> {

            for (Evidencia e : evidencias) {

                if (e.getId() == nova.getId()) {
                    return;
                }
            }

            if (evidencias.size() >= TOTAL_SLOTS) {
                return;
            }

            evidencias.add(nova);

            atualizarGrade();

            if (selecionada == null) {
                mostrarDetalhes(null);
            }
        });
    }

    public void definirEvidencias(final List<Evidencia> lista) {

        naInterface(() -> {

            evidencias.clear();
            selecionada = null;

            for (Evidencia e : lista) {

                if (evidencias.size() < TOTAL_SLOTS) {
                    evidencias.add(e);
                }
            }

            atualizarGrade();
            mostrarDetalhes(null);
        });
    }
    

    public void limpar() {

        definirEvidencias(new ArrayList<>());
    }
}

public class JanelaInventario {
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame janela = new JFrame("Database Detective - Inventário");

            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            janela.setSize(900, 550);

            janela.setLocationRelativeTo(null);

            PainelInventario painel = new PainelInventario();

            janela.add(painel);

            janela.setVisible(true);
        });
    }
}
