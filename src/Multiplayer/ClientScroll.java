package Multiplayer;

import Lists.*;
import Screen.GamePlayScrolling.Background;
import Multiplayer.ClientGameEventHandler;
import Screen.GamePlayScrolling.PauseMenu;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static Multiplayer.ClientGameEventHandler.*;

@SuppressWarnings("ALL")
public class ClientScroll extends Canvas implements MouseMotionListener, MouseListener, KeyListener, Runnable  {
    /////////////////////////////////////
    public static int timerD=0;
    public static int timerP=0;
    public static int paintnumCounter = 0;
    public int timeStep = 10;
    public static boolean isDragged;
    public static boolean isPressed;
    public static boolean isPaused = false;
    public static int mouseCoordinateChangeX=0;
    public static int mouseCoordinateChangeY=0;
    public static int nextPosX;
    public static int nextPosY;
    public static int currentPosX;
    public static int currentPosY;
    public static boolean isOverheated = false;
    public static int overheatTimer = 0;
    public static int waveTimer;
    public static int levelIndex = 1;
    public static int waveIndex = 1;
    public static boolean waveIndexDraw=false;
    public static int rotateTime=20;
    public static int safeZoneDisplayTimer=0;
    public boolean firstTime = true;
    public boolean firstTimeP = true;
    /////////////////////////////////////
    // Two copies of the background image to scroll
    private ClientBackground backOne;
    private ClientBackground backTwo;

    private BufferedImage back;

    public ClientScroll() throws IOException {
        backOne = new ClientBackground();
        backTwo = new ClientBackground(backOne.getImageWidth(), 0);

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);

        new Thread(this).start();
        setVisible(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e){
        eventOutput("Mouse clicked", e);
    }

    @Override
    public void mousePressed(MouseEvent e){
        eventOutput("Mouse pressed", e);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        eventOutput("Mouse released", e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        eventOutput("Mouse dragged", e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        eventOutput("Mouse moved", e);
    }

    @Override
    public void run() {

        try {
            Thread th2 = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ///////////////////// Load lists from server's "network message " /////////////////////
//                        BombList.Bombs = GameClient.serverUpdateMessage.Bombs;
//                        ListOfBullets.Bullets = GameClient.serverUpdateMessage.Bullets;
//                        ListOfEnemies.Enemies = GameClient.serverUpdateMessage.Enemies;
//                        ListOfEnemyGroups.EnemyGroups = GameClient.serverUpdateMessage.EnemyGroups;
//                        ListOfExplosions.Explosions = GameClient.serverUpdateMessage.Explosions;
//                        ListOfFirings.Firings = GameClient.serverUpdateMessage.Firings;
//                        ListOfGiants.Giants = GameClient.serverUpdateMessage.Giants;
//                        ListOfPowerups.Powerups = GameClient.serverUpdateMessage.Powerups;
                        /////////////////////////// Next Step ///////////////////////////
                        try {
                            Thread.sleep(timeStep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        repaint();

                        }
                    }

                }
            );
            th2.start();
        }
        catch (Exception e) {}
    }

    @Override
    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
        Graphics2D twoD = (Graphics2D)window;

        if (back == null)
            back = (BufferedImage)(createImage(getWidth(), getHeight()));

        // Create a buffer to draw to
        Graphics buffer = back.createGraphics();

        // Put the two copies of the background image onto the buffer
        backOne.draw(buffer);
        backTwo.draw(buffer);

        // Draw the image onto the window
        twoD.drawImage(back, null, 0, 0);

    }

    void eventOutput(String eventDescription, MouseEvent e) {
        // Checking if the player is spectating or actually playing.
        if (!isSpectator){

            int xOffset = 55;
            int yOffset = 20;
            if (eventDescription.equals("Mouse moved")){
//            System.out.println("Mouse moved");
                event_x = e.getX();
                event_y = e.getY();
                ClientGameEventHandler.spaceship.setX(event_x - xOffset);
                ClientGameEventHandler.spaceship.setY(event_y - yOffset);
            }
            if (eventDescription.equals("Mouse dragged")){
                System.out.println("Mouse dragged");
//            if (e.getButton() == MouseEvent.BUTTON3){
//                Gun.bombShoot(e.getX(), e.getY(), getAccessibleContext()));
//            }
                event_x = e.getX();
                event_y = e.getY();
                ClientGameEventHandler.spaceship.setX(event_x - xOffset);
                ClientGameEventHandler.spaceship.setY(event_y - yOffset);
                if (e.getButton() == MouseEvent.BUTTON3 && !spaceship.isExploded){
                    Gun.bombShoot(event_x, event_y);
                }
                if (e.getButton() == MouseEvent.BUTTON1){
                    isPressed = false;
                    isDragged = true;
                    if (!spaceship.isExploded){
                        Gun.longShotD(event_x, event_y, Gun.damage);
                    }
                }
            }
            if (eventDescription.equals("Mouse pressed")){
//            if (e.getButton() == MouseEvent.BUTTON3){
//                Gun.bombShoot(e.getX(), e.getY(), gameFrame.getContentPane());
//            }
                System.out.println("Mouse pressed");
//            if (e.getButton() == MouseEvent.BUTTON3){
//                Gun.bombShoot(e.getX(), e.getY(), gameFrame.getContentPane());
//            }
                event_x = e.getX();
                event_y = e.getY();
                ClientGameEventHandler.spaceship.setX(event_x - xOffset);
                ClientGameEventHandler.spaceship.setY(event_y - yOffset);
                if (e.getButton() == MouseEvent.BUTTON3 && !spaceship.isExploded){
                    Gun.bombShoot(event_x, event_y);
                }
                if (e.getButton() == MouseEvent.BUTTON1){
                    isDragged = false;
                    isPressed = true;
//            GameObjects.Gun.longShotP(event_x, event_y, gameFrame.getContentPane(), GameObjects.Gun.damage);
                    Thread thread = new Thread(new Runnable() {

                        public void run() {
                            boolean firstTime = true;
                            while (isPressed){
//                        System.out.println("Pressed and trying");
                                System.out.print("");
                                if (timerP == 200){
//                            System.out.println("First Time!");
                                    firstTime = true;
                                }
                                if (timerP % 200 == 0 && firstTime){
                                    firstTime = false;
                                    System.out.println("Press Accomplished");
                                    if (!spaceship.isExploded){
                                        Gun.longShotP(event_x, event_y, Gun.damage);
                                    }
                                }
                            }
                        }
                    });
                    thread.start();

                }

            }
            if (eventDescription.equals("Mouse released")){
                System.out.println("Mouse released");
                Gun.interruptShooting();
            }

            if (eventDescription.equals("Mouse clicked")){
//            isPressed = false;
//            isDragged = false;
//            System.out.println("Mouse clicked");
//            GameObjects.Gun.singleShot(event_x, event_y, GameObjects.Gun.damage);
            }
//        System.out.println(eventDescription
//                + " (" + e.getX() + "," + e.getY() + ")"
//                + " detected on "
//                + e.getComponent().getClass().getName());
        }
    }

}