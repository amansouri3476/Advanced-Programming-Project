package Lists;

import java.util.ArrayList;

public class ListOfUsers {
    public static ArrayList<String> Players = new ArrayList<>();
    public static ArrayList<Player> PlayersObj = new ArrayList<>();
    public static String selectedUser;
    ListOfUsers(){
        Players.add(null);
    }
    public static ArrayList<String> getListOfPlayers(){
        return (ArrayList<String>) Players;
    }
    public static Player getPlayerByUsername(String username){
        int idx = Players.indexOf(username);
        return ListOfUsers.PlayersObj.get(idx);
    }
}
