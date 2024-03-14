
import java.awt.Graphics2D;
import java.awt.Color;

public class Scoreboard{

    int score = 0;
    String text;
    int xpos;

    public Scoreboard (String txt, int xpos) {
        this.text = txt;
        this.xpos = xpos;
    }

    public void scoreUpdate() {
        score++;
    }

    public void drawScore(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString(text + score, xpos, 30);
    }
}
