import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;

public class LoopSound implements Runnable {

    public String path;
    public boolean isContinuous;
    public Clip clip;
    public LoopSound(String path, boolean isContinuous){
        this.path = path;
        this.isContinuous = isContinuous;
        this.run();
    }

    @Override
    public void run() {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            this.clip = clip;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        // getAudioInputStream() also accepts a File or InputStream
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(new File(path));
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
        if (isContinuous){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        else {
            clip.start();
            clip.stop();
        }
    }
}