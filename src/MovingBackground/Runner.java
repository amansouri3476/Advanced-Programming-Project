package MovingBackground;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Runner extends JFrame {
    JFrame frame;
    public Runner(JFrame frame) {
        this.frame = frame;
        this.setSize(3000, 1500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        try {
//            this.add(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\star_wars_logo_PNG36.png")))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        this.setLayout(new FlowLayout());
        this.setResizable(true);
        this.setVisible(true);

        MovingBackground.ScrollingBackground back = null;
        try {
            back = new MovingBackground.ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((Component)back).setFocusable(true);
        getContentPane().add(back);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

}