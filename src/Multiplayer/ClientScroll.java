package Multiplayer;

import GameObjects.*;
import Lists.*;
import Screen.GamePlayScrolling.Background;
import Screen.GamePlayScrolling.GameEventHandler;
import Screen.GamePlayScrolling.PauseMenu;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static Screen.GamePlayScrolling.GameEventHandler.*;

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
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

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
}