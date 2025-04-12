package game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import gameConstants.Constants;
public class GameManager {

    /*
     * This class is where all game assets are added and their positons. ie. basket, falling eggs, score tracker etc.
     */

     private Basket basket;
     private EggSpawner eggSpawner;
     private int score = 0;  //  Track the player's score
     private boolean isGameOver = false;
     private int lives = 3;
     private BufferedImage heartImage;

     // 🚀 Detects if the basket catches an egg
    private boolean checkCollision(Egg egg, Basket basket) {
        return egg.getX() < basket.getX() + basket.getWidth() &&
        egg.getX() + 40 > basket.getX() && // 40 = egg width
        egg.getY() + 50 > basket.getY() && // 50 = egg height
        egg.getY() < basket.getY() + basket.getHeight();
    }


     public GameManager(){

    }

    // Runs once on startup. Instantiate game objects here
    public void start(){
        basket = new Basket("Player One", Constants.BASKET_X, Constants.BASKET_Y, Constants.BASKET_WIDTH, Constants.BASKET_HEIGHT, "basket_01.png");
        eggSpawner = new EggSpawner();

        // Load heart image for representing lives
        try {
            heartImage = ImageIO.read(new File("src/images/redHeart.png"));
        } catch (IOException e) {
            System.err.println(" Could not load heart image redHeart.png");
            e.printStackTrace(); // Log the error if the image can't be loaded
        }
    }

    public void drawSprites(Graphics2D graphics, JPanel panel) {
        // Draws basket
        graphics.drawImage(basket.getImage(), basket.getX(), basket.getY(), basket.getWidth(), basket.getHeight(), panel);

        // Draw Eggs
        for (Egg e : eggSpawner.eggList) {
            graphics.drawImage(e.getEggImage(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), panel);
        }

        // 🏆 Display Score on Screen
        graphics.setColor(java.awt.Color.BLACK);
        graphics.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        graphics.drawString("Score: " + score, 20, 30);

        if (isGameOver) {
            graphics.setColor(java.awt.Color.RED);
            graphics.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36));

            // centering the game over text
            String gameOverText = "Game Over!!!";
            FontMetrics fm = graphics.getFontMetrics();
            int textWidth = fm.stringWidth(gameOverText);
            int x = (Constants.FRAME_WIDTH - textWidth) / 2;
            int y = Constants.FRAME_HEIGHT / 2;

            graphics.drawString(gameOverText, x, y);
        } else {
            graphics.setColor(java.awt.Color.BLACK);
            graphics.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));

            int paddingRight = 20;// adding space to the right border
            int paddingTop = 20;// adding spacing form the top
            int spacing = 10; // space between hearts

            if(heartImage !=null) {
                for (int i = 0; i < lives; i++) {
                    int x = Constants.FRAME_WIDTH - paddingRight - ((i + 1) * Constants.HEART_SIZE) - (i * spacing);
                    int y = paddingTop;

                    graphics.drawImage(
                            heartImage,
                            x,
                            y,
                            Constants.HEART_SIZE,
                            Constants.HEART_SIZE,
                            panel
                    );
                }
            }else{
                graphics.drawString("❤️ x " + lives, Constants.FRAME_WIDTH - 100, paddingTop + 20);
            }
        }
    }

    /*
     * Updates game objects
     * Objects will render in order placed within method. Each object in front of previous
     */
    public void update() {
        eggSpawner.update();
        basket.update();
        updateCollision();

        if (lives <= 0) {
            isGameOver = true;
        }
    }


    // Checks if egg collides with basket, delets egg, score ++
    public void updateCollision(){
        // Move eggs down and check for collision
        for (int i = 0; i < eggSpawner.getEggList().size(); i++) {
            Egg egg = eggSpawner.getEggList().get(i);
            egg.update();

            if (checkCollision(egg, basket)) {
                score += 10;  // 🏆 Increase score
                eggSpawner.getEggList().remove(i); // Remove egg from list
                i--; // Adjust index after removal
            } else if (egg.getY() > Constants.FRAME_HEIGHT) {
            //  Egg missed, life will be reduced
            lives--;
            eggSpawner.getEggList().remove(i); // Remove missed egg
            i--; //  index is adjusted after removal
        }
        }
    }

    public Basket getBasket() {
        return basket;
    }

    public int getScore() {
        return score;
    }
    public boolean isGameOver() {
        return isGameOver;
    }
}