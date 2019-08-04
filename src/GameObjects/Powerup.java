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

        System.out.println(">>>>>>>> Powerup");
        double random = Math.random();
        // If the powerup is a "shooting power powerup"
        if (random < 0.5){
            category = "I";
            double random_1 = Math.random();
            this.type = typeIdentifierI(random_1);
            System.out.println(">>>>>>>>" + random + "\t" + category + "\t" + random_1 + "\t" + type);
        }
        // If the powerup is a "type powerup"
        else {
            category = "II";
            double random_1 = Math.random();
            this.type = typeIdentifierII(random_1);
            System.out.println(">>>>>>>>" + random + "\t" + category + "\t" + random_1 + "\t" + type);
        }

        this.setX(x);
        this.setY(y);
        this.powerupMover = new PowerupMover();
        ListOfPowerups.Powerups.add(this);
        LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\shoot.wav", false);
    }

    private int typeIdentifierI(double random) {
        if (random < 0.5){
            // overheat improver
            type = 1;
        }
        else {
            // Burst
            type = 2;
        }
        return type;
    }

    private int typeIdentifierII(double random) {
        if (random < 0.2){
            // Blue Saber
            type = 1;
        }
        if (random >= 0.2 && random < 0.4){
            // Bright Blue Saber
            type = 2;
        }
        if (random >= 0.4 && random < 0.6){
            // Cyan Saber
            type = 3;
        }
        if (random >= 0.6 && random < 0.7){
            // Green Saber
            type = 4;
        }
        if (random >= 0.7 && random < 0.8){
            // Pink Saber
            type = 5;
        }
        if (random >= 0.8 && random < 0.9){
            // Yellow Saber
            type = 6;
        }
        if (random >= 0.9){
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
