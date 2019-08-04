package Multiplayer;

import GameObjects.Bomb;
import GameObjects.Bullet;
import GameObjects.Powerup;
import Lists.BombList;
import Lists.ListOfUsers;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Gun implements Serializable {
    //TODO: Powerups are not collected by clients.
    //public class GameObjects.Gun {
    public int heatLimit;
    public int bulletType=0;
    public int firingPeriod = 200;
    public int heatIncrement = 5;
    public boolean overheated;
    public double damage;
    public int level;
    private transient Container container;
    private transient JLabel spaceshipLabel;
    public Gun(Container container, JLabel spaceshipLabel){
        System.out.println("GameObjects.Gun Constructed");
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

    public void downgrade() {
        level = 1;
        bulletType = 0;
        damage = 2;
        heatIncrement = 5;
        firingPeriod = 200;
    }

    public void singleShot(int x, int y){
        System.out.println("Single Shot Tried");
        if (!ClientScroll.isOverheated){
            System.out.println("Single Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage, bulletType);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
            ListOfClientBullets.clientBullets.add(bullet);

        }

    }


    public synchronized void longShotD(int x, int y){
        System.out.println("Long Shot Tried");
        if (ClientScroll.timerD % firingPeriod == 0 && !ClientScroll.isOverheated){
            ClientScroll.timerD = 0;
            System.out.println("Long Shot Accomplished");
            // No powerups collected
            if (level == 1){
                Bullet bullet = new Bullet(x, y, damage, bulletType);
                ListOfClientBullets.clientBullets.add(bullet);
                ListOfClientBullets.heat += heatIncrement;
            }
            // 1 powerup collected
            if (level == 2){
                Bullet bullet = new Bullet(x, y, damage, bulletType);
                Bullet bullet_2 = new Bullet(x + 100, y, damage, bulletType);
                ListOfClientBullets.clientBullets.add(bullet);
                ListOfClientBullets.clientBullets.add(bullet_2);
                ListOfClientBullets.heat += heatIncrement;
            }
            // 2 powerups collected
            if (level == 3){
                Bullet bullet = new Bullet(x + 20, y, damage, -5, bulletType);
                Bullet bullet_2 = new Bullet(x + 60, y, damage, bulletType);
                Bullet bullet_3 = new Bullet(x + 110, y, damage, 5, bulletType);
                ListOfClientBullets.clientBullets.add(bullet);
                ListOfClientBullets.clientBullets.add(bullet_2);
                ListOfClientBullets.clientBullets.add(bullet_3);
                ListOfClientBullets.heat += heatIncrement;
            }
            // 3 powerups collected
            if (level == 4){
                Bullet bullet = new Bullet(x + 20, y, damage, -10, bulletType);
                Bullet bullet_2 = new Bullet(x + 35, y, damage, bulletType);
                Bullet bullet_3 = new Bullet(x + 85, y, damage, bulletType);
                Bullet bullet_4 = new Bullet(x + 110, y, damage, 10, bulletType);
                ListOfClientBullets.clientBullets.add(bullet);
                ListOfClientBullets.clientBullets.add(bullet_2);
                ListOfClientBullets.clientBullets.add(bullet_3);
                ListOfClientBullets.clientBullets.add(bullet_4);
                ListOfClientBullets.heat += heatIncrement;
            }                   
            else {
                if (level > 4){
                    this.damage = this.damage * 1.25;
                    Bullet bullet = new Bullet(x + 20, y, damage, -10, bulletType);
                    Bullet bullet_2 = new Bullet(x + 35, y, damage, bulletType);
                    Bullet bullet_3 = new Bullet(x + 85, y, damage, bulletType);
                    Bullet bullet_4 = new Bullet(x + 110, y, damage, 10, bulletType);
                    ListOfClientBullets.clientBullets.add(bullet);
                    ListOfClientBullets.clientBullets.add(bullet_2);
                    ListOfClientBullets.clientBullets.add(bullet_3);
                    ListOfClientBullets.clientBullets.add(bullet_4);
                    ListOfClientBullets.heat += heatIncrement;
                }
            }

        }
    }

    public synchronized void longShotP(int x, int y){
        System.out.println("Long Shot Tried");
        if (ClientScroll.timerP % firingPeriod == 0 && !ClientScroll.isOverheated){
            ClientScroll.timerP = 0;
            System.out.println("Long Shot Accomplished");
            Gun g = ListOfUsers.getPlayerObjByUsername(ListOfUsers.selectedUser).spaceship.clientGun;
            System.out.println("damage: " + g.damage + ", firing period: "+ g.firingPeriod +
                                                            ", level: " + g.level + ", heatLimit: " + g.heatLimit + ", bulletType: "
                                                            + g.bulletType + ", heatIncrement: " + g.heatIncrement);
            if (level == 1){
                Bullet bullet = new Bullet(x, y, damage, bulletType);
                ListOfClientBullets.clientBullets.add(bullet);
                ListOfClientBullets.heat += heatIncrement;
            }
            // 1 powerup collected
            if (level == 2){
                Bullet bullet = new Bullet(x, y, damage, bulletType);
                Bullet bullet_2 = new Bullet(x + 100, y, damage, bulletType);
                ListOfClientBullets.clientBullets.add(bullet);
                ListOfClientBullets.clientBullets.add(bullet_2);
                ListOfClientBullets.heat += heatIncrement;
            }
            // 2 powerups collected
            if (level == 3){
                Bullet bullet = new Bullet(x + 20, y, damage, -5, bulletType);
                Bullet bullet_2 = new Bullet(x + 60, y, damage, bulletType);
                Bullet bullet_3 = new Bullet(x + 110, y, damage, 5, bulletType);
                ListOfClientBullets.clientBullets.add(bullet);
                ListOfClientBullets.clientBullets.add(bullet_2);
                ListOfClientBullets.clientBullets.add(bullet_3);
                ListOfClientBullets.heat += heatIncrement;
            }
            // 3 powerups collected
            if (level == 4){
                Bullet bullet = new Bullet(x + 20, y, damage, -10, bulletType);
                Bullet bullet_2 = new Bullet(x + 35, y, damage, bulletType);
                Bullet bullet_3 = new Bullet(x + 85, y, damage, bulletType);
                Bullet bullet_4 = new Bullet(x + 110, y, damage, 10, bulletType);
                ListOfClientBullets.clientBullets.add(bullet);
                ListOfClientBullets.clientBullets.add(bullet_2);
                ListOfClientBullets.clientBullets.add(bullet_3);
                ListOfClientBullets.clientBullets.add(bullet_4);
                ListOfClientBullets.heat += heatIncrement;
            }
            else {
                if (level > 4){
                    this.damage = this.damage * 1.25;
                    Bullet bullet = new Bullet(x + 20, y, damage, -10, bulletType);
                    Bullet bullet_2 = new Bullet(x + 35, y, damage, bulletType);
                    Bullet bullet_3 = new Bullet(x + 85, y, damage, bulletType);
                    Bullet bullet_4 = new Bullet(x + 110, y, damage, 10, bulletType);
                    ListOfClientBullets.clientBullets.add(bullet);
                    ListOfClientBullets.clientBullets.add(bullet_2);
                    ListOfClientBullets.clientBullets.add(bullet_3);
                    ListOfClientBullets.clientBullets.add(bullet_4);
                    ListOfClientBullets.heat += heatIncrement;
                }
            }
        }

    }
    public void interruptShooting(){
        System.out.println("Shooting Interrupted");
        ClientScroll.timerD = 0;
        ClientScroll.timerP = 0;
        ClientScroll.isDragged = false;
        ClientScroll.isPressed = false;
    }

    public void bombShoot(int x, int y){
        System.out.println("Bomb Shot!");
        Bomb bomb = new Bomb(x, y);
        BombList.clientBombs.add(bomb);
    }

}