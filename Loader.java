import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Loader {
    Loader(String directory){
        try (ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(directory))){
            Object obj = objInput.readObject();
            if (obj instanceof Player){
                Player retrievedPlayer = (Player) obj;
                System.out.println("Retrieved " + retrievedPlayer.getUserName());
            }
        }
        catch (Exception e){
            System.out.println("Exception thrown reading serialized object: " + e.getMessage());
        }
    }

}
