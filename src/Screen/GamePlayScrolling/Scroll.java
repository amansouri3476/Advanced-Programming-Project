package Screen.GamePlayScrolling;

import GameObjects.Bullet;
import GameObjects.Spaceship;
import Lists.ListOfBullets;
import Others.GameEventHandler;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Scroll extends Canvas implements Runnable {
    /////////////////////////////////////
    public static int timerD=0;
    public static int timerP=0;
    public static int paintnumCounter = 0;
    /////////////////////////////////////
    // Two copies of the background image to scroll
    private Background backOne;
    private Background backTwo;

    private BufferedImage back;

    public Scroll() throws IOException {
        backOne = new Background();
        backTwo = new Background(backOne.getImageWidth(), 0);

        new Thread(this).start();
        setVisible(true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                dodo();
                repaint();
                try {
                    if (GameEventHandler.isDragged)
                    {
                        timerD += 20;
                    }
                    if (GameEventHandler.isPressed)
                    {
                        timerP += 20;
                    }
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Thread.currentThread().sleep(5);
            }
        }
        catch (Exception e) {}
    }

    private void dodo() {
        paintnumCounter += 1;
        // GameObjects.Spaceship Painting
        Spaceship.spaceshipLabel.setBounds(GameEventHandler.event_x - 50, GameEventHandler.event_y + 20, 200, 200);
        // Bullets Painting
        if (!ListOfBullets.Bullets.isEmpty()){
            System.out.println("List is not Empty");
//                        System.out.println("GameObjects.Bullet List is Non-Empty");
            AtomicInteger bulletCounter = new AtomicInteger(0);
            synchronized (ListOfBullets.Bullets){
                ListOfBullets.Bullets.forEach(bullet -> {
                    bulletCounter.getAndIncrement();
//                            System.out.println("bullet ID:" + bulletCounter + "Painted At:" + bullet.x_coordinate + ", " + bullet.y_coordinate);
                    bullet.bulletLabel.setBounds(bullet.x_coordinate, bullet.y_coordinate, 50, 100);
                    bullet.container.add(bullet.bulletLabel, 0);
                    bullet.container.validate();
                    bullet.container.repaint();
                    bullet.container.setVisible(true);

                });
            }
        }
//                    System.out.println("Before Bomb");
//                    if (!BombList.Bombs.isEmpty()){
//                        System.out.println("GameObjects.Bombs List is Non-Empty");
//
//                            BombList.Bombs.forEach(bomb -> {
//                                bomb.bombLabel.setBounds(bomb.x_coordinate, bomb.y_coordinate, 50, 100);
//                                bomb.container.add(bomb.bombLabel, 0);
//                                bomb.container.validate();
//                                bomb.container.repaint();
//                                bomb.container.setVisible(true);
//
//                            });
//                        }

        ////////////////////////////////////// Updating //////////////////////////////////////
        ListOfBullets.updateList();
        ////////////////////////////////////// Moving //////////////////////////////////////
        if (!ListOfBullets.Bullets.isEmpty()){
//                        System.out.println("GameObjects.Bullet List is Non-Empty");
            AtomicInteger bulletCounter = new AtomicInteger(0);
//                        System.out.println(Lists.ListOfBullets.Bullets.size());
            synchronized (ListOfBullets.Bullets){
                for (int i=0; i<ListOfBullets.Bullets.size(); i++){
                    Bullet bullet = ListOfBullets.Bullets.get(i);
                    bulletCounter.getAndIncrement();
                    System.out.println("Number of Bullets moved: " + ListOfBullets.Bullets.size());
                    bullet.bulletMover.changeX(bullet);
                    bullet.bulletMover.changeY(bullet);
//                            System.out.println("bullet ID:" + bulletCounter + "Moved to:" + bullet.getX() + ", " + bullet.getY());

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

}