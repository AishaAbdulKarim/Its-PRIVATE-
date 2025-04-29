package game;

import gameConstants.Constants;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// GamePanel is responsible for handling the game screen, UI buttons, and input events
public class GamePanel extends JPanel implements KeyListener {

    private SPGameManager game; // Game logic manager
    private boolean movingLeft = false;  // Flag for left movement
    private boolean movingRight = false; // Flag for right movement
    private boolean paused = false;      // Flag to check if game is paused
    private String playerName;

    // UI Buttons
    private JButton restartGameButton;
    private JButton returnToMenuButton;
    private JButton resumeButton;

    // Constructor: Initializes panel, game, and UI buttons
    public GamePanel(String playerName) {
        this.playerName = playerName;
        setLayout(null); // No layout manager; absolute positioning
        addKeyListener(this); // Add key listener to detect keyboard input
        setFocusable(true);   // Allows the panel to receive keyboard focus
        game = new SPGameManager(playerName);

        // --- Restart Game Button Setup ---
        restartGameButton = new JButton("Restart Game");
        restartGameButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 150, 200, 40);
        restartGameButton.setFocusable(false);
        restartGameButton.setVisible(false);
        restartGameButton.setOpaque(true);
        restartGameButton.setBackground(Color.WHITE);
        restartGameButton.setForeground(Color.BLACK);
        restartGameButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        restartGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartGameButton.addActionListener(e -> {
            game.start(); // Restart the game logic
            restartGameButton.setVisible(false);
            returnToMenuButton.setVisible(false);
            resumeButton.setVisible(false);
            paused = false;
            this.requestFocusInWindow(); // Regain focus for key events
        });
        this.add(restartGameButton);

        // --- Return to Menu Button Setup ---
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
            // Call the method from Init class to return to main menu
            ((Init) SwingUtilities.getWindowAncestor(this)).returnToMainMenu();
        });
        this.add(returnToMenuButton);

        // --- Resume Button Setup ---
        resumeButton = new JButton("Resume");
        resumeButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 200, 200, 40);
        resumeButton.setFocusable(false);
        resumeButton.setVisible(false);
        resumeButton.setOpaque(true);
        resumeButton.setBackground(Color.WHITE);
        resumeButton.setForeground(Color.BLACK);
        resumeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        resumeButton.setFont(new Font("Arial", Font.BOLD, 16));
        resumeButton.addActionListener(e -> {
            paused = false; // Unpause the game
            resumeButton.setVisible(false); // Hide resume button
            this.requestFocusInWindow();    // Regain focus for key input
        });
        this.add(resumeButton);
    }

    // Custom painting logic
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(graphics);             // Draw sky background
        game.drawSprites(graphics, this);     // Draw game elements

        if (paused) {
            // Dim background when paused
            graphics.setColor(new Color(0, 0, 0, 100));
            graphics.fillRect(0, 0, getWidth(), getHeight());
            // Display "PAUSED" text
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Arial", Font.BOLD, 36));
            graphics.drawString("PAUSED", Constants.FRAME_WIDTH / 2 - 80, Constants.FRAME_HEIGHT / 2);
        }
    }

    // Draws background sky
    public void drawBackground(Graphics2D graphics) {
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    // Called each frame to update game logic
    public void update() {
        if (paused || game.getIsGameOver()) {
            if (game.getIsGameOver()) {
                resetMove(); // Stop player movement
                restartGameButton.setVisible(true);
                returnToMenuButton.setVisible(true);
                resumeButton.setVisible(false);
                repaint();

                // Update high score if current score is higher
                if (game.getScore() > ((Init) SwingUtilities.getWindowAncestor(this)).getHighScore()) {
                    ((Init) SwingUtilities.getWindowAncestor(this)).setHighScore(game.getScore());
                }
            }
            return; // Skip update when paused or game over
        }

        game.update(); // Game logic update
        this.repaint(); // Trigger repaint
        updateMove();   // Apply movement
    }

    // Updates basket movement based on input flags
    public void updateMove() {
        if (movingLeft) game.getBasket().moveLeft();
        if (movingRight) game.getBasket().moveRight();
    }

    // Called when a key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        // Toggle pause on 'P' key
        if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
            resumeButton.setVisible(paused);
            repaint();
            return;
        }

        // Ignore inputs if game is paused or over
        if (game.getIsGameOver() || paused) return;

        // Movement control
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = true;
        }
    }

    // Called when a key is released
    @Override
    public void keyReleased(KeyEvent e) {
        if (game.getIsGameOver() || paused) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
    }

    // Not used but required by interface
    @Override
    public void keyTyped(KeyEvent e) {}

    // Resets movement flags
    public void resetMove() {
        movingLeft = false;
        movingRight = false;
    }

    // --- Getter and Setter Methods ---

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
