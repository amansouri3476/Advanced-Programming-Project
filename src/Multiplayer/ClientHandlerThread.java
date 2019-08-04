package Multiplayer;

import GameObjects.Spaceship;
import Lists.*;
import Screen.GamePlayScrolling.GameEventHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static Screen.GamePlayScrolling.GameEventHandler.spaceship;

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
            /* TODO: If a new player joins the game which has not registered before, server encounters a problem
                hence ListOfUsers needs to be updated upon shake-hand message.
             */

            GameServer.joinedPlayersObjects.add(ListOfUsers.getPlayerObjByUsername(shakeHandMessage));
            this.clientName = shakeHandMessage;

            Spaceship shakeHandMessage_2 = (Spaceship) ois.readUnshared();
            int index = GameServer.joinedPlayers.indexOf(shakeHandMessage);
            GameServer.joinedPlayersObjects.get(index).spaceship = shakeHandMessage_2;
            ////////////////// Sending Messages to clients //////////////////
            Thread senderThread = new Thread(() -> {
                while (true){
                    try {
                        NetworkMessage message = new NetworkMessage();
//                        checkStatus(message);
                        oos.writeUnshared(message);
//                        System.out.println(">>>>>>>>>>>>>> Server sent a message");
                        oos.reset();
                        oos.flush();
                        //////////////// Only listen to non-spectators. (Otherwise because of the lack of
                        // messages, listen will block the program.
                        // Other solution would be to send empty messages until spectator decides to join.
//                        if (!ClientGameEventHandler.isSpectator){
                            NetworkMessage serverUpdateMessage = (NetworkMessage) ois.readUnshared();
                        System.out.println(">>>>>>>>>>>>>> Server received a message");
                            decodeMessage(serverUpdateMessage);
//                        }
                        ///////////////
                        Thread.sleep(1);
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
//                        e.printStackTrace();
                        try {
//                            ois.close();
//                            oos.close();
                            //TODO: If an spectator is removed, situation must be different from some other ones.
                            GameServer.joinedPlayersObjects.remove(ListOfUsers.getPlayerObjByUsername(clientName));
                            GameServer.joinedPlayers.remove(clientName);
                            sChannel.close();
                            System.out.println("Client disconnected.");
                            break;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
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
        if (serverUpdateMessage.player != null){
            int index_1 = GameServer.joinedPlayers.indexOf(ListOfUsers.selectedUser);
            GameServer.joinedPlayersObjects.get(index_1).spaceship = spaceship;
            int index = GameServer.joinedPlayers.indexOf(serverUpdateMessage.player.getUserName());
            GameServer.joinedPlayersObjects.get(index).x_coordinate = serverUpdateMessage.player.x_coordinate;
            GameServer.joinedPlayersObjects.get(index).y_coordinate = serverUpdateMessage.player.y_coordinate;
            GameServer.joinedPlayersObjects.get(index).isSpectator = serverUpdateMessage.player.isSpectator;
            // temporarily saving client-gun
            Gun g_temp = GameServer.joinedPlayersObjects.get(index).spaceship.clientGun;
            GameServer.joinedPlayersObjects.get(index).spaceship = serverUpdateMessage.player.spaceship;
            GameServer.joinedPlayersObjects.get(index).spaceship.clientGun = g_temp;
            //
//            System.out.println("<<<<<" + GameServer.joinedPlayersObjects.get(index).x_coordinate + "," +
//                    GameServer.joinedPlayersObjects.get(index).y_coordinate + "\t" +
//                    GameServer.joinedPlayersObjects.get(index).spaceship.x_coordinate + "," +
//                    GameServer.joinedPlayersObjects.get(index).spaceship.y_coordinate + ">>>>>");
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
