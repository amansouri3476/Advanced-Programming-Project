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
    private static int counter = 1;


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

        bSpectator.addActionListener(e -> {
            Thread thread1 = new Thread(() -> {
//                container.setVisible(false);
                ClientGameEventHandler clientGameEventHandler = new ClientGameEventHandler(true, container);
                clientGameEventHandler.run();
            });
            thread1.start(); /// preventing message passing from being blocked by the beginning of this thread.

            run();
        });

        bPlayer.addActionListener(e -> {
            Thread thread = new Thread(() -> {
//                container.setVisible(false);
                ClientGameEventHandler clientGameEventHandler = new ClientGameEventHandler(false, container);
                clientGameEventHandler.run();
            });
            thread.start(); /// preventing message passing from being blocked by the beginning of this thread.

            run();
        });

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

                    final ObjectOutputStream oos =
                            new ObjectOutputStream(sChannel.socket().getOutputStream());
                    oos.flush();
                    final ObjectInputStream ois =
                            new ObjectInputStream(sChannel.socket().getInputStream());


                     ////////////////// Receiving Messages from the Server //////////////////
                        Thread receiverThread = new Thread(() -> {
                            while (true){
//                                System.out.println(">>>>>>>>>>>>>>Client");
                                ////////////////// Receiving Messages from the Server //////////////////
                                try {
                                    //////////////
                                    NetworkMessage serverUpdateMessage = (NetworkMessage) ois.readUnshared();
                                    decodeMessage(serverUpdateMessage);

                                    Thread.sleep(2);
                                    //////////////
                                    NetworkMessage message = new NetworkMessage(ListOfBullets.clientBullets, BombList.clientBombs);
                                    checkStatus(message);
                                    oos.writeUnshared(message);
                                    oos.reset();
                                    oos.flush();
                                    refreshClientLists();
                                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                                    e.printStackTrace();
                                }

//                                try {
//                                    Thread.sleep(5);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        });
                        receiverThread.start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        thread.start();

    }

    private void refreshClientLists() {
        ListOfBullets.clientBullets.clear();
        BombList.clientBombs.clear();
    }

    private void decodeMessage(NetworkMessage serverUpdateMessage) {
        System.out.println(">>>>>>>>> Number of bullets before receiving from the server: " + ListOfBullets.Bullets.size());
        System.out.println(">>>>>>>>> Number of bullets sent from the server: " + serverUpdateMessage.Bullets.size());
//        System.out.println(serverUpdateMessage.a);
//        ClientGameEventHandler.spaceship.x_coordinate = serverUpdateMessage.spaceship_x;
//        ClientGameEventHandler.spaceship.y_coordinate = serverUpdateMessage.spaceship_y;
        BombList.Bombs = serverUpdateMessage.Bombs;
        ListOfBullets.Bullets = serverUpdateMessage.Bullets;
        ListOfEnemies.Enemies = serverUpdateMessage.Enemies;
        ListOfEnemyGroups.EnemyGroups = serverUpdateMessage.EnemyGroups;
        ListOfExplosions.Explosions = serverUpdateMessage.Explosions;
        ListOfFirings.Firings = serverUpdateMessage.Firings;
        ListOfGiants.Giants = serverUpdateMessage.Giants;
        ListOfPowerups.Powerups = serverUpdateMessage.Powerups;
//        if (counter == 100){
//            System.out.println("Enemy 0 x coordinate: " + serverUpdateMessage.Enemies.get(0).x_coordinate);
//            System.out.println("Number of Bullets: " + serverUpdateMessage.Bullets.size());
//            System.out.println("Number of Enemies: " + serverUpdateMessage.Enemies.size());
//            System.out.println("Server ss x coordinate: " + ClientGameEventHandler.spaceship.x_coordinate);
//            counter = 1;
//        }
//        else counter++;

    }
    private void checkStatus(NetworkMessage networkMessage) {
//            System.out.println(networkMessage.a);
//            System.out.println("Enemy 0 x coordinate: " + networkMessage.Enemies.get(0).x_coordinate);
//            System.out.println("Number of Bullets: " + networkMessage.Bullets.size());
//            System.out.println("Number of Enemies: " + networkMessage.Enemies.size());
//            System.out.println("Server ss x coordinate: " + networkMessage.spaceship_x);
    }
}
