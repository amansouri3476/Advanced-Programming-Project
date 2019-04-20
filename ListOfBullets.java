import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfBullets {

        public static CopyOnWriteArrayList<Bullet> Bullets = new CopyOnWriteArrayList<>();
        ListOfBullets()
        {
            Bullets.add(null);
        }

        public synchronized static void updateList(){
            for (Bullet bullet : Bullets) {
                System.out.println();
                if (bullet.isNotScreen()){
                    System.out.println("Bullet Removed");
                    Bullets.remove(bullet);
                    MouseMotionEventDemo.gameFrame.remove(bullet.bulletLabel);
                }
            }
        }

}
