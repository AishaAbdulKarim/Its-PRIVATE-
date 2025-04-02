package game;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import gameConstants.Constants;
public class GameManager {

    /*
     * This class is where all game assets are added and their positons. ie. basket, falling eggs, score tracker etc.
     * 
     */
    
     /*
      * Create instance of your class here
      */
    private Basket basket;

    /*
     * Executed by GamePanel
     */
    public GameManager(){
        start();
    }
    

    /*
     * Initialize classes to be rendered here
     */
    public void start(){
    	String fileName = "empty_basket";
        basket = new Basket("Player One", Constants.BASKET_X, Constants.BASKET_Y, Constants.BASKET_WIDTH, Constants.BASKET_HEIGHT, "basket_01.png");
    }

    /*
     * Renders images to panel
     * Implement graphics.drawImage(image, int x position, int y position, int wifth, int height, panel); to render an image to the panel
     */
 



    public void drawSprites(Graphics2D graphics, JPanel panel){
        graphics.drawImage(basket.getImage(), basket.getX(), basket.getY(), basket.getWidth(), basket.getHeight(), panel);
    }

    public void update(){
        // Update basket position (called every frame)
    }

    public Basket getBasket() {
    	
        return basket;
        
    }
}




