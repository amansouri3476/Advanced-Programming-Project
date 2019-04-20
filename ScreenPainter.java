import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenPainter extends JFrame implements Runnable{

    public void run() {
        Thread th = new Thread(new Runnable() {

            public void run() {
                while (true){
//                    System.out.println("ScreenPainter Thread");
                    if (!ListOfBullets.Bullets.isEmpty()){
//                        System.out.println("Bullet List is Non-Empty");
                        AtomicInteger bulletCounter = new AtomicInteger(0);
                        synchronized (ListOfBullets.Bullets){
                            ListOfBullets.Bullets.forEach(bullet -> {
                                bulletCounter.getAndIncrement();
//                            System.out.println("bullet ID:" + bulletCounter + "Painted At:" + bullet.x_coordinate + ", " + bullet.y_coordinate);
                                bullet.bulletLabel.setBounds(bullet.x_coordinate, bullet.y_coordinate, 50, 100);
                                bullet.container.add(bullet.bulletLabel, 0);
                                bullet.container.validate();
                                bullet.container.repaint();
                                bullet.container.setVisible(true);

                            });
                        }
                    }
                    else{
//                        System.out.println("Bullet List is Empty");
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        th.start();
    }
}
