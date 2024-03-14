import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import java.awt.CardLayout;

public class KeyHandler implements KeyListener {

    public boolean up, down, up2, down2;
    SettingsAction settingsAction = new SettingsAction();

    KeyHandler(GamePanel panel) {
        panel.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "Settings Action");
        panel.getActionMap().put("Settings Action", settingsAction);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            up = true;
        }
        if (code == KeyEvent.VK_S) {
            down = true;
        }
        if (code == KeyEvent.VK_UP) {
            up2 = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            down2 = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            up = false;
        }
        if (code == KeyEvent.VK_S) {
            down = false;
        }
        if (code == KeyEvent.VK_UP) {
            up2 = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            down2 = false;
        }
    }

    public class SettingsAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {

            Main.cl.show(Main.card, "Settings Menu");

            if (Main.gmode.equals("Singleplayer")) {

                Main.singleplayerGP.running = false;

            } else if (Main.gmode.equals("Doubleplayer")) {

                Main.doubleplayerGP.running = false;

            }

        }

    }

}
