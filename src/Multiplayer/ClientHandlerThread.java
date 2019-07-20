package Multiplayer;

import Lists.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientHandlerThread implements Runnable {

    private SocketChannel sChannel;
    private String clientName;

    public ClientHandlerThread(SocketChannel socketChannel) {
        this.sChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            System.out.println("Client connected");
            final ObjectOutputStream oos = new
                    ObjectOutputStream(sChannel.socket().getOutputStream());
            oos.flush();
            final ObjectInputStream ois =
                    new ObjectInputStream(sChannel.socket().getInputStream());

            ////////////////// Shaking Hands //////////////////
            String shakeHandMessage = (String) ois.readUnshared();
            GameServer.joinedPlayers.add(shakeHandMessage);
            this.clientName = shakeHandMessage;
            ////////////////// Sending Messages to clients //////////////////
            Thread senderThread = new Thread(() -> {
                while (true){
//                        System.out.println(">>>>>>>>>>>>>>Server");
                    try {
                        NetworkMessage message = new NetworkMessage();
//                        checkStatus(message);
                        oos.writeUnshared(message);
                        oos.reset();
                        oos.flush();

                        ////////////////
                        NetworkMessage serverUpdateMessage = (NetworkMessage) ois.readUnshared();
//                        decodeMessage(serverUpdateMessage);
                        ///////////////
                        Thread.sleep(2);
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            senderThread.start();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkStatus(NetworkMessage networkMessage) {
//            System.out.println(networkMessage.a);
//            System.out.println("Enemy 0 x coordinate: " + networkMessage.Enemies.get(0).x_coordinate);
//            System.out.println("Number of Bullets: " + networkMessage.Bullets.size());
//            System.out.println("Number of Enemies: " + networkMessage.Enemies.size());
//            System.out.println("Server ss x coordinate: " + networkMessage.spaceship_x);
    }

    private void decodeMessage(NetworkMessage serverUpdateMessage) {
//        System.out.println(">>>>>>>>> Number of bullets before receiving from the client: " + ListOfBullets.Bullets.size());
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
