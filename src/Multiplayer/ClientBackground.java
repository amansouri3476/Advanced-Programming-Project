package Multiplayer;

import GameObjects.Giant;
import Lists.*;
import Multiplayer.ClientGameEventHandler;
import Multiplayer.ClientScroll;
import Screen.GamePlayScrolling.GameEventHandler;
import Screen.GamePlayScrolling.ImageLoader;
import Screen.GamePlayScrolling.Scroll;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;

public class ClientBackground {
    private BufferedImage image;

    private int x;
    private int y;

    private BufferedImage logo;
    private BufferedImage destroyer;
    private BufferedImage destroyer_respawn;
    private BufferedImage heart;
    private BufferedImage rocket_small;
    private BufferedImage tieSmall;
    private BufferedImage engineBackground;
    private BufferedImage bulletImg;
    private BufferedImage bombImg;
    private BufferedImage death_star;
    private BufferedImage overheat;
    private BufferedImage fighter;
    private BufferedImage giantStarDestroyer;
    private BufferedImage giantCircular;
    private BufferedImage giantATT;
    private BufferedImage explosion;
    private BufferedImage spaceshipExplosion;
    private BufferedImage blasterFire;
    private BufferedImage powerupBlue;
    private BufferedImage powerupBrightBlue;
    private BufferedImage powerupCyan;
    private BufferedImage powerupGreen;
    private BufferedImage powerupPink;
    private BufferedImage powerupYellow;
    private BufferedImage powerupDouble;

    private BufferedImage waveLogoI;
    private BufferedImage waveLogoII;
    private BufferedImage waveLogoIII;
    private BufferedImage waveLogoIV;

    private BufferedImage safeZone;

    public static boolean showSafeZone=false;
    public static int SZcenterX;
    public static int SZcenterY;
    public static int SZcenterRadii;

