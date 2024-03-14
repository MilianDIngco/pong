
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    
    static final int FPS = 120;
    Gametype gametype;
    Thread gameThread = new Thread(this);
    KeyHandler keyH = new KeyHandler(this);
    boolean running = true;
    boolean isRunning = false;
    ML brain;

    public GamePanel(Gametype gametype) {
        this.gametype = gametype;
        this.setPreferredSize(Main.size);
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setBrain(ML brain) {
        this.brain = brain;
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null && brain == null) {
            currentTime = System.nanoTime();
        
            delta += (currentTime - lastTime) / drawInterval;
        
            lastTime = currentTime;
        
            if (delta >= 1) {
                update();
            
                repaint();
            
                delta--;
            }
        
        }    

    }

    public void update() {
        if (running) {
        
            gametype.update(keyH);
        
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        gametype.draw(g2);

        g2.dispose();

    }

}
