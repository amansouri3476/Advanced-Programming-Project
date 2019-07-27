package Multiplayer;

import Lists.*;
import Screen.GamePlayScrolling.Background;
import Multiplayer.ClientGameEventHandler;
import Screen.GamePlayScrolling.PauseMenu;

import java.awt.*;
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
    public static boolean screenInactive = false;
    public static boolean firstTimeMouseX = false;
    public static boolean firstTimeMouseY = false;
    public static boolean changePermittedX = true;
    public static boolean changePermittedY = true;
    public static boolean changePositionX = true;
    public static boolean changePositionY = true;
    public static int mouseCoordinateChangeX=0;
    public static int mouseCoordinateChangeY=0;
    public static int nextPosX=0;
    public static int nextPosY=0;
    public static int currentPosX=0;
    public static int currentPosY=0;
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
    private Robot robot;
    private int refresh = 0;

    public static int score = 0;
    private ClientGameEventHandler clientGameEventHandler;
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

    public ClientScroll(ClientGameEventHandler clientGameEventHandler) throws IOException {
        backOne = new ClientBackground();
        backTwo = new ClientBackground(backOne.getImageWidth(), 0);

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);

        spaceship.setX(700);
        spaceship.setY(650);

        this.clientGameEventHandler = clientGameEventHandler;

        new Thread(this).start();
        setVisible(true);

        ///////// For adjusting the mouse position
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        try {
            robot = new Robot(gs[0]);
        } catch (AWTException e) {
            e.printStackTrace();
        }

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

                        ////////////////////////////////////// Updating //////////////////////////////////////
                        ListOfClientBullets.updateList();
                        ///////////// Engine Cooling /////////////
                        engineTimerUpdate();
                        ///////////////////// Load lists from server's "network message " /////////////////////
                        spaceshipMove();
                        mouseCheck();
                        /////////////////////////// Next Step ///////////////////////////
                        try {
                            Thread.sleep(timeStep);
//                            System.out.println(ListOfBullets.Bullets.size());
                            if (refresh == 10){
                                refresh = 0;
                                changePermittedX = true;
                                changePermittedY = true;
                            }
                            else refresh++;
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

    private void mouseCheck() {
        if (!clientGameEventHandler.isActive()){
            firstTimeMouseX = false;
            firstTimeMouseY = false;
            changePermittedX = true;
            changePermittedY = true;
            screenInactive = true;
        }
    }

    private void spaceshipMove() {
        if (changePositionX){
            spaceship.setX(spaceship.getX() + mouseCoordinateChangeX);
            if (spaceship.x_coordinate > 1420){
                spaceship.setX(1420);
            }
            else if (spaceship.x_coordinate < 0){
                spaceship.setX(0);
            }
            changePositionX = false;
        }
        if (changePositionY){
            spaceship.setY(spaceship.getY() + mouseCoordinateChangeY);
            if (spaceship.y_coordinate < 0){
                spaceship.setY(0);
            }
            else if (spaceship.y_coordinate > 700){
                spaceship.setY(700);
            }
            changePositionY = false;
        }

        ListOfUsers.getPlayerObjByUsername(ListOfUsers.selectedUser).x_coordinate = spaceship.getX();
        ListOfUsers.getPlayerObjByUsername(ListOfUsers.selectedUser).y_coordinate = spaceship.getY();
//        ListOfUsers.getPlayerObjByUsername(ListOfUsers.selectedUser).spaceship = spaceship;
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
    private void engineTimerUpdate() {
        if (isOverheated){
            overheatTimer += timeStep;
            ListOfClientBullets.heat -= 0.250;
            if (overheatTimer >= 4000){
                isOverheated = false;
                overheatTimer = 0;

            }
        }
        else {
            ListOfClientBullets.heat -= 0.2;
        }
    }

    void eventOutput(String eventDescription, MouseEvent e) {

        if (!isSpectator){
            int xOffset = 55;
            int yOffset = 20;


            if (eventDescription.equals("Mouse moved")){
                smoothMoving(e);
//            GameEventHandler.spaceship.setX(event_x - xOffset);
//            GameEventHandler.spaceship.setY(event_y - yOffset);

//            GameEventHandler.spaceship.setX(100);
//            GameEventHandler.spaceship.setY(200);
//            if (screenInactive){
//                currentPosY = nextPosX;
//                currentPosY = nextPosY;
//            }
//            else {
//                Scroll.nextPosX = event_x - xOffset;
//                Scroll.nextPosY = event_y - yOffset;
//
//                mouseCoordinateChangeX = nextPosX - currentPosX;
//                mouseCoordinateChangeY = nextPosY - currentPosY;
//
//                if (changePermittedX){
//                    GameEventHandler.spaceship.setX(spaceship.x_coordinate + mouseCoordinateChangeX);
//                    GameEventHandler.spaceship.setY(spaceship.y_coordinate + mouseCoordinateChangeY);
//                }
//
//                currentPosX = nextPosX;
//                currentPosY = nextPosY;
//            }

            }
            if (eventDescription.equals("Mouse dragged")){
                System.out.println("Mouse dragged");
//            if (e.getButton() == MouseEvent.BUTTON3){
//                Gun.bombShoot(e.getX(), e.getY(), getAccessibleContext()));
//            }
                smoothMoving(e);
//            event_x = e.getX();
//            event_y = e.getY();
//            GameEventHandler.spaceship.setX(event_x - xOffset);
//            GameEventHandler.spaceship.setY(event_y - yOffset);
                if (e.getButton() == MouseEvent.BUTTON3 && !spaceship.isExploded){
                    Gun.bombShoot(spaceship.getX(), spaceship.getY());
                }
                if (e.getButton() == MouseEvent.BUTTON1){
                    isPressed = false;
                    isDragged = true;
                    if (!spaceship.isExploded){
                        Gun.longShotD(spaceship.getX(), spaceship.getY(), Gun.damage);
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
//            GameEventHandler.spaceship.setX(event_x - xOffset);
//            GameEventHandler.spaceship.setY(event_y - yOffset);
                if (e.getButton() == MouseEvent.BUTTON3 && !spaceship.isExploded){
                    Gun.bombShoot(spaceship.getX(), spaceship.getY());
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
                                        Gun.longShotP(spaceship.getX(), spaceship.getY(), Gun.damage);
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

    private void smoothMoving(MouseEvent e) {
        System.out.println(">>>>>>>>>>>>>>>>> SmoothMoving called");
        if (screenInactive){
            System.out.println("Mouse moved after Inactive");
            event_x = e.getX();
            event_y = e.getY();

            nextPosX = event_x;
            currentPosX = nextPosX;

            nextPosY = event_y;
            currentPosY = nextPosY;

            screenInactive = false;
        }
        else {
            event_x = e.getX();
            event_y = e.getY();
        }


//            System.out.println(event_x + "\t" + event_y);
        ////////// X //////////
        if (currentPosX == nextPosX){
            if (!firstTimeMouseX){
                nextPosX = event_x;
                currentPosX = nextPosX;
                firstTimeMouseX = true;
            }
            else {
                nextPosX = event_x;
                mouseCoordinateChangeX = nextPosX - currentPosX;
                changePositionX = true;
//                spaceship.setX(spaceship.getX() + mouseCoordinateChangeX);
                currentPosX = nextPosX;
            }
        }
        else currentPosX = nextPosX;

        ////////// Y //////////
        if (currentPosY == nextPosY){
            if (!firstTimeMouseY){
                nextPosY = event_y;
                currentPosY = nextPosY;
                firstTimeMouseY = true;
            }
            else {
                nextPosY = event_y;
                mouseCoordinateChangeY = nextPosY - currentPosY;
                changePositionY = true;
//                spaceship.setY(spaceship.getY() + mouseCoordinateChangeY);
                currentPosY = nextPosY;
            }
        }
        else currentPosY = nextPosY;

        /////////// Mouse Change XXX ///////////
        if (event_x > 1450 || event_x < 20){
            if (changePermittedX){
                changePermittedX = false;
                System.out.println(">>>>>>>>>>>>>>>>> Mouse Change XXX");
                if (event_x > 1400) {
                    robot.mouseMove(1100, event_y);
                }
                else robot.mouseMove(300, event_y);
//                nextPosX = 750;
//                currentPosX = 750;

//                nextPosY = event_y;
//                currentPosY = nextPosY;

                firstTimeMouseX = false;
                firstTimeMouseY = false;
            }
        }

        /////////// Mouse Change YYY ///////////
        if (event_y > 730 || event_y < 20){
            if (changePermittedY){
                changePermittedY = false;
                System.out.println(">>>>>>>>>>>>>>>>> Mouse Change YYY");
                if (event_y > 730) {
                    robot.mouseMove(event_x, 650);
                }
                else robot.mouseMove(event_x, 200);
//                nextPosX = 750;
//                currentPosX = 750;

//                nextPosY = event_y;
//                currentPosY = nextPosY;

                firstTimeMouseX = false;
                firstTimeMouseY = false;
            }
        }
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

}