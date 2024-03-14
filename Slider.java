import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;

public class Slider extends JSlider implements ChangeListener{
    JLabel label;
    String labelText;
    static Dimension sliderSize = new Dimension(300, 50);
    static String text = "Network";

    public Slider(NetSave netSave) {
        this.setMinimum(1);
        this.setMaximum(netSave.numNetworks);
        this.setPaintTicks(true);
        this.setMinorTickSpacing(1);
        this.setBackground(Main.purp);
        this.label = new JLabel(text);
        this.addChangeListener(this);
    }

    public Slider(int[] size, JLabel label, int ticks) {
        this.setMinimum(size[0]);
        this.setMaximum(size[1]);
        this.label = label;
        labelText = label.getText();
        this.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                label.setText(labelText + ": " + ((JSlider)e.getSource()).getValue());
            }
            
        });
        this.setBackground(Main.purp);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        label.setText(": " + Main.netSave.commentList.get(this.getValue() - 1));
        Menu.displayNetPics(this.getValue() - 1);
    }


}
