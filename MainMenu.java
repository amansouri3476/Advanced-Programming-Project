import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

@SuppressWarnings("Duplicates")
public class MainMenu extends Menu {

    // Transparent 16 x 16 pixel cursor image.
    private BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    // Create a new blank cursor.
    private Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");
    MainMenu(String name) {
        super(name);
    }

    void menu(){
        JButton bResumeGame = addButton("Resume Game", 50, 100, 200,100);
        this.add(bResumeGame);
        JButton bStartGame = addButton("Start a New Game", 50, 300, 200,100);
        this.add(bStartGame);
        JButton bRanking = addButton("Hall of Fame", 50, 500, 200,100);
        this.add(bRanking);
        JButton bSettings = addButton("Settings", 50, 700, 200,100);
        this.add(bSettings);
        JButton bAboutUS = addButton("About Us", 50, 900, 200,100);
        this.add(bAboutUS);
        System.out.println("Buttons Added");
        this.pack();
        this.validate();
        this.repaint();
        this.setVisible(true);

        // Adding ActionListeners
        bResumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ListOfUsers.getPlayerByUsername(ListOfUsers.selectedUser).canResume){
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
                frame.dispose();
//                GameScreen gameScreen = new GameScreen();
                MoverThread moverThread = new MoverThread();
                ScreenPainter screenPainter = new ScreenPainter();
                MouseMotionEventDemo mouseMotionEventDemo = new MouseMotionEventDemo();
                mouseMotionEventDemo.run();
//                gameScreen.run();
                moverThread.run();
                screenPainter.run();
            }
        });

    }

}
