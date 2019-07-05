package Menu;

import GameObjects.LoopSound;
import Lists.ListOfUsers;
import MovingBackground.ScrollingBackground;
import Screen.GamePlayScrolling.GameEventHandler;
import Screen.ScreenPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("Duplicates")
public class MainMenu extends JFrame {

    public JFrame frame;
    public JFrame prevFrame;
    public LoopSound loopSound;
    int radius = 10;
    // Transparent 16 x 16 pixel cursor image.
    private BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    // Create a new blank cursor.
    private Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");

    public JButton bResumeGame, bStartGame, bRanking, bSettings, bAboutUS, bBack,bMultiplayer;

    MainMenu(String name, JFrame previousFrame) {
        previousFrame.setVisible(false);
        this.prevFrame = previousFrame;
        this.frame = new JFrame(name);
        this.loopSound = loopSound;
        setSize(3000, 1500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 200;
        int height = 50;
        int x = 650;
        int y = 200;
        int delta_y = 100;
        JButton bResumeGame = addButton("Resume Game", x, y, width,height, radius);
        this.add(bResumeGame);
        this.bResumeGame = bResumeGame;
        JButton bStartGame = addButton("Start a New Game", x,  y + delta_y, width,height, radius);
        this.add(bStartGame);
        this.bStartGame = bStartGame;
        JButton bRanking = addButton("Hall of Fame", x, y + delta_y * 2, width,height, radius);
        this.add(bRanking);
        this.bRanking = bRanking;
        JButton bSettings = addButton("Settings", x, y + delta_y * 3, width,height, radius);
        this.add(bSettings);
        this.bSettings = bSettings;
        JButton bAboutUS = addButton("About Us", x, y + delta_y * 4, width,height, radius);
        this.add(bAboutUS);
        this.bAboutUS = bAboutUS;
        JButton bBack = addButton("Back to Starting Menu", x - 300, y + delta_y * 4 + 100, width + 50,height, radius);
        this.add(bBack);
        this.bBack = bBack;
        JButton bMultiplayer = addButton("Multi-Player", x, y + delta_y * 5, width,height, radius);
        this.add(bMultiplayer);
        this.bMultiplayer = bMultiplayer;
        System.out.println("Buttons Added");

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component)back).setFocusable(true);
        getContentPane().add(back);

        this.setVisible(true);
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

    void menu(){


        // Adding ActionListeners
        bResumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ListOfUsers.getPlayerObjByUsername(ListOfUsers.selectedUser).canResume){
                    JOptionPane.showMessageDialog(frame, "You have no saved game to resume!","Alert", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    // Resume Game implemented
                }
            }
        });
        bStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
//                Screen.GameScreen gameScreen = new Screen.GameScreen();
//                MoverThread moverThread = new MoverThread();
                ScreenPainter screenPainter = null;
                try {
                    screenPainter = new ScreenPainter();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                GameEventHandler gameEventHandler = new GameEventHandler();
//                GameUpdate gameUpdate = new GameUpdate();
                gameEventHandler.run();
//                gameScreen.run();
//                moverThread.run();
//                screenPainter.run();
//                gameUpdate.run();
            }
        });
        bBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
//                prevFrame.setVisible(true);
                MenuFrame menuFrame = null;
                try {
                    menuFrame = new MenuFrame("User Menu");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                menuFrame.menu();
            }
        });
        bMultiplayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
            }
        });

    }

}
