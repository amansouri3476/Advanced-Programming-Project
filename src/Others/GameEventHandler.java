package Others;

import GameObjects.Gun;
import GameObjects.Spaceship;
import MovingBackground.ScrollingBackground;
import MovingBackground.ScrollingBackground;
import Screen.ScreenPainter;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("ALL")
public class GameEventHandler extends JFrame implements MouseMotionListener,MouseListener,Runnable {
//    Screen.GameScreen gameScreen;
    public static int event_x;
    public static int event_y;
    public static boolean isDragged;
    public static boolean isPressed;
    public static Spaceship spaceship;
    public static JFrame gameFrame;
    public static int gameWidth = 2000;
    public static int gameHeight = 1500;
    public void run() {
        createAndShowGUI();
    }
    private void createAndShowGUI() {
        //Create and set up the window.
//        Screen.GameScreen gameScreen = new Screen.GameScreen();
        JFrame gameFrame = new JFrame("Star Wars");

        this.gameFrame = gameFrame;
        gameFrame.addMouseMotionListener(this);
        gameFrame.addMouseListener(this);
//        gameFrame.addMouseListener((MouseListener) this);
        JLabel backgroundLabel = new JLabel();
        JLabel spaceshipLabel = new JLabel();
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();
        Component component = gameFrame.getContentPane();
//        GameObjects.Spaceship spaceship;
        boolean isDragging;
        boolean isPressed;
        boolean firstTime = true;
        boolean firstTimeP = true;

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");


        gameFrame.setSize(gameWidth, gameHeight);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLayout(layout);
//        gameFrame.setVisible(true);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gameFrame.add(backgroundLabel, gbc);
        backgroundLabel.setIcon(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Nebula_Red.png"));

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundLabel.add(spaceshipLabel, gbc);
        spaceshipLabel.setIcon(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Spaceship.png"));

//        gameFrame.validate();
//        gameFrame.repaint();
//        gameFrame.setVisible(true);
        this.spaceship = new Spaceship(gameFrame.getContentPane(), spaceshipLabel);
//        GameObjects.Gun gun = new GameObjects.Gun(gameFrame.getContentPane(), spaceshipLabel);

        component.setCursor(blankCursor);
//        gameFrame.validate();
//        gameFrame.repaint();
//        gameFrame.setVisible(true);

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component)back).setFocusable(true);
        gameFrame.getContentPane().add(back);

        gameFrame.setVisible(true);

    }

    public GameEventHandler() {
//        Screen.GameScreen gameScreen = new Screen.GameScreen();
        //Register for mouse events on blankArea and panel.
//        .addMouseMotionListener(this);
//        addMouseMotionListener(this);

    }



    void eventOutput(String eventDescription, MouseEvent e) {
        if (eventDescription.equals("Mouse moved")){
//            System.out.println("Mouse moved");
            event_x = e.getX();
            event_y = e.getY();
        }
        if (eventDescription.equals("Mouse dragged")){
            isPressed = false;
            System.out.println("Mouse dragged");
            if (e.getButton() == MouseEvent.BUTTON3){
                Gun.bombShoot(e.getX(), e.getY(), gameFrame.getContentPane());
            }
            event_x = e.getX();
            event_y = e.getY();
            isDragged = true;
            Gun.longShotD(event_x, event_y, gameFrame.getContentPane(), Gun.damage);
        }
        if (eventDescription.equals("Mouse pressed")){
            if (e.getButton() == MouseEvent.BUTTON3){
                Gun.bombShoot(e.getX(), e.getY(), gameFrame.getContentPane());
            }
            isDragged = false;
            System.out.println("Mouse pressed");
            if (e.getButton() == MouseEvent.BUTTON3){
                Gun.bombShoot(e.getX(), e.getY(), gameFrame.getContentPane());
            }
            event_x = e.getX();
            event_y = e.getY();
            isPressed = true;
//            GameObjects.Gun.longShotP(event_x, event_y, gameFrame.getContentPane(), GameObjects.Gun.damage);
            Thread thread = new Thread(new Runnable() {

                public void run() {
                    boolean firstTime = true;
                    while (isPressed){
                        System.out.println("Pressed and trying");
                        if (ScreenPainter.timerP == 400){
                            firstTime = true;
                        }
                        if (ScreenPainter.timerP % 400 == 0 && firstTime){
                            firstTime = false;
                            System.out.println("Press Accomplished");
                            Gun.longShotP(event_x, event_y, gameFrame.getContentPane(), Gun.damage);
                        }
                    }
                }
            });
            thread.start();

        }
        if (eventDescription.equals("Mouse released")){
            System.out.println("Mouse released");
            Gun.interruptShooting();
        }

//        if (eventDescription.equals("Mouse clicked")){
//            isPressed = false;
//            isDragged = false;
//            System.out.println("Mouse clicked");
//            GameObjects.Gun.singleShot(event_x, event_y, gameFrame.getContentPane(), GameObjects.Gun.damage);
//        }
//        System.out.println(eventDescription
//                + " (" + e.getX() + "," + e.getY() + ")"
//                + " detected on "
//                + e.getComponent().getClass().getName());
    }

    public void mouseMoved(MouseEvent e) {
        eventOutput("Mouse moved", e);
    }

    public void mouseDragged(MouseEvent e) {
        eventOutput("Mouse dragged", e);
    }
    public void mouseReleased(MouseEvent e){
        eventOutput("Mouse released", e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e){
        eventOutput("Mouse pressed", e);
    }
    public void mouseClicked(MouseEvent e){
        eventOutput("Mouse clicked", e);
    }

}