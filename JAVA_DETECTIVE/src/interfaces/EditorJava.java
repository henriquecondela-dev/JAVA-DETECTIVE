/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.io.*;
import java.nio.file.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.swing.text.*;
import java.awt.Color;
import java.awt.Image;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Administrator
 */
public class EditorJava extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EditorJava.class.getName());

    /**
     * Creates new form EditorJava
     */
    public EditorJava() {
        initComponents();

        PainelFundo fundoTopo = new PainelFundo("/imagens/ack.jpeg");
        fundoTopo.setLayout(new BorderLayout());

        fundoTopo.add(jLabel1, BorderLayout.CENTER);

        topo.setLayout(new BorderLayout());
        topo.add(fundoTopo, BorderLayout.CENTER);

        topo.setOpaque(false);

        terminal.setEditable(false);
        configurarNumerosLinha();

        painelCodigo.setBorder(BorderFactory.createEmptyBorder());
        painelTerminal.setBorder(BorderFactory.createEmptyBorder());

        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(65, 65, 65), 1));
        jScrollPane2.setBorder(BorderFactory.createLineBorder(new Color(65, 65, 65), 1));

        areaDeCodigo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        terminal.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jScrollPane1.getViewport().setBorder(null);
        jScrollPane2.getViewport().setBorder(null);
    }

    private void escreverTerminal(String mensagem, Color cor) {

        StyledDocument documento = terminal.getStyledDocument();

        SimpleAttributeSet estilo = new SimpleAttributeSet();

        StyleConstants.setForeground(estilo, cor);

        try {
            documento.insertString(
                    documento.getLength(),
                    mensagem + "\n",
                    estilo
            );

            terminal.setCaretPosition(documento.getLength());

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    public class PainelFundo extends JPanel {

        private final Image fundo;

        public PainelFundo(String caminho) {
            java.net.URL url = getClass().getResource(caminho);

            System.out.println("Imagem encontrada: " + url);

            fundo = (url != null) ? new ImageIcon(url).getImage() : null;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (fundo != null) {
                g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void mostrarMensagem(String tipo, String mensagem) {

        switch (tipo) {
            case "INFO":
                escreverTerminal("[INFO] " + mensagem, Color.CYAN);
                break;

            case "SUCCESS":
                escreverTerminal("[SUCCESS] " + mensagem, Color.GREEN);
                break;

            case "ERROR":
                escreverTerminal("[ERROR] " + mensagem, Color.RED);
                break;

            case "WARNING":
                escreverTerminal("[WARNING] " + mensagem, Color.ORANGE);
                break;

            case "OUTPUT":
                escreverTerminal(mensagem, Color.WHITE);
                break;
        }

    }

    private void configurarNumerosLinha() {

        numerosLinha = new javax.swing.JTextArea();

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

        areaDeCodigo.getDocument().addDocumentListener(new DocumentListener() {

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        topo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        areaGeralMenor = new javax.swing.JPanel();
        painelCodigo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaDeCodigo = new javax.swing.JTextArea();
        painelTerminal = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        terminal = new javax.swing.JTextPane();
        btnExecutar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        topo.setBackground(new java.awt.Color(30, 25, 20));
        topo.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(242, 242, 242));
        jLabel1.setText("Editor de Codigo");

        jLabel2.setBackground(new java.awt.Color(25, 35, 35));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(242, 242, 242));
        jLabel2.setText("</>");

        javax.swing.GroupLayout topoLayout = new javax.swing.GroupLayout(topo);
        topo.setLayout(topoLayout);
        topoLayout.setHorizontalGroup(
                topoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topoLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel2)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel1)
                                .addContainerGap(288, Short.MAX_VALUE))
        );
        topoLayout.setVerticalGroup(
                topoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(topoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        areaGeralMenor.setBackground(new java.awt.Color(0, 0, 0));

        areaDeCodigo.setBackground(new java.awt.Color(0, 0, 0));
        areaDeCodigo.setColumns(20);
        areaDeCodigo.setFont(new java.awt.Font("Monospaced", 0, 15)); // NOI18N
        areaDeCodigo.setForeground(new java.awt.Color(242, 242, 242));
        areaDeCodigo.setRows(5);
        areaDeCodigo.setBorder(null);
        jScrollPane1.setViewportView(areaDeCodigo);

        javax.swing.GroupLayout painelCodigoLayout = new javax.swing.GroupLayout(painelCodigo);
        painelCodigo.setLayout(painelCodigoLayout);
        painelCodigoLayout.setHorizontalGroup(
                painelCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
        );
        painelCodigoLayout.setVerticalGroup(
                painelCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );

        terminal.setBackground(new java.awt.Color(15, 15, 15));
        terminal.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        terminal.setForeground(new java.awt.Color(220, 220, 220));
        jScrollPane2.setViewportView(terminal);
        terminal.getAccessibleContext().setAccessibleName("textpaneTerminal");

        javax.swing.GroupLayout painelTerminalLayout = new javax.swing.GroupLayout(painelTerminal);
        painelTerminal.setLayout(painelTerminalLayout);
        painelTerminalLayout.setHorizontalGroup(
                painelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2)
        );
        painelTerminalLayout.setVerticalGroup(
                painelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelTerminalLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnExecutar.setBackground(new java.awt.Color(46, 125, 50));
        btnExecutar.setText("Executar");
        btnExecutar.addActionListener(this::btnExecutarActionPerformed);

        javax.swing.GroupLayout areaGeralMenorLayout = new javax.swing.GroupLayout(areaGeralMenor);
        areaGeralMenor.setLayout(areaGeralMenorLayout);
        areaGeralMenorLayout.setHorizontalGroup(
                areaGeralMenorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(painelTerminal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, areaGeralMenorLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(areaGeralMenorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(painelCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, areaGeralMenorLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnExecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        areaGeralMenorLayout.setVerticalGroup(
                areaGeralMenorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(areaGeralMenorLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(painelCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(painelTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(117, 117, 117))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(topo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(areaGeralMenor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(topo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(areaGeralMenor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecutarActionPerformed
        // TODO add your handling code here:

        terminal.setText("");

        mostrarMensagem("INFO", "A iniciar execução...");

        mostrarMensagem("SUCCESS", "Código executado com sucesso!");

        mostrarMensagem("OUTPUT", "Olá mundo!");
    }//GEN-LAST:event_btnExecutarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new EditorJava().setVisible(true));
    }

    private javax.swing.JTextArea numerosLinha;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaDeCodigo;
    private javax.swing.JPanel areaGeralMenor;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel painelCodigo;
    private javax.swing.JPanel painelTerminal;
    private javax.swing.JTextPane terminal;
    private javax.swing.JPanel topo;
    // End of variables declaration//GEN-END:variables
}
