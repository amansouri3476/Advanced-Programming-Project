package MovingBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ScrollingBackground extends Canvas implements Runnable {

    // Two copies of the background image to scroll
    private Background backOne;
    private Background backTwo;

    private BufferedImage back;

    public ScrollingBackground() throws IOException {
        backOne = new Background();
        backTwo = new Background(backOne.getImageWidth(), 0);

        Image cursorImage = new ImageIcon("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\saber_double2.png").getImage();
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "cursor"));

        new Thread(this).start();
        setVisible(true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        }
        catch (Exception e) {}
    }

    @Override
    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
        Graphics2D twoD = (Graphics2D)window;

        if (back == null)
            back = (BufferedImage)(createImage(getWidth(), getHeight()));

        // Create a buffer to draw to
        Graphics buffer = back.createGraphics();

        // Put the two copies of the background image onto the buffer
        backOne.draw(buffer);
        backTwo.draw(buffer);

        // Draw the image onto the window
        twoD.drawImage(back, null, 0, 0);

    }

}