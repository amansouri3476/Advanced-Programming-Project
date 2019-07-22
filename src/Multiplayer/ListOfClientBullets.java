package Multiplayer;

import GameObjects.Bullet;
import Screen.GamePlayScrolling.Scroll;

import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfClientBullets {

    public static CopyOnWriteArrayList<Bullet> clientBullets = new CopyOnWriteArrayList<>();
    public static float heat=0;

    public ListOfClientBullets() {}

    public synchronized static void updateList(){
        if (heat <= 0){
            heat = 0;
        }
        if (heat >= 100){
            ClientScroll.isOverheated = true;
        }

    }
}
