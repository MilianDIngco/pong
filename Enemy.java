
import java.awt.Graphics2D;
import java.awt.Dimension;

public class Enemy extends Model {

    static Dimension size = Main.size;    

    public Enemy() {
        super.x = (int) size.getWidth() - super.dts;
        super.y = (int) size.getHeight() / 2 - (super.height / 2);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillRect(super.x, super.y, super.width, super.height);
    }

    // SINGLEPLAYER
    @Override
    public void enemyUpdate(Ball ball) {
        if(ball.ballY + ball.ballR > (super.y + super.height - 3)) {
            super.y += super.speed;
        }

        if(ball.ballY + ball.ballR < (super.y + 3)) {
            super.y -= super.speed;
        }
    }

    // TWO PLAYERS
    public void enemyUpdate(KeyHandler keyH) {
        if(keyH.up2 && super.y >= 0) {

            super.y -= super.speed;

        } else if(keyH.down2 && super.y <= size.getHeight() - super.height) {

            super.y += super.speed;

        }
    }

    // MACHINE LEARNING?

    public void pongAI(double dist) {
        dist = dist * Main.size.getHeight();
        if(super.y < dist) {
            super.y += super.speed;
        }
        if(super.y > dist) {
            super.y -= super.speed;
        }
    }
}
