import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

class AnimatedPanelDemo {
    static class ImagePanel extends JPanel {
        private Image image;
        ImagePanel(Image image) {
            this.image = image;
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
        }
    }

    private static void createAndShowUI() {
        try {
            JFrame frame = new JFrame("Image");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(new File("./src/GameObjects.Spaceship.png"));

            ImagePanel imagePanel = new ImagePanel(image);

            imagePanel.add(new JLabel("Some label"));
            frame.add(imagePanel);
            frame.setSize(100, 100);
            frame.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
    }
}