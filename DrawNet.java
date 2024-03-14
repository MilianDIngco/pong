import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.*;

public class DrawNet extends JPanel{
    JPanel panel;
    double border = 50;
    int coordY = 50;
    int coordX = 100;
    int yGap;
    int xGap = 100;
    int neuronRadius = 25;
    int[] shape;
    int[][][] coord;
    double midStroke = 2.5;
    ArrayList<double[][]> weights;

    //why is it height and then width fix that dumb.
    DrawNet(int[] shape, int height, int width, ArrayList<double[][]> weights) {
        this.shape = shape;
        this.setSize(new Dimension(width, height));
        coord = new int[shape.length][][];
        generateCoord(shape);
        this.weights = weights;
        this.setBackground(new Color(170, 177, 255));
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        System.out.println(coord[0].length);
        System.out.println(coord[1].length);

        // Draw Lines
        g2.setColor(Color.black);
        for(int i = 0; i < coord.length - 1; i++) {
            for(int n = 0; n < coord[i].length; n++) {
                for(int k = 0; k < coord[i + 1].length; k++) {
                    g2.setStroke(new BasicStroke((int)((weights.get(i)[n][k] + 1) * midStroke)));
                    g2.drawLine(coord[i][n][0], coord[i][n][1], coord[i + 1][k][0], coord[i + 1][k][1]);
                }
            }
        }

        // Draw Neurons
        g2.setColor(new Color(120, 127, 255));
        g2.setStroke(new BasicStroke(1));
        for(int i = 0; i < coord.length; i++) {
            for(int n = 0; n < coord[i].length; n++) {
                g2.fillOval(coord[i][n][0] - neuronRadius / 2, coord[i][n][1] - neuronRadius / 2, neuronRadius, neuronRadius);
            }
        }

        g2.dispose();
    }

    void generateCoord(int[] shape) {
        for(int i = 0; i < shape.length; i++) {
            coord[i] = new int[shape[i]][2];
            yGap = this.getHeight() / (shape[i] + 1);
            coordY = yGap;
            for(int n = 0; n < shape[i]; n++) {
                coord[i][n][0] = coordX;
                coord[i][n][1] = coordY;
                coordY += yGap;
            }
            coordX += xGap;
        }
    }

}  
