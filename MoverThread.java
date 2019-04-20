import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MoverThread implements Runnable {

    public void run() {
        Thread th = new Thread(new Runnable() {

            public void run() {
                while (true){
//                    System.out.println("Mover Thread");
                    if (!ListOfBullets.Bullets.isEmpty()){
//                        System.out.println("Bullet List is Non-Empty");
                        AtomicInteger bulletCounter = new AtomicInteger(0);
//                        System.out.println(ListOfBullets.Bullets.size());
                        synchronized (ListOfBullets.Bullets){
                            for (int i=0; i<ListOfBullets.Bullets.size(); i++){
                                Bullet bullet = ListOfBullets.Bullets.get(i);
                                bulletCounter.getAndIncrement();
//                            System.out.println("bullet ID:" + bulletCounter + "Is Going From:" + bullet.getX() + ", " + bullet.getY());
                                bullet.bulletMover.changeX(bullet);
                                bullet.bulletMover.changeY(bullet);
//                            System.out.println("bullet ID:" + bulletCounter + "Moved to:" + bullet.getX() + ", " + bullet.getY());

                            }
                        }
                    }
                    else{
//                        System.out.println("Bullet List is Empty");
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        th.start();
        }
}
