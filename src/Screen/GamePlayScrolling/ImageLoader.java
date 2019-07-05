package Screen.GamePlayScrolling;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage imgLoader(String name) throws IOException {
        if(name.equals("logo")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\star_wars_logo_PNG36.png"));
        }
        if (name.equals("destroyer"))
        {
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Spaceship.png"));
        }
        if (name.equals("destroyer_respawn"))
        {
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Spaceship_Respawn.png"));
        }
        if (name.equals("heart"))
        {
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\Webp.net-resizeimage.png"));
        }
        if (name.equals("rocket_small")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\rocket_small.png"));
        }
        if (name.equals("tie_small")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\TIE_small.png"));
        }
        if (name.equals("status")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\rtlbo2.png"));
        }
        if (name.equals(("bullet"))){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\shuttle.png"));
        }
        if (name.equals("death_star")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\death_star.png"));
        }
        if (name.equals("overheat")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\overheat.png"));
        }
        if (name.equals("fighter")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\TIE_small.png"));
        }
        if (name.equals("giant_star_destroyer")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\giant_star_destroyer.png"));
        }
        if (name.equals("giant_circular")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\giant_star_destroyer.png"));
        }
        if (name.equals("giant_ATT")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\giant_star_destroyer.png"));
        }
        if (name.equals("explosion")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\explosion2.png"));
        }
        if (name.equals("spaceship_explosion")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\explosion3.png"));
        }
        if (name.equals("blasterFire")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\blaster.png"));
        }
        if (name.equals("PU_Blue")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\saber_blue.png"));
        }
        if (name.equals("PU_BBlue")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\saber_bright_blue.png"));
        }
        if (name.equals("PU_Cyan")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\saber_cyan.png"));
        }
        if (name.equals("PU_Green")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\saber_green.png"));
        }
        if (name.equals("PU_Pink")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\saber_pink.png"));
        }
        if (name.equals("PU_Yellow")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\saber_yellow.png"));
        }
        if (name.equals("PU_Double")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\saber_double.png"));
        }
        if (name.equals("wave_I")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\wave_I.png"));
        }
        if (name.equals("wave_II")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\wave_II.png"));
        }
        if (name.equals("wave_III")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\wave_III.png"));
        }
        if (name.equals("wave_IV")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\wave_IV.png"));
        }
        if (name.equals("safe_zone")){
            return ImageIO.read(new File("C:\\Users\\Amin\\IdeaProjects\\StarWars\\src\\GameAssets\\safe_zone.png"));
        }
        return null;
    }
}
