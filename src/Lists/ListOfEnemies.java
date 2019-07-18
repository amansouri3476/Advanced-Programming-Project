package Lists;

import GameObjects.Bullet;
import GameObjects.Enemy;
import Screen.GamePlayScrolling.Scroll;

import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfEnemies {
    public static CopyOnWriteArrayList<Enemy> Enemies = new CopyOnWriteArrayList<>();

    public synchronized static void updateList(){

        for (Enemy enemy : Enemies) {
//            System.out.println();
            if (enemy.isDead){
                System.out.println("Enemy Removed");
                enemy.death();
                Enemies.remove(enemy);
            }
        }
    }
}
