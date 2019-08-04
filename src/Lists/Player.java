package Lists;

import GameObjects.Spaceship;
import Screen.GamePlayScrolling.GameEventHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Player implements Serializable {
    public JSONObject player=new JSONObject();
    public static JSONArray players=new JSONArray();

    public int score;
    public int x_coordinate, y_coordinate;
    public Spaceship spaceship;
    private String userName;
    public boolean canResume;
    public boolean isExploded = false;
    public boolean isSpectator;
    public boolean isServer = true;
    transient JLabel spaceshipLabel;
    public Player(String name){
        player.put("name",name);
        players.add(player);
        //Write JSON file
        try (FileWriter file = new FileWriter("players.json")) {

            file.write(players.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        userName = name;
        ListOfUsers.Players.add(name);
        ListOfUsers.PlayersObj.add(this);
        score = 0;
        canResume = false;
    }

    public String getUserName(){
        return userName;
    }
    public int getX_coordinate(){
        return x_coordinate;
    }

    public int getY_coordinate() {
        return y_coordinate;
    }

    public static void addProperties(JSONObject jsonPlayer, Player player){
        jsonPlayer.put("x_coordinate", GameEventHandler.spaceship.x_coordinate);
        jsonPlayer.put("y_coordinate", GameEventHandler.spaceship.y_coordinate);
        jsonPlayer.put("score", player.score);

    }
}
