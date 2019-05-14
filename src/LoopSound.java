import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;

public class LoopSound implements Runnable {

    LoopSound(){
        this.run();
    }

    @Override
    public void run() {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        // getAudioInputStream() also accepts a File or InputStream
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(new File("src/imperial_march.wav"));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip.open(ais);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}