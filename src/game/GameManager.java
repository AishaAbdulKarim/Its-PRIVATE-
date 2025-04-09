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
 
    
     public GameManager(){
        start();
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

    public void update() {
        basket.update();
        eggSpawner.update();
        // // Move eggs down and check for collision
        // for (int i = 0; i < eggs.size(); i++) {
        //     Egg egg = eggs.get(i);
        //     egg.update();

        //     if (checkCollision(egg, basket)) {
        //         score += 10;  // 🏆 Increase score
        //         eggs.remove(i); // Remove egg from list
        //         i--; // Adjust index after removal
        //     }
        // }
    }

 // 🚀 Detects if the basket catches an egg
 private boolean checkCollision(Egg egg, Basket basket) {
    return egg.getX() < basket.getX() + basket.getWidth() &&
           egg.getX() + 40 > basket.getX() && // 40 = egg width
           egg.getY() + 50 > basket.getY() && // 50 = egg height
           egg.getY() < basket.getY() + basket.getHeight();
}

public Basket getBasket() {
    return basket;
}

public int getScore() {
    return score;
}
}