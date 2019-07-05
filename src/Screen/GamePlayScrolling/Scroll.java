package Screen.GamePlayScrolling;

import GameObjects.*;
import Lists.*;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    private Background backOne;
    private Background backTwo;

    private BufferedImage back;

    public Scroll() throws IOException {
        backOne = new Background();
        backTwo = new Background(backOne.getImageWidth(), 0);

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);

        new Thread(this).start();
        setVisible(true);

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

                            /////////////////////// Spaceship ///////////////////////

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
            if (spaceship.explosionTimer > 4000){
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
                                    ListOfExplosions.updateList();
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
                                    ListOfExplosions.updateList();
                                    ListOfBullets.Bullets.remove(bullet);
                                }
                            }
                        }
                    }
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
//            System.out.println("Mouse moved");
            event_x = e.getX();
            event_y = e.getY();
            GameEventHandler.spaceship.setX(event_x - xOffset);
            GameEventHandler.spaceship.setY(event_y - yOffset);
        }
        if (eventDescription.equals("Mouse dragged")){
            System.out.println("Mouse dragged");
//            if (e.getButton() == MouseEvent.BUTTON3){
//                Gun.bombShoot(e.getX(), e.getY(), getAccessibleContext()));
//            }
            event_x = e.getX();
            event_y = e.getY();
            GameEventHandler.spaceship.setX(event_x - xOffset);
            GameEventHandler.spaceship.setY(event_y - yOffset);
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
            GameEventHandler.spaceship.setX(event_x - xOffset);
            GameEventHandler.spaceship.setY(event_y - yOffset);
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
            new EnemyGroup("Circular", "Fixed", 500, -50, 55);
        }
        if (waveIdx == 3 && levelIdx == 1){
//            new Enemy(500, -100, "fighter");
            new EnemyGroup("Rotational", 750, 400, 150, 60);
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