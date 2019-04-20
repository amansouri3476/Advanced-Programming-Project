import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String s[]){
       MenuFrame UserMenu = new MenuFrame("User Menu");
       System.out.println("Salam");
       Player player = new Player("Amin");
       Saver saver = new Saver("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src" + player.getUserName(), player);
       Loader loader = new Loader("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src" + player.getUserName());
       Player player1 = new Player("Erfan");
       UserMenu.menu();

    }
}
