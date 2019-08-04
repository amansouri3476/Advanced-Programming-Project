package Lists;

import GameObjects.Bomb;
import GameObjects.Enemy;
import GameObjects.LoopSound;
import Menu.Scorer;

import java.util.concurrent.CopyOnWriteArrayList;

public class BombList {
    public static CopyOnWriteArrayList<Bomb> Bombs = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Bomb> clientBombs = new CopyOnWriteArrayList<>();

    public synchronized static void updateList(){
        if (!Bombs.isEmpty()){

            for (Bomb bomb : Bombs) {
                ////// Explosion Check (Bomb reached center) //////
                try{
                    if (bomb.bombMover.reached_x && bomb.bombMover.reached_y){
                        for (Enemy enemy: ListOfEnemies.Enemies){
                            enemy.isDead = true;
                        }
                        Scorer.bombScore(bomb);
                        LoopSound loopSound = new LoopSound("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Depth_Charge_Short.wav", false);
                        ListOfExplosions.updateList();
                        Bombs.remove(bomb);
                    }
                }
                catch (NullPointerException e){

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
}
