import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings({"FieldCanBeLocal", "Duplicates"})
public class GameScreen extends MouseAdapter implements Runnable {
    public JFrame gameFrame = new JFrame("Chicken Invaders");
    public JLabel backgroundLabel = new JLabel();
    public JLabel spaceshipLabel = new JLabel();
    public GridBagConstraints gbc = new GridBagConstraints();
    public GridBagLayout layout = new GridBagLayout();
    public Component component = gameFrame.getContentPane();
    public Spaceship spaceship;
    static boolean isDragging;
    static boolean isPressed;
    static boolean firstTime = true;
    static boolean firstTimeP = true;

    // Transparent 16 x 16 pixel cursor image.
    private BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    // Create a new blank cursor.
    private Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");

    GameScreen(){
        gameFrame.setSize(2000, 1500);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(layout);
        gameFrame.setVisible(true);
//        mouseActivate();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gameFrame.add(backgroundLabel, gbc);
        backgroundLabel.setIcon(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\tenor1.gif"));

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundLabel.add(spaceshipLabel, gbc);
        spaceshipLabel.setIcon(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\Spaceship.png"));

        gameFrame.validate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        Spaceship spaceship = new Spaceship(gameFrame.getContentPane(), spaceshipLabel);
        Gun gun = new Gun(gameFrame.getContentPane(), spaceshipLabel);

        /**
         *
         *
         *
         */
//        Spaceship spaceship = new Spaceship();
//        gameFrame.addMouseListener(new MouseAdapter() {
//            public void mouseMoved(MouseEvent e) {
//                    spaceshipLabel.setBounds(e.getX() - 150, e.getY() - 150, 300, 300);
//            }
//            public void mouseClicked(MouseEvent e) {
//                spaceship.gun.singleShot(e.getX(), e.getY(), gameFrame.getContentPane());
//            }
//
//
//            public void mouseDragged(MouseEvent e) {
////                spaceshipLabel.setBounds(e.getX() - 150, e.getY() - 150, 300, 300);
//                if (firstTime){
//                    isDragging = true;
//                    System.out.println("Dragging");
//                    firstTime = false;
//                    Thread thread = new Thread(new Runnable() {
//
//                        public void run() {
//                            int counter = 0;
//                            while (isDragging) {
////                                spaceshipLabel.setBounds(e.getX() - 150, e.getY() - 150, 300, 300);
//                                try {
//                                    Thread.sleep(400);
//                                } catch (InterruptedException e1) {
//                                    e1.printStackTrace();
//                                }
//                                System.out.println("Dragged Counter: " + counter);
//                                counter++;
//                                System.out.println("Before Bullet:" + isDragging);
//                                spaceship.gun.singleShot(e.getX(), e.getY(), gameFrame.getContentPane());
//                                }
//
//                        }
//                    });
//                    if (isDragging && firstTime){
//                        thread.start();
//                        System.out.println("Thread Dragging Started");
//                    }
//                }
//
//
//            }
//            public void mousePressed(MouseEvent e) {
//                spaceshipLabel.setBounds(e.getX() - 150, e.getY() - 150, 300, 300);
//                if (firstTimeP){
//                    isPressed = true;
//                    firstTimeP = false;
//                    Thread thread_1 = new Thread(new Runnable() {
//
//                        public void run() {
//                            int counter = 0;
//                            while (isPressed){
//                                try {
//                                    Thread.sleep(400);
//                                } catch (InterruptedException e1) {
//                                    e1.printStackTrace();
//                                }
//                                System.out.println("Pressed Counter: " + counter);
//                                counter++;
//                                System.out.println("Before Bullet:" + isPressed);
//                                spaceship.gun.singleShot(e.getX(), e.getY(), gameFrame.getContentPane());
//                            }
//
//                        }
//                    });
//                    if (isPressed && firstTimeP){
//                        thread_1.start();
//                        System.out.println("Thread Pressing Started");
//
//                    }
//                }
//
//            }
//
//            public void mouseReleased(MouseEvent e) {
//                isDragging = false;
//                isPressed = false;
//                firstTime = true;
//                firstTimeP = true;
//                System.out.println("Released");
//            }
//
//        });
        component.setCursor(blankCursor);
        gameFrame.validate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
    }

    public void activateMouse(){
        gameFrame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spaceship.gun.singleShot(e.getX(),e.getY(),gameFrame.getContentPane());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                spaceshipLabel.setBounds(e.getX()-150, e.getY()-150,300,300);
            }
        });
    }

    public void activateMouseDrag(){
        Drag drag = new Drag();
        gameFrame.addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Released");
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                spaceshipLabel.setBounds(e.getX()-150, e.getY()-150,300,300);
                System.out.println("Dragged");
            }
        });
    }

    public void activateMouseRelease(){
        gameFrame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Released");
            }
        });
    }

        @Override
    public void run() {
        activateMouse();
        activateMouseDrag();
        activateMouseRelease();
    }
}

class Drag extends Thread{

    public void run() {
        super.run();
    }
}

@SuppressWarnings("Duplicates")
class Gun1 {
    public boolean overheated;
    public double damage;
    public int level;
    Gun1(){
        this.overheated = false;
        this.damage = 2;
        this.level = 1;
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
}