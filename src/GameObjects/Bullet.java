package GameObjects;


import Interfaces.hasCoordinates;
import Interfaces.hasRange;
import Lists.ListOfBullets;
import Lists.ListOfUsers;
import Movers.BulletMover;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Bullet extends coordinatedObject implements hasRange, hasCoordinates {

    public String shooter;
    double damage;
    public BulletMover bulletMover;
    public JLabel bulletLabel;
    public Container container;

    public Bullet(int x, int y, double damage){
        this.shooter = ListOfUsers.selectedUser;
        this.setX(x);
        this.setY(y);
        this.damage = damage;
        this.bulletMover = new BulletMover();
        ListOfBullets.Bullets.add(this);
        ListOfBullets.heat += 5;
        System.out.println("Number of Bullets Created:" + ListOfBullets.Bullets.size());
        LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\shoot.wav", false);
        this.bulletLabel = new JLabel(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\shuttle.png"));
    }

    public boolean isNotScreen(){
        if (this.getX() < -20 || this.getY() < -50){
            return true;
        }
        else return false;
    }

    public boolean isInRange(coordinatedObject object) {
        return false;
    }

}