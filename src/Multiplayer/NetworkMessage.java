package Multiplayer;

import GameObjects.*;
import Lists.*;

import java.io.Serializable;
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
    }


    public NetworkMessage(CopyOnWriteArrayList<Bullet> bullets, CopyOnWriteArrayList<Bomb> bombs) {
        this.Bombs = null;
        this.Bullets = bullets;

        this.Enemies = null;
        this.EnemyGroups = null;
        this.Explosions = null;
        this.Firings = null;
        this.Giants = null;
        this.Powerups = null;
    }
}
