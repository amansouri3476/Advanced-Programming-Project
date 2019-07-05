import Lists.ListOfUsers;
import Lists.Player;
import LoadSave.Loader;
import LoadSave.Saver;
import Menu.MenuFrame;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String s[]){


        MenuFrame UserMenu = null;
        try {
            UserMenu = new MenuFrame("User Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Player player = new Player("Amin");
        Saver saver = new Saver("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src" + player.getUserName(), player);
        Loader loader = new Loader("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src" + player.getUserName());
        Player player1 = new Player("Erfan");
        LoopSound loopSound = new LoopSound("src/imperial_march.wav", true);
//       loopSound.clip.stop();

    }
}
