package Screen.GamePlayScrolling;

import MovingBackground.ScrollingBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PauseMenu extends JFrame implements WindowListener {
    public JFrame frame;
    int radius = 10;
    // Transparent 16 x 16 pixel cursor image.
    private BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    // Create a new blank cursor.
    private Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");

    public JButton bResumeGame, bSettings, bQuit;

    public PauseMenu() {
        System.out.println("PAUSE MENU CREATED");
        this.frame = new JFrame();
        setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;
        JButton bResumeGame = addButton("Resume Game", x, y, width,height, radius);
        this.add(bResumeGame);
        this.bResumeGame = bResumeGame;
        JButton bSettings = addButton("Settings", x,  y + delta_y, width,height, radius);
        this.add(bSettings);
        this.bSettings = bSettings;
        JButton bQuit = addButton("Quit Game", x, y + delta_y * 2, width,height, radius);
        this.add(bQuit);
        this.bQuit = bQuit;

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((Component)back).setFocusable(true);
        getContentPane().add(back);

        setVisible(true);
        menu();
    }

    protected JButton addButton(String buttonName, int x, int y, int width, int height, int r){
        JButton button = new JButton(buttonName);
//        button.setBorder(new RoundedBorder(r));
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);
        button.setForeground(Color.RED);
        button.setBackground(Color.orange);
        button.setOpaque(true);
        button.setFont(new Font("Georgia", Font.ITALIC, 18));
        add(button,0);

        return button;
    }

    void menu(){


        // Adding ActionListeners
        bResumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GAME RESUMED");
                System.out.println("Scroll.isPaused: " + Scroll.isPaused);
                dispose();
                Scroll.isPaused = false;
                System.out.println("Scroll.isPaused: " + Scroll.isPaused);
            }
        });
        bQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frame.setVisible(false);
            }
        });
        bSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("GAME RESUMED");
        dispose();
        Scroll.isPaused = false;
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}


