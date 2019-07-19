package Multiplayer;

import GameObjects.Enemy;
import Lists.*;
import MovingBackground.ScrollingBackground;
import Screen.GamePlayScrolling.GameEventHandler;
import com.sun.source.tree.NewArrayTree;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer implements Runnable {

    public JButton bStartGame;
    public Container container;
    public JTextArea messages;

    private int width = 200;
    private int height = 50;
    private int x = 650;
    private int y = 200;
    private int delta_y = 100;
    private int radius = 10;

    public static ArrayList<String> joinedPlayers = new ArrayList<>();
    private static NetworkMessage networkMessage = new NetworkMessage();
    private static int counter = 1;

    public GameServer(Container contentPane, JFrame frame, String port_number, String player_number, String level_number) {

        // Transparent 16 x 16 pixel cursor image.
        this.container = contentPane;
        GameServer.joinedPlayers.add(ListOfUsers.selectedUser);
        container.removeAll();

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");


        JButton bStartGame = addButton("Start Game!", x - 300, y + delta_y * 4 + 100, width, height, radius);
        this.bStartGame = bStartGame;

        messages = new JTextArea("The following players\n have joined the game.\n Press start to begin \nthe game!\n");
        messages.setForeground(Color.RED);
        messages.setBackground(Color.orange);
        messages.setFont(new Font("Georgia", Font.ITALIC, 36));
        messages.setBounds(x - 100, y - 100,2 * width,height * 10);
        messages.setEditable(false);
        container.add(messages);

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

        bStartGame.addActionListener(e -> {
            System.out.println("Server Started The Game!");
//            container.setVisible(false);
            Thread thread1 = new Thread(() -> {
                GameEventHandler gameEventHandler = new GameEventHandler(container);
                gameEventHandler.run();
            });
            thread1.start(); /// preventing message passing from being blocked by the beginning of this thread.

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
        container.add(button);

        return button;
    }

    @Override
    public void run() {

        System.out.println("Sender Start");


        Thread thread = new Thread(() -> {
            try {
                ServerSocketChannel ssChannel = ServerSocketChannel.open();
                ssChannel.configureBlocking(true);
                int port = 10005;
                ssChannel.socket().bind(new InetSocketAddress(port));

                System.out.println("listening to Client");
                SocketChannel sChannel = ssChannel.accept();
                System.out.println("Client connected");
                final ObjectOutputStream oos = new
                        ObjectOutputStream(sChannel.socket().getOutputStream());
                oos.flush();
                final ObjectInputStream ois =
                        new ObjectInputStream(sChannel.socket().getInputStream());

                ////////////////// Sending Messages to clients //////////////////
                Thread senderThread = new Thread(() -> {
                    while (true){
//                        System.out.println(">>>>>>>>>>>>>>Server");
                        try {
                            NetworkMessage message = new NetworkMessage();
                            checkStatus(message);
                            oos.writeUnshared(message);
                            oos.reset();
                            oos.flush();

                            ////////////////
                            NetworkMessage serverUpdateMessage = (NetworkMessage) ois.readUnshared();
                            decodeMessage(serverUpdateMessage);
                            ///////////////
                            Thread.sleep(2);
                        } catch (IOException | ClassNotFoundException | InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                senderThread.start();


            } catch (IOException e) {
                e.printStackTrace();
            }
//            oos.close();


        });
        thread.start();

    }

    private void checkStatus(NetworkMessage networkMessage) {
//            System.out.println(networkMessage.a);
//            System.out.println("Enemy 0 x coordinate: " + networkMessage.Enemies.get(0).x_coordinate);
//            System.out.println("Number of Bullets: " + networkMessage.Bullets.size());
//            System.out.println("Number of Enemies: " + networkMessage.Enemies.size());
//            System.out.println("Server ss x coordinate: " + networkMessage.spaceship_x);
    }

    private void showNameOnScreen(String playerName) {
        joinedPlayers.add(playerName);
        messages.append("\t" + playerName + "\n");
//        JLabel playerLabel = new JLabel(playerName);
//        playerLabel.setBounds(x, y + joinedPlayers.size() * delta_y, width, height);
//        playerLabel.setBackground(Color.orange);
//        playerLabel.setForeground(Color.RED);
//        playerLabel.setOpaque(true);
//        playerLabel.setFont(new Font("Georgia", Font.ITALIC, 18));
//        container.add(playerLabel);
//
//        container.validate();
//        container.repaint();
//
//        container.setVisible(true);
    }
    private void decodeMessage(NetworkMessage serverUpdateMessage) {
        System.out.println(">>>>>>>>> Number of bullets before receiving from the client: " + ListOfBullets.Bullets.size());
//        System.out.println(">>>>>>>>> Number of bullets before sent from the client: " + serverUpdateMessage.Bullets.size());
//        System.out.println(serverUpdateMessage.Enemies.size());
//        System.out.println(serverUpdateMessage.Enemies.get(0).x_coordinate);
//        System.out.println(serverUpdateMessage.a);
//        ClientGameEventHandler.spaceship.x_coordinate = serverUpdateMessage.spaceship_x;
//        ClientGameEventHandler.spaceship.y_coordinate = serverUpdateMessage.spaceship_y;
        if (serverUpdateMessage.Bombs != null){
            BombList.Bombs.addAll(serverUpdateMessage.Bombs);
        }
        if (serverUpdateMessage.Bullets != null){
            ListOfBullets.Bullets.addAll(serverUpdateMessage.Bullets);
        }
        if (serverUpdateMessage.Enemies != null){
            ListOfEnemies.Enemies = serverUpdateMessage.Enemies;
        }
        if (serverUpdateMessage.EnemyGroups != null){
            ListOfEnemyGroups.EnemyGroups = serverUpdateMessage.EnemyGroups;
        }
        if (serverUpdateMessage.Explosions != null){
            ListOfExplosions.Explosions = serverUpdateMessage.Explosions;
        }
        if (serverUpdateMessage.Firings != null){
            ListOfFirings.Firings = serverUpdateMessage.Firings;
        }
        if (serverUpdateMessage.Giants != null){
            ListOfGiants.Giants = serverUpdateMessage.Giants;
        }
        if (serverUpdateMessage.Powerups != null){
            ListOfPowerups.Powerups = serverUpdateMessage.Powerups;
        }
//        if (counter == 100){
//            System.out.println("Enemy 0 x coordinate: " + serverUpdateMessage.Enemies.get(0).x_coordinate);
//            System.out.println("Number of Bullets: " + serverUpdateMessage.Bullets.size());
//            System.out.println("Number of Enemies: " + serverUpdateMessage.Enemies.size());
//            System.out.println("Server ss x coordinate: " + ClientGameEventHandler.spaceship.x_coordinate);
//            counter = 1;
//        }
//        else counter++;

    }
}
