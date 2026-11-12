/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author andrea-sande
 */
package interfaces;

import java.awt.*;
import javax.swing.*;

public class PainelFundo1 extends JPanel {

    private final Image fundo;

    public PainelFundo1(String caminho) {

        java.net.URL url = getClass().getResource(caminho);

        System.out.println("Imagem: " + url);

        fundo = (url != null)
                ? new ImageIcon(url).getImage()
                : null;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (fundo != null) {

            int largura = fundo.getWidth(this);
            int altura = fundo.getHeight(this);

            // Aproximar o pergaminho, cortando margens
            int x = (int) (largura * 0.08);
            int y = (int) (altura * 0.08);

            int larguraCorte = (int) (largura * 0.84);
            int alturaCorte = (int) (altura * 0.84);

            g.drawImage(
                fundo,
                0, 0, getWidth(), getHeight(),
                x, y,
                x + larguraCorte,
                y + alturaCorte,
                this
            );
        }
    }
}
