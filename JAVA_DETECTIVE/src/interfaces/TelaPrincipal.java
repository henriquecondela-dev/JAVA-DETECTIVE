/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaces;

import java.awt.*;

import javax.swing.*;



/**
 *
 * @author andrea-sande
 */
public class TelaPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaPrincipal.class.getName());

    /**
     * Cria new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        txtDescricao.setEditable(false);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        javax.swing.DefaultListModel<String> modelo = new javax.swing.DefaultListModel<>();
        modelo.addElement("Cena do crime");
        modelo.addElement("Registo de Cameras");
        lstEvidencias.setModel(modelo);
       
        PainelFundo fundo = new PainelFundo("/imagens/fundoPrincipal.jpg");
        fundo.setLayout(new BorderLayout(0, 0));

        fundo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

//        topo
        pnlTopo.setLayout(new BorderLayout(20, 0));

        lblPessoa.setFont(new Font("Serif", Font.BOLD, 18));

        lblPessoa.setForeground(Color.WHITE);
        lblCaso.setFont(new Font("Serif", Font.BOLD, 22));
        lblCaso.setForeground(Color.WHITE);
        lblCaso.setHorizontalAlignment(SwingConstants.CENTER);

        barraProgresso.setPreferredSize(new Dimension(250, 25));
        btnMenu.setPreferredSize(new Dimension(130, 40));

   

        pnlTopo.add(lblPessoa, BorderLayout.WEST);
        pnlTopo.add(lblCaso, BorderLayout.CENTER);

        
        
        pnlTopo.setLayout(new BorderLayout(20, 0));

        lblPessoa.setFont(new Font("Serif", Font.BOLD, 18));
        lblPessoa.setForeground(Color.WHITE);

        lblCaso.setFont(new Font("Serif", Font.BOLD, 22));
        lblCaso.setForeground(new Color(216, 189, 145));
        lblCaso.setHorizontalAlignment(SwingConstants.CENTER);

   
        barraProgresso.setMinimum(0);
        barraProgresso.setMaximum(100);
        barraProgresso.setValue(60);
        barraProgresso.setStringPainted(true);
        barraProgresso.setString("60%");
        barraProgresso.setForeground(new Color(80, 150, 65));
        barraProgresso.setBackground(new Color(45, 35, 25));
        barraProgresso.setBorder(
            BorderFactory.createLineBorder(new Color(140, 106, 58))
        );
        barraProgresso.setPreferredSize(new Dimension(230, 28));

        btnMenu.setText("Menu");
        btnMenu.setFont(new Font("Serif", Font.BOLD, 18));
        btnMenu.setForeground(new Color(240, 220, 180));
        btnMenu.setBackground(new Color(55, 37, 23));
        btnMenu.setFocusPainted(false);
        btnMenu.setBorder(
            BorderFactory.createLineBorder(new Color(140, 106, 58))
        );
        btnMenu.setPreferredSize(new Dimension(130, 42));
        JPopupMenu menu = new JPopupMenu();
        JMenuItem sair = new JMenuItem("Sair do jogo");
        sair.addActionListener(e -> {System.exit(0);});
        menu.add(sair);
        btnMenu.addActionListener(e ->menu.show(btnMenu, 0, btnMenu.getHeight()) );
        
        JPanel topoDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        topoDireita.setOpaque(false);
        topoDireita.add(barraProgresso);
        topoDireita.add(btnMenu);
        pnlTopo.add(lblPessoa, BorderLayout.WEST);
        pnlTopo.add(lblCaso, BorderLayout.CENTER);
        pnlTopo.add(topoDireita, BorderLayout.EAST);

         pnlMenu.setOpaque(false);
         pnlMenu.removeAll();
         pnlMenu.setLayout(new BoxLayout(pnlMenu, BoxLayout.Y_AXIS));
         pnlMenu.setMinimumSize(new Dimension(380, 0));
         pnlMenu.setMaximumSize(new Dimension(380, Integer.MAX_VALUE));
         pnlMenu.setPreferredSize(new Dimension(380, 0));
         Dimension tam = new Dimension(380, 104);   // l a
         ImageIcon fundoBtn = new ImageIcon(new ImageIcon(getClass().getResource("/imagens/fundoBotao.jpg")).getImage().getScaledInstance(tam.width, tam.height, Image.SCALE_SMOOTH));
         JButton[] botoes = {btnCenario, btnEvidencia, btnBD, btnEditorCodigo, btnObjectivos};
         String[] textos = {"Cenário", "Evidências", "Base de Dados", "Editor de Código", "Objectivos"};

        pnlMenu.add(Box.createVerticalGlue());
         for (int i = 0; i < botoes.length; i++) {
             JButton b = botoes[i];
             b.setText(textos[i]);
             b.setIcon(fundoBtn);
             b.setHorizontalTextPosition(SwingConstants.CENTER); 
             b.setVerticalTextPosition(SwingConstants.CENTER);
             b.setFont(new Font("Serif", Font.BOLD, 20));
             b.setForeground(new Color(36, 23, 14));
             b.setBorderPainted(false);
             b.setContentAreaFilled(false);
             b.setFocusPainted(false);
             b.setMargin(new Insets(0, 0, 0, 0));
             b.setPreferredSize(tam);
             b.setMinimumSize(tam);
             b.setMaximumSize(tam);
             b.setAlignmentX(Component.CENTER_ALIGNMENT);
             pnlMenu.add(b);
             pnlMenu.add(Box.createVerticalStrut(12));
         }
         pnlMenu.add(Box.createVerticalGlue());

        txtDescricao.setOpaque(false);  
        pnlCentro.setOpaque(false);
        pnlCentro.setLayout(new BorderLayout(0, 0));                     
        lblCenario.setHorizontalAlignment(SwingConstants.CENTER);
        jScrollPane2.setPreferredSize(new Dimension(0, 110));
        pnlCentro.setOpaque(false);
pnlCentro.setLayout(new BorderLayout(0, 0));
lblCenario.setHorizontalAlignment(SwingConstants.CENTER);
jScrollPane2.setPreferredSize(new Dimension(0, 110));

// Usa o jLabel1 que já existe como título
jLabel1.setText("Descrição:");
jLabel1.setFont(new Font("Serif", Font.BOLD, 18));
jLabel1.setForeground(Color.WHITE);

// Painel que junta Label + Scroll
JPanel painelDescricao = new JPanel(new BorderLayout(0, 5));
painelDescricao.setOpaque(false);
painelDescricao.add(jLabel1, BorderLayout.NORTH);
painelDescricao.add(jScrollPane2, BorderLayout.CENTER);

Color corTopo = new Color(42, 29, 18, 200);
Color corTexto = new Color(240, 220, 180);

// Painel de baixo com a mesma cor do topo
painelDescricao.setOpaque(false);
painelDescricao.setBackground(corTopo);

// Scroll também com a mesma cor
jScrollPane2.setOpaque(false);
jScrollPane2.setBackground(corTopo);
jScrollPane2.getViewport().setBackground(corTopo);
jScrollPane2.setBorder(BorderFactory.createEmptyBorder());

// TextArea com a mesma cor do topo e letra clara
txtDescricao.setBackground(corTopo);
txtDescricao.setForeground(corTexto);
txtDescricao.setCaretColor(corTexto);
txtDescricao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

pnlCentro.add(lblCenario, BorderLayout.CENTER);
pnlCentro.add(painelDescricao, BorderLayout.SOUTH);

        
        Color dourado    = new Color(216, 189, 145);
        Color textoClaro = new Color(240, 220, 180);
        Color bordaLeve  = new Color(140, 106, 58, 90);   

        pnlDireita.setOpaque(false);
        pnlDireita.setBackground(new Color(20, 14, 9));
        pnlDireita.removeAll();
        pnlDireita.setLayout(new BorderLayout(0, 15));
        pnlDireita.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlDireita.setPreferredSize(new Dimension(420, 0));

        class CaixaImagem extends JPanel {
            private final Image img;
            CaixaImagem(String caminho) {
                super(new BorderLayout(0, 8));
                java.net.URL url = getClass().getResource(caminho);
                img = (url != null) ? new ImageIcon(url).getImage() : null;
                setOpaque(false);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bordaLeve, 1),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)));
            }
            @Override protected void paintComponent(java.awt.Graphics g) {
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 90));       
                } else {
                    g.setColor(new Color(42, 29, 18, 200));
                }
                if (img != null) g.fillRect(0, 0, getWidth(), getHeight());
                else g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        }

        
        lblobjectivoTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        lblobjectivoTitulo.setForeground(dourado);
        lblEvidenciasTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        lblEvidenciasTitulo.setForeground(dourado);
        lblObjectivo.setFont(new Font("Serif", Font.PLAIN, 17));
        lblObjectivo.setForeground(textoClaro);
        lblObjectivo.setText("<html><body style='width:320px'>Descobre quem esteve no local na noite do desaparecimento.</body></html>");

        lstEvidencias.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        lstEvidencias.setVisibleRowCount(-1);
        lstEvidencias.setFixedCellWidth(100);
        lstEvidencias.setFixedCellHeight(120);
        lstEvidencias.setOpaque(false);
        lstEvidencias.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        jScrollPane3.setOpaque(false);
        jScrollPane3.getViewport().setOpaque(false);
        jScrollPane3.setBorder(BorderFactory.createEmptyBorder());
        
        
        java.net.URL urlIcone = TelaPrincipal.class.getResource("/imagens/iconeEvidencia2.jpg");
javax.swing.Icon iconeUnico;
if (urlIcone != null) {
    iconeUnico = new ImageIcon(new ImageIcon(urlIcone).getImage()
            .getScaledInstance(64, 64, Image.SCALE_SMOOTH));
} else {
    iconeUnico = new javax.swing.Icon() {
        public void paintIcon(Component c, java.awt.Graphics g, int x, int y) {
            g.setColor(new Color(0, 0, 0, 80));
            g.fillRoundRect(x, y, 64, 64, 8, 8);
            g.setColor(bordaLeve);
            g.drawRoundRect(x, y, 63, 63, 8, 8);
        }
        public int getIconWidth()  { return 64; }
        public int getIconHeight() { return 64; }
    };
}

        lstEvidencias.setCellRenderer(new javax.swing.ListCellRenderer<String>() {
            @Override
    public Component getListCellRendererComponent(javax.swing.JList<? extends String> list,
            String value, int index, boolean selecionado, boolean foco) {
        javax.swing.JLabel l = new javax.swing.JLabel(
                "<html><div style='text-align:center;width:84px'>" + value + "</div></html>",
                iconeUnico, SwingConstants.CENTER); // <-- AQUI, sempre o mesmo
        l.setVerticalTextPosition(SwingConstants.BOTTOM);
        l.setHorizontalTextPosition(SwingConstants.CENTER);
        l.setIconTextGap(6);
        l.setFont(new Font("Serif", Font.PLAIN, 12));
        l.setForeground(textoClaro);
        l.setToolTipText(value);
        l.setOpaque(selecionado);
        l.setBackground(new Color(90, 62, 34, 160));
        return l;
    }
        });

      CaixaImagem objetivo = new CaixaImagem("caminho falso");
      objetivo.add(lblobjectivoTitulo, BorderLayout.NORTH);
    objetivo.add(lblObjectivo, BorderLayout.CENTER);
    objetivo.setPreferredSize(new Dimension(390, 260)); 
    objetivo.setMinimumSize(new Dimension(300, 230));    
    CaixaImagem evidencias = new CaixaImagem("/imagens/fundoCaixa.jpg");
    evidencias.add(lblEvidenciasTitulo, BorderLayout.NORTH);
    evidencias.add(jScrollPane3, BorderLayout.CENTER);


    pnlDireita.add(objetivo, BorderLayout.NORTH);
    pnlDireita.add(evidencias, BorderLayout.CENTER);
       
                fundo.add(pnlTopo, BorderLayout.NORTH);
        fundo.add(pnlMenu, BorderLayout.WEST);
        fundo.add(pnlCentro, BorderLayout.CENTER);
        fundo.add(pnlDireita, BorderLayout.EAST);
        setContentPane(fundo);

       
    }
    public TelaPrincipal(String nome, String idade){
        this();
        lblPessoa.setText(nome +"| Idade: "+idade);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        pnlTopo = new javax.swing.JPanel();
        lblPessoa = new javax.swing.JLabel();
        lblCaso = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();
        btnMenu = new javax.swing.JButton();
        pnlMenu = new javax.swing.JPanel();
        btnCenario = new javax.swing.JButton();
        btnEvidencia = new javax.swing.JButton();
        btnBD = new javax.swing.JButton();
        btnEditorCodigo = new javax.swing.JButton();
        btnObjectivos = new javax.swing.JButton();
        pnlDireita = new javax.swing.JPanel();
        lblobjectivoTitulo = new javax.swing.JLabel();
        lblObjectivo = new javax.swing.JLabel();
        lblEvidenciasTitulo = new javax.swing.JLabel();
        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstEvidencias = new javax.swing.JList<>();
        pnlCentro = new javax.swing.JPanel();
        lblCenario = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlTopo.setBackground(new java.awt.Color(20, 14, 9));
        pnlTopo.setPreferredSize(new java.awt.Dimension(100, 70));
        pnlTopo.setLayout(new java.awt.GridLayout(1, 4));

        lblPessoa.setText("Nome|Idade:");
        pnlTopo.add(lblPessoa);

        lblCaso.setText("O CASO NEXUS");
        pnlTopo.add(lblCaso);
        pnlTopo.add(barraProgresso);

        btnMenu.setText("jButton6");
        pnlTopo.add(btnMenu);

        getContentPane().add(pnlTopo, java.awt.BorderLayout.PAGE_START);

        pnlMenu.setBackground(new java.awt.Color(20, 14, 9));
        pnlMenu.setToolTipText("");
        pnlMenu.setMaximumSize(new java.awt.Dimension(260, 150));
        pnlMenu.setMinimumSize(new java.awt.Dimension(260, 150));
        pnlMenu.setName(""); // NOI18N
        pnlMenu.setPreferredSize(new java.awt.Dimension(260, 150));
        pnlMenu.setLayout(new javax.swing.BoxLayout(pnlMenu, javax.swing.BoxLayout.LINE_AXIS));

        btnCenario.setBackground(new java.awt.Color(216, 189, 145));
        btnCenario.setFont(new java.awt.Font("Serif", 0, 16)); // NOI18N
        btnCenario.setForeground(new java.awt.Color(36, 23, 14));
        btnCenario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fundoBotao.jpg"))); // NOI18N
        btnCenario.setText("Cenário");
        btnCenario.setMaximumSize(new java.awt.Dimension(500, 150));
        btnCenario.setMinimumSize(new java.awt.Dimension(500, 150));
        btnCenario.setPreferredSize(new java.awt.Dimension(500, 150));
        pnlMenu.add(btnCenario);

        btnEvidencia.setBackground(new java.awt.Color(216, 189, 145));
        btnEvidencia.setFont(new java.awt.Font("Serif", 0, 16)); // NOI18N
        btnEvidencia.setForeground(new java.awt.Color(36, 23, 14));
        btnEvidencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fundoBotao.jpg"))); // NOI18N
        btnEvidencia.setText("Evidências");
        btnEvidencia.setMaximumSize(new java.awt.Dimension(300, 150));
        btnEvidencia.setMinimumSize(new java.awt.Dimension(300, 150));
        btnEvidencia.setPreferredSize(new java.awt.Dimension(300, 150));
        pnlMenu.add(btnEvidencia);

        btnBD.setBackground(new java.awt.Color(216, 189, 145));
        btnBD.setFont(new java.awt.Font("Serif", 0, 16)); // NOI18N
        btnBD.setForeground(new java.awt.Color(36, 23, 14));
        btnBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fundoBotao.jpg"))); // NOI18N
        btnBD.setText("Base de dados");
        btnBD.setMaximumSize(new java.awt.Dimension(280, 80));
        btnBD.setMinimumSize(new java.awt.Dimension(230, 60));
        btnBD.setPreferredSize(new java.awt.Dimension(230, 60));
        btnBD.addActionListener(this::btnBDActionPerformed);
        pnlMenu.add(btnBD);

        btnEditorCodigo.setBackground(new java.awt.Color(216, 189, 145));
        btnEditorCodigo.setFont(new java.awt.Font("Serif", 0, 16)); // NOI18N
        btnEditorCodigo.setForeground(new java.awt.Color(36, 23, 14));
        btnEditorCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fundoBotao.jpg"))); // NOI18N
        btnEditorCodigo.setText("</>    Editor de Código");
        btnEditorCodigo.setMaximumSize(new java.awt.Dimension(280, 80));
        btnEditorCodigo.setMinimumSize(new java.awt.Dimension(230, 60));
        btnEditorCodigo.setPreferredSize(new java.awt.Dimension(230, 60));
        btnEditorCodigo.addActionListener(this::btnEditorCodigoActionPerformed);
        pnlMenu.add(btnEditorCodigo);

        btnObjectivos.setBackground(new java.awt.Color(216, 189, 145));
        btnObjectivos.setFont(new java.awt.Font("Serif", 0, 16)); // NOI18N
        btnObjectivos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fundoBotao.jpg"))); // NOI18N
        btnObjectivos.setText("Objectivos");
        btnObjectivos.setMaximumSize(new java.awt.Dimension(280, 80));
        btnObjectivos.setMinimumSize(new java.awt.Dimension(230, 60));
        btnObjectivos.setPreferredSize(new java.awt.Dimension(230, 60));
        pnlMenu.add(btnObjectivos);

        getContentPane().add(pnlMenu, java.awt.BorderLayout.LINE_START);

        pnlDireita.setPreferredSize(new java.awt.Dimension(300, 100));
        pnlDireita.setLayout(new java.awt.GridLayout(4, 1));

        lblobjectivoTitulo.setBackground(new java.awt.Color(36, 23, 14));
        lblobjectivoTitulo.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        lblobjectivoTitulo.setForeground(new java.awt.Color(216, 189, 145));
        lblobjectivoTitulo.setText("Objectivo Actual");
        pnlDireita.add(lblobjectivoTitulo);

        lblObjectivo.setText("jLabel2");
        pnlDireita.add(lblObjectivo);

        lblEvidenciasTitulo.setBackground(new java.awt.Color(36, 23, 14));
        lblEvidenciasTitulo.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        lblEvidenciasTitulo.setForeground(new java.awt.Color(216, 189, 145));
        lblEvidenciasTitulo.setText("Evidências Coletadas");
        lblEvidenciasTitulo.setPreferredSize(new java.awt.Dimension(80, 26));
        lblEvidenciasTitulo.setVerifyInputWhenFocusTarget(false);
        pnlDireita.add(lblEvidenciasTitulo);

        lstEvidencias.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstEvidencias.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        lstEvidencias.setVisibleRowCount(1);
        jScrollPane3.setViewportView(lstEvidencias);

        scrollPane1.add(jScrollPane3);

        pnlDireita.add(scrollPane1);

        getContentPane().add(pnlDireita, java.awt.BorderLayout.LINE_END);

        pnlCentro.setLayout(new java.awt.BorderLayout());
        pnlCentro.add(lblCenario, java.awt.BorderLayout.CENTER);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerifyInputWhenFocusTarget(false);

        txtDescricao.setColumns(20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setRows(5);
        jScrollPane2.setViewportView(txtDescricao);

        pnlCentro.add(jScrollPane2, java.awt.BorderLayout.PAGE_END);
        pnlCentro.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(pnlCentro, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditorCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditorCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditorCodigoActionPerformed

    private void btnBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnBD;
    private javax.swing.JButton btnCenario;
    private javax.swing.JButton btnEditorCodigo;
    private javax.swing.JButton btnEvidencia;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnObjectivos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblCaso;
    private javax.swing.JLabel lblCenario;
    private javax.swing.JLabel lblEvidenciasTitulo;
    private javax.swing.JLabel lblObjectivo;
    private javax.swing.JLabel lblPessoa;
    private javax.swing.JLabel lblobjectivoTitulo;
    private javax.swing.JList<String> lstEvidencias;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlDireita;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlTopo;
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.JTextArea txtDescricao;
    // End of variables declaration//GEN-END:variables
}
