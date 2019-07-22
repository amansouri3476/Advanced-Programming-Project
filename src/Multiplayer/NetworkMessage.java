package Multiplayer;

import GameObjects.*;
import Lists.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkMessage implements Serializable {

    //        int spaceship_x = GameEventHandler.spaceship.x_coordinate;
//        int spaceship_y = GameEventHandler.spaceship.y_coordinate;
    CopyOnWriteArrayList<Bomb> Bombs;
    CopyOnWriteArrayList<Bullet> Bullets;
    CopyOnWriteArrayList<Enemy> Enemies;
    CopyOnWriteArrayList<EnemyGroup> EnemyGroups;
    CopyOnWriteArrayList<Enemy> Explosions;
    CopyOnWriteArrayList<EnemyFire> Firings;
    CopyOnWriteArrayList<Giant> Giants;
    CopyOnWriteArrayList<Powerup> Powerups;
    CopyOnWriteArrayList<Player> joinedPlayers;
    ArrayList<String> joinedPlayersNames;
    Dictionary scoreDict;
    Player player;

    NetworkMessage(){
//        int spaceship_x = GameEventHandler.spaceship.x_coordinate;
//        int spaceship_y = GameEventHandler.spaceship.y_coordinate;
//        String username = ListOfUsers.selectedUser;
        this.Bombs = BombList.Bombs;
        this.Bullets = ListOfBullets.Bullets;
        this.Enemies = ListOfEnemies.Enemies;
        this.EnemyGroups = ListOfEnemyGroups.EnemyGroups;
        this.Explosions = ListOfExplosions.Explosions;
        this.Firings = ListOfFirings.Firings;
        this.Giants = ListOfGiants.Giants;
        this.Powerups = ListOfPowerups.Powerups;

        this.joinedPlayers = GameServer.joinedPlayersObjects;
        this.joinedPlayersNames = GameServer.joinedPlayers;

        this.scoreDict = scoreDictionary(GameServer.joinedPlayers);
    }

    private Dictionary scoreDictionary(ArrayList<String> joinedPlayers) {
        Dictionary scores = new Hashtable();

        for (String joinedPlayer: joinedPlayers){
            scores.put(joinedPlayer, ListOfUsers.getPlayerObjByUsername(joinedPlayer).score);
        }

        return scores;
    }


    public NetworkMessage(CopyOnWriteArrayList<Bullet> bullets, CopyOnWriteArrayList<Bomb> bombs, Player player) {
        if (bombs.size() == 0){
            this.Bombs = null;
        }
        else this.Bombs = bombs;

        if (bullets.size() == 0){
            this.Bullets = null;
        }
        else this.Bullets = bullets;

        this.Enemies = null;
        this.EnemyGroups = null;
        this.Explosions = null;
        this.Firings = null;
        this.Giants = null;
        this.Powerups = null;

        this.player = player;
    }
}
