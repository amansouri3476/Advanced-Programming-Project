import java.awt.*;
import java.awt.event.MouseEvent;

//public class GunHold extends Thread {
//    MouseEvent event;
//    Container container;
//    Spaceship spaceship;
//    GunHold(MouseEvent event, Container container, Spaceship spaceship){
//        this.event = event;
//        this.container = container;
//        this.spaceship = spaceship;
//    }
//    public void run() {
//        Thread thread = new Thread(new Runnable() {
//
//            public void run() {
//                while (true){
//                    spaceship.gun.singleShot(event.getX(), event.getY(), container);
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            }
//        });
//        thread.start();
//    }
//}
