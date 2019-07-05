package GameObjects;

import Interfaces.hasCoordinates;
import Interfaces.hasRange;
import Lists.ListOfGiants;
import Movers.GiantMover;

public class Giant extends coordinatedObject implements hasCoordinates, hasRange {
    public String type;
    public GiantMover giantMover;
    public int health;
    int rangeX=680;
    int rangeY=442;
    public int explosionTimer=0;
    public boolean isDead=false;

    public Giant(String type, int health, int x, int y) {
        this.type = type;
        this.health = health;
        this.x_coordinate = x;
        this.y_coordinate = y;
        this.giantMover = new GiantMover();
        ListOfGiants.Giants.add(this);
    }

    @Override
    public boolean isInRange(coordinatedObject object) {
        return false;
    }

    public boolean checkCollision(coordinatedObject object){
        if (type.equals("starDestroyer")){
            if (this.getX() < object.getX() && object.getX() < this.getX() + rangeX && this.getY() < object.getY() && object.getY() < this.getY() + rangeY){
                if (rangeY - (object.getY() - this.getY()) > (int)((object.getX() - this.getX())*Math.tan(0.5))){
                    this.health -= 5;
                    new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Metal_Bang.wav", false);
                    if (this.health <= 0) {
                        isDead = true;
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean checkCollisionSpaceship(Spaceship object){
        if (type.equals("starDestroyer")){
            if (this.getX() < object.getX() && object.getX() < this.getX() + rangeX && this.getY() < object.getY() && object.getY() < this.getY() + rangeY){
                if (rangeY - (object.getY() - this.getY()) > (int)((object.getX() - this.getX())*Math.tan(0.5))){
                    return true;
                }
                return false;
            }
            return false;
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
        if (random < 0.6) {
            new Powerup(this.x_coordinate, this.y_coordinate);
        }
    }
}
