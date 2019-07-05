package Menu;

import Lists.ListOfUsers;
import Lists.Player;
import LoadSave.Saver;
import Menu.MenuFrameButtons.RemoveUser;
import Menu.MenuFrameButtons.SelectUser;
import MovingBackground.ScrollingBackground;
import Multiplayer.GameClient;
import Multiplayer.GameServer;
import Screen.GamePlayScrolling.GameEventHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("ALL")
public class MenuFrame extends JFrame {

    public JFrame frame;
    public JButton bAddUser, bRemoveUser, bStartUser, bSelectUser, bExitGame;
    private int radius = 40;
    BufferedImage duke = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\darth_vader.png"));
    public MenuFrame(String name) throws IOException {
        frame = new JFrame(name);
        setSize(3000, 1500);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;

        JButton bAddUser = addButton("Add User", x, y, width,height, radius);
        this.bAddUser = bAddUser;

        JButton bRemoveUser = addButton("Remove User", x, y + delta_y, width,height, radius);
        this.bRemoveUser = bRemoveUser;

        JButton bStartUser = addButton("Start User", x, y + delta_y * 2, width,height, radius);
        this.bStartUser = bStartUser;

        JButton bSelectUser = addButton("Select User", x, y + delta_y * 3, width,height, radius);
        this.bSelectUser = bSelectUser;

        JButton bExitGame = addButton("Quit Game", x, y + delta_y * 4 + 100, width,height, radius);
        this.bExitGame = bExitGame;

        ScrollingBackground back = new ScrollingBackground();
        System.out.println("new scrolling background");
        ((Component)back).setFocusable(true);
        getContentPane().add(back);

        setVisible(true);
        System.out.println("Buttons Added");

        // Adding ActionListeners to buttons
        bStartUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ////////////////////////////////
//                setVisible(false);
//                MainMenu Game = new MainMenu(ListOfUsers.selectedUser, frame);
//                Game.menu();
                ////////////////////////////////
                singleMultiSelection(getContentPane());
            }
        });
        bAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame12 = new JFrame();
                frame12.setSize(400, 400);
                Object[] options = { "Let's Go"};

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

    private void startMenu(Container contentPane) {
        int radius = 10;
        // Transparent 16 x 16 pixel cursor image.
        contentPane.removeAll();
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;
        JButton bResumeGame = addButton("Resume Game", x, y, width, height, radius);
        JButton bStartGame = addButton("Start a New Game", x, y + delta_y, width, height, radius);
        JButton bRanking = addButton("Hall of Fame", x, y + delta_y * 2, width, height, radius);
        JButton bSettings = addButton("Settings", x, y + delta_y * 3, width, height, radius);
        JButton bAboutUS = addButton("About Us", x, y + delta_y * 4, width, height, radius);
        JButton bBack = addButton("Back to Starting Menu", x - 300, y + delta_y * 4 + 100, width + 50, height, radius);
        JButton bMultiplayer = addButton("Multi-Player", x, y + delta_y * 5, width, height, radius);
        System.out.println("Buttons Added");

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component) back).setFocusable(true);
        add(back);

        setVisible(true);

        // Adding ActionListeners
        bResumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ListOfUsers.getPlayerObjByUsername(ListOfUsers.selectedUser).canResume) {
                    JOptionPane.showMessageDialog(frame, "You have no saved game to resume!", "Alert", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Resume Game implemented
                }
            }
        });

        bStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                GameEventHandler gameEventHandler = new GameEventHandler();
                gameEventHandler.run();
            }
        });
        bBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    menuFrame(contentPane);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        bMultiplayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
            }
        });

    }

    private void menuFrame(Container contentPane) throws IOException {
        contentPane.removeAll();
        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;

        JButton bAddUser = addButton("Add User", x, y, width,height, radius);


        JButton bRemoveUser = addButton("Remove User", x, y + delta_y, width,height, radius);
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

        // Adding ActionListeners to buttons
        bStartUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ////////////////////////////////
//                setVisible(false);
//                MainMenu Game = new MainMenu(ListOfUsers.selectedUser, frame);
//                Game.menu();
                ////////////////////////////////
                singleMultiSelection(getContentPane());
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

    private void singleMultiSelection(Container contentPane) {
        int radius = 10;
        // Transparent 16 x 16 pixel cursor image.
        contentPane.removeAll();
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;
        JButton bSinglePlayer = addButton("Campaign", x, y, width, height, radius);
        JButton bMultiPlayer = addButton("Multi-Player", x, y + delta_y, width, height, radius);
        JButton bBack = addButton("Back", x - 300, y + delta_y * 4 + 100, width, height, radius);
        System.out.println("Buttons Added");

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component) back).setFocusable(true);
        add(back);

        setVisible(true);

        // Adding ActionListeners
        bSinglePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu(getContentPane());
            }
        });

        bMultiPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adHocJoinSelection(getContentPane());
            }
        });

        bBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    menuFrame(contentPane);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void adHocJoinSelection(Container contentPane) {
        int radius = 10;
        // Transparent 16 x 16 pixel cursor image.
        contentPane.removeAll();
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;
        JButton bAdHoc = addButton("Ad-Hoc", x, y, width, height, radius);
        JButton bJoin = addButton("Join", x, y + delta_y, width, height, radius);
        JButton bBack = addButton("Back", x - 300, y + delta_y * 4 + 100, width, height, radius);
        System.out.println("Buttons Added");

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component) back).setFocusable(true);
        add(back);

        setVisible(true);

        // Adding ActionListeners
        bAdHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adHoc(getContentPane());
            }
        });

        bJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                join(getContentPane());
            }
        });
        bBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                singleMultiSelection(contentPane);
            }
        });
    }

    private void join(Container contentPane) {
        int radius = 10;
        // Transparent 16 x 16 pixel cursor image.
        contentPane.removeAll();
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;

        JButton bBack = addButton("Back", x - 300, y + delta_y * 4 + 100, width, height, radius);
        JButton bJoin = addButton("Join Game", x, y + delta_y * 4, width, height, radius);

        JLabel ipAddress = new JLabel("IP Address of Server");
        ipAddress.setBounds(x - 100, y + delta_y, 180, 30);
        ipAddress.setForeground(Color.RED);
        ipAddress.setBackground(Color.orange);
        ipAddress.setOpaque(true);
        ipAddress.setFont(new Font("Georgia", Font.ITALIC, 18));
        add(ipAddress);

        JLabel portNumber = new JLabel("Port Number");
        portNumber.setBounds(x - 100, y, 180, 30);
        portNumber.setForeground(Color.RED);
        portNumber.setBackground(Color.orange);
        portNumber.setOpaque(true);
        portNumber.setFont(new Font("Georgia", Font.ITALIC, 18));
        add(portNumber);

        JTextField ip_address = new JTextField("127.0.0.1", 100);
        ip_address.setBounds(x + 100, y + delta_y, 100, 30);
        ip_address.setForeground(Color.RED);
        ip_address.setBackground(Color.orange);
        ip_address.setOpaque(true);
        ip_address.setFont(new Font("Georgia", Font.ITALIC, 18));
        ip_address.setBackground(Color.orange);
        add(ip_address);

        JTextField port_number = new JTextField("6006", 100);
        port_number.setBounds(x + 100, y, 100, 30);
        port_number.setForeground(Color.RED);
        port_number.setBackground(Color.orange);
        port_number.setOpaque(true);
        port_number.setFont(new Font("Georgia", Font.ITALIC, 18));
        port_number.setBackground(Color.orange);
        add(port_number);

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component) back).setFocusable(true);
        add(back);

        setVisible(true);

        bBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adHocJoinSelection(contentPane);
            }
        });
        bJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinGame(contentPane, portNumber.getText(), ipAddress.getText());
            }
        });
    }

    private void joinGame(Container contentPane, String portNumber, String ipAddress) {
        GameClient gameClient = new GameClient(contentPane, portNumber, ipAddress);
    }

    private void adHoc(Container contentPane) {
        int radius = 10;
        // Transparent 16 x 16 pixel cursor image.
        contentPane.removeAll();
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;


        JButton bBack = addButton("Back", x - 300, y + delta_y * 4 + 100, width, height, radius);
        JButton bCreate = addButton("Create Game", x, y + delta_y * 4, width, height, radius);

        JLabel portNumber = new JLabel("Port Number");
        portNumber.setBounds(x - 100, y, 150, 30);
        portNumber.setForeground(Color.RED);
        portNumber.setBackground(Color.orange);
        portNumber.setOpaque(true);
        portNumber.setFont(new Font("Georgia", Font.ITALIC, 18));
        add(portNumber);

        JLabel maxPlayer = new JLabel("Maximum Players");
        maxPlayer.setBounds(x - 100, y + delta_y, 150, 30);
        maxPlayer.setForeground(Color.RED);
        maxPlayer.setBackground(Color.orange);
        maxPlayer.setOpaque(true);
        maxPlayer.setFont(new Font("Georgia", Font.ITALIC, 18));
        add(maxPlayer);

        JLabel maxLevel = new JLabel("Number of Levels");
        maxLevel.setBounds(x - 100, y + 2*delta_y, 150, 30);
        maxLevel.setForeground(Color.RED);
        maxLevel.setBackground(Color.orange);
        maxLevel.setOpaque(true);
        maxLevel.setFont(new Font("Georgia", Font.ITALIC, 18));
        add(maxLevel);

        JTextField port_number = new JTextField("6006", 100);
        port_number.setBounds(x + 100, y, 100, 30);
        port_number.setForeground(Color.RED);
        port_number.setBackground(Color.orange);
        port_number.setOpaque(true);
        port_number.setFont(new Font("Georgia", Font.ITALIC, 18));
        port_number.setBackground(Color.orange);
        add(port_number);

        JTextField player_number = new JTextField("4", 100);
        player_number.setBounds(x + 100, y + delta_y, 100, 30);
        player_number.setForeground(Color.RED);
        player_number.setBackground(Color.orange);
        player_number.setOpaque(true);
        player_number.setFont(new Font("Georgia", Font.ITALIC, 18));
        player_number.setBackground(Color.orange);
        add(player_number);

        JTextField level_number = new JTextField("4", 100);
        level_number.setBounds(x + 100, y + 2*delta_y, 100, 30);
        level_number.setForeground(Color.RED);
        level_number.setBackground(Color.orange);
        level_number.setOpaque(true);
        level_number.setFont(new Font("Georgia", Font.ITALIC, 18));
        level_number.setBackground(Color.orange);
        add(level_number);

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component) back).setFocusable(true);
        add(back);

        setVisible(true);

        bBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adHocJoinSelection(contentPane);
            }
        });
        bCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGame(contentPane, frame, port_number.getText(), player_number.getText(), level_number.getText());
            }
        });
    }

    private void createGame(Container contentPane, JFrame frame, String port_number, String player_number, String level_number) {
        GameServer gameServer = new GameServer(contentPane, frame, port_number, player_number, level_number);
    }

}