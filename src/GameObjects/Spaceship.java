package GameObjects;

import Interfaces.hasCoordinates;
import Interfaces.hasRange;
import Movers.SpaceshipMover;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Spaceship extends coordinatedObject implements hasCoordinates, hasRange {
    public static Gun gun;
    public Container container;
    public static JLabel spaceshipLabel;
    public static BufferedImage spaceshipImage;
    public static SpaceshipMover spaceshipMover;
    public boolean isExploded=false;
    public int explosionTimer=0;
    public int explosionX;
    public int explosionY;
    int rangeX=85;
    int rangeY=75;
    int rangeXPU =100;
    int rangeYPU =40;


    public Spaceship(Container container, JLabel spaceshipLabel){
        System.out.println("GameObjects.Spaceship Constructed");
        Spaceship.gun = new Gun(container, spaceshipLabel);
        Spaceship.spaceshipMover = new SpaceshipMover();
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

    public boolean checkCollisionClientBullet(Bullet bullet) {
        if (this.getX() < bullet.getX() && bullet.getX() < this.getX() + rangeX && this.getY() < bullet.getY() && bullet.getY() < this.getY() + rangeY){
            this.isExploded = true;
            new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Sonic_Boom.wav", false);
            return true;
        }
        return false;
    }

    public boolean checkCollisionPowerup(Powerup powerup) {
//        if (this.getX() + 50 < powerup.getX() + rangeXPU && powerup.getX() < this.getX() + 50 && this.getY() + 30 > powerup.getY() && powerup.getY() + rangeYPU < this.getY() + 30){
        if (this.getX() < powerup.getX() + 50 && powerup.getX() + 50 < this.getX() + rangeX && this.getY() + rangeY > powerup.getY() && powerup.getY() > this.getY()){
//            new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Sonic_Boom.wav", false);
            return true;
        }
        return false;
    }
    public int getRangeX(){
        return rangeX;
    }
    public int getRangeY(){
        return rangeY;
    }
}
