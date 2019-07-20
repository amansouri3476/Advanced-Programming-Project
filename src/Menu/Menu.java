package Menu;

import MovingBackground.ScrollingBackground;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;

abstract public class Menu extends JFrame {
    JFrame frame;
    //    GridBagConstraints gbc = new GridBagConstraints();
    Menu(String name, JFrame previousFrame) throws IOException {
        previousFrame.setVisible(false);
        frame = new JFrame(name);
        frame.setSize(3000, 1500);
//        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        try{
//            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\ulukai\\corona_up.png")))));
//
//        }catch(IOException e)
//        {
//            e.printStackTrace();
//
//        }


        ScrollingBackground back = new ScrollingBackground();
        ((Component)back).setFocusable(true);
        getContentPane().add(back);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(null);
        this.setResizable(true);
        this.setVisible(true);
    }
    protected JButton addButton(String buttonName, int x, int y, int width, int height){
        JButton button = new JButton(buttonName);
        button.setBounds(x, y, width, height);
        this.add(button);

        return button;
    }
    abstract void menu();
}