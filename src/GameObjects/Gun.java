package GameObjects;

import Lists.ListOfBullets;
import Screen.GamePlayScrolling.Scroll;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;


public class Gun implements Serializable {
//public class GameObjects.Gun {
    public boolean overheated;
    public double damage;
    public int level;
    public int heatLimit;
    private int bulletType=0;
    public int firingPeriod = 200;
    private int heatIncrement = 5;
    private transient Container container;
    private transient JLabel spaceshipLabel;
    Gun(Container container, JLabel spaceshipLabel){
        System.out.println("GameObjects.Gun Constructed");
        // Default Values
        this.heatLimit = 100;
        this.overheated = false;
        this.damage = 2;
        this.level = 1;
        this.container = container;
        this.spaceshipLabel = spaceshipLabel;
    }

    public void updateGun(Powerup pw){
        if (pw.category.equals("I")){
            if (pw.type == 1){
                // Increase heat resistance
                heatLimit += 5;
            }
            else {
                level += 1;
            }
        }
        else {
            if (pw.type == 1){
                // Blue Saber
                bulletType = 1;
                // power:H, heat: H, period: M
                damage = 4;
                firingPeriod = 150;
                heatIncrement = 10;
            }
            if (pw.type == 2){
                // Bright Blue Saber
                bulletType = 2;
                // power:H, heat: H, period: L
                damage = 4;
                firingPeriod = 100;
                heatIncrement = 10;
            }
            if (pw.type == 3){
                // Cyan Saber
                bulletType = 3;
                // power:H, heat: L, period: L
                damage = 4;
                firingPeriod = 100;
                heatIncrement = 3;
            }
            if (pw.type == 4){
                // Green Saber
                bulletType = 4;
                // power:VH, heat: L, period: L
                damage = 10;
                firingPeriod = 50;
                heatIncrement = 2;
            }
            if (pw.type == 5){
                // Pink Saber
                bulletType = 5;
                // power:VH, heat: H, period: M
                damage = 10;
                firingPeriod = 150;
                heatIncrement = 10;
            }
            if (pw.type == 6){
                // Yellow Saber
                bulletType = 6;
                // power:VH, heat: M, period: L
                damage = 10;
                firingPeriod = 100;
                heatIncrement = 5;
            }
            if (pw.type == 7){
                // Double Saber
                bulletType = 7;
                // power:VVH, heat: VH, period: H
                damage = 20;
                firingPeriod = 400;
                heatIncrement = 20;
            }
        }
    }
    //        public static void singleShot(int x, int y, int damage){
        // damage is a field which should be determined by the gun.
        public void singleShot(int x, int y){
            System.out.println("Single Shot Tried");
        if (!Scroll.isOverheated){
            System.out.println("Single Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage, bulletType);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
        }

        }


//    public synchronized void longShotD(int x, int y, double damage){
        // damage is a field which should be determined by the gun.
    public synchronized void longShotD(int x, int y) {
        System.out.println("Long Shot Tried");
        //TODO: in Scroll.timerD % 200, 200 should be changed according to the bulletType.
        if (Scroll.timerD % firingPeriod == 0 && !Scroll.isOverheated) {
            Scroll.timerD = 0;
            System.out.println("Long Shot Accomplished");
            if (level == 1) {
                Bullet bullet = new Bullet(x, y, damage, bulletType);
                ListOfBullets.heat += heatIncrement;
            }
            // 1 powerup collected
            if (level == 2) {
                new Bullet(x, y, damage, bulletType);
                new Bullet(x + 100, y, damage, bulletType);
                ListOfBullets.heat += heatIncrement;
            }
            // 2 powerups collected
            if (level == 3) {
                new Bullet(x + 20, y, damage, -5, bulletType);
                new Bullet(x + 60, y, damage, bulletType);
                new Bullet(x + 110, y, damage, 5, bulletType);
                ListOfBullets.heat += heatIncrement;
            }
            // 3 powerups collected
            if (level == 4) {
                new Bullet(x + 20, y, damage, -10, bulletType);
                new Bullet(x + 35, y, damage, bulletType);
                new Bullet(x + 85, y, damage, bulletType);
                new Bullet(x + 110, y, damage, 10, bulletType);
                ListOfBullets.heat += heatIncrement;
            } else {
                if (level > 4){
                    this.damage = this.damage * 1.25;
                    new Bullet(x + 20, y, damage, -10, bulletType);
                    new Bullet(x + 35, y, damage, bulletType);
                    new Bullet(x + 85, y, damage, bulletType);
                    new Bullet(x + 110, y, damage, 10, bulletType);
                    ListOfBullets.heat += heatIncrement;
                }
            }
//            bullet.bulletLabel.setBounds(x, y, 100, 100);
//            container.validate();
//            container.repaint();
//            container.setVisible(true);
        }
    }
//    public synchronized void longShotP(int x, int y, double damage){
        // damage is a field which should be determined by the gun.
    public synchronized void longShotP(int x, int y){
        System.out.println("Long Shot Tried");
        if (Scroll.timerP % firingPeriod == 0 && !Scroll.isOverheated){
            Scroll.timerP = 0;
            System.out.println("Long Shot Accomplished");
            if (level == 1){
                Bullet bullet = new Bullet(x+10, y, damage, bulletType);
                ListOfBullets.heat += heatIncrement;
            }
            // 1 powerup collected
            if (level == 2){
                new Bullet(x, y, damage, bulletType);
                new Bullet(x + 100, y, damage, bulletType);
                ListOfBullets.heat += heatIncrement;
            }
            // 2 powerups collected
            if (level == 3){
                new Bullet(x + 20, y, damage,-5, bulletType);
                new Bullet(x + 60, y, damage, bulletType);
                new Bullet(x + 110, y, damage, 5, bulletType);
                ListOfBullets.heat += heatIncrement;
            }
            // 3 powerups collected
            if (level == 4){
                new Bullet(x + 20, y, damage,-10, bulletType);
                new Bullet(x + 35, y, damage, bulletType);
                new Bullet(x + 85, y, damage, bulletType);
                new Bullet(x + 110, y, damage, 10, bulletType);
                ListOfBullets.heat += heatIncrement;
            }
            else {
                if (level > 4){
                    this.damage = this.damage * 1.25;
                    new Bullet(x + 20, y, damage,-10, bulletType);
                    new Bullet(x + 35, y, damage, bulletType);
                    new Bullet(x + 85, y, damage, bulletType);
                    new Bullet(x + 110, y, damage, 10, bulletType);
                    ListOfBullets.heat += heatIncrement;
                }
            }
//            Bullet bullet = new Bullet(x, y, damage);
//            bullet.bulletLabel.setBounds(x, y, 100, 100);

        }

    }
    public void interruptShooting(){
        System.out.println("Shooting Interrupted");
        Scroll.timerD = 0;
        Scroll.timerP = 0;
        Scroll.isDragged = false;
        Scroll.isPressed = false;
    }

    public void bombShoot(int x, int y){
        System.out.println("Bomb Shot!");
        Bomb bomb = new Bomb(x, y);
    }

    public void downgrade() {
        level = 1;
        bulletType = 0;
        damage = 2;
        heatIncrement = 5;
        firingPeriod = 200;
    }
}

