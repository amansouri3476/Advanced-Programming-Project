package Movers;

import GameObjects.coordinatedObject;
import Screen.GamePlayScrolling.Scroll;

public class SpaceshipMover implements Mover{
    @Override
    public void changeX(coordinatedObject object) {
        object.setX(object.getX() + Scroll.mouseCoordinateChangeX);
    }

    @Override
    public void changeY(coordinatedObject object) {
        object.setY(object.getY() + Scroll.mouseCoordinateChangeY);
    }
}
