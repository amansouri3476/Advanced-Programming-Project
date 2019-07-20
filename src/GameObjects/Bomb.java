package GameObjects;

import Interfaces.hasCoordinates;
import Lists.BombList;
import Lists.ListOfUsers;
import Movers.BombMover;

import java.io.Serializable;

public class Bomb extends coordinatedObject implements hasCoordinates {

    public String shooter;
    public BombMover bombMover;
    public int speed = 2;
    public double directionAngle;

    public Bomb(int x,int y){
        this.setX(x);
        this.setY(y);
        this.shooter = ListOfUsers.selectedUser;
//        destination_x = GameEventHandler.gameWidth / 2;
//        destination_y = GameEventHandler.gameHeight / 2;
        destination_x = 750;
        destination_y = 400;
        int delta_x = destination_x - x_coordinate;
        int delta_y = destination_y - y_coordinate;
        BombList.Bombs.add(this);
        this.bombMover = new BombMover(this);
        this.directionAngle = Math.atan2(delta_y, delta_x);

    }
    public boolean isNotInScreen(){
        if (this.getX() < -20 || this.getX() > 1550 || this.getY() < -50 || this.getY() > 800){
            return true;
        }
        else return false;
    }

}
