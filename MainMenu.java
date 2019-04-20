import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("Duplicates")
public class MainMenu extends Menu {

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
                GameScreen gameScreen = new GameScreen();
                MoverThread moverThread = new MoverThread();
                ScreenPainter screenPainter = new ScreenPainter();
                gameScreen.run();
                moverThread.run();
                screenPainter.run();
            }
        });
    }
}
