
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

public class Player extends Model {

    static Dimension size = Main.size;

    public Player() {
        super.x = super.dts;
        super.y = (int) size.getHeight() / 2 - (super.height / 2);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(super.x, super.y, super.width, super.height);
        g2.dispose();
    }

    // SINGLEPLAYER AND TWO PLAYER

    @Override
    public void playerUpdate(KeyHandler keyH) {
        if(keyH.up && super.y >= 0) {
            super.y -= super.speed;
        } else if(keyH.down && super.y <= size.getHeight() - super.height) {
            super.y += super.speed; 
        }
    }

    // MACHINE LEARNING
    @Override
    public void playerUpdate(Ball ball) {
        if(ball.ballY + ball.ballR > (super.y + super.height - 3)) {
            super.y += super.speed;
        }

        if(ball.ballY + ball.ballR < (super.y + 3)) {
            super.y -= super.speed;
        }
    }

}
