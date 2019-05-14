package GameObjects;

import Others.GameEventHandler;
import Screen.ScreenPainter;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("Duplicates")
public class Gun {
//public class GameObjects.Gun {
    public boolean overheated;
    public static int damage;
    public int level;
    Container container;
    JLabel spaceshipLabel;
    Gun(Container container, JLabel spaceshipLabel){
        System.out.println("GameObjects.Gun Constructed");
        this.overheated = false;
        this.damage = 2;
        this.level = 1;
        this.container = container;
        this.spaceshipLabel = spaceshipLabel;
    }

    public static void singleShot(int x, int y, Container container, int damage){
        System.out.println("Single Shot Tried");
        System.out.println("Single Shot Accomplished");
        Bullet bullet = new Bullet(x, y, damage, container);
        container.setLayout(null);
        container.add(bullet.bulletLabel, 0);
        bullet.bulletLabel.setBounds(x, y, 100, 100);
        container.validate();
        container.repaint();
        container.setVisible(true);
        }

    public synchronized static void longShotD(int x, int y, Container container, int damage){
        System.out.println("Long Shot Tried");
        if (ScreenPainter.timerD % 200 == 0){
            ScreenPainter.timerD = 0;
            System.out.println("Long Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage, container);
            container.setLayout(null);
            container.add(bullet.bulletLabel, 0);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
            container.validate();
            container.repaint();
            container.setVisible(true);
        }
    }
    public synchronized static void longShotP(int x, int y, Container container, int damage){
        System.out.println("Long Shot Tried");
        if (ScreenPainter.timerP % 400 == 0){
            ScreenPainter.timerP = 0;
            System.out.println("Long Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage, container);
            container.setLayout(null);
            container.add(bullet.bulletLabel, 0);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
            container.validate();
            container.repaint();
            container.setVisible(true);
        }

    }
    public static void interruptShooting(){
        System.out.println("Shooting Interrupted");
        ScreenPainter.timerD = 0;
        ScreenPainter.timerP = 0;
        GameEventHandler.isDragged = false;
        GameEventHandler.isPressed = false;
    }

    public static void bombShoot(int x, int y, Container container){
        System.out.println("Bomb Shot!");
        Bomb bomb = new Bomb(x, y, container);
        container.setLayout(null);
        container.add(bomb.bombLabel, 0);
        bomb.bombLabel.setBounds(x, y, 100, 100);
        container.validate();
        container.repaint();
        container.setVisible(true);
    }

}

