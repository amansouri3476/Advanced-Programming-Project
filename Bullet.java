import javax.swing.*;
import java.awt.*;

public class Bullet extends coordinatedObject implements hasRange,hasCoordinates {
    double damage;
    BulletMover bulletMover;
    JLabel bulletLabel;
    Container container;

    Bullet(int x,int y, double damage, Container container){
        this.setX(x);
        this.setY(y);
        this.damage = damage;
        ListOfBullets.Bullets.add(this);
        System.out.println("Number of Bullets Created:" + ListOfBullets.Bullets.size());
        BulletMover bulletMover = new BulletMover();
        this.bulletMover = bulletMover;
        JLabel bulletLabel = new JLabel(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\ShuttleB.gif"));
        this.bulletLabel = bulletLabel;
        this.container = container;
    }

    public boolean isInRange(coordinatedObject object) {
        return false;
    }

}
