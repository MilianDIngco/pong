import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.BorderLayout;

public class MLFrame extends JFrame {

    CardLayout cl = new CardLayout();
    JPanel north, west, center;
    ImageIcon defaultButton = new ImageIcon("images\\buttonUP.png");
    ImageIcon pressedButton = new ImageIcon("images\\buttonDOWN.png");
    
    MLFrame() {
        this.setSize(500, 400);
        this.setBackground(new Color(255, 182, 199));
        this.setTitle("ML Settings");
        this.setLayout(new BorderLayout());

        this.setResizable(false);
        this.setVisible(true);
    }

}
