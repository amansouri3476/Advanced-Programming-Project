package GameObjects;

import Interfaces.hasCoordinates;
import Interfaces.hasRange;
import Movers.FireMover;

import javax.swing.*;

public abstract class EnemyFire extends coordinatedObject implements hasRange, hasCoordinates {
    double damage;
    public FireMover fireMover;
    public JLabel fireLabel;

    public abstract boolean isNotScreen();
}
