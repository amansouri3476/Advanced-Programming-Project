package Lists;

import GameObjects.Giant;

import java.util.concurrent.CopyOnWriteArrayList;

public class ListOfGiants {
    public static CopyOnWriteArrayList<Giant> Giants = new CopyOnWriteArrayList<>();
    public synchronized static void updateList(){

        for (Giant giant : Giants) {
            System.out.println();
            if (giant.isDead){
                System.out.println("Giant Removed");
                giant.death();
                Giants.remove(giant);
            }
        }
    }

}
