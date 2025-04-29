package game;

import gameConstants.Constants;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GamePanel extends JPanel implements KeyListener {
    private SPGameManager game; // Manages game elements
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private JButton restartGameButton;
    private JButton returnToMenuButton; 
    private boolean paused = false;

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
            returnToMenuButton.setVisible(false); 
            this.requestFocusInWindow();
        });

        this.add(restartGameButton);

        // return menu button
        returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 100, 200, 40);
        returnToMenuButton.setFocusable(false);
        returnToMenuButton.setVisible(false);

        returnToMenuButton.setOpaque(true);
        returnToMenuButton.setBackground(Color.WHITE);
        returnToMenuButton.setForeground(Color.BLACK);
        returnToMenuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        returnToMenuButton.setFont(new Font("Arial", Font.BOLD, 16));
        returnToMenuButton.addActionListener(e -> {
            ((Init) SwingUtilities.getWindowAncestor(this)).returnToMainMenu();
        });

        this.add(returnToMenuButton);
    }

    private JButton resumeButton; // Declare the resume button

resumeButton = new JButton("Resume");
resumeButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 200, 200, 40);
resumeButton.setFocusable(false);
resumeButton.setVisible(false);  // Initially hidden
resumeButton.setOpaque(true);
resumeButton.setBackground(Color.WHITE);
resumeButton.setForeground(Color.BLACK);
resumeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
resumeButton.setFont(new Font("Arial", Font.BOLD, 16));
resumeButton.addActionListener(e -> {
    paused = false; // Unpause when the button is clicked
    resumeButton.setVisible(false); // Hide resume button after resuming
    this.requestFocusInWindow(); // Refocus the panel
});
this.add(resumeButton);
@Override
public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_P) {
        paused = !paused; // Toggle the paused state
        resumeButton.setVisible(paused); // Show resume button when paused
        repaint(); // Repaint to update the screen
        return;
    }

    if (game.getIsGameOver() || paused) return; // Skip movement if game over or paused

    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        movingLeft = true;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        movingRight = true;
    }
}
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    drawBackground(graphics);
    game.drawSprites(graphics, this);

    if (paused) {
        graphics.setColor(new Color(0, 0, 0, 100)); // dim background
        graphics.fillRect(0, 0, getWidth(), getHeight()); // Fill screen with a semi-transparent color
        graphics.setColor(Color.WHITE); // White text
        graphics.setFont(new Font("Arial", Font.BOLD, 36)); // Large font size
        graphics.drawString("PAUSED", Constants.FRAME_WIDTH / 2 - 80, Constants.FRAME_HEIGHT / 2); // Draw "PAUSED" message
    }
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
            resetMove();
            restartGameButton.setVisible(true);
            returnToMenuButton.setVisible(true); // Show Return button
            repaint();

            // Update high score
            if (game.getScore() > ((Init) SwingUtilities.getWindowAncestor(this)).getHighScore()) {
                ((Init) SwingUtilities.getWindowAncestor(this)).setHighScore(game.getScore());
            }
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

    public void resetMove(){
        movingLeft = false;
        movingRight = false;
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
