package GameObjects;

import Interfaces.hasCoordinates;
import Interfaces.hasRange;
import Movers.SpaceshipMover;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Spaceship extends coordinatedObject implements hasCoordinates, hasRange, Serializable {
    public static Gun gun;
    public transient Container container;
    public transient static JLabel spaceshipLabel;
    public transient static BufferedImage spaceshipImage;
//    public static SpaceshipMover spaceshipMover;
    public boolean isExploded=false;
    public int explosionTimer=0;
    public int explosionTimerLimit=2000;
    public int explosionX;
    public int explosionY;
    int rangeX=120;
    int rangeY=90;
    int rangeXPU =100;
    int rangeYPU =40;


    public Spaceship(Container container, JLabel spaceshipLabel){
        System.out.println("GameObjects.Spaceship Constructed");
        Spaceship.gun = new Gun(container, spaceshipLabel);
//        Spaceship.spaceshipMover = new SpaceshipMover();
        this.container = container;
        this.spaceshipLabel = spaceshipLabel;
        this.x_coordinate = 750;
        this.y_coordinate = 250;

//        this.spaceshipImage = spaceshipIm
    }

    public int getX() {
        return this.x_coordinate;
    }

    public int getY() {
        return this.y_coordinate;
    }

    public void setX(int x) {
        this.x_coordinate = x;
    }

    public void setY(int y) {
        this.y_coordinate = y;
    }

    public Gun getGun(){
        return this.gun;
    }

    public boolean isInRange(coordinatedObject object) {
        return false;
    }

    public boolean checkCollision(EnemyFire enemyFire) {
        if (this.getX() < enemyFire.getX() && enemyFire.getX() < this.getX() + rangeX && this.getY() < enemyFire.getY() && enemyFire.getY() < this.getY() + rangeY){
            this.isExploded = true;
            new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Sonic_Boom.wav", false);
            return true;
        }
        return false;
    }

    public static boolean checkCollisionMultiplayer(EnemyFire enemyFire, Spaceship spaceship) {
        int rangeX=120;
        int rangeY=90;

        if (spaceship.getX() < enemyFire.getX() && enemyFire.getX() < spaceship.getX() + rangeX && spaceship.getY() < enemyFire.getY() && enemyFire.getY() < spaceship.getY() + rangeY){
            spaceship.isExploded = true;
            new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Sonic_Boom.wav", false);
            return true;
        }
        return false;
    }

    public boolean checkCollisionPowerup(Powerup powerup) {
        if (this.getX() < powerup.getX() && powerup.getX() < this.getX() + rangeX && this.getY() + rangeY > powerup.getY() && powerup.getY() > this.getY()){
//            new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Sonic_Boom.wav", false);
            return true;
        }
        return false;
    }

    public static boolean checkCollisionClientBullets(Bullet bullet, Spaceship spaceship) {
        int rangeX=120;
        int rangeY=90;
        if (spaceship.getX() < bullet.getX() && bullet.getX() < spaceship.getX() + rangeX && spaceship.getY() < bullet.getY() + 80 && bullet.getY() + 80 < spaceship.getY() + rangeY){
            new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Sonic_Boom.wav", false);
            return true;
        }
        return false;
    }
}
