package Movers;

import GameObjects.Bullet;
import GameObjects.coordinatedObject;

public class BulletMover implements Mover {

    private double hiddenX;
    private double hiddenY;
    private double angle;

    public BulletMover(){
        angle = 0;
    }
    public BulletMover(Bullet bullet, double angle){
        hiddenX = bullet.x_coordinate;
        hiddenY = bullet.y_coordinate;
        this.angle = angle;
    }

    public void changeX(coordinatedObject object) {
        if (hiddenX == 0){
            hiddenX = object.x_coordinate;
        }
        hiddenX += Math.sin(angle * Math.PI /180) * 10;
        object.setX((int)hiddenX);
    }

    public void changeY(coordinatedObject object) {
        if (hiddenY == 0){
            hiddenY = object.y_coordinate;
        }
        hiddenY -= Math.cos(angle * Math.PI /180) * 10;
        object.setY((int)hiddenY);
    }
}
