import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

abstract public class Menu extends JFrame {
    JFrame frame;
//    GridBagConstraints gbc = new GridBagConstraints();
    Menu(String name){
        frame = new JFrame(name);
        frame.setSize(3000, 1500);
//        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\ulukai\\corona_up.png")))));

        }catch(IOException e)
        {
            e.printStackTrace();

        }
        this.setLayout(new FlowLayout());
        this.setResizable(true);
        this.setVisible(true);
    }
    protected JButton addButton(String buttonName, int x, int y, int width, int height){
//        JButton button = new JButton(buttonName);
//        button.setBounds(x, y, width, height);

        return new JButton(buttonName);
    }
//    protected JLabel addLabel(String labelName, int x, int y, int width, int height){
//        JLabel label = new JLabel(labelName);
//        label.setBounds(x, y, width, height);
//        return label;
//    }
//    protected JPanel addPanel(int x, int y, int width, int height, Color background){
//        JPanel panel = new JPanel();
//        panel.setBackground(background);
//        panel.setBounds(x, y, width, height);
//        return panel;
//    }
    abstract void menu();
}
