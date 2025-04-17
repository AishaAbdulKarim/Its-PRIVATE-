package game;

import gameConstants.Constants;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {
    private SPGameManager game; // Manages game elements
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private JButton restartGameButton;

    // Constructor initializes the game and sets up key listener
    public GamePanel() {
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        game = new SPGameManager();

        // Setup "Restart Game" Button
        restartGameButton = new JButton("Restart Game");
        restartGameButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 150, 200, 40);
        restartGameButton.setFocusable(false);
        restartGameButton.setVisible(false);

        // Styling for Restart button
        restartGameButton.setOpaque(true);
        restartGameButton.setBackground(Color.WHITE);
        restartGameButton.setForeground(Color.BLACK);
        restartGameButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        restartGameButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Action: Restart the game and hide winner message
        restartGameButton.addActionListener(e -> {
            game.start();
            restartGameButton.setVisible(false);
            this.requestFocusInWindow();
        });

        
        this.add(restartGameButton);

    }

    // Draws the game components on the panel
    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
        if(game.getIsGameOver() == true){
            restartGameButton.setVisible(true);
            repaint();
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