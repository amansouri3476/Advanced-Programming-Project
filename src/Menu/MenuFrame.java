package Menu;

import Lists.ListOfUsers;
import Lists.Player;
import LoadSave.Saver;
import Menu.MenuFrameButtons.RemoveUser;
import Menu.MenuFrameButtons.SelectUser;
import MovingBackground.ScrollingBackground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("Duplicates")
public class MenuFrame extends JFrame {

    public JFrame frame;
    public JButton bAddUser, bRemoveUser, bStartUser, bSelectUser, bExitGame;
    private int radius = 40;
//    BufferedImage duke = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\darth_vader.png"));

    public MenuFrame(String name) throws IOException {
        frame = new JFrame(name);
        setSize(3000, 1500);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;

        JButton bAddUser = addButton("Add User", x, y, width,height, radius);
//        try {
//            bAddUser.setIcon(new ImageIcon(ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\button1.png"))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.add(bAddUser);
        this.bAddUser = bAddUser;

        JButton bRemoveUser = addButton("Remove User", x, y + delta_y, width,height, radius);
//        this.add(bRemoveUser);
        this.bRemoveUser = bRemoveUser;

        JButton bStartUser = addButton("Start User", x, y + delta_y * 2, width,height, radius);
//        this.add(bStartUser);
        this.bStartUser = bStartUser;

        JButton bSelectUser = addButton("Select User", x, y + delta_y * 3, width,height, radius);
//        this.add(bSelectUser);
        this.bSelectUser = bSelectUser;

        JButton bExitGame = addButton("Quit Game", x, y + delta_y * 4 + 100, width,height, radius);
//        this.add(bSelectUser);
        this.bExitGame = bExitGame;


        ScrollingBackground back = new ScrollingBackground();
        System.out.println("new scrolling background");
        ((Component)back).setFocusable(true);
        getContentPane().add(back);

        setVisible(true);
        System.out.println("Buttons Added");


    }

    protected JButton addButton(String buttonName, int x, int y, int width, int height, int r){
        JButton button = new JButton(buttonName);
        button.setBorder(new RoundedBorder(r));
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);
        button.setForeground(Color.RED);
        button.setBackground(Color.orange);
        button.setOpaque(true);
        button.setFont(new Font("Georgia", Font.ITALIC, 18));
        add(button,0);

        return button;
    }

    public void menu(){

        // Adding ActionListeners to buttons
        bStartUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
//                dispose();
                MainMenu Game = new MainMenu(ListOfUsers.selectedUser, frame);
                Game.menu();
            }
        });
        bAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame12 = new JFrame();
                frame12.setSize(400, 400);
                Object[] options = { "Let's Go"};

//
                JPanel panel = new JPanel();
                panel.add(new JLabel("Enter your username:"));
                JTextField textField = new JTextField(10);
                panel.add(textField);

                ImageIcon icon = null;
                try {
                    icon = new ImageIcon(ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\darth_vader.png")));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                int result = JOptionPane.showOptionDialog(null, panel, "New User",
                JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
                        icon, options, null);
                if (result == JOptionPane.YES_OPTION){
//                JOptionPane.showMessageDialog(null, textField.getText());
                    String name = textField.getText();
                    if (!name.isEmpty()){
                        Player player = new Player(name);
                        Saver saver = new Saver("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src" + player.getUserName(), player);
                    }

                }

            }
        });
        bRemoveUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    new RemoveUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                JFrame fRemoveUser = new JFrame();
//                fRemoveUser.setSize(500, 500);
//                fRemoveUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                fRemoveUser.setLayout(new FlowLayout());
//                fRemoveUser.setVisible(true);
//                if (ListOfUsers.Players != null) {
//                    AtomicInteger playerCounter = new AtomicInteger(0);
//                    ListOfUsers.Players.forEach(player -> {
//                        playerCounter.getAndIncrement();
//                        System.out.println("Remove:" + playerCounter);
//                        JButton bUser = addButton(player, 250, 100 * playerCounter.get(), 100, 100, radius);
//                        fRemoveUser.add(bUser);
//                        fRemoveUser.setVisible(true);
//                        bUser.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                ListOfUsers.Players.remove(player);
//                                fRemoveUser.dispose();
//                            }
//                        });
//
//                    });
//                }
            }
        });
        // SelectUser
        bSelectUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SelectUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                JFrame fSelectUser = new JFrame("User Select");
//                fSelectUser.setSize(500, 500);
//                fSelectUser.setLayout(null);

//                fSelectUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//                fSelectUser.setLayout(new FlowLayout());
//                if (ListOfUsers.Players != null) {
//
//                    AtomicInteger playerCounter = new AtomicInteger(0);
//                    ListOfUsers.Players.forEach(player -> {
//                        playerCounter.getAndIncrement();
//                        System.out.println("Remove:" + playerCounter);
//                        JButton bUser = addButton(player, 250, 100 * playerCounter.get(), 100, 100, radius);
//                        fSelectUser.add(bUser);
//                        fSelectUser.setVisible(true);
//                        bUser.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                ListOfUsers.selectedUser = player;
//                                fSelectUser.dispose();
//                            }
//                        });
//
//                    });
//                }

            }
        });
        bExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }
}