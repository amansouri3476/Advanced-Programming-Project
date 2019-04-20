import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfBullets {

        public static CopyOnWriteArrayList<Bullet> Bullets = new CopyOnWriteArrayList<>();
        ListOfBullets()
        {
            Bullets.add(null);
        }

}
