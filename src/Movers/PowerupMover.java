package Movers;

import GameObjects.coordinatedObject;

public class PowerupMover implements Mover{
    public static double velocityX=2;
    public static double velocityY=1;

    @Override
    public void changeX(coordinatedObject object) {
        if (object.getX() < 1450 && object.getX() > 50){
            object.setX(object.getX() + (int) velocityX);
        }
        else {
//            velocityX = (int) Math.round(-velocityX * 0.7);
            if (Math.abs(velocityX) != 0){
                velocityX = -(Math.abs(velocityX) * 0.7);
            }
            if (Math.abs(velocityX) <= 0.1){
                velocityX = 0;
            }
            object.setX(object.getX() + (int) velocityX);
        }
    }

    @Override
    public void changeY(coordinatedObject object) {
        if (object.getY() < 740){
            if (Math.abs(velocityY) != 0) {
                velocityY += 0.1;
            }
            if (Math.abs(velocityY) == 0) {
                velocityY = 0;
            }
            object.setY(object.getY() + (int) velocityY);
        }
        else {
//            velocityY = (int) Math.round(-velocityY * 0.7);
            if (Math.abs(velocityY) != 0){
                velocityY = -(Math.abs(velocityY) * 0.7);
            }
            if (Math.abs(velocityY) == 0){
                velocityY = 0;
            }
//            velocityX = (int) Math.round(velocityX * 0.5);
            if (Math.abs(velocityX) != 0){
                velocityX = velocityX - 1;
            }
            if (Math.abs(velocityX) == 0){
                velocityX = 0;
            }
            object.setY(object.getY() + (int) velocityY);
        }
    }
}
