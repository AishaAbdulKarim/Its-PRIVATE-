package game;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import gameConstants.Constants;
public class GameManager {

    /*
     * This class is where all game assets are added and their positons. ie. basket, falling eggs, score tracker etc.
     * 
     */
    

	public class GameManager {

	    // Manages game elements like the basket and falling eggs

	    // Basket instance
	    private Basket basket;

	    // Constructor initializes the game
	    public GameManager() {
	        start();
	    }

	    // Initializes game elements
	    public void start() {
	        basket = new Basket("Player One", Constants.BASKET_X, Constants.BASKET_Y, Constants.BASKET_WIDTH, Constants.BASKET_HEIGHT, "basket_01.png");
	    }

	    // Draws game objects on the screen
	    public void drawSprites(Graphics2D graphics, JPanel panel) {
	        graphics.drawImage(basket.getImage(), basket.getX(), basket.getY(), basket.getWidth(), basket.getHeight(), panel);
	    }

	    // Updates game logic
	    public void update() {
	        // Future updates go here
	    }

	    // Returns the basket object
	    public Basket getBasket() {
	        return basket;
	    }
	}
