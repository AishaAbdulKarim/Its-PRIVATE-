package game;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Egg {
    private int x;
    private int y;
    private int speed = 2;
    private Image eggImage;

    public Egg(int x, int y, String imageName) {
        this.x = x;
        this.y = y;
        eggImage = new ImageIcon("images/egg_01.png" + imageName).getImage();
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics2D graphics, JPanel panel) {
        graphics.drawImage(eggImage, x, y, 40, 50, panel);
    }

    public int getY() {
        return y;
    }
}