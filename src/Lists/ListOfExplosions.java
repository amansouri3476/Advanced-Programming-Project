package Lists;

import GameObjects.Enemy;

import java.util.concurrent.CopyOnWriteArrayList;

import static Lists.ListOfEnemies.Enemies;

public class ListOfExplosions {
    public static CopyOnWriteArrayList<Enemy> Explosions = new CopyOnWriteArrayList<>();

    public synchronized static void updateList(){

        for (Enemy enemy : Enemies) {
            System.out.println();
            if (enemy.isDead){
                System.out.println("Explosion!");
                Explosions.add(enemy);
            }
        }
    }
}
