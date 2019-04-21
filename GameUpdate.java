public class GameUpdate implements Runnable{

    public void run() {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                while (true){
//                    ListOfBullets.updateList();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
