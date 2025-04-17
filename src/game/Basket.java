package game;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;

public class Basket {
    private String name;       // Basket name
    private String fileName;   // Image file name
    private Image image;       // Basket image
    private int x;             // X position of the basket
    private int y;             // Y position of the basket
    private int width;         // Width of the basket
    private int height;        // Height of the basket
    private int speed = 5;

    // Constructor to initialize the basket
    public Basket(String name, int x, int y, int width, int height, String fileName) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fileName = fileName;

        // Load the basket image
        URL imageUrl = getClass().getClassLoader().getResource("images/" + fileName);
        if (imageUrl == null) {
            System.out.println("Basket image not found: images/" + fileName);
            // if found, confirm it and load the image
        } else {
            image = new ImageIcon(imageUrl).getImage();
        }
    }

    public void moveLeft() {
        if (x > 0) {  // Prevent moving out of bounds
            x -= speed;
        }
    }

    public void moveRight() {
        if (x + width < gameConstants.Constants.FRAME_WIDTH) {  // Prevent moving out of bounds
            x += speed;
        }
    }

    // Update basket (done in future updates)
    public void update() {
    }

    // Move basket 
    public void move() {
    }

    // Increase basket speed 
    public void increaseSpeed() {
    }

    // Getter for basket name
    public String getName() {
        return name;
    }

    // Setter for basket name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for file name
    public String getFileName() {
        return fileName;
    }

    // Setter for file name
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public Image getImage() {
        return image;
    }

    // Setter for basket image
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    // Getter for x position
    public int getX() {
        return x;
    }

    // Setter for x position
    public void setX(int x) {
        this.x = x;
    }

    // Getter for y position
    public int getY() {
        return y;
    }

    // Setter for y position
    public void setY(int y) {
        this.y = y;
    }

    // Getter for basket width
    public int getWidth() {
        return width;
    }

    // Setter for basket width
    public void setWidth(int width) {
        this.width = width;
    }

    // Getter for basket height
    public int getHeight() {
        return height;
    }

    // Setter for basket height
    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    
}

