package Movers;

import GameObjects.coordinatedObject;

public class EnemyMover implements Mover {

    public int phase;
    public int velocityX;
    public int velocityY;
    public boolean right = true;
    public boolean down = true;
    private String movement;
    public int stop_y;
    public boolean destinationActive_x=false;
    public boolean destinationActive_y=false;

    public EnemyMover(int phase, String movement, int y_dest){
        this.phase = phase;
        this.movement = movement;
        this.stop_y = y_dest;
    }
    @Override
    public void changeX(coordinatedObject object) {
        ////////////////// when there is a destination for the object //////////////////
        if (destinationActive_x){
            if (Math.sqrt(Math.pow(Math.abs(object.x_coordinate-object.destination_x), 2) + Math.pow(Math.abs(object.y_coordinate-object.destination_y), 2)) < 3 ){
                object.setX(object.destination_x);
                destinationActive_x = false;
            }
            else {
                int delta_x = (int) Math.signum(object.destination_x - object.x_coordinate);
                int delta_y = (int) Math.signum(object.destination_y - object.y_coordinate);
                double phi = Math.atan2(delta_y, delta_x);
                object.setX(object.x_coordinate + (int) (3 * Math.cos(phi)));
            }
        }
        ////////////////// Normal movement of the object //////////////////
        else {
            if (movement.equals("Fixed")){
                fixed_X(object);

            }
            if (movement.equals("zigzag")){
                zigzag_X(object);
            }
        }
    }

    private void fixed_X(coordinatedObject object) {
        if (phase == 1){
            velocityX = 0;
        }
        if (phase == 2){
            object.setX(object.getX());
        }
    }

    private void zigzag_X(coordinatedObject object) {
        if (phase == 1){
            velocityX = 0;
        }
        if (phase == 2){
            velocityX = 2;
            if (right && down){
                object.setX(object.x_coordinate + velocityX);
            }
            if (right && !down){
                object.setX(object.x_coordinate + velocityX);
            }
            if (!right && down){
                object.setX(object.x_coordinate - velocityX);
            }
            if (!right && !down){
                object.setX(object.x_coordinate - velocityX);
            }
            if (object.x_coordinate > 1500){
                right = false;
            }
            if (object.x_coordinate < 0){
                right = true;
            }
        }
    }

    @Override
    public void changeY(coordinatedObject object) {
        ////////////////// when there is a destination for the object //////////////////
        if (destinationActive_y){
            if (Math.sqrt(Math.pow(Math.abs(object.x_coordinate-object.destination_x), 2) + Math.pow(Math.abs(object.y_coordinate-object.destination_y), 2)) < 3 ){
                object.setY(object.destination_y);
                destinationActive_y = false;
            }
            else {
                int delta_x = (int) Math.signum(object.destination_x - object.x_coordinate);
                int delta_y = (int) Math.signum(object.destination_y - object.y_coordinate);
                double phi = Math.atan2(delta_y, delta_x);
                object.setY(object.y_coordinate + (int) (3 * Math.sin(phi)));
            }
        }
        ////////////////// Normal movement of the object //////////////////
        else {
            if (movement.equals("Fixed")){
                fixed_Y(object);

            }
            if (movement.equals("zigzag")){
                zigzag_Y(object);
            }
        }
    }

    private void fixed_Y(coordinatedObject object) {
        if (phase == 1){
            velocityY = 5;

            if (object.y_coordinate < stop_y){
                object.setY(object.y_coordinate + velocityY);
            }
            else phase = 2;
        }
        if (phase == 2){
            object.setY(object.getY());
        }
    }

    private void zigzag_Y(coordinatedObject object) {
        if (phase == 1){
            velocityY = 5;
            if (object.y_coordinate < 400){
                object.setY(object.y_coordinate + velocityY);
            }
            else phase = 2;
        }
        if (phase == 2){
            velocityY = 2;
            if (right && down){
                object.setY(object.y_coordinate + velocityY);
            }
            if (right && !down){
                object.setY(object.y_coordinate - velocityY);
            }
            if (!right && down){
                object.setY(object.y_coordinate + velocityY);
            }
            if (!right && !down){
                object.setY(object.y_coordinate - velocityY);
            }
            if (object.y_coordinate > 300){
                down = false;
            }
            if (object.y_coordinate < 200){
                down = true;
            }
        }
    }
}
