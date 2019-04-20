import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("Duplicates")
class MenuFrame extends Menu {
    MenuFrame(String name) {
        super(name);
    }

    void menu(){
        JButton bAddUser = addButton("Add User", 50, 100, 200,100);
        this.add(bAddUser);

        JButton bRemoveUser = addButton("Remove User", 50, 700, 200,100);
        this.add(bRemoveUser);

        JButton bStartUser = addButton("Start User", 50, 300, 200,100);
        this.add(bStartUser);

        JButton bSelectUser = addButton("Select User", 50, 500, 200,100);
        this.add(bSelectUser);

        this.pack();
        this.validate();
        this.repaint();
        this.setVisible(true);
        System.out.println("Buttons Added");


        // Adding ActionListeners to buttons
        bStartUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                MainMenu Game = new MainMenu(ListOfUsers.selectedUser);
                Game.menu();
            }
        });
        bAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name=JOptionPane.showInputDialog(frame,"Please Choose a Username");
                Player player = new Player(name);
            }
        });
        bRemoveUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fRemoveUser = new JFrame();
                fRemoveUser.setSize(500, 500);
                fRemoveUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fRemoveUser.setLayout(new FlowLayout());
                fRemoveUser.setVisible(true);
                if (ListOfUsers.Players != null) {
                    AtomicInteger playerCounter = new AtomicInteger(0);
                    ListOfUsers.Players.forEach(player -> {
                        playerCounter.getAndIncrement();
                        System.out.println("Remove:" + playerCounter);
                        JButton bUser = addButton(player, 250, 100 * playerCounter.get(), 100, 100);
                        fRemoveUser.add(bUser);
                        fRemoveUser.setVisible(true);
                        bUser.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ListOfUsers.Players.remove(player);
                                fRemoveUser.dispose();
                            }
                        });

                    });
                }
            }
        });
        // SelectUser
        bSelectUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fSelectUser = new JFrame();
                fSelectUser.setSize(500, 500);
                fSelectUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fSelectUser.setLayout(new FlowLayout());
                fSelectUser.setVisible(true);
                if (ListOfUsers.Players != null) {
                    AtomicInteger playerCounter = new AtomicInteger(0);
                    ListOfUsers.Players.forEach(player -> {
                        playerCounter.getAndIncrement();
                        System.out.println("Remove:" + playerCounter);
                        JButton bUser = addButton(player, 250, 100 * playerCounter.get(), 100, 100);
                        fSelectUser.add(bUser);
                        fSelectUser.setVisible(true);
                        bUser.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ListOfUsers.selectedUser = player;
                                fSelectUser.dispose();
                            }
                        });

                    });
                }
            }
        });
    }
}