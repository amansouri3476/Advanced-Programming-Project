import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("Duplicates")
public class Gun {
//public class Gun {
    public boolean overheated;
    public static int damage;
    public int level;
    Container container;
    JLabel spaceshipLabel;
    Gun(Container container, JLabel spaceshipLabel){
        System.out.println("Gun Constructed");
        this.overheated = false;
        this.damage = 2;
        this.level = 1;
        this.container = container;
        this.spaceshipLabel = spaceshipLabel;
    }

    public static void singleShot(int x, int y, Container container, int damage){
        System.out.println("Single Shot Tried");
        System.out.println("Single Shot Accomplished");
        Bullet bullet = new Bullet(x, y, damage, container);
        container.setLayout(null);
        container.add(bullet.bulletLabel, 0);
        bullet.bulletLabel.setBounds(x, y, 100, 100);
        container.validate();
        container.repaint();
        container.setVisible(true);
        }

    public synchronized static void longShotD(int x, int y, Container container, int damage){
        System.out.println("Long Shot Tried");
        if (ScreenPainter.timerD % 200 == 0){
            ScreenPainter.timerD = 0;
            System.out.println("Long Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage, container);
            container.setLayout(null);
            container.add(bullet.bulletLabel, 0);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
            container.validate();
            container.repaint();
            container.setVisible(true);
        }
    }
    public synchronized static void longShotP(int x, int y, Container container, int damage){
        System.out.println("Long Shot Tried");
        if (ScreenPainter.timerP % 200 == 0){
            ScreenPainter.timerP = 0;
            System.out.println("Long Shot Accomplished");
            Bullet bullet = new Bullet(x, y, damage, container);
            container.setLayout(null);
            container.add(bullet.bulletLabel, 0);
            bullet.bulletLabel.setBounds(x, y, 100, 100);
            container.validate();
            container.repaint();
            container.setVisible(true);
        }
    }
    public static void interruptShooting(){
        System.out.println("Shooting Interrupted");
        ScreenPainter.timerD = 0;
        ScreenPainter.timerP = 0;
        MouseMotionEventDemo.isDragged = false;
        MouseMotionEventDemo.isPressed = false;
    }

}