    {
        try {
            logo = ImageLoader.imgLoader("logo");
            destroyer = ImageLoader.imgLoader("destroyer");
            destroyer_respawn = ImageLoader.imgLoader("destroyer_respawn");
            heart = ImageLoader.imgLoader("heart");
            rocket_small = ImageLoader.imgLoader("rocket_small");
            tieSmall = ImageLoader.imgLoader("tie_small");
            engineBackground = ImageLoader.imgLoader("status");
            bulletImg = ImageLoader.imgLoader("bullet");
            bombImg = ImageLoader.imgLoader("bullet");
            death_star = ImageLoader.imgLoader("death_star");
            overheat = ImageLoader.imgLoader("overheat");
            fighter = ImageLoader.imgLoader("fighter");
            giantStarDestroyer = ImageLoader.imgLoader("giant_star_destroyer");
            giantCircular = ImageLoader.imgLoader("giant_circular");
            giantATT = ImageLoader.imgLoader("giant_ATT");
            explosion = ImageLoader.imgLoader("explosion");
            spaceshipExplosion = ImageLoader.imgLoader("spaceship_explosion");
            blasterFire = ImageLoader.imgLoader("blasterFire");
            powerupBlue = ImageLoader.imgLoader("PU_Blue");
            powerupBrightBlue = ImageLoader.imgLoader("PU_BBlue");
            powerupCyan = ImageLoader.imgLoader("PU_Cyan");
            powerupGreen = ImageLoader.imgLoader("PU_Green");
            powerupPink = ImageLoader.imgLoader("PU_Pink");
            powerupYellow = ImageLoader.imgLoader("PU_Yellow");
            powerupDouble = ImageLoader.imgLoader("PU_Double");
            waveLogoI = ImageLoader.imgLoader("wave_I");
            waveLogoII = ImageLoader.imgLoader("wave_II");
            waveLogoIII = ImageLoader.imgLoader("wave_III");
            waveLogoIV = ImageLoader.imgLoader("wave_IV");
            safeZone = ImageLoader.imgLoader("safe_zone");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ClientBackground() throws IOException {
        this(0,0);
    }

    public ClientBackground(int x, int y) {
        this.x = x;
        this.y = y;

        // Try to open the image file background.png
        try {
            image = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Nebula_Red.png"));
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
        window.setColor(Color.BLUE);
        window.fillRoundRect(10, 10, 500, 50, 50, 50);
        window.setColor(Color.getHSBColor(0, 0.2f + 0.6f * (ListOfBullets.heat)/100, 0.5f));
        window.fillRoundRect(10, 10, (int) (ListOfBullets.heat * 5), 50, 50, 50);

        //////////// Moving Background ////////////

//         Move the x position left for next time
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

        //////////// Moving Background Finished ////////////

        Graphics2D g = (Graphics2D) window;

//        BufferedImage i = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\GameAssets\\star_wars_logo_PNG36.png"));
//        g.drawImage(logo, null, 20, 300);
//        g.drawImage(death_star, null, 1000, 250);
        if (!ClientGameEventHandler.spaceship.isExploded){
            g.drawImage(destroyer, null, ClientGameEventHandler.spaceship.x_coordinate, ClientGameEventHandler.spaceship.y_coordinate);
        }
        else {
            g.drawImage(destroyer_respawn, null, ClientGameEventHandler.spaceship.x_coordinate, ClientGameEventHandler.spaceship.y_coordinate);
        }
        g.drawImage(engineBackground, null, 10, 700);
        g.drawImage(heart, null, 25, 725);
        g.drawImage(rocket_small, null, 95, 725);
        g.drawImage(tieSmall, null, 205, 720);


        if (showSafeZone){
            window.setColor(Color.green);
            window.drawOval(SZcenterX - SZcenterRadii + 15, SZcenterY - SZcenterRadii + 15, 2 * SZcenterRadii - 5, 2 * SZcenterRadii - 5);
            g.drawImage(safeZone, null, SZcenterX - 100, SZcenterY);
        }

        if (!ListOfBullets.Bullets.isEmpty()){
            AtomicInteger bulletCounter = new AtomicInteger(0);
            synchronized (ListOfBullets.Bullets){
                ListOfBullets.Bullets.forEach(bullet -> {
                    bulletCounter.getAndIncrement();
                    g.drawImage(bulletImg, null, bullet.x_coordinate, bullet.y_coordinate);

                });
            }
        }

        if (!BombList.Bombs.isEmpty()){
            AtomicInteger bombCounter = new AtomicInteger(0);
            synchronized (BombList.Bombs){
                BombList.Bombs.forEach(bomb -> {
                    bombCounter.getAndIncrement();
                    AffineTransform old = g.getTransform();
                    double theta = bomb.directionAngle;
                    g.rotate(theta + Math.PI/2, bomb.x_coordinate + 20, bomb.y_coordinate + 20);
                    g.drawImage(bombImg, null, bomb.x_coordinate, bomb.y_coordinate);
                    g.setTransform(old);
                });
            }
        }

        if (!ListOfPowerups.Powerups.isEmpty()){
            AtomicInteger powerupCounter = new AtomicInteger(0);
            synchronized (ListOfPowerups.Powerups){
                ListOfPowerups.Powerups.forEach(powerup -> {
                    powerupCounter.getAndIncrement();
                    BufferedImage puImg = powerupIdentifier(powerup.type);
                    g.drawImage(puImg, null, powerup.x_coordinate, powerup.y_coordinate);

                });
            }
        }

        if (!ListOfFirings.Firings.isEmpty()){
            AtomicInteger fireCounter = new AtomicInteger(0);
            synchronized (ListOfFirings.Firings){
                ListOfFirings.Firings.forEach(fire -> {
                    fireCounter.getAndIncrement();
                    g.drawImage(blasterFire, null, fire.x_coordinate, fire.y_coordinate);

                });
            }
        }

        if (!ListOfEnemies.Enemies.isEmpty()){
            AtomicInteger enemyCounter = new AtomicInteger(0);
            synchronized (ListOfEnemies.Enemies){
                ListOfEnemies.Enemies.forEach(enemy -> {
                    enemyCounter.getAndIncrement();
                    g.drawImage(fighter, null, enemy.x_coordinate, enemy.y_coordinate);

                });
            }
        }

        if (!ListOfGiants.Giants.isEmpty()){
            AtomicInteger giantCounter = new AtomicInteger(0);
            synchronized (ListOfGiants.Giants){
                ListOfGiants.Giants.forEach(giant -> {
                    giantCounter.getAndIncrement();
                    g.drawImage(giantTypeIdentifier(giant), null, giant.x_coordinate, giant.y_coordinate);
                });
            }
        }

        if (!ListOfExplosions.Explosions.isEmpty()){
            AtomicInteger explosionCounter = new AtomicInteger(0);
            synchronized (ListOfExplosions.Explosions){
                ListOfExplosions.Explosions.forEach(enemy -> {
                    explosionCounter.getAndIncrement();
                    if (enemy.explosionTimer < 600){
                        g.drawImage(explosion, null, enemy.x_coordinate, enemy.y_coordinate);
                    }

                });
            }
        }

        if (ClientGameEventHandler.spaceship.isExploded){
            if (ClientGameEventHandler.spaceship.explosionTimer < 4000){
                g.drawImage(spaceshipExplosion, null, ClientGameEventHandler.spaceship.explosionX, ClientGameEventHandler.spaceship.explosionY);
            }
        }

        if (ClientScroll.isOverheated){
//            LoopSound loopSound = new LoopSound("src/imperial_march.wav", false);
            g.drawImage(overheat, null, 400, 300);
//            window.fillRoundRect(750, 300, 100, 50, 50, 50);
//            window.setColor(Color.getHSBColor(0, 0.2f + 0.6f, 0.5f));
        }

    }

    private BufferedImage giantTypeIdentifier(Giant giant) {
        if (giant.type.equals("starDestroyer")){
            return giantStarDestroyer;
        }
        if (giant.type.equals("circular")){
            return giantCircular;
        }
        if (giant.type.equals("ATT")){
            return giantATT;
        }
        else {
            return giantStarDestroyer;
        }
    }

    private BufferedImage powerupIdentifier(int type) {
        if (type == 1){
            return powerupBlue;
        }
        if (type == 2){
            return powerupBrightBlue;
        }
        if (type == 3){
            return powerupCyan;
        }
        if (type == 4){
            return powerupGreen;
        }
        if (type == 5){
            return powerupPink;
        }
        if (type == 6){
            return powerupYellow;
        }
        else return powerupDouble;
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

    @Override
    public String toString() {
        return "Background{" +
                "image=" + image +
                '}';
    }
}