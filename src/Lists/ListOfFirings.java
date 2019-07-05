package Lists;

import GameObjects.Bullet;
import GameObjects.EnemyFire;
import Screen.GamePlayScrolling.Scroll;

import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfFirings {
    public static CopyOnWriteArrayList<EnemyFire> Firings = new CopyOnWriteArrayList<>();

    public ListOfFirings() {}

    public synchronized static void updateList(){

        for (EnemyFire enemyFire : Firings) {
            if (enemyFire.isNotScreen()){
                Firings.remove(enemyFire);
            }
        }
    }

}
