package GameObjects;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class LoopSound implements Runnable {

    public String path;
    public boolean isContinuous;
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
        }
    }
}