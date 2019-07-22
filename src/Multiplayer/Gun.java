package Multiplayer;

import GameObjects.Bomb;
import GameObjects.Bullet;
import Lists.BombList;
import Lists.ListOfBullets;

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

    public static void singleShot(int x, int y, int damage){
        System.out.println("Single Shot Tried");
        if (!ClientScroll.isOverheated){
            System.out.println("Single Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
            ListOfClientBullets.clientBullets.add(bullet);

        }

    }


    public synchronized static void longShotD(int x, int y, int damage){
        System.out.println("Long Shot Tried");
        if (ClientScroll.timerD % 200 == 0 && !ClientScroll.isOverheated){
            ClientScroll.timerD = 0;
            System.out.println("Long Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage);
            ListOfClientBullets.clientBullets.add(bullet);
            ListOfClientBullets.heat += 5;

        }
    }

    public synchronized static void longShotP(int x, int y, int damage){
        System.out.println("Long Shot Tried");
        if (ClientScroll.timerP % 200 == 0 && !ClientScroll.isOverheated){
            ClientScroll.timerP = 0;
            System.out.println("Long Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
            ListOfClientBullets.clientBullets.add(bullet);
            ListOfClientBullets.heat += 5;


        }

    }
    public static void interruptShooting(){
        System.out.println("Shooting Interrupted");
        ClientScroll.timerD = 0;
        ClientScroll.timerP = 0;
        ClientScroll.isDragged = false;
        ClientScroll.isPressed = false;
    }

    public static void bombShoot(int x, int y){
        System.out.println("Bomb Shot!");
        Bomb bomb = new Bomb(x, y);
        BombList.clientBombs.add(bomb);
    }

}
