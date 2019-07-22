package GameObjects;

import Interfaces.hasCoordinates;
import Interfaces.hasRange;
import Lists.ListOfEnemies;
import Movers.EnemyMover;

import javax.swing.*;

public class Enemy extends coordinatedObject implements hasRange, hasCoordinates {
    public String type;
    public EnemyMover enemyMover;
    public int health=10;
    public transient JLabel enemyLabel;
    int rangeX=40;
    int rangeY=40;
    public int explosionTimer=0;
    public boolean isDead=false;
    public int inGroupX;
    public int inGroupY;
    public boolean isInGroup=false;

    public Enemy(int x, int y, String type, String movement, int positionXGroup, int positionYGroup){
        this.setX(x);
        this.setY(y);
        isInGroup = true;
        this.inGroupX = positionXGroup;
        this.inGroupY = positionYGroup;
        this.type = type;
        this.enemyMover = new EnemyMover(this, 1, movement, y + 600);
        ListOfEnemies.Enemies.add(this);
        this.enemyLabel = new JLabel(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\TIE_small.png"));

    }
    public Enemy(int x, int y, String type, String movement){
        this.setX(x);
        this.setY(y);
        this.type = type;
        this.enemyMover = new EnemyMover(this, 1, movement, y + 600);
        ListOfEnemies.Enemies.add(this);
        this.enemyLabel = new JLabel(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\TIE_small.png"));

    }
    @Override
    public boolean isInRange(coordinatedObject object) {
        return false;
    }


    public boolean checkCollision(coordinatedObject object){

        if (this.getX() - rangeX < object.getX() && object.getX() < this.getX() + rangeX && this.getY() - rangeY < object.getY() && object.getY() < this.getY() + rangeY){
            this.health -= 5;
            new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Metal_Bang.wav", false);
            if (this.health <= 0) {
                isDead = true;
            }
            return true;
        }
        return false;
    }
    public boolean checkCollisionSpaceship(Spaceship object){
        if (this.getX() < object.getX() + object.rangeX && object.getX() < this.getX() && this.getY() < object.getY() + object.rangeY && object.getY() < this.getY()){
            return true;
        }
        return false;
    }

    public void shoot(int x, int y) {
        double random = Math.random();
        if (random < 0.0001){
            Fire_I fire_i = new Fire_I(x, y);
        }
    }

    public void death() {
        LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Depth_Charge_Short.wav", false);
        double random = Math.random();
        if (random < 0.5) {
            new Powerup(this.x_coordinate, this.y_coordinate);
        }
    }
}
