package game;

import gameConstants.Constants;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GamePanel is the main panel that handles the drawing and user interaction
 * for the egg-catching game. It supports multiplayer gameplay and manages
 * game updates, player input, and button interactions.
 */
public class GamePanel extends JPanel implements KeyListener {

    // Game state manager
    private final GameManager GAME;

    // Player movement tracking
    private boolean movingLeft = false;
    private boolean movingRight = false;

    // UI buttons
    private JButton startPlayer2Button;
    private JButton restartGameButton;

    // Message displayed when a player wins or game ends in a tie
    private String winnerMessage = "";

    /**
     * Constructs the game panel, initializes the game and UI components.
     */
    public GamePanel() {
        setLayout(null); // Absolute layout for manual positioning

        // Create and start game logic
        GAME = new GameManager();
        GAME.start();

        // Enable key input
        addKeyListener(this);
        setFocusable(true);

        // Setup "Start Player 2" Button
        startPlayer2Button = new JButton("Start Player 2");
        startPlayer2Button.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 100, 200, 40);
        startPlayer2Button.setFocusable(false);
        startPlayer2Button.setVisible(false);

       
        startPlayer2Button.setOpaque(true);
        startPlayer2Button.setBackground(Color.WHITE);
        startPlayer2Button.setForeground(Color.BLACK);
        startPlayer2Button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        startPlayer2Button.setFont(new Font("Arial", Font.BOLD, 16));

        
        startPlayer2Button.addActionListener(e -> {
            GAME.startPlayer2();
            startPlayer2Button.setVisible(false);
            this.requestFocusInWindow();
        });

     
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
            GAME.start();
            restartGameButton.setVisible(false);
            winnerMessage = "";
            this.requestFocusInWindow();
        });

        // Add buttons to panel
        this.add(startPlayer2Button);
        this.add(restartGameButton);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(graphics); // Draw sky
        GAME.drawSprites(graphics, this); // Draw basket, eggs, score, etc.

        // Display winner message if available
        if (!winnerMessage.isEmpty()) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.BOLD, 34));
            int x = (Constants.FRAME_WIDTH - graphics.getFontMetrics().stringWidth(winnerMessage)) / 2;
            int y = Constants.FRAME_HEIGHT / 2 + 40;
            graphics.drawString(winnerMessage, x, y);
        }
    }

   
    public void drawBackground(Graphics2D graphics) {
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    
    public void update() {
        if (GAME.isGameOver()) {
            if (GAME.isWaitingForPlayer2()) {
                // Show Start Player 2 button if waiting
                startPlayer2Button.setVisible(true);
            }

            
            if (!GAME.isWaitingForPlayer2() && GAME.getCurrentPlayer() == 2) {
                restartGameButton.setVisible(true);
                compareScores();
            }

            repaint();
            return;
        }

       
        if (movingLeft) GAME.getBasket().moveLeft();
        if (movingRight) GAME.getBasket().moveRight();

       
        GAME.update();
        repaint();
    }

    
    private void compareScores() {
        int player1Score = GAME.getPlayer1Score();
        int player2Score = GAME.getPlayer2Score();

        if (player1Score > player2Score) {
            winnerMessage = "Player 1 Wins! (" + player1Score + " - " + player2Score + ")";
        } else if (player2Score > player1Score) {
            winnerMessage = "Player 2 Wins! (" + player2Score + " - " + player1Score + ")";
        } else {
            winnerMessage = "It's a Tie! (" + player1Score + " - " + player2Score + ")";
        }
    }

    
    public GameManager getGameManager() {
        return GAME;
    }

    // ==== KEY LISTENER METHODS ====

    @Override
    public void keyPressed(KeyEvent e) {
        if (GAME.isGameOver()) return;

        // Track left/right arrow key presses
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (GAME.isGameOver()) return;

       
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but required by KeyListener interface
    }
}

