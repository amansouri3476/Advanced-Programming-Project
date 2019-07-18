package GameObjects;

import Interfaces.hasCoordinates;

import java.io.Serializable;

public class coordinatedObject implements hasCoordinates, Serializable {
    public int x_coordinate;
    public int y_coordinate;
    public int destination_x;
    public int destination_y;

    public int getX() {
        return this.x_coordinate;
    }

    public int getY() { return this.y_coordinate; }

    public void setX(int x) {
        this.x_coordinate = x;
    }

    public void setY(int y) {
        this.y_coordinate = y;
    }

}
