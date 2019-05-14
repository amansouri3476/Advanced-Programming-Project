package Menu.MenuFrameButtons;

import Lists.ListOfUsers;
import MovingBackground.ScrollingBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class RemoveUser extends JFrame {
    public JFrame frame;
    private int radius = 40;

    public RemoveUser() throws IOException {
        frame = new JFrame("REMOVE USER");
        setSize(3000, 1500);
//        setLayout(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        setLayout(new FlowLayout());
        if (ListOfUsers.Players != null) {

            AtomicInteger playerCounter = new AtomicInteger(0);
            ListOfUsers.Players.forEach(player -> {
                playerCounter.getAndIncrement();
                System.out.println("Remove:" + playerCounter);
                JButton bUser = addButton(player, 700, 100 * playerCounter.get(), 150, 70, radius);
                add(bUser);
//                setVisible(true);
                bUser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ListOfUsers.Players.remove(player);
                        dispose();
                    }
                });

            });
        }
        ScrollingBackground back = new ScrollingBackground();
        System.out.println("new scrolling background");
        ((Component) back).setFocusable(true);
        getContentPane().add(back);


        setVisible(true);
//        System.out.println("Buttons Added");

    }
    protected JButton addButton(String buttonName, int x, int y, int width, int height, int r){
        JButton button = new JButton(buttonName);
//        button.setBorder(new RoundedBorder(r));
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);
        button.setForeground(Color.RED);
        button.setBackground(Color.orange);
        button.setOpaque(true);
        button.setFont(new Font("Georgia", Font.ITALIC, 18));
        add(button,0);

        return button;
    }
}
