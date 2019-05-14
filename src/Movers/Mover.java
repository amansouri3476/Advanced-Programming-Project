package Movers;

import GameObjects.coordinatedObject;

public interface Mover {
    public void changeX(coordinatedObject object);
    public void changeY(coordinatedObject object);
}
