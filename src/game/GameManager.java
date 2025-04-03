package game;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import gameConstants.Constants;
import java.util.ArrayList;
public class GameManager {

    /*
     * This class is where all game assets are added and their positons. ie. basket, falling eggs, score tracker etc.
     * 
     */


     private Egg egg;
     private Basket basket;
     private ArrayList<Egg> eggs;
     private int score = 0;  //  Track the player's score
 
    
     public GameManager(){
        start();
    }
    
    public void start(){
        basket = new Basket("Player One", Constants.BASKET_X, Constants.BASKET_Y, Constants.BASKET_WIDTH, Constants.BASKET_HEIGHT, "basket_01.png");
        eggs = new ArrayList<>();

        // Adding a few eggs with different positions
        eggs.add(new Egg(100, 0, "egg_01.png"));
        eggs.add(new Egg(200, -100, "egg_01.png"));
        eggs.add(new Egg(300, -200, "egg_01.png"));
    }

    public void drawSprites(Graphics2D graphics, JPanel panel){
        for (Egg egg : eggs) {
            egg.draw(graphics, panel);
        }
        graphics.drawImage(basket.getImage(), basket.getX(), basket.getY(), basket.getWidth(), basket.getHeight(), panel);

        // 🏆 Display Score on Screen
        graphics.setColor(java.awt.Color.BLACK);
        graphics.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        graphics.drawString("Score: " + score, 20, 30);
    }

    public void update() {
        basket.update();
        
        // Move eggs down and check for collision
        for (int i = 0; i < eggs.size(); i++) {
            Egg egg = eggs.get(i);
            egg.update();

            if (checkCollision(egg, basket)) {
                score += 10;  // 🏆 Increase score
                eggs.remove(i); // Remove egg from list
                i--; // Adjust index after removal
            }
        }
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