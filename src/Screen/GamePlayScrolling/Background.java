package Screen.GamePlayScrolling;

import GameObjects.Spaceship;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Background {
    private BufferedImage image;

    private int x;
    private int y;

//    private BufferedImage logo;
//
//    {
//        try {
//            logo = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\star_wars_logo_PNG36.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private BufferedImage death_star;
//
//    {
//        try {
//            death_star = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\death_star.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public Background() throws IOException {
        this(0,0);
    }

    public Background(int x, int y) {
        this.x = x;
        this.y = y;

        // Try to open the image file background.png
        try {
            image = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\corona_rt.png"));
        }
        catch (Exception e) { System.out.println(e); }

    }

    /**
     * Method that draws the image onto the Graphics object passed
     * @param window
     */
    public void draw(Graphics window) {

        // Draw the image onto the Graphics reference
        window.drawImage(image, getX(), getY(), image.getWidth(), image.getHeight(), null);

        // Move the x position left for next time
        this.x -= 5;
//        this.y -= 5;

        // Check to see if the image has gone off stage left
        if (this.x <= -1 * image.getWidth()) {

            // If it has, line it back up so that its left edge is
            // lined up to the right side of the other background image
            this.x = this.x + image.getWidth() * 2;
        }

        // Check to see if the image has gone off stage left
        if (this.y <= -1 * image.getHeight()) {

            // If it has, line it back up so that its left edge is
            // lined up to the right side of the other background image
            this.y = this.y + image.getHeight() * 2;
        }
        Graphics2D g = (Graphics2D) window;

//        g.drawImage(Spaceship.spaceshipLabel, null, Spaceship.spaceshipLabel.getX(), Spaceship.spaceshipLabel.getY());
//        BufferedImage i = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\GameAssets\\star_wars_logo_PNG36.png"));
//        g.drawImage(logo, null, 20, 300);
//        g.drawImage(death_star, null, 1000, 250);


//        try {
//            Graphics2D g = (Graphics2D) window;
//            BufferedImage i = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\death_star.png"));
//            g.drawImage(i, null, 1000, 250);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getImageWidth() {
        return image.getWidth();
    }

//    public String toString() {
//        return "Background: x=&amp;amp;amp;quot; + getX() + ", art=" + getY() + ", height=" + image.getHeight() + ", width=" + image.getWidth()";
//    }

    @Override
    public String toString() {
        return "Background{" +
                "image=" + image +
                '}';
    }
}