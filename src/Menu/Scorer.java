package Menu;

import GameObjects.Bomb;
import GameObjects.Bullet;
import GameObjects.Enemy;
import Lists.ListOfEnemies;
import Lists.ListOfUsers;

public class Scorer {


    public static void bombScore(Bomb bomb) {
        ListOfUsers.getPlayerObjByUsername(bomb.shooter).score += ListOfEnemies.Enemies.size();
    }

    public static void enemyScore(Enemy enemy, Bullet bullet) {
//        ListOfUsers.getPlayerObjByUsername(bullet.shooter).score += enemy.score;
        ListOfUsers.getPlayerObjByUsername(bullet.shooter).score += 1;
    }
}
