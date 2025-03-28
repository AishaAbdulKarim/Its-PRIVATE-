package game;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.net.URL;

public class Egg {
    private int x;
    private int y;
    private int speed = 2;
    private Image eggImage;

    public Egg(int x, int y, String imageName) {
        this.x = x;
        this.y = y;

        URL imageUrl = getClass().getClassLoader().getResource("images/" + imageName);
        if (imageUrl == null) {
            System.out.println("Egg image not found: images/" + imageName);
        } else {
            System.out.println("Egg image loaded: " + imageUrl);
            eggImage = new ImageIcon(imageUrl).getImage();
        }
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics2D graphics, JPanel panel) {
        if (eggImage != null) {
            graphics.drawImage(eggImage, x, y, 40, 50, panel);
        }
    }

    public int getY() {
        return y;
    }
}
