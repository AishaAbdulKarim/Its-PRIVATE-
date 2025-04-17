package game;

import gameConstants.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {
    private SPGameManager game; // Manages game elements
    private boolean movingLeft = false;
    private boolean movingRight = false;

    // Constructor initializes the game and sets up key listener
    public GamePanel() {
        addKeyListener(this);
        setFocusable(true);
        game = new SPGameManager();

    }

    // Draws the game components on the panel
    @Override
    public void paint(Graphics g) {
    super.paint(g);
    Graphics2D graphics = (Graphics2D) g;
    RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setRenderingHints(hints);

    drawBackground(graphics);
    game.drawSprites(graphics, this);
}



    // Draws the background color
    public void drawBackground(Graphics2D graphics) {
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    // Updates the game state and repaints the screen
    public void update() {
        if(!game.getIsGameOver()){
            game.update();
            this.repaint();
            updateMove();
        }
        
    }

    public SPGameManager getGameManager() { 
        return game;
    }

    public void updateMove(){
        if(movingLeft) game.getBasket().moveLeft();
        if(movingRight) game.getBasket().moveRight();
    }

    // Handles key press events for moving the basket
    @Override
    public void keyPressed(KeyEvent e) {
        if (game.getIsGameOver()) return;

        // Track left/right arrow key presses
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (game.getIsGameOver()) return;

        // Track key release to stop movement
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
    }

    // Handles key typing (not used)
    @Override
    public void keyTyped(KeyEvent e) {
    }

    public SPGameManager getGame() {
        return game;
    }

    public void setGame(SPGameManager game) {
        this.game = game;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    
}