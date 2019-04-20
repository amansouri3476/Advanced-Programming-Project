import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("Duplicates")
public class Gun implements MouseMotionListener,MouseListener{
//public class Gun {
    public boolean overheated;
    public double damage;
    public int level;
    Container container;
    JLabel spaceshipLabel;
    Shooter shooter;
    Gun(Container container, JLabel spaceshipLabel){
        this.overheated = false;
        this.damage = 2;
        this.level = 1;
        this.container = container;
        this.spaceshipLabel = spaceshipLabel;
    }

    public void singleShot(int x, int y, Container container){
        System.out.println("Single Shot");
        Bullet bullet = new Bullet(x, y, damage, container);
        container.setLayout(null);
//        JLabel bulletLabel = new JLabel(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\ShuttleB.gif"));
        container.add(bullet.bulletLabel, 0);
        bullet.bulletLabel.setBounds(x, y, 100, 100);
        container.validate();
        container.repaint();
        container.setVisible(true);
    }
    public void interruptShooting(){

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        this.singleShot(e.getX(),e.getY(),this.container);

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        spaceshipLabel.setBounds(e.getX()-150, e.getY()-150,300,300);
        shooter = new Shooter();
        shooter.run();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        spaceshipLabel.setBounds(e.getX()-150, e.getY()-150,300,300);
    }

    class Shooter implements Runnable {

        @Override
        public void run() {

            Thread thread = new Thread(new Runnable() {
                Gun gun = new Gun(container, spaceshipLabel);
                @Override
                public void run() {
                    while (true){

                    }
                }
            });
        }
    }

}

