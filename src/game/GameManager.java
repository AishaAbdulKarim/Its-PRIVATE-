package game;
import gameConstants.Constants;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class GameManager {

    /*
     * This class is where all game assets are added and their positons. ie. basket, falling eggs, score tracker etc.
     */

     private Basket basket;
     private EggSpawner eggSpawner;
     private int score = 0;  //  Track the player's score
     private boolean isGameOver = false;//Tracks if the player has lost all lives
     private int lives = 3;// Tracks the player's  live
     private BufferedImage heartImage;// store heart image for life display

     private int currentPlayer = 1; 
     private int player1Score = 0; 
     private int player2Score = 0; 
     private boolean isMultiplayer = true; 
     private boolean waitingForPlayer2Start = false; 


     public GameManager(){

    }

    // Runs once on startup. Instantiate game objects here
    public void start(){
        basket = new Basket("Player One", Constants.BASKET_X, Constants.BASKET_Y, Constants.BASKET_WIDTH, Constants.BASKET_HEIGHT, "basket_01.png");
        eggSpawner = new EggSpawner();

        score = 0; 
        lives = 3; 
        isGameOver = false; 
        waitingForPlayer2Start = false;
        currentPlayer = 1; // Starts with Player 1

        // try to Load heart image for representing lives
        try {
            heartImage = ImageIO.read(new File("src/images/redHeart.png"));
        } catch (IOException e) {
            System.err.println(" Could not load heart image redHeart.png");
            e.printStackTrace(); // Log the error if the image can't be loaded
        }
    }

    public void update() {
        if (isGameOver) return; // If the game is over, stop updating the game

        eggSpawner.update(); // Update the state of the eggs
        basket.update(); // Update the state of the basket
        updateCollision(); // Check for collisions between the basket and the eggs

        if (lives <= 0) { // Check if lives have reached zero
            if (isMultiplayer && currentPlayer == 1 && !waitingForPlayer2Start) {
                player1Score = score; // Save Player 1's score
                waitingForPlayer2Start = true; // Set the flag to wait for Player 2
                isGameOver = true; // End the game
                return;
            }
            if (!waitingForPlayer2Start && currentPlayer == 2) {
                player2Score = score; // Save Player 2's score
                isGameOver = true; // End the game
            }
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

        loseState(graphics, panel);
        
    }

    /*
     * Updates game objects
     * Objects will render in order placed within method. Each object in front of previous
     */
    public void update() {
        eggSpawner.update();
        basket.update();
        updateCollision();

        if (lives <= 0) {// if player has no lives left, trigger game over
            isGameOver = true;
        }
    }

    public void loseState(Graphics2D g, JPanel p){
        if (isGameOver) {// If the player has lost all lives, the "Game Over" message is shown
            g.setColor(java.awt.Color.RED);// Set the text color to red to indicate game over
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36)); // Use a bold, large font for the game over message

            // centering the game over text
            String gameOverText = "Game Over!!!";
            // Measure the text width so we can center it horizontally
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(gameOverText);
            // Calculating the x-position to center the text on the screen
            int x = (Constants.FRAME_WIDTH - textWidth) / 2;
            int y = Constants.FRAME_HEIGHT / 2;
            g.drawString(gameOverText, x, y);

        } else {// else if game is ongoing, draw hearts representing remaining lives
            g.setColor(java.awt.Color.BLACK);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));

            int paddingRight = 20;// adding space to the right border
            int paddingTop = 20;// adding spacing form the top
            int spacing = 10; // space between hearts

            if(heartImage !=null) {//if the heart image was loaded successfully
                for (int i = 0; i < lives; i++) {// Loop through the number of lives and draw one heart per life
                    // Calculate X and Y position for each heart
                    int x = Constants.FRAME_WIDTH - paddingRight - ((i + 1) * Constants.HEART_SIZE) - (i * spacing);
                    int y = paddingTop;

                    // Draw the heart icon at the calculated position
                    g.drawImage(
                            heartImage,
                            x,
                            y,
                            Constants.HEART_SIZE,
                            Constants.HEART_SIZE,
                            p
                    );
                }
            }else{ //else if heart image failed to load, fallback to emoji text
                g.drawString("❤️ x " + lives, Constants.FRAME_WIDTH - 100, paddingTop + 20);
            }
        }
    }

    // Checks if egg collides with basket, deletes egg, score ++
    public void updateCollision(){
        // Move eggs down and check for collision
        for (int i = 0; i < eggSpawner.getEggList().size(); i++) {
            Egg egg = eggSpawner.getEggList().get(i);
            egg.update(); // Move the egg down

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
    // exposes game over state to other classes
    public boolean isGameOver() {
        return isGameOver;
    }
}