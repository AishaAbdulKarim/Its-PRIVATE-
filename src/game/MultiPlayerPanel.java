package game;

import gameConstants.Constants;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MultiPlayerPanel  extends JPanel implements KeyListener {

    // Game state manager
    private final GameManager GAME;
    private JButton resumeButton;


    // Player movement tracking
    private boolean movingLeft = false;
    private boolean movingRight = false;

    // UI buttons
    private JButton startPlayer2Button;
    private JButton restartGameButton;
    private JButton returnToMenuButton; //

    // Message displayed when a player wins or game ends in a tie
    private String winnerMessage = "";

    
      //Constructs the game panel, initializes the game and UI components.
    
    public MultiPlayerPanel() {
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

        // Styling for Player 2 button
        startPlayer2Button.setOpaque(true);
        startPlayer2Button.setBackground(Color.WHITE);
        startPlayer2Button.setForeground(Color.BLACK);
        startPlayer2Button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        startPlayer2Button.setFont(new Font("Arial", Font.BOLD, 16));

        // Action: Start second player and hide button
        startPlayer2Button.addActionListener(e -> {
            GAME.startPlayer2();
            resetMove();
            startPlayer2Button.setVisible(false);
            this.requestFocusInWindow();
        });

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
            resetMove();
            GAME.start();
            restartGameButton.setVisible(false);
            returnToMenuButton.setVisible(false); // Hide Return button
            winnerMessage = "";
            this.requestFocusInWindow();
        });

        // Setup "Return to Menu" Button
        returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 200, 200, 40);
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

        resumeButton = new JButton("Resume");
resumeButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 250, 200, 40);
styleButton(resumeButton);
resumeButton.setVisible(false);
resumeButton.addActionListener(e -> {
    paused = false;
    resumeButton.setVisible(false);
    this.requestFocusInWindow();
});
add(resumeButton);
private void styleButton(JButton button) {
    button.setFocusable(false);
    button.setOpaque(true);
    button.setBackground(Color.WHITE);
    button.setForeground(Color.BLACK);
    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    button.setFont(new Font("Arial", Font.BOLD, 16));
}

        // Add buttons to panel
        this.add(startPlayer2Button);
        this.add(restartGameButton);
        this.add(returnToMenuButton);
    }

    
    // Paints the background, game objects, and winner message if applicable.
     
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

    
     //Fills the background with sky blue color.
     
    public void drawBackground(Graphics2D graphics) {
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    
     //Updates game state, handles player transitions, and repaints panel.
     
    public void update() {
        if (GAME.isGameOver()) {
            if (GAME.isWaitingForPlayer2()) {
                // Show Start Player 2 button if waiting
                startPlayer2Button.setVisible(true);
            }

            // Game over after Player 2 finishes
            if (!GAME.isWaitingForPlayer2() && GAME.getCurrentPlayer() == 2) {
                restartGameButton.setVisible(true);
                returnToMenuButton.setVisible(true); // Show Return
                compareScores();
            }

            repaint();
            return;
        }

        // Handle player movement input
        updateMove();

        // Update game logic and repaint screen
        GAME.update();
        repaint();
    }

    
      //Compares player scores and sets the winner message.
     
    private void compareScores() {
        int player1Score = GAME.getPlayer1Score();
        int player2Score = GAME.getPlayer2Score();

        if (player1Score > player2Score) {
            winnerMessage = "Player 1 Wins! (" + player1Score + " - " + player2Score + ")";
            updateHighScore(player1Score);
        } else if (player2Score > player1Score) {
            winnerMessage = "Player 2 Wins! (" + player2Score + " - " + player1Score + ")";
            updateHighScore(player2Score);
        } else {
            winnerMessage = "It's a Tie! (" + player1Score + " - " + player2Score + ")";
            updateHighScore(player1Score); 
        }
    }

    
    // Updates the overall high score if needed.
     
    private void updateHighScore(int score) {
        Init parent = (Init) SwingUtilities.getWindowAncestor(this);
        if (score > parent.getHighScore()) {
            parent.setHighScore(score);
        }
    }

    
     //Returns the current GameManager instance.
     
    public GameManager getGameManager() {
        return GAME;
    }

    public void updateMove(){
        if(movingLeft) GAME.getBasket().moveLeft();
        if(movingRight) GAME.getBasket().moveRight();
    }

    public void resetMove(){
        movingLeft = false;
        movingRight = false;
    }

    // key listener methods

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

        // Track key release to stop movement
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
