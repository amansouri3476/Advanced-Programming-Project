package Screen.GamePlayScrolling;

import GameObjects.Giant;
import GameObjects.Gun;
import GameObjects.Spaceship;
import Lists.*;
import Multiplayer.ClientGameEventHandler;
import Multiplayer.ClientScroll;
import Multiplayer.GameServer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;

import static Multiplayer.GameServer.isMultiplayer;

public class Background {
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
    private BufferedImage powerupR2D2;
    private BufferedImage powerupBurst;

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
            powerupR2D2 = ImageLoader.imgLoader("r2d2");
            powerupBurst = ImageLoader.imgLoader("burst");
            waveLogoI = ImageLoader.imgLoader("wave_I");
            waveLogoII = ImageLoader.imgLoader("wave_II");
            waveLogoIII = ImageLoader.imgLoader("wave_III");
            waveLogoIV = ImageLoader.imgLoader("wave_IV");
            safeZone = ImageLoader.imgLoader("safe_zone");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Background() throws IOException {
        this(0,0);
    }

    public Background(int x, int y) {
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
        window.fillRoundRect(10, 10, GameEventHandler.spaceship.gun.heatLimit * 5, 50, 50, 50);
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

        //////////// Displaying player's score ////////////
        Graphics2D g = (Graphics2D) window;
        g.setFont(new Font("Georgia", Font.ITALIC, 42));
        g.setColor(Color.ORANGE);
        g.drawString(ListOfUsers.selectedUser + ": " + ListOfUsers.getPlayerObjByUsername(ListOfUsers.selectedUser).score, 20, 100);
        ///////////////////////////////////////////////////

//        BufferedImage i = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\ChickenInvaders\\src\\GameAssets\\star_wars_logo_PNG36.png"));
//        g.drawImage(logo, null, 20, 300);
//        g.drawImage(death_star, null, 1000, 250);

        //////////////////////// Drawing Player(s) Spaceships ///////////////////////////
        {
            if (!isMultiplayer){
                if (!GameEventHandler.spaceship.isExploded){
                    g.drawImage(destroyer, null, GameEventHandler.spaceship.x_coordinate, GameEventHandler.spaceship.y_coordinate);

                    // Displaying player's Name
                    g.setFont(new Font("Georgia", Font.ITALIC, 32));
                    g.setColor(Color.ORANGE);
                    g.drawString(ListOfUsers.selectedUser, GameEventHandler.spaceship.x_coordinate + 20, GameEventHandler.spaceship.y_coordinate + 100);
                }
                else {
                    g.drawImage(destroyer_respawn, null, GameEventHandler.spaceship.x_coordinate, GameEventHandler.spaceship.y_coordinate);
                }
                g.drawImage(engineBackground, null, 10, 700);
                g.drawImage(heart, null, 25, 725);
                g.drawImage(rocket_small, null, 95, 725);
                g.drawImage(tieSmall, null, 205, 720);
            }
            else {
                //////////////// Server Spaceship and Details ////////////////
                if (!GameEventHandler.spaceship.isExploded){
                    g.drawImage(destroyer, null, GameEventHandler.spaceship.x_coordinate, GameEventHandler.spaceship.y_coordinate);

                    // Displaying player's Name
                    g.setFont(new Font("Georgia", Font.ITALIC, 32));
                    g.setColor(Color.ORANGE);
                    g.drawString(ListOfUsers.selectedUser, GameEventHandler.spaceship.x_coordinate + 20, GameEventHandler.spaceship.y_coordinate + 100);
                }
                else {
                    g.drawImage(destroyer_respawn, null, GameEventHandler.spaceship.x_coordinate, GameEventHandler.spaceship.y_coordinate);
                    g.setFont(new Font("Georgia", Font.ITALIC, 32));
                    g.setColor(Color.ORANGE);
                    g.drawString(ListOfUsers.selectedUser, GameEventHandler.spaceship.x_coordinate + 20, GameEventHandler.spaceship.y_coordinate + 100);
                }
                g.drawImage(engineBackground, null, 10, 700);
                g.drawImage(heart, null, 25, 725);
                g.drawImage(rocket_small, null, 95, 725);
                g.drawImage(tieSmall, null, 205, 720);
                //////////////// Other Players' Spaceships ////////////////
                for (Player player: GameServer.joinedPlayersObjects){
                    if (player.getUserName().equals(ListOfUsers.selectedUser)){
                        // So as not to draw server's spaceship twice
                    }
                    else {
                        try{
                            if (!player.spaceship.isExploded && !player.isSpectator){
                                g.drawImage(destroyer, null, player.x_coordinate, player.y_coordinate);

                                // Displaying player's Name
                                g.setFont(new Font("Georgia", Font.ITALIC, 32));
                                g.setColor(Color.GREEN);
                                g.drawString(player.getUserName(), player.x_coordinate + 20, player.y_coordinate + 100);
                                g.drawString(player.x_coordinate + " ," + player.y_coordinate, player.x_coordinate, player.y_coordinate);
                            }
                            else if (player.spaceship.isExploded){
                                g.drawImage(destroyer_respawn, null, player.x_coordinate, player.y_coordinate);
                                g.setFont(new Font("Georgia", Font.ITALIC, 32));
                                g.setColor(Color.GREEN);
                                g.drawString(player.getUserName(), player.x_coordinate + 20, player.y_coordinate + 100);
                            }
                            //TODO: Waiting to Join the game
                            else {
//                        // Should include a condition so as to draw just when 'join request' is sent.
//                        g.drawImage(destroyer_respawn, null, player.x_coordinate, player.y_coordinate);
//                        g.setFont(new Font("Georgia", Font.ITALIC, 32));
//                        g.setColor(Color.GREEN);
//                        g.drawString(player.getUserName(), player.x_coordinate + 20, player.y_coordinate + 100);
                            }

                        }
                        catch (NullPointerException e){

                        }
                    }
                }
            }

        }


        if (showSafeZone){
            window.setColor(Color.green);
            window.drawOval(SZcenterX - SZcenterRadii + 15, SZcenterY - SZcenterRadii + 15, 2 * SZcenterRadii - 5, 2 * SZcenterRadii - 5);
            g.drawImage(safeZone, null, SZcenterX - 100, SZcenterY);
        }

        if (!ListOfBullets.Bullets.isEmpty()){
            AtomicInteger bulletCounter = new AtomicInteger(0);
            synchronized (ListOfBullets.Bullets){
                ListOfBullets.Bullets.forEach(bullet -> {
                    BufferedImage bulletTypeImage = bulletTypeIdentifier(bullet.type);
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
                    BufferedImage puImg = powerupIdentifier(powerup.category, powerup.type);
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

        //////////////////////// Drawing Player(s) EXPLODED Spaceships ///////////////////////////
        {
            if (GameEventHandler.spaceship.isExploded) {
                if (GameEventHandler.spaceship.explosionTimer < GameEventHandler.spaceship.explosionTimerLimit) {
                    g.drawImage(spaceshipExplosion, null, GameEventHandler.spaceship.explosionX, GameEventHandler.spaceship.explosionY);
                }
            }
            if (isMultiplayer){
                for (Player player: GameServer.joinedPlayersObjects){
                    if (player.getUserName().equals(ListOfUsers.selectedUser)){
                        // So as not to draw server's spaceship twice
                    }
                    else {
                        try {
                            if (player.spaceship.isExploded){
                                if (player.spaceship.explosionTimer < player.spaceship.explosionTimerLimit)
                                    g.drawImage(spaceshipExplosion, null, player.spaceship.explosionX - 100, player.spaceship.explosionY - 130);
                            }
                        }
                        catch (NullPointerException e){

                        }
                    }
                }
            }
        }

        if (Scroll.isOverheated){
//            LoopSound loopSound = new LoopSound("src/imperial_march.wav", false);
            g.drawImage(overheat, null, 400, 300);
//            window.fillRoundRect(750, 300, 100, 50, 50, 50);
//            window.setColor(Color.getHSBColor(0, 0.2f + 0.6f, 0.5f));
        }

        if (Scroll.waveIndexDraw){
            if (Scroll.waveIndex == 1){
                g.drawImage(waveLogoI, null, 500, 300);
            }
            if (Scroll.waveIndex == 2){
                g.drawImage(waveLogoII, null, 500, 300);
            }
            if (Scroll.waveIndex == 3){
                g.drawImage(waveLogoIII, null, 500, 300);
            }
            if (Scroll.waveIndex == 4){
                g.drawImage(waveLogoIV, null, 500, 300);
            }
        }


//        try {
//            Graphics2D g = (Graphics2D) window;
//            BufferedImage i = ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\death_star.png"));
//            g.drawImage(i, null, 1000, 250);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    private BufferedImage bulletTypeIdentifier(int type){
        if (type == 1){
            // Blue Saber
            return powerupBlue;
        }
        if (type == 2){
            // Bright Blue Saber
            return powerupBrightBlue;
        }
        if (type == 3){
            // Cyan Saber
            return powerupCyan;
        }
        if (type == 4){
            // Green Saber
            return powerupGreen;
        }
        if (type == 5){
            // Pink Saber
            return powerupPink;
        }
        if (type == 6){
            // Yellow Saber
            return powerupYellow;
        }
        if (type == 7){
            // Double Saber
            return powerupDouble;
        }
        else return bulletImg;
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

    private BufferedImage powerupIdentifier(String category, int type) {
        BufferedImage powerupImg = tieSmall;
        if (category.equals("II")){
            if (type == 1){
                powerupImg = powerupBlue;
            }
            if (type == 2){
                powerupImg = powerupBrightBlue;
            }
            if (type == 3){
                powerupImg = powerupCyan;
            }
            if (type == 4){
                powerupImg = powerupGreen;
            }
            if (type == 5){
                powerupImg = powerupPink;
            }
            if (type == 6){
                powerupImg = powerupYellow;
            }
            if (type == 7){
                powerupImg = powerupDouble;
            }
        }
        else {
            if (type == 1){
                powerupImg = powerupR2D2;
            }
            if (type == 2){
                powerupImg = powerupBurst;
            }
        }
        return powerupImg;
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