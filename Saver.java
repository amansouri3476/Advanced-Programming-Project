import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Saver {
    Saver(String directory, Object object){
        try (ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(directory))) {
            System.out.println("Serializing");
            objOutput.writeObject(object);
        }
        catch (IOException ioe){
            System.out.println("IO Exception Thrown writing serialized object: " + ioe.getMessage());
        }
    }
}
