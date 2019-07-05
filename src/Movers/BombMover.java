package Movers;

import GameObjects.Bomb;
import GameObjects.coordinatedObject;

public class BombMover implements Mover {
    double speedX, speedY;
    public boolean reached_x;
    public boolean reached_y;
    public double hiddenX;
    public double hiddenY;
    public BombMover(double speed_x, double speed_y){
        this.speedX = speed_x;
        this.speedY = speed_y;
    }
    public BombMover(Bomb bomb){
        hiddenX = bomb.x_coordinate;
        hiddenY = bomb.y_coordinate;
    }
    @Override
    public void changeX(coordinatedObject object) {

        if (Math.sqrt(Math.pow(Math.abs(object.x_coordinate-object.destination_x), 2) + Math.pow(Math.abs(object.y_coordinate-object.destination_y), 2)) < 5 ){
            object.setX(object.destination_x);
            reached_x = true;
        }
        else {
            int delta_x = (object.destination_x - object.x_coordinate);
            int delta_y = (object.destination_y - object.y_coordinate);
            double phi = Math.atan2(delta_y, delta_x);
            hiddenX += (4 * Math.cos(phi));
            object.setX((int) hiddenX);
        }
    }

    @Override
    public void changeY(coordinatedObject object) {
        if (Math.sqrt(Math.pow(Math.abs(object.x_coordinate-object.destination_x), 2) + Math.pow(Math.abs(object.y_coordinate-object.destination_y), 2)) < 5 ){
            object.setY(object.destination_y);
            reached_y = true;
        }
        else {
            int delta_x = (object.destination_x - object.x_coordinate);
            int delta_y = (object.destination_y - object.y_coordinate);
            double phi = Math.atan2(delta_y, delta_x);
            hiddenY += (4 * Math.sin(phi));
            object.setY((int) hiddenY);
        }
    }
}
