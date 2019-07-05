package Multiplayer;

import GameObjects.*;
import Lists.*;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkMessage implements Serializable {
    String username = ListOfUsers.selectedUser;
    CopyOnWriteArrayList<Bomb> Bombs = BombList.Bombs;
    CopyOnWriteArrayList<Bullet> Bullets = ListOfBullets.Bullets;
    CopyOnWriteArrayList<Enemy> Enemies = ListOfEnemies.Enemies;
    CopyOnWriteArrayList<EnemyGroup> EnemyGroups = ListOfEnemyGroups.EnemyGroups;
    CopyOnWriteArrayList<Enemy> Explosions = ListOfExplosions.Explosions;
    CopyOnWriteArrayList<EnemyFire> Firings = ListOfFirings.Firings;
    CopyOnWriteArrayList<Giant> Giants = ListOfGiants.Giants;
    CopyOnWriteArrayList<Powerup> Powerups = ListOfPowerups.Powerups;

}
