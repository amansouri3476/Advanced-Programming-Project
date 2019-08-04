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
            Done: 2. Scoreboard for all players needs to be added.
            Done: 3. Different Players Spaceships should be displayed on different screens (needs to send their location.)
            Done: 4. engine heat of each player should be displayed properly according to the number of bullets it has shot.
            \Done: 5. Checking whether or not more than one client is able to join the game, both as an spectator or a player.
            6. Some powerups don't fall correctly. Their functioning needs to be debugged. Also there are too many
                powerups falling and needs to be decreased
            Done: 7. Every bullet and bomb should have an ID to indicate its shooter and enables scoring.
            Done: 8. Client's shooting seems to have some problems. Reconsideration required.
            9. In the network-message, each player only requires other's position and color (perhaps its remaining lives)
            10. According to latter, network-message requires a field containing the list of all players or a dictionary
                with required values with respect to keys (which are the names of those who have joined the game)
            Done: 11. Spaceship moving is completely the same as chicken invaders. It is not implemented using e.getX() and
                e.getY() anymore so enabling the player to move between tabs without experiencing any glitches meanwhile.
            12. Later on, spectators need to be able to join the game when a new wave or level is initiated.
            13. Joined players position is fixed and explodes all the time at the center.
            14. Joined players are not collecting the powerups.
            15. Powerups need have some effect!
            16. Giants shooting pattern needs to be implemented.
            17. Adding more giants to the game. (lack of time, as simple as possible)
            18. Collision check could be done using radial distance.
            ********>>
            19. Game Data file needs to be added.
            20. Adding the health and coins and score. for both S-player and M-player.
            ********<<
            21. Heat for burst-shots should NOT be increased proportional to the number of the shots.
            22. An enemy type had to shoot right to the spaceship and I haven't implemented it!
            22. Refer to this point that game has sound and video. (Even when objects are hit.)
            ********>>
            23. Checking spectator status and enabling them to join accordingly.
            24. Enabling Esc and Resuming of the game.
            ********<<
            25. Feature: Heat changes color of the engine.
         */

    }
}
