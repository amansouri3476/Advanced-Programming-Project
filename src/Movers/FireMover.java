package Movers;

import GameObjects.coordinatedObject;

public class FireMover implements Mover{

    public void changeX(coordinatedObject object) {

    }

    public void changeY(coordinatedObject object) {
        object.setY(object.getY() + 3);
    }
}
