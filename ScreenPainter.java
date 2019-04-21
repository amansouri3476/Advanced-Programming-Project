import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenPainter extends JFrame implements Runnable{
    public static int timerD=0;
    public static int timerP=0;
    public static int paintntCounter = 0;
    public void run() {
        Thread th = new Thread(new Runnable() {

            public void run() {
                while (true){
                    ////////////////////////////////////// Painting //////////////////////////////////////
                    paintntCounter += 1;
                    // Spaceship Painting
                    Spaceship.spaceshipLabel.setBounds(MouseMotionEventDemo.event_x, MouseMotionEventDemo.event_y, 300, 300);
                    // Bullets Painting
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
                    ////////////////////////////////////// Updating //////////////////////////////////////
                    ListOfBullets.updateList();
                    ////////////////////////////////////// Moving //////////////////////////////////////
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
                    ////////////////////////////////////// Next Step //////////////////////////////////////
                    try {
                        if (MouseMotionEventDemo.isDragged)
                        {
                            timerD += 20;
                        }
                        if (MouseMotionEventDemo.isPressed)
                        {
                            timerP += 20;
                        }
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
