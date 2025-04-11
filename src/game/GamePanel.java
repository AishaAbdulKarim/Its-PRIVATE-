package game;

import javax.swing.JPanel;
import gameConstants.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {
    final private GameManager GAME; // Manages game elements

    // Constructor initializes the game and sets up key listener
    @SuppressWarnings("LeakingThisInConstructor")
    public GamePanel() {
        addKeyListener(this);
        setFocusable(true);
        GAME = new GameManager();
        GAME.start();

    }

    // Draws the game components on the panel
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHints(hints);

        drawBackground(graphics);
        GAME.drawSprites(graphics, this);
}



    // Draws the background color
    public void drawBackground(Graphics2D graphics) {
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    // Updates the game state and repaints the screen
    public void update() {
        GAME.update();
        this.repaint();
    }
    public GameManager getGameManager() { 
        return GAME;
    }

    // Handles key press events for moving the basket
    @Override
    public void keyPressed(KeyEvent e) {
        Basket basket = GAME.getBasket();
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            basket.moveLeft(); // Move left
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            basket.moveRight(); // Move right
        }
    }

    // Handles key release (not used for now)
    @Override
    public void keyReleased(KeyEvent e) {
    }

    // Handles key typing (not used)
    @Override
    public void keyTyped(KeyEvent e) {
    }
}

