package Multiplayer;

import GameObjects.Bullet;
import Lists.*;
import MovingBackground.ScrollingBackground;
import Screen.GamePlayScrolling.GameEventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameClient implements Runnable {
    public JButton bStartGame;
    public Container container;
    public JTextArea listOfPlayersTextArea;
    private JButton bSpectator;
    private JButton bPlayer;
    public static NetworkMessage serverUpdateMessage;

    public GameClient(Container contentPane, String portNumber, String ipAddress) {
        int radius = 10;
        // Transparent 16 x 16 pixel cursor image.
        this.container = contentPane;
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

        listOfPlayersTextArea = new JTextArea("Please wait for the\n host to start the\n game...\n");
        listOfPlayersTextArea.setForeground(Color.RED);
        listOfPlayersTextArea.setBackground(Color.orange);
        listOfPlayersTextArea.setFont(new Font("Georgia", Font.ITALIC, 36));
        listOfPlayersTextArea.setBounds(x - 100, y - 100,2 * width,height * 10);
        listOfPlayersTextArea.setEditable(false);
        container.add(listOfPlayersTextArea);

        bSpectator = addButton("Spectator", x -200, 700, width, height,radius);
        bPlayer = addButton("Playing", x + 100, 700, width, height,radius);

        ScrollingBackground back = null;
        try {
            back = new ScrollingBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("new scrolling background");
        ((Component) back).setFocusable(true);
        container.add(back);

        container.validate();
        container.repaint();

        container.setVisible(true);

        run();

    }

    protected JButton addButton(String buttonName, int x, int y, int width, int height, int r){
        JButton button = new JButton(buttonName);
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);
        button.setForeground(Color.RED);
        button.setBackground(Color.orange);
        button.setOpaque(true);
        button.setFont(new Font("Georgia", Font.ITALIC, 18));
        container.add(button,0);

        return button;
    }

    @Override
    public void run() {
        System.out.println("Receiver Start");

        Thread thread = new Thread(() -> {
            SocketChannel sChannel;
            try {
                sChannel = SocketChannel.open();
                sChannel.configureBlocking(true);
                if (sChannel.connect(new InetSocketAddress("localhost", 10005))) {

                    ObjectOutputStream oos =
                            new ObjectOutputStream(sChannel.socket().getOutputStream());
                    oos.flush();
                    ObjectInputStream ois =
                            new ObjectInputStream(sChannel.socket().getInputStream());

                     ////////////////// Sending Messages to the Server //////////////////
                        Thread senderThread = new Thread(() -> {
                            while (true){
                                System.out.println("sender while began");
                                //                        oos.writeUnshared(obj);
                                try {
                                    oos.writeObject(new NetworkMessage());
                                    oos.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Thread.sleep(5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        senderThread.start();
                    ////////////////// Receiving Messages from the Server //////////////////
                        Thread receiverThread = new Thread(() -> {
                            while (true){
                                System.out.println("recv while began");
                                try {
                                    serverUpdateMessage = (NetworkMessage) ois.readObject();
                                    decodeMessage();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Message received from the server");
//                        String playerName = ((NetworkMessage) ois.readObject()).username;
//                        if (!listOfPlayersTextArea.getText().contains(playerName)){
//                            listOfPlayersTextArea.append("\t" + playerName + "\n");
//                        }
//                        System.out.println(message);
//                        messages.append(message);
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        receiverThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        thread.start();

        bSpectator.addActionListener(e -> {
            Thread thread1 = new Thread(() -> {
                container.setVisible(false);
                ClientGameEventHandler clientGameEventHandler = new ClientGameEventHandler(true);
                clientGameEventHandler.run();
            });
            thread1.start(); /// preventing message passing from being blocked by the beginning of this thread.
        });
        bPlayer.addActionListener(e -> {

        });
    }

    private void decodeMessage() {
        BombList.Bombs = serverUpdateMessage.Bombs;
        ListOfBullets.Bullets = serverUpdateMessage.Bullets;
        ListOfEnemies.Enemies = serverUpdateMessage.Enemies;
        ListOfEnemyGroups.EnemyGroups = serverUpdateMessage.EnemyGroups;
        ListOfExplosions.Explosions = serverUpdateMessage.Explosions;
        ListOfFirings.Firings = serverUpdateMessage.Firings;
        ListOfGiants.Giants = serverUpdateMessage.Giants;
        ListOfPowerups.Powerups = serverUpdateMessage.Powerups;
    }
}
