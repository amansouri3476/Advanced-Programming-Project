package Lists;

import GameObjects.Bullet;
import GameObjects.Spaceship;
import Screen.GamePlayScrolling.GameEventHandler;
import Screen.GamePlayScrolling.Scroll;

import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfBullets {

    public static CopyOnWriteArrayList<Bullet> Bullets = new CopyOnWriteArrayList<>();
    public static float heat=0;

    public ListOfBullets() {}

    public synchronized static void updateList(){
        if (heat <= 0){
            heat = 0;
        }
        if (heat >= GameEventHandler.spaceship.gun.heatLimit){
            Scroll.isOverheated = true;
        }
        for (Bullet bullet : Bullets) {
//            System.out.println();
            if (bullet.isNotScreen()){
                System.out.println("Bullet Removed");
                Bullets.remove(bullet);
//                    GameEventHandler.gameFrame.remove(bullet.bulletLabel);
            }
        }
    }

}
