
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.lang.Integer;

public class Ball extends Model {

    int ballX, ballY, ballXVel, ballYVel;
    static int ballR = 10;
    static Dimension size = Main.size;
    static int nextPos;

    public Ball() {
        ballX = (int) size.getWidth() / 2;
        ballY = (int) size.getHeight() / 2;
        ballXVel = (int) (-super.speed * 1.5);
        ballYVel = 0;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillOval(ballX, ballY, ballR, ballR);
    }

    public void ballUpdate(Model model, Scoreboard playerScore, Scoreboard enemyScore) {

        // Move ball
        ballX += ballXVel;
        ballY -= ballYVel;

        // Paddle Collision
        if (Math.abs((model.x + model.width / 2) - (ballX + ballR / 2)) <= model.width / 2 + ballR / 2 &&
                Math.abs((model.y + model.height / 2) - (ballY + ballR / 2)) < model.height / 2 + ballR / 2) {
            ballXVel *= -1;
            ballYVel = (int) (super.speed * 1.5 * ((double) ((model.y + model.height / 2) - (ballY + ballR / 2)))
                    / (model.height / 2));
        }

        // Bottom collision
        if (Math.abs(size.getHeight() - (ballY + ballR / 2)) <= ballR / 2) {
            ballYVel *= -1;
            ballY = (int) size.getHeight() - ballR - 1;
        }

        // Top collision
        if (Math.abs((ballY + ballR / 2)) <= ballR / 2) {
            ballYVel *= -1;
            ballY = 1;
        }

        // Left collision, enemy score update
        if (ballX <= 0) {
            ballX = (int) size.getWidth() / 2;
            ballY = (int) size.getHeight() / 2;
            ballXVel = (int) (super.speed * 1.5 * Integer.signum(ballXVel));
            ballYVel = 0;
            super.rounds++;
            enemyScore.scoreUpdate();
        }

        // Right collision, player score update
        if (ballX >= size.getWidth()) {
            ballX = (int) size.getWidth() / 2;
            ballY = (int) size.getHeight() / 2;
            ballXVel = (int) (super.speed * 1.5 * Integer.signum(ballXVel));
            ballYVel = 0;
            super.rounds++;
            playerScore.scoreUpdate();
        }

        // If inside paddle, reset
        if (Math.abs((model.x + model.width / 2) - (ballX + ballR / 2)) <= model.width / 2 &&
                Math.abs((model.y + model.height / 2) - (ballY + ballR / 2)) <= model.height / 2) {
            ballX = (int) size.getWidth() / 2;
            ballY = (int) size.getHeight() / 2;
        }

    }

    public int ballUpdate(Model model, Scoreboard playerScore, Scoreboard enemyScore, ML brain) {

        // Move ball
        ballX += ballXVel;
        ballY -= ballYVel;

        // Paddle Collision
        if (Math.abs((model.x + model.width / 2) - (ballX + ballR / 2)) <= model.width / 2 + ballR / 2 &&
                Math.abs((model.y + model.height / 2) - (ballY + ballR / 2)) < model.height / 2 + ballR / 2) {
            ballXVel *= -1;
            ballYVel = (int) (super.speed * 1.5 * ((double) ((model.y + model.height / 2) - (ballY + ballR / 2)))
                    / (model.height / 2));
            double[] input = { ballX, ballY, ballXVel, ballYVel, model.y };
            nextPos = (int) (brain.fProp(input)[0] * size.getHeight());
        }

        // Bottom collision
        if (Math.abs(size.getHeight() - (ballY + ballR / 2)) <= ballR / 2) {
            ballYVel *= -1;
            ballY = (int) size.getHeight() - ballR - 1;
        }

        // Top collision
        if (Math.abs((ballY + ballR / 2)) <= ballR / 2) {
            ballYVel *= -1;
            ballY = 1;
        }

        // Left collision, enemy score update
        if (ballX <= 0) {
            ballX = (int) size.getWidth() / 2;
            ballY = (int) size.getHeight() / 2;
            ballXVel = super.speed * Integer.signum(ballXVel);
            ballYVel = 0;
            super.rounds++;
            enemyScore.scoreUpdate();
        }

        // Right collision, player score update
        if (ballX >= size.getWidth()) {
            ballX = (int) size.getWidth() / 2;
            ballY = (int) size.getHeight() / 2;
            ballXVel = (int) (super.speed * 1.5 * Integer.signum(ballXVel));
            ballYVel = 0;
            super.rounds++;
            playerScore.scoreUpdate();
        }

        // If inside paddle, reset
        if (Math.abs((model.x + model.width / 2) - (ballX + ballR / 2)) <= model.width / 2 &&
                Math.abs((model.y + model.height / 2) - (ballY + ballR / 2)) <= model.height / 2) {
            ballX = (int) size.getWidth() / 2;
            ballY = (int) size.getHeight() / 2;
        }

        return nextPos;

    }

}
