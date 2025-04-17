package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Egg { // Defines the Egg class
    private int x; // Horizontal position of the egg
    private int y; // Vertical position of the egg
    private int width = 40; // Egg sprite width
    private int height = 50; // Egg sprite height
    private int speed = 3; // Speed at which the egg falls
    private Image eggImage; // Image used to display the egg

    // Constructor to initialize the egg's position and load its image
    public Egg(int x, int y, int width, int height, String imageName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        URL imageUrl = getClass().getClassLoader().getResource("images/" + imageName);
        if (imageUrl == null) {
            System.out.println("Egg image not found: images/" + imageName);
            // if found, confirm it and load the image
        } else {
            eggImage = new ImageIcon(imageUrl).getImage();
        }
    }

    // Updates the egg's vertical position to make it fall
    public void update() {
        y += speed; // Move the egg down by 'speed' pixels
    }

    // Draws the egg image on the screen
    public void draw(Graphics2D graphics, JPanel panel) {
        if (eggImage != null) { // Only draw if the image was successfully loaded
            graphics.drawImage(eggImage, x, y, this.width, this.height, panel); // Draw egg at (x, y) with width 40 and height 50
        }
    }

    // Returns the current y-coordinate of the egg (used for checking collisions or out-of-bounds)
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Image getEggImage() {
        return eggImage;
    }

    public void setEggImage(Image eggImage) {
        this.eggImage = eggImage;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    
}
