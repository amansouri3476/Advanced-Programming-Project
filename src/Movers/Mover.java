package Movers;

import GameObjects.coordinatedObject;

import java.io.Serializable;

public interface Mover extends Serializable {
    public void changeX(coordinatedObject object);
    public void changeY(coordinatedObject object);
}
