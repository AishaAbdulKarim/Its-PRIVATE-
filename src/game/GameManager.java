package game;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import gameConstants.Constants;
public class GameManager {

    /*
     * This class is where all game assets are added and their positons. ie. basket, falling eggs, score tracker etc.
     * 
     */

     private Basket basket;
     private EggSpawner eggSpawner;
     private int score = 0;  //  Track the player's score

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
    }

    public void drawSprites(Graphics2D graphics, JPanel panel){
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
    }

    /*
     * Updates game objects
     * Objects will render in order placed within method. Each object in front of previous
     */
    public void update() {
        eggSpawner.update();
        basket.update();
        updateCollision();
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
            }
        }
    }

    public Basket getBasket() {
        return basket;
    }

    public int getScore() {
        return score;
    }
}