import javax.swing.*;
import java.awt.*;

public class Spaceship extends coordinatedObject implements hasCoordinates, hasRange {
    public Gun gun;
    Container container;
    JLabel spaceshipLabel;
    Spaceship(Container container, JLabel spaceshipLabel){
        gun = new Gun(container, spaceshipLabel);
    }

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

    public boolean isInRange(coordinatedObject object) {
        return false;
    }
}
