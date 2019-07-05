package Lists;

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
    double x_coordinate, y_coordinate;
    private String userName;
    public boolean canResume;
    JLabel spaceshipLabel;
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
    public double getX_coordinate(){
        return x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }

    public static void addProperties(JSONObject jsonPlayer, Player player){
        jsonPlayer.put("x_coordinate", GameEventHandler.spaceship.x_coordinate);
        jsonPlayer.put("y_coordinate", GameEventHandler.spaceship.y_coordinate);
        jsonPlayer.put("score", player.score);

    }
}
