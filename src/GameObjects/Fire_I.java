package GameObjects;

import Lists.ListOfBullets;
import Lists.ListOfFirings;
import Movers.BulletMover;
import Movers.FireMover;

import javax.swing.*;

public class Fire_I extends EnemyFire {

    Fire_I(int x, int y){
        this.setX(x);
        this.setY(y);
        this.damage = damage;
        this.fireMover = new FireMover();
        ListOfFirings.Firings.add(this);
        LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\blaster-firing.wav", false);
        this.fireLabel = new JLabel(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\blaster.png"));
    }
    @Override
    public boolean isInRange(coordinatedObject object) {
        return false;
    }

    @Override
    public boolean isNotScreen() {
        if (this.getX() < -20 || this.getY() < -50){
            return true;
        }
        else return false;
    }
}
