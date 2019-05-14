package Screen;

import GameObjects.Spaceship;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings({"FieldCanBeLocal", "Duplicates"})
public class GameScreen extends JFrame{
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

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gameFrame.add(backgroundLabel, gbc);
        backgroundLabel.setIcon(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\ulukai\\redeclipse_up.png"));
        gameFrame.validate();
        gameFrame.repaint();
        gameFrame.setVisible(true);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundLabel.add(spaceshipLabel, gbc);
        spaceshipLabel.setIcon(new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Nebula_Red.png"));

        gameFrame.validate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        Spaceship spaceship = new Spaceship(gameFrame.getContentPane(), spaceshipLabel);
//        GameObjects.Gun gun = new GameObjects.Gun(gameFrame.getContentPane(), spaceshipLabel);

        component.setCursor(blankCursor);
        gameFrame.validate();
        gameFrame.repaint();
        gameFrame.pack();
        gameFrame.setVisible(true);
    }


    public void run() {
    }

}