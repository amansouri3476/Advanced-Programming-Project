package Lists;

import GameObjects.Bomb;
import GameObjects.Enemy;
import GameObjects.LoopSound;

import java.util.concurrent.CopyOnWriteArrayList;

public class BombList {
    public static CopyOnWriteArrayList<Bomb> Bombs = new CopyOnWriteArrayList<>();

    public synchronized static void updateList(){
        for (Bomb bomb : Bombs) {
            ////// Explosion Check (Bomb reached center) //////
            if (bomb.bombMover.reached_x && bomb.bombMover.reached_y){
                for (Enemy enemy: ListOfEnemies.Enemies){
                    enemy.isDead = true;
                }
                LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Depth_Charge_Short.wav", false);
                ListOfExplosions.updateList();
                Bombs.remove(bomb);
            }
            if (bomb.isNotInScreen()){
                Bombs.remove(bomb);
            }
            else {
                bomb.bombMover.changeX(bomb);
                bomb.bombMover.changeY(bomb);
            }
        }
    }
}
