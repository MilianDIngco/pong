import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import javax.swing.*;

import java.awt.GridLayout;
import java.awt.CardLayout;

public class Menu extends JPanel implements ActionListener{

    static int chosenNetwork = 0;
    ArrayList<JButton> buttonList = new ArrayList<>();
    ArrayList<JLabel> labelList = new ArrayList<>();
    ArrayList<JPanel> sliderPanel = new ArrayList<>();
    
    JPanel buttonHolder = new JPanel();
    JPanel titleHolder = new JPanel();
    
    JLabel title = new JLabel();
    Font text = new Font("Arial", Font.PLAIN, 20);
    Font header = new Font("Arial", Font.BOLD, 40);
    Dimension buttonSize = new Dimension(233, 30);
    JPanel center = new JPanel(null);

    static JPanel netDisplay = new JPanel();
    static CardLayout cl = new CardLayout();

    ArrayList<DrawNet> netPics;

    static Slider netPicker;

    //Basic button only menu
    public Menu (ImageIcon defaultButtonLook, ImageIcon pressedButtonLook, String[] buttonTitles, String menuTitle, Color bgColor, ActionListener[] actions) {
        this.setLayout(new BorderLayout());
        buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.Y_AXIS)); 

        //set title of menu
        title.setText(menuTitle);
        title.setFont(header);
        titleHolder.add(title, CENTER_ALIGNMENT);
        titleHolder.setBackground(bgColor);
        titleHolder.setPreferredSize(new Dimension(100, 80));
        this.add(titleHolder, BorderLayout.NORTH);

        //Create list of buttons
        buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.Y_AXIS));
        buttonHolder.setBackground(bgColor);
        for(int i = 0; i < buttonTitles.length; i++) {
            buttonList.add(new JButton());
            buttonList.get(i).setIcon(defaultButtonLook);
            buttonList.get(i).setPressedIcon(pressedButtonLook);
            buttonList.get(i).setBorderPainted(false);
            buttonList.get(i).setAlignmentX(Component.CENTER_ALIGNMENT); //might have to do Component.CENTER_ALIGNMENT and import java.awt.Component
            buttonList.get(i).setMaximumSize(buttonSize);
            buttonList.get(i).setLayout(new GridLayout(0, 1));
            //add labels to buttons
            buttonList.get(i).setFont(text);
            buttonList.get(i).setText(buttonTitles[i]);
            buttonList.get(i).setHorizontalTextPosition(SwingConstants.CENTER);

            //add action listeners to buttons
            buttonList.get(i).addActionListener(actions[i]);

            //Add to buttonHolder panel, to menu
            buttonHolder.add(buttonList.get(i));
            buttonHolder.add(Box.createRigidArea(new Dimension(0, 30)));
        }
        this.add(buttonHolder, BorderLayout.CENTER);
    
    }

    //Slider menu
    public Menu (ImageIcon defaultButton, ImageIcon pressedButton, String[] sliderTitles, int[][] sliderSizes, int[] tickSpacing, String menuTitle, Color bgColor, ActionListener mlrunActionListener) {
        this.setBackground(bgColor);
        this.setLayout(new BorderLayout());
        
        // Add menu title
        JLabel title = new JLabel(menuTitle);
        title.setFont(header);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(title, BorderLayout.NORTH); 

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(bgColor);

        JPanel east = new JPanel();
        east.setPreferredSize(new Dimension(50, 0));
        east.setBackground(bgColor);
        //Make list of sliders
        center.add(Box.createRigidArea(new Dimension(0, 20)));
        for(int i = 0; i < sliderTitles.length; i++) {
            Main.sliderList.add(new Slider(sliderSizes[i], new JLabel(sliderTitles[i]), tickSpacing[i]));
            center.add(Main.sliderList.get(i).label);
            center.add(Box.createRigidArea(new Dimension(0, 5)));
            center.add(Main.sliderList.get(i));
            center.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        JPanel west = new JPanel();
        west.setPreferredSize(new Dimension(50, 0));
        west.setBackground(bgColor);

        // Manual button
        JButton manual = new JButton("Manual?");
        manual.setPreferredSize(new Dimension(233, 30));
        manual.setIcon(defaultButton);
        manual.setPressedIcon(pressedButton);
        manual.setBorderPainted(false);
        manual.setMaximumSize(buttonSize);
        manual.setAlignmentX(Component.CENTER_ALIGNMENT);
        manual.setLayout(new GridLayout(0, 1));
        manual.setHorizontalTextPosition(SwingConstants.CENTER);
        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Main.pressManual) {
                    manual.setIcon(pressedButton);
                    Main.pressManual = true;
                } else {
                    manual.setIcon(defaultButton);
                    Main.pressManual = false;
                }
            }
        });

        center.add(manual);
        center.add(Box.createRigidArea(new Dimension(0, 10)));

        // Run button
        JButton run = new JButton("Run.");
        run.setPreferredSize(new Dimension(233, 30));
        run.setIcon(defaultButton);
        run.setPressedIcon(pressedButton);
        run.setBorderPainted(false);
        run.setMaximumSize(buttonSize);
        run.setAlignmentX(Component.CENTER_ALIGNMENT);
        run.setLayout(new GridLayout(0, 1));
        run.setHorizontalTextPosition(SwingConstants.CENTER);

        // Run ML with these params.
        run.addActionListener(mlrunActionListener);

        center.add(run);
        center.add(Box.createRigidArea(new Dimension(0, 10)));

        // back button

        JButton back = new JButton("Back.");
        back.setPreferredSize(new Dimension(233, 30));
        back.setIcon(defaultButton);
        back.setPressedIcon(pressedButton);
        back.setBorderPainted(false);
        back.setMaximumSize(buttonSize);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setLayout(new GridLayout(0, 1));
        back.setHorizontalTextPosition(SwingConstants.CENTER);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cl.show(Main.card, "Main Menu");

                Main.gmode = "";
            }
        });

        center.add(back);

        this.add(west, BorderLayout.WEST);
        this.add(center, BorderLayout.CENTER);
        this.add(east, BorderLayout.EAST);
    }

    //Load network menu
    public Menu (ImageIcon defaultButtonLook, ImageIcon pressedButtonLook, String menuTitle, Color bgColor, NetSave netSave, ArrayList<DrawNet> nets) {
        this.setLayout(new BorderLayout());

        this.netPics = nets;
        for(int i = 0; i < nets.size(); i++) {
            netPics.get(i).setLocation(50, 80);
            netPics.get(i).setVisible(false);
            center.add(netPics.get(i));
        }

        //set title of menu
        title.setText(menuTitle);
        title.setFont(header);
        titleHolder.add(title, CENTER_ALIGNMENT);
        titleHolder.setBackground(bgColor);
        titleHolder.setPreferredSize(new Dimension(100, 80));
        this.add(titleHolder, BorderLayout.NORTH);

        //Create slider with length number of networks
        netPicker = new Slider(netSave);
        netPicker.setBounds(10, 10, 400, 50);
        netPicker.label.setBounds(10, 30, 700, 70);
        netPicker.label.setBackground(Color.black);

        //Picker button
        JButton pick = new JButton("Pick.");
        pick.setBounds((int)Main.size.getWidth() / 2 - 233 / 2, 350, 233, 30);
        pick.setIcon(defaultButtonLook);
        pick.setPressedIcon(pressedButtonLook);
        pick.setBorderPainted(false);
        pick.setMaximumSize(buttonSize);
        pick.setAlignmentX(Component.CENTER_ALIGNMENT);
        pick.setLayout(new GridLayout(0, 1));
        pick.setHorizontalTextPosition(SwingConstants.CENTER);
        pick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenNetwork = netPicker.getValue() - 1;
                ML phold = new ML(NetSave.networkShapes.get(chosenNetwork), NetSave.netList.get(chosenNetwork), NetSave.biasList.get(chosenNetwork));
                System.out.println("r u sure about this");
                Main.mlgame();
                
            }
        });

        //Add Slider to center panel
        center.setBackground(bgColor);
        center.add(netPicker);
        center.add(pick);

        //network pic panel
        netDisplay.setBackground(new Color(170, 177, 255));
        netDisplay.setLayout(cl);
        netDisplay.setSize(700, 250);
        for(int i = 0; i < nets.size(); i++) {
            netDisplay.add(nets.get(i), "net" + i);
        }
        cl.show(netDisplay, "net" + 1);
        center.add(netDisplay);
        netDisplay.setLocation(10, 70);

        center.add(netPicker.label);

        JButton back = new JButton("Back.");
        back.setPreferredSize(new Dimension(233, 30));
        back.setIcon(defaultButtonLook);
        back.setPressedIcon(pressedButtonLook);
        back.setBorderPainted(false);
        back.setMaximumSize(buttonSize);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setLayout(new GridLayout(0, 1));
        back.setHorizontalTextPosition(SwingConstants.CENTER);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cl.show(Main.card, "Main Menu");

                Main.gmode = "";
            }
        });

        JPanel south = new JPanel();
        south.setBackground(bgColor);
        south.add(back);

        this.add(center, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);
    }

    public void addNetPics(DrawNet nets) {
        
    }

    static public void displayNetPics(int index) {
        cl.show(netDisplay, "net" + index);
    }

    //Default action; moves text down with button
    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO document why this method is empty
    }

}