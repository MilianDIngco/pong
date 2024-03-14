
import java.awt.Dimension;
import java.awt.Graphics2D;

public class Doubleplayer extends Gametype{

    static Dimension size = Main.size;
    Ball ball = new Ball();
    Player player = new Player();
    Enemy enemy = new Enemy();
    Scoreboard playerScore = new Scoreboard("Player Score: ", 60);
    Scoreboard enemyScore = new Scoreboard("Enemy Score: ", 540);

    @Override
    public void update(KeyHandler keyH) {

        if(ball.ballX < 350) {
            ball.ballUpdate((Model)player, playerScore, enemyScore);
        } else {
            ball.ballUpdate((Model)enemy, playerScore, enemyScore);
        }

        enemy.enemyUpdate(keyH);

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
