package game;

import gameConstants.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {
    final private GameManager GAME; // Manages game elements
    boolean movingLeft = false;
    boolean movingRight = false;


     private JButton startPlayer2Button;
    private JButton restartGameButton;
    private String winnerMessage = "";

    // Constructor initializes the game and sets up key listener
    public GamePanel() {
        setLayout(null); // Absolute layout for manual positioning

        // Create and start game logic
        GAME = new GameManager();
        GAME.start();

        addKeyListener(this);
        setFocusable(true);

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

    this.add(startPlayer2Button);
    this.add(restartGameButton);

});
    // Draws the background color
    public void drawBackground(Graphics2D graphics) {
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

          // Display winner message if available
          if (!winnerMessage.isEmpty()) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.BOLD, 34));
            int x = (Constants.FRAME_WIDTH - graphics.getFontMetrics().stringWidth(winnerMessage)) / 2;
            int y = Constants.FRAME_HEIGHT / 2 + 40;
            graphics.drawString(winnerMessage, x, y);
        }
    }

    // Updates the game state and repaints the screen
    public void update() {
        if (GAME.isGameOver()) {
            if (GAME.isWaitingForPlayer2()) {
                // Show Start Player 2 button if waiting
                startPlayer2Button.setVisible(true);
            }

                // Game over after Player 2 finishes
                if (!GAME.isWaitingForPlayer2() && GAME.getCurrentPlayer() == 2) {
                    restartGameButton.setVisible(true);
                    compareScores();
                }
    
                repaint();
                return;
            }
    public GameManager getGameManager() { 
        return GAME;
    }

    public void updateMove(){
        if(movingLeft) GAME.getBasket().moveLeft();
        if(movingRight) GAME.getBasket().moveRight();
    }
    private void compareScores() {
        int player1Score = GAME.getPlayer1Score();
        int player2Score = GAME.getPlayer2Score();

        if (player1Score > player2Score) {
            winnerMessage = "Player 1 Wins! (" + player1Score + " - " + player2Score + ")";
        } 
    // Handles key press events for moving the basket
    @Override
    public void keyPressed(KeyEvent e) {
        if (GAME.isGameOver()) return; // Ignore movement if game is over
        
        Basket basket = GAME.getBasket();
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = true; // Move left
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = true; // Move right
        }
    }

    // Handles key release (not used for now)
    @Override
    public void keyReleased(KeyEvent e) {
        if (GAME.isGameOver()) return; // Ignore movement if game is over
        
        Basket basket = GAME.getBasket();
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = false; // Move left
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = false; // Move right
        }
    }

    // Handles key typing (not used)
    @Override
    public void keyTyped(KeyEvent e) {
    }
}

