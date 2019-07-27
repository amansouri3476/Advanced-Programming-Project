package GameObjects;

import Screen.GamePlayScrolling.Scroll;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

@SuppressWarnings("Duplicates")
public class Gun implements Serializable {
//public class GameObjects.Gun {
    public boolean overheated;
    public static int damage;
    public int level;
    transient Container container;
    transient JLabel spaceshipLabel;
    Gun(Container container, JLabel spaceshipLabel){
        System.out.println("GameObjects.Gun Constructed");
        this.overheated = false;
        this.damage = 2;
        this.level = 1;
        this.container = container;
        this.spaceshipLabel = spaceshipLabel;
    }

//    public static void singleShot(int x, int y, Container container, int damage){
//        System.out.println("Single Shot Tried");
//        System.out.println("Single Shot Accomplished");
//        Bullet bullet = new Bullet(x, y, damage, container);
//        container.setLayout(null);
//        container.add(bullet.bulletLabel, 0);
//        bullet.bulletLabel.setBounds(x, y, 100, 100);
//        container.validate();
//        container.repaint();
//        container.setVisible(true);
//        }
        public static void singleShot(int x, int y, int damage){
        System.out.println("Single Shot Tried");
        if (!Scroll.isOverheated){
            System.out.println("Single Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
        }

        }

//    public synchronized static void longShotD(int x, int y, Container container, int damage){
//        System.out.println("Long Shot Tried");
//        if (Scroll.timerD % 200 == 0){
//            Scroll.timerD = 0;
//            System.out.println("Long Shot Accomplished");
//            Bullet bullet = new Bullet(x, y, damage, container);
//            container.setLayout(null);
//            container.add(bullet.bulletLabel, 0);
//            bullet.bulletLabel.setBounds(x, y, 100, 100);
//            container.validate();
//            container.repaint();
//            container.setVisible(true);
//        }
//    }
    public synchronized static void longShotD(int x, int y, int damage){
        System.out.println("Long Shot Tried");
        if (Scroll.timerD % 200 == 0 && !Scroll.isOverheated){
            Scroll.timerD = 0;
            System.out.println("Long Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage);
//            bullet.bulletLabel.setBounds(x, y, 100, 100);
//            container.validate();
//            container.repaint();
//            container.setVisible(true);
        }
    }
//    public synchronized static void longShotP(int x, int y, Container container, int damage){
//        System.out.println("Long Shot Tried");
//        if (Scroll.timerP % 400 == 0){
//            Scroll.timerP = 0;
//            System.out.println("Long Shot Accomplished");
//            Bullet bullet = new Bullet(x, y, damage, container);
//            container.setLayout(null);
//            container.add(bullet.bulletLabel, 0);
//            bullet.bulletLabel.setBounds(x, y, 100, 100);
//            container.validate();
//            container.repaint();
//            container.setVisible(true);
//        }
//
//    }
    public synchronized static void longShotP(int x, int y, int damage){
        System.out.println("Long Shot Tried");
        if (Scroll.timerP % 200 == 0 && !Scroll.isOverheated){
            Scroll.timerP = 0;
            System.out.println("Long Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage);
            bullet.bulletLabel.setBounds(x, y, 100, 100);

        }

    }
    public static void interruptShooting(){
        System.out.println("Shooting Interrupted");
        Scroll.timerD = 0;
        Scroll.timerP = 0;
        Scroll.isDragged = false;
        Scroll.isPressed = false;
    }

    public static void bombShoot(int x, int y){
        System.out.println("Bomb Shot!");
        Bomb bomb = new Bomb(x, y);
    }

}

