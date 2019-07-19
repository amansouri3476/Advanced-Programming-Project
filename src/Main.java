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

        /* TODO
            1. Third Wave and all enemies should have hidden coordinates for smoother and clean movements.
            2. Scoreboard for all players needs to be added.
            3. Different Players Spaceships should be displayed on different screens (needs to send their location.)
            4. engine heat of each player should be displayed properly according to the number of bullets it has shot.
            5. Checking whether or not more than one client is able to join the game, both as an spectator or a player.
         */
    }
}
