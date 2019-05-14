package GameObjects;

import Interfaces.hasCoordinates;

public class coordinatedObject implements hasCoordinates {
    public int x_coordinate;
    public int y_coordinate;

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

}
