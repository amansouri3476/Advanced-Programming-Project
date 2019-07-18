package Lists;

import GameObjects.Enemy;
import GameObjects.EnemyGroup;

import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfEnemyGroups {
    public static CopyOnWriteArrayList<EnemyGroup> EnemyGroups = new CopyOnWriteArrayList<>();
    public synchronized static void updateList(){

        for (EnemyGroup enemyGroup : EnemyGroups) {
//            System.out.println("EnemyGroups updateList");
            if (enemyGroup.ListOfFighters.size() == 0){
                System.out.println("EnemyGroup Removed");
                EnemyGroups.remove(enemyGroup);
            }

            else {
                for (Enemy enemy: enemyGroup.ListOfFighters){
                    if (enemy.isDead){
                        enemyGroup.ListOfFighters.remove(enemy);
                    }
                }
                for (Enemy enemy: enemyGroup.innerCircleFightersI){
                    if (enemy.isDead){
                        enemyGroup.innerCircleFightersI.remove(enemy);
                    }
                }
                for (Enemy enemy: enemyGroup.innerCircleFightersII){
                    if (enemy.isDead){
                        enemyGroup.innerCircleFightersII.remove(enemy);
                    }
                }
                enemyGroup.updateConfiguration();
            }
        }
    }
}
