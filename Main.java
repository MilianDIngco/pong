/*
 * To Do List
 * Replace key listener with key binds
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;

public class Main {

    static Color purp = new Color(170, 177, 255);
    static Dimension size = new Dimension(700, 500);
    static JFrame frame = new JFrame();
    static GamePanel singleplayerGP = new GamePanel(new Singleplayer());
    static GamePanel doubleplayerGP = new GamePanel(new Doubleplayer());
    static GamePanel machineplayerGP;
    static CardLayout cl = new CardLayout();
    static JPanel card = new JPanel();
    static String gmode = "";
    static ArrayList<Slider> sliderList = new ArrayList();
    static boolean pressManual = false;
    static File file = new File("NeuralNetFiles\\nets.txt");
    static NetSave netSave;
    static ArrayList<DrawNet> drawNetArr = new ArrayList<>();
    static int[] shape = {5, 4, 4, 1};
    static MLRun mlRun;
    public static void main(String[] args) throws IOException {
        netSave = new NetSave(file);

        netSave.loadStr();
        System.out.println("Loaded");
        System.out.println(NetSave.numNetworks);
        machineplayerGP = new GamePanel(new MLPlayer());

        for(int i = 0; i < NetSave.numNetworks; i++) {
            ML phold = new ML(NetSave.networkShapes.get(i), NetSave.netList.get(i), NetSave.biasList.get(i));
            drawNetArr.add(new DrawNet(NetSave.networkShapes.get(i), 250, 400, phold.transposeNet()));
        }

        ImageIcon defaultButton = new ImageIcon("images\\buttonUP.png");
        ImageIcon pressedButton = new ImageIcon("images\\buttonDOWN.png");
        
        // Ml load
        Menu loadNetworkMenu = new Menu(defaultButton, pressedButton, "Load Menu", purp, netSave, drawNetArr);
        

        // Define Main Menu attributes
        String[] titlesMenu = {"SINGLEPLAYER", "ok", "VPLAYER", "Load Player"}; 
        ActionListener[] mainMenuActionListeners = {
            // Singleplayer button
            new ActionListener() { 
                public void actionPerformed(ActionEvent e) {

                    cl.show(card, "Singleplayer");
                    
                    if (!singleplayerGP.isRunning) {

                        singleplayerGP.gameThread.start();
                        singleplayerGP.isRunning = true;
                    
                    }
                    
                    singleplayerGP.grabFocus();
                    singleplayerGP.running = true;
                    gmode = "Singleplayer";

                }
            }, 
            // Doubleplayer button
            new ActionListener() { 
                public void actionPerformed(ActionEvent e) {
                    
                    cl.show(card, "Doubleplayer");

                    if (!doubleplayerGP.isRunning) {

                        doubleplayerGP.gameThread.start();
                        doubleplayerGP.isRunning = true;
                    
                    }

                    doubleplayerGP.grabFocus();
                    doubleplayerGP.running = true;
                    gmode = "Doubleplayer";

                }
            },
            // Machine learning button
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    cl.show(card, "Machine Learning Settings");

                    gmode = "Machinelearner";

                }
            },
            // Load network button
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Menu.netPicker.setMinimum(1);
                    Menu.netPicker.setMaximum(NetSave.numNetworks);

                    cl.show(card, "Load Network");
                }

            }
        }; 

        Menu mainMenu = new Menu(defaultButton, pressedButton, titlesMenu, "PONG", purp, mainMenuActionListeners);

        // Define Settings Menu
        String[] titlesSettings = {"Resume", "Return to Main Menu"};
        ActionListener[] settingActionListeners = {
            
            // Resume
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    cl.show(card, Main.gmode);

                    if (gmode.equals("Singleplayer")) {

                        singleplayerGP.grabFocus();
                        singleplayerGP.running = true;

                    } else if (gmode.equals("Doubleplayer")) {

                        doubleplayerGP.grabFocus();
                        doubleplayerGP.running = true;

                    }

                }

            },
            
            // Return to Main Menu
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    cl.show(card, "Main Menu");

                    if (gmode.equals("Singleplayer")) {

                        card.remove(singleplayerGP);
                        Main.singleplayerGP = new GamePanel(new Singleplayer());;
                        card.add(singleplayerGP, "Singleplayer");

                    } else if (gmode.equals("Doubleplayer")) {

                        card.remove(doubleplayerGP);
                        Main.doubleplayerGP = new GamePanel(new Doubleplayer());
                        card.add(doubleplayerGP, "Doubleplayer");

                    }

                    gmode = "";

                }

            }
        };

        Menu settingsMenu = new Menu(defaultButton, pressedButton, titlesSettings, "SETTINGS", purp, settingActionListeners);

        // Define Machine Learning Menu

        String[] sliderTitles = {"NumBabies", "Growth Rate", "Len time"};
        int[][] sliderSizes = {{0, 30}, {0, 100}, {1, 10}};
        int[] tickSpacing = {5, 10, 1};

        // Ml run
        ActionListener mlrunActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // (sliderList.get(0).getValue(), ((double)(sliderList.get(1).getValue())) / 100, sliderList.get(2).getValue(), pressManual)
            }
        };

        Menu mlSettings = new Menu(defaultButton, pressedButton, sliderTitles, sliderSizes, tickSpacing, "M.L Settings", purp, mlrunActionListener);

        // Window settings
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Pong");
        frame.getContentPane().setBackground(new Color(150, 107, 255));
        frame.setBounds(400, 100, (int) size.getWidth(), (int) size.getHeight());
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setMinimumSize(size);
        
        // ADD CARD
        card.setLayout(cl);
        card.add(mainMenu, "Main Menu");
        card.add(settingsMenu, "Settings Menu");
        card.add(singleplayerGP, "Singleplayer");
        card.add(doubleplayerGP, "Doubleplayer");
        card.add(mlSettings, "Machine Learning Settings");
        card.add(loadNetworkMenu, "Load Network");
        card.add(machineplayerGP, "MLGame");

        cl.show(card, "Main Menu");
        
        frame.add(card);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void mlgame() {
        cl.show(card, "MLGame");
        
            machineplayerGP.gameThread.start();
            machineplayerGP.running = true;
    }

}
