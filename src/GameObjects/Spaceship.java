package GameObjects;

import Interfaces.hasCoordinates;
import Interfaces.hasRange;

import javax.swing.*;
import java.awt.*;

public class Spaceship extends coordinatedObject implements hasCoordinates, hasRange {
    public static Gun gun;
    public Container container;
    public static JLabel spaceshipLabel;
    public Spaceship(Container container, JLabel spaceshipLabel){
        System.out.println("GameObjects.Spaceship Constructed");
        Spaceship.gun = new Gun(container, spaceshipLabel);
        this.container = container;
        this.spaceshipLabel = spaceshipLabel;
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

    public Gun getGun(){
        return this.gun;
    }

    public boolean isInRange(coordinatedObject object) {
        return false;
    }
}
