package Lists;

import Lists.ListOfUsers;

import javax.swing.*;
import java.io.Serializable;

public class Player implements Serializable {
    int score;
    double x_coordinate, y_coordinate;
    private String userName;
    public boolean canResume;
    JLabel spaceshipLabel;
    public Player(String name){
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
}
