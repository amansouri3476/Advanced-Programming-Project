package Lists;

import GameObjects.Bomb;
import Others.GameEventHandler;

import java.util.concurrent.CopyOnWriteArrayList;

public class BombList {
    public static CopyOnWriteArrayList<Bomb> Bombs = new CopyOnWriteArrayList<>();
    public BombList()
    {
        Bombs.add(null);
    }

    public synchronized static void updateList(){
        for (Bomb bomb : Bombs) {

        }
    }
}
