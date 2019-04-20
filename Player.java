import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    int score;
    double x_coordinate, y_coordinate;
    private String userName;
    public boolean canResume;
    Player(String name){
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
