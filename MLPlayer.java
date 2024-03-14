
import java.awt.Dimension;
import java.awt.Graphics2D;

public class MLPlayer extends Gametype{
    static Dimension size = Main.size;
    Ball ball = new Ball();
    Player player = new Player();
    Enemy enemy = new Enemy();
    Scoreboard playerScore = new Scoreboard("Player Score: ", 60);
    Scoreboard enemyScore = new Scoreboard("Enemy Score: ", 540);
    ML brain;
    int nextPos = 0;

    public MLPlayer () {
        ML brain = new ML(NetSave.networkShapes.get(0), NetSave.netList.get(0), NetSave.biasList.get(0));
        this.brain = brain;
    }

    public MLPlayer (ML brain) {
        this.brain = brain;
    }

    public void setBrain(ML brain) {
        this.brain = brain;
    }

    @Override
    public void update(KeyHandler keyH) {
        if(ball.ballX < 100) {
            nextPos = ball.ballUpdate((Model)player, playerScore, enemyScore, brain);
        } else {
            ball.ballUpdate((Model)enemy, playerScore, enemyScore);
        }
        System.out.println(nextPos);
        enemy.pongAI(nextPos);

        player.playerUpdate(keyH);
        
    }

    @Override
    public void draw(Graphics2D g2) {

        playerScore.drawScore(g2);

        enemyScore.drawScore(g2);

        enemy.draw(g2);

        ball.draw(g2);

        player.draw(g2);

    }
}
