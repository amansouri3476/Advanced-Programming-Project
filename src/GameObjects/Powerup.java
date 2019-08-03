package GameObjects;

import Interfaces.hasCoordinates;
import Interfaces.hasRange;
import Lists.ListOfPowerups;
import Movers.PowerupMover;

public class Powerup extends coordinatedObject implements hasRange, hasCoordinates {
    public int type;
    public String category;
    public PowerupMover powerupMover;

    Powerup(int x, int y){

        double random = Math.random();
        this.type = typeIdentifier(random);
        this.setX(x);
        this.setY(y);
        this.powerupMover = new PowerupMover();
        ListOfPowerups.Powerups.add(this);
        LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\shoot.wav", false);
    }

    private int typeIdentifier(double random) {
        if (random < 0.1){
            // Blue Saber
            type = 1;
        }
        if (random >= 0.1 && random < 0.2){
            // Bright Blue Saber
            type = 2;
        }
        if (random >= 0.2 && random < 0.3){
            // Cyan Saber
            type = 3;
        }
        if (random >= 0.3 && random < 0.4){
            // Green Saber
            type = 4;
        }
        if (random >= 0.4 && random < 0.5){
            // Pink Saber
            type = 5;
        }
        if (random >= 0.5 && random < 0.6){
            // Yellow Saber
            type = 6;
        }
        if (random >= 0.6){
            // Double Saber
            type = 7;
        }
        return type;
    }

    @Override
    public boolean isInRange(coordinatedObject object) {
        return false;
    }
}
