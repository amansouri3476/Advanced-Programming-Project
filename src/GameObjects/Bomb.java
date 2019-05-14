package GameObjects;

import Interfaces.hasCoordinates;
import Interfaces.hasRange;
import Lists.BombList;
import Lists.ListOfBullets;
import Movers.BombMover;
import Movers.BulletMover;
import Others.GameEventHandler;

import javax.swing.*;
import java.awt.*;

public class Bomb extends coordinatedObject implements hasCoordinates {
    public BombMover bombMover;
    public JLabel bombLabel;
    public Container container;
    public int speed = 50;

    public Bomb(int x,int y, Container container){
        this.setX(x);
        this.setY(y);

        int delta_x = ((GameEventHandler.gameWidth / 2) - x);
        int delta_y = ((GameEventHandler.gameHeight / 2) - y);
        double angle = Math.atan2(delta_y, delta_x);
        BombList.Bombs.add(this);
        this.bombMover = new BombMover(speed * Math.cos(angle), speed * Math.sin(angle));
        this.bombLabel = new JLabel(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\cohete_off.png"));
        this.container = container;
        container.add(bombLabel);
    }

}
