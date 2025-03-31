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
        basket = new Basket("Player One", Constants.BASKET_X, Constants.BASKET_Y, Constants.BASKET_WIDTH, Constants.BASKET_HEIGHT, "basket_01.png");
        egg = new Egg(150, 0, "egg_01.png");
        eggs = new ArrayList<>();

        // Adding a few eggs with different positions
        eggs.add(new Egg(100, 0, "egg_01.png"));
        eggs.add(new Egg(200, -100, "egg_01.png"));
        eggs.add(new Egg(300, -200, "egg_01.png"));
    }

    /*
     * Renders images to panel
     * Implement graphics.drawImage(image, int x position, int y position, int with, int height, panel); to render an image to the panel
     */
    public void drawSprites(Graphics2D graphics, JPanel panel){
        for (Egg egg : eggs) {// draws eggs first
            egg.draw(graphics, panel);
        }
        //Draw Basket
        graphics.drawImage(basket.getImage(), basket.getX(), basket.getY(), basket.getWidth(), basket.getHeight(), panel);

    }

    /*
     * Implement class methods to run each frame here
     * basket.move();
     * egg.spawn();
     * etc.
     */
    public void update() {
        basket.update();
        for (Egg egg : eggs) {
            egg.update();
        }
    }
}
