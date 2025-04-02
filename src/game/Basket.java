package game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Basket {
    private String name;
    private String fileName;
    private BufferedImage image;
    private int x;                          // x position for Basket
    private int y;                          // y position for Basket
    private int width;                      // width of Basket image
    private int height;                     // height of basket image

    /*
     * Constructor
     * Image for basket will be selected with fileName when Basket is constructed
     */
    public Basket(String name, int x, int y, int width, int height, String fileName){
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // Initializes image from images folder. Image selected via fileName
        File pic = new File("images/" + fileName);
        try {
            image = ImageIO.read(pic);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("file not found");
        }
    }

    /*
     * Methods
     * Created methods here for movement
     * Implement these methods in GameManager
     * I've added some examples but you can create any method you wish to run each frame of the game
     */
    public void update(){

    }
    public void move(){

    }
    public void increaseSpeed(){

    }


    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
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
