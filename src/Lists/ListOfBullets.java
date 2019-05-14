package Lists;

import GameObjects.Bullet;
import Others.GameEventHandler;

import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfBullets {

        public static CopyOnWriteArrayList<Bullet> Bullets = new CopyOnWriteArrayList<>();
        public ListOfBullets()
        {

        }

        public synchronized static void updateList(){
            for (Bullet bullet : Bullets) {
                System.out.println();
                if (bullet.isNotScreen()){
                    System.out.println("GameObjects.Bullet Removed");
                    Bullets.remove(bullet);
                    GameEventHandler.gameFrame.remove(bullet.bulletLabel);
                }
            }
        }

}
