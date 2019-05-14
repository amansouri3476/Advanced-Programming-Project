package Interfaces;

import GameObjects.coordinatedObject;

public interface hasRange extends hasCoordinates {
    public boolean isInRange(coordinatedObject object);
}
