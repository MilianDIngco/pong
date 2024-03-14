import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameInstance extends JPanel implements Runnable {
    private ML brain;
    private int fitness;
    private Player player = new Player();
    private Enemy enemy = new Enemy();
    private Ball ball = new Ball();
    private Scoreboard playerScore = new Scoreboard("Player Score: ", 60);
    private Scoreboard enemyScore = new Scoreboard("Enemy Score: ", 540);
    private Thread gameThread;
    private int FPS = 120;

    GameInstance(ML brain) {
        this.brain = brain;
        this.fitness = 0;
        this.gameThread = new Thread(this);
    }

    GameInstance(ML brain, int fitness) {
        this(brain);
        this.fitness = fitness;
    }

    GameInstance(ML brain, int fitness, Thread gameThread) {
        this(brain, fitness);
        this.gameThread = gameThread;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                
                if(this.isVisible()) {
                    repaint();
                }

                delta--;
            }

        }        
    }

    public void update() {
        System.out.println("hello");
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("print");
    }

    
}
