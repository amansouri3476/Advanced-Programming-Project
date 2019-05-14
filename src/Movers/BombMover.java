package Movers;

import GameObjects.coordinatedObject;

public class BombMover implements Mover {
    double speedX, speedY;
    public BombMover(double speed_x, double speed_y){
        this.speedX = speed_x;
        this.speedY = speed_y;
    }
    @Override
    public void changeX(coordinatedObject object) {
        object.setX((int) (object.getX() + Math.round(speedX)));
    }

    @Override
    public void changeY(coordinatedObject object) {
        object.setY((int) (object.getY() + Math.round(speedY)));
    }
}
