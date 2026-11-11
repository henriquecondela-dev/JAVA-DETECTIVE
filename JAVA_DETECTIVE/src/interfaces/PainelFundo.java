package interfaces;

import java.awt.*;
import javax.swing.*;

public class PainelFundo extends JPanel {
    private final Image fundo;

    public PainelFundo(String caminho) {
    java.net.URL url = getClass().getResource(caminho);
    System.out.println("Caminho: " + caminho + " -> " + url);
    fundo = (url != null) ? new ImageIcon(url).getImage() : null;
}

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (fundo != null) {
        g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
    }
}
   
}
/*
public PainelFundo(String caminho) extends JPanel {
    java.net.URL url = getClass().getResource(caminho);
    System.out.println("Caminho: " + caminho + " -> " + url);
    fundo = (url != null) ? new ImageIcon(url).getImage() : null;
}

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (fundo != null) {
        g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
    }
}
*/