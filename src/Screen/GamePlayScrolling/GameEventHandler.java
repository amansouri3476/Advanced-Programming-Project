package Screen.GamePlayScrolling;

import GameObjects.Gun;
import GameObjects.Spaceship;
import MovingBackground.ScrollingBackground;
import MovingBackground.ScrollingBackground;
import Screen.GamePlayScrolling.Runner;
import Screen.GamePlayScrolling.Scroll;
import Screen.ScreenPainter;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("ALL")
public class GameEventHandler extends JFrame implements Runnable {
    public static int event_x;
    public static int event_y;
    public static Spaceship spaceship;
    public static JFrame gameFrame;
    public static int gameWidth = 2000;
    public static int gameHeight = 1500;

    public GameEventHandler(Container container) {

        container.removeAll();

        JLabel spaceshipLabel = new JLabel();
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");


        this.spaceship = new Spaceship(getContentPane(), spaceshipLabel);

        container.setCursor(blankCursor);

        Scroll scroll = null;
        try {
            scroll = new Scroll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component)scroll).setFocusable(true);
        container.add(scroll);

        container.setSize(gameWidth, gameHeight);

        container.validate();
        container.repaint();

        container.setVisible(true);
    }

    public void run() {

        createAndShowGUI();
    }
    private void createAndShowGUI() {

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

    }

    public GameEventHandler() {

        JLabel spaceshipLabel = new JLabel();
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");


        this.spaceship = new Spaceship(getContentPane(), spaceshipLabel);

        Component component = getContentPane();
        component.setCursor(blankCursor);

        Scroll scroll = null;
        try {
            scroll = new Scroll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component)scroll).setFocusable(true);
        getContentPane().add(scroll);


        setSize(gameWidth, gameHeight);

        setVisible(true);
    }


}