package Screen.GamePlayScrolling;

import GameObjects.*;
import Lists.*;
import Menu.Scorer;
import Multiplayer.GameServer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static Screen.GamePlayScrolling.GameEventHandler.*;

@SuppressWarnings("ALL")
public class Scroll extends Canvas implements MouseMotionListener, MouseListener, KeyListener, Runnable  {
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
    /////////////////////////////////////
    // Two copies of the background image to scroll
    private Background backOne;
    private Background backTwo;

    private BufferedImage back;

    // GameEventHandler for checking whether it is active or not
    private GameEventHandler gameEventHandler;

    public Scroll() throws IOException {
        backOne = new Background();
        backTwo = new Background(backOne.getImageWidth(), 0);

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);

        new Thread(this).start();
        setVisible(true);

    }

    public Scroll(GameEventHandler gameEventHandler) throws IOException {
        backOne = new Background();
        backTwo = new Background(backOne.getImageWidth(), 0);

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);

        this.gameEventHandler = gameEventHandler;

        new Thread(this).start();
        setVisible(true);

        spaceship.setX(700);
        spaceship.setY(650);

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
                    wave(levelIndex, waveIndex);
                    while (true) {
//                        currentPosX = event_x;
//                        currentPosY = event_y;
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        System.out.println("inside the while");
                        if(isPaused){
//                            System.out.println("Is Paused");
//                            continue;
                        }
                        else {
//                            System.out.println("NOT Paused");
                            ////////////////////////////////////// Updating //////////////////////////////////////
                            waveCheck();
                            ListOfBullets.updateList();
                            ListOfEnemies.updateList();
                            ListOfEnemyGroups.updateList();
                            BombList.updateList();
                            ListOfGiants.updateList();
                            /////////////////////////// Moving ///////////////////////////
                            spaceshipMove();
                            mouseCheck();
                            /////////////////////// Spaceship ///////////////////////
                            // Checking collision of other spaceships' bullets with each client.
//                            if (GameServer.isMultiplayer){
//                                interClientHitCheck();
//                            }
                            /////////////////////// Bullets ///////////////////////
                            bulletsMoveAndCollisionCheck();
                            /////////////////////// Enemies and Giants ///////////////////////
                            enemiesMovingandShoot();
                            giantsMovingandShoot();
                            firingsMoving();
                            powerupsMoving();
                            enemyGroupsMoving();

                            /////////////////////////// Timer Updates ///////////////////////////

                            ///////////// Enemies and Spaceship Explosion Timer /////////////
                            enemyExplosionTimerUpdate();
                            spaceshipExplosionTimerUpdate();
                            ///////////// Engine Cooling /////////////
                            engineTimerUpdate();
                            ///////////// Drag and Press Timers /////////////
                            dragAndPressTimerUpdate();
                            ///////////// Wave Logo Timers /////////////
                            waveLogoTimerUpdate();
                            ///////////// Safe Zone Timer /////////////
                            safeZoneTimerUpdate();
                            /////////////////////////// Next Step ///////////////////////////
                            try {
                                Thread.sleep(timeStep);
                                if (refresh == 10){
//                                    System.out.println(">>>> Spaceship: " + spaceship.getX() + "\t" + mouseCoordinateChangeX);
//                                    System.out.println(">>>>>>>> Event: " + event_x + "\t" + event_y);
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
            }
            );
            th2.start();
        }
        catch (Exception e) {}
    }

    private void clientBulletsSpaceshipCollisionCheck() {
//        for (String playerName : GameServer.joinedPlayers){
//            for (Bullet bullet: ListOfBullets.Bullets){
//                int index = GameServer.joinedPlayers.indexOf(playerName);
//                if (GameServer.joinedPlayersObjects.get(index).spaceship.checkCollisionClientBullets(bullet)){
//                    if (!bullet.shooter.equals(playerName)){
//                        ListOfUsers.getPlayerObjByUsername(playerName).isExploded = true;
//                        ListOfUsers.getPlayerObjByUsername(bullet.shooter).score += 100;
//                        ListOfBullets.Bullets.remove(bullet);
//                    }
//                }
//            }
//        }
    }

    private void interClientHitCheck() {
        for (String playerName: GameServer.joinedPlayers){
            int index = GameServer.joinedPlayers.indexOf(playerName);
            for (Bullet bullet:ListOfBullets.Bullets){
                if (!bullet.shooter.equals(playerName)){
                    if (ListOfUsers.getPlayerObjByUsername(playerName).spaceship.checkCollisionClientBullet(bullet)){
                        spaceship.explosionX = bullet.x_coordinate;
                        spaceship.explosionY = bullet.y_coordinate;
                        ListOfBullets.Bullets.remove(bullet);
                    }
                }
            }
        }
    }

    private void mouseCheck() {
        if (!gameEventHandler.isActive()){
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
    }


    private void giantsMovingandShoot() {
        if (!ListOfGiants.Giants.isEmpty()){
            AtomicInteger giantCounter = new AtomicInteger(0);
            synchronized (ListOfGiants.Giants){
                for (int i=0; i<ListOfGiants.Giants.size(); i++){
                    Giant giant = ListOfGiants.Giants.get(i);
                    giantCounter.getAndIncrement();
                    /////////////////// Collision Check ///////////////////

                    if (giant.checkCollisionSpaceship(spaceship) && !spaceship.isExploded){
                        spaceship.isExploded = true;
                        spaceship.explosionX = spaceship.x_coordinate - 50;
                        spaceship.explosionY = spaceship.y_coordinate - 50;

                        giant.health -= 20;
                        if (giant.health <= 0){
                            giant.isDead = true;
                        }
//                                    ListOfExplosions.updateList();
                        LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Sonic_Boom.wav", false);
//                                    ListOfEnemies.Enemies.remove(enemy);
                    }
                    giant.shoot(giant.x_coordinate, giant.y_coordinate);
                    /////////////////// Moving Giants ///////////////////
                    giant.giantMover.changeX(giant);
                    giant.giantMover.changeY(giant);
                }
            }

        }
    }

    private void safeZoneTimerUpdate() {
        if (Background.showSafeZone){
            if (safeZoneDisplayTimer < 5000){
                safeZoneDisplayTimer += timeStep;
            }
            else {
                safeZoneDisplayTimer = 0;
                Background.showSafeZone = false;
            }
        }
    }

    private void enemyGroupsMoving() {
        if (!ListOfEnemyGroups.EnemyGroups.isEmpty()){
            AtomicInteger enemyGroupCounter = new AtomicInteger(0);
            synchronized (ListOfEnemyGroups.EnemyGroups){
                for (int i=0; i<ListOfEnemyGroups.EnemyGroups.size(); i++){
                    EnemyGroup enemyGroup = ListOfEnemyGroups.EnemyGroups.get(i);
                    enemyGroupCounter.getAndIncrement();

                    /////////////////// Moving Group ///////////////////
                    enemyGroup.enemyGroupMover.changeX(enemyGroup);
                    enemyGroup.enemyGroupMover.changeY(enemyGroup);

                    ////////////////// EnemyGroup Timer Updates //////////////////
                    if (enemyGroup.rotateTimer < rotateTime){
                        enemyGroup.rotateTimer += timeStep;
                    }
                    else enemyGroup.rotateTimer = 0;

                    if (enemyGroup.changeCenterTimer < 4000){
                        enemyGroup.changeCenterTimer += timeStep;
                    }
                    else enemyGroup.changeCenterTimer = 0;
                }
            }
        }
    }

    private void powerupsMoving() {
        if (!ListOfPowerups.Powerups.isEmpty()){
            AtomicInteger powerupCounter = new AtomicInteger(0);
            synchronized (ListOfPowerups.Powerups){
                for (int i=0; i<ListOfPowerups.Powerups.size(); i++){
                    Powerup powerup = ListOfPowerups.Powerups.get(i);
                    powerupCounter.getAndIncrement();

                    /////////////////// Collision Check ///////////////////
                    if (spaceship.checkCollisionPowerup(powerup)){
                        ListOfPowerups.Powerups.remove(powerup);
                    }
                    /////////////////// Moving Bullets ///////////////////
                    powerup.powerupMover.changeX(powerup);
                    powerup.powerupMover.changeY(powerup);

                }
            }
        }
    }

    private void firingsMoving() {
        if (!ListOfFirings.Firings.isEmpty()){
            AtomicInteger fireCounter = new AtomicInteger(0);
            synchronized (ListOfFirings.Firings){
                for (int i=0; i<ListOfFirings.Firings.size(); i++){
                    EnemyFire enemyFire = ListOfFirings.Firings.get(i);
                    fireCounter.getAndIncrement();

                    /////////////////// Collision Check ///////////////////
                    if (spaceship.checkCollision(enemyFire)){
                        spaceship.explosionX = enemyFire.x_coordinate;
                        spaceship.explosionY = enemyFire.y_coordinate;
                        ListOfFirings.Firings.remove(enemyFire);
                    }
                    //TODO: Collision of firings with clients' spaceships needs to be checked and performed.
                    /////////////////// Moving Bullets ///////////////////
                    enemyFire.fireMover.changeX(enemyFire);
                    enemyFire.fireMover.changeY(enemyFire);

                }
            }
        }
    }


    private void spaceshipExplosionTimerUpdate() {
        if (spaceship.isExploded){
            spaceship.explosionTimer += timeStep;
            if (spaceship.explosionTimer > 1000){
                spaceship.isExploded = false;
                spaceship.explosionTimer = 0;
            }
        }
    }

    private void dragAndPressTimerUpdate() {
        if (isDragged)
        {
            timerD += timeStep;
        }
        if (isPressed)
        {
            timerP += timeStep;
        }
    }

    private void engineTimerUpdate() {
        if (isOverheated){
            overheatTimer += timeStep;
            ListOfBullets.heat -= 0.250;
            if (overheatTimer >= 4000){
                isOverheated = false;
                overheatTimer = 0;

            }
        }
        else {
            ListOfBullets.heat -= 0.2;
        }
    }

    private void waveLogoTimerUpdate() {
        if (waveIndexDraw){
            waveTimer += timeStep;
            if (waveTimer >= 2000){
                waveIndexDraw = false;
                waveTimer = 0;
            }
        }
    }

    private void enemyExplosionTimerUpdate() {
        if (!ListOfExplosions.Explosions.isEmpty()){
            AtomicInteger enemyCounter = new AtomicInteger(0);
            synchronized (ListOfExplosions.Explosions){
                for (int i=0; i<ListOfExplosions.Explosions.size(); i++){
                    Enemy enemy = ListOfExplosions.Explosions.get(i);
                    enemyCounter.getAndIncrement();
                    enemy.explosionTimer += timeStep;

                }
            }
        }
    }

    private void enemiesMovingandShoot() {
        if (!ListOfEnemies.Enemies.isEmpty()){
            AtomicInteger enemyCounter = new AtomicInteger(0);
            synchronized (ListOfEnemies.Enemies){
                for (int i=0; i<ListOfEnemies.Enemies.size(); i++){
                    Enemy enemy = ListOfEnemies.Enemies.get(i);
                    enemyCounter.getAndIncrement();
                    /////////////////// Collision Check ///////////////////

                                if (enemy.checkCollisionSpaceship(spaceship) && !spaceship.isExploded){
                                    spaceship.isExploded = true;
                                    enemy.isDead = true;
                                    spaceship.explosionX = spaceship.x_coordinate - 50;
                                    spaceship.explosionY = spaceship.y_coordinate - 50;
//                                    ListOfExplosions.updateList();
                                    LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Sonic_Boom.wav", false);
                                    enemy.isDead = true;
//                                    ListOfEnemies.Enemies.remove(enemy);
                                }
                    enemy.shoot(enemy.x_coordinate, enemy.y_coordinate);
                    /////////////////// Moving Enemies ///////////////////
//                    if (!enemy.isInGroup){
//                        enemy.enemyMover.changeX(enemy);
//                        enemy.enemyMover.changeY(enemy);
//                    }
                    enemy.enemyMover.changeX(enemy);
                    enemy.enemyMover.changeY(enemy);
                }
            }

        }
    }

    private void bulletsMoveAndCollisionCheck() {
        if (!ListOfBullets.Bullets.isEmpty()){
            AtomicInteger bulletCounter = new AtomicInteger(0);
            synchronized (ListOfBullets.Bullets){
                for (int i=0; i<ListOfBullets.Bullets.size(); i++){
                    Bullet bullet = ListOfBullets.Bullets.get(i);
                    bulletCounter.getAndIncrement();

                    /////////////////// Collision Check (With Enemies) ///////////////////
                    if (!ListOfEnemies.Enemies.isEmpty()){
                        AtomicInteger enemyCounter = new AtomicInteger(0);
                        synchronized (ListOfEnemies.Enemies){
                            for (int j=0; j<ListOfEnemies.Enemies.size(); j++){
                                Enemy enemy = ListOfEnemies.Enemies.get(j);
                                enemyCounter.getAndIncrement();
                                if (enemy.checkCollision(bullet)){
                                    if (enemy.isDead){
                                        Scorer.enemyScore(enemy, bullet);
                                    }
                                    ListOfBullets.Bullets.remove(bullet);
                                }
                            }
                        }
                    }
                    /////////////////// Collision Check (With Giants) ///////////////////
                    if (!ListOfGiants.Giants.isEmpty()){
                        AtomicInteger giantCounter = new AtomicInteger(0);
                        synchronized (ListOfGiants.Giants){
                            for (int j=0; j<ListOfGiants.Giants.size(); j++){
                                Giant giant = ListOfGiants.Giants.get(j);
                                giantCounter.getAndIncrement();
                                if (giant.checkCollision(bullet)){
                                    if (giant.isDead){
                                        Scorer.giantScore(giant, bullet);
                                    }
                                    ListOfExplosions.updateList();
                                    ListOfBullets.Bullets.remove(bullet);
                                }
                            }
                        }
                    }
                    /////////////////// Collision Check (With Other Players) ///////////////////
                    //TODO: There needs to be a collision check with other clients' spaceships
                    /////////////////// Moving Bullets ///////////////////
                    bullet.bulletMover.changeX(bullet);
                    bullet.bulletMover.changeY(bullet);

                }
            }
        }
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

    private void keyEventOutput(String key_description, KeyEvent e) {
        if (key_description.equals("key pressed")){
            System.out.println("Key Pressed");
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE){

                System.out.println("ESCAPE ENTERED");
                isPaused = true;
                new PauseMenu();

            }
        }
        if (key_description.equals("key typed")){
            System.out.println("Key Typed");
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
//                System.out.println("ESCAPE ENTERED");
//                isPaused = true;
//                new PauseMenu();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyEventOutput("key typed", e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyEventOutput("key pressed", e);
    }


    @Override
    public void keyReleased(KeyEvent e) {
        keyEventOutput("key released", e);
    }


    private void waveCheck() {
        if (ListOfEnemies.Enemies.isEmpty() && ListOfGiants.Giants.isEmpty()){
            waveIndex = (waveIndex % 4 + 1);
            waveIndexDraw = true;
            wave(levelIndex, waveIndex);
        }
    }

    public void wave(int levelIdx, int waveIdx){
        //////////////////////////// Level 1 ////////////////////////////
        if (waveIdx == 1 && levelIdx == 1){
            waveIndexDraw = true;
            new EnemyGroup("Rectangular", 500, 100,"zigzag",  50);
//            new EnemyGroup("Circular", "Fixed", 500, -50, 55);
//            new EnemyGroup("Rotational", 750, 400, 150, 60);
//            new Giant("starDestroyer", levelIdx * 250, 200, 100);
        }
        if (waveIdx == 2 && levelIdx == 1){
//            new EnemyGroup("Circular", "Fixed", 500, -50, 55);
        }
        if (waveIdx == 3 && levelIdx == 1){
//            new Enemy(500, -100, "fighter");
//            new EnemyGroup("Rotational", 750, 400, 150, 60);
        }
        if (waveIdx == 4 && levelIdx == 1){
//            new Enemy(500, -100, "fighter");
//            new Giant("star_destroyer", levelIdx * 250);
        }
        //////////////////////////// Level 2 ////////////////////////////
        if (waveIdx == 1 && levelIdx == 2){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 2 && levelIdx == 2){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 3 && levelIdx == 2){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 4 && levelIdx == 2){
//            new Enemy(500, -100, "fighter");
        }
        //////////////////////////// Level 3 ////////////////////////////
        if (waveIdx == 1 && levelIdx == 3){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 2 && levelIdx == 3){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 3 && levelIdx == 3){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 4 && levelIdx == 3){
//            new Enemy(500, -100, "fighter");
        }
        //////////////////////////// Level 4 ////////////////////////////
        if (waveIdx == 1 && levelIdx == 4){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 2 && levelIdx == 4){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 3 && levelIdx == 4){
//            new Enemy(500, -100, "fighter");
        }
        if (waveIdx == 4 && levelIdx == 4){
//            new Enemy(500, -100, "fighter");
        }
    }

}