package game;

import gameConstants.Constants;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MultiPlayerPanel extends JPanel implements KeyListener {

    private final GameManager GAME;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean paused = false;

    private JButton startPlayer2Button;
    private JButton restartGameButton;
    private JButton returnToMenuButton;
    private JButton resumeButton;

    private String winnerMessage = "";

    public MultiPlayerPanel() {
        setLayout(null);
        GAME = new GameManager();
        GAME.start();

        addKeyListener(this);
        setFocusable(true);

        // Start Player 2
        startPlayer2Button = new JButton("Start Player 2");
        startPlayer2Button.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 100, 200, 40);
        styleButton(startPlayer2Button);
        startPlayer2Button.setVisible(false);
        startPlayer2Button.addActionListener(e -> {
            GAME.startPlayer2();
            resetMove();
            startPlayer2Button.setVisible(false);
            this.requestFocusInWindow();
        });

        // Restart Game
        restartGameButton = new JButton("Restart Game");
        restartGameButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 150, 200, 40);
        styleButton(restartGameButton);
        restartGameButton.setVisible(false);
        restartGameButton.addActionListener(e -> {
            resetMove();
            GAME.start();
            restartGameButton.setVisible(false);
            returnToMenuButton.setVisible(false);
            winnerMessage = "";
            this.requestFocusInWindow();
        });

        // Return to Menu
        returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 200, 200, 40);
        styleButton(returnToMenuButton);
        returnToMenuButton.setVisible(false);
        returnToMenuButton.addActionListener(e -> {
            ((Init) SwingUtilities.getWindowAncestor(this)).returnToMainMenu();
        });

        // Resume Button
        resumeButton = new JButton("Resume");
        resumeButton.setBounds(Constants.FRAME_WIDTH / 2 - 100, Constants.FRAME_HEIGHT - 250, 200, 40);
        styleButton(resumeButton);
        resumeButton.setVisible(false);
        resumeButton.addActionListener(e -> {
            paused = false;
            resumeButton.setVisible(false);
            this.requestFocusInWindow();
        });

        add(startPlayer2Button);
        add(restartGameButton);
        add(returnToMenuButton);
        add(resumeButton);
    }

    private void styleButton(JButton button) {
        button.setFocusable(false);
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(graphics);
        GAME.drawSprites(graphics, this);

        if (!winnerMessage.isEmpty()) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.BOLD, 34));
            int x = (Constants.FRAME_WIDTH - graphics.getFontMetrics().stringWidth(winnerMessage)) / 2;
            int y = Constants.FRAME_HEIGHT / 2 + 40;
            graphics.drawString(winnerMessage, x, y);
        }

        if (paused) {
            graphics.setColor(new Color(0, 0, 0, 120));
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Arial", Font.BOLD, 40));
            String pauseMsg = "Game Paused";
            int x = (Constants.FRAME_WIDTH - graphics.getFontMetrics().stringWidth(pauseMsg)) / 2;
            graphics.drawString(pauseMsg, x, Constants.FRAME_HEIGHT / 2);
        }
    }

    public void drawBackground(Graphics2D graphics) {
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    public void update() {
        if (paused) return;

        if (GAME.isGameOver()) {
            if (GAME.isWaitingForPlayer2()) {
                startPlayer2Button.setVisible(true);
            }

            if (!GAME.isWaitingForPlayer2() && GAME.getCurrentPlayer() == 2) {
                restartGameButton.setVisible(true);
                returnToMenuButton.setVisible(true);
                compareScores();
            }

            repaint();
            return;
        }

        updateMove();
        GAME.update();
        repaint();
    }

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

    private void updateHighScore(int score) {
        Init parent = (Init) SwingUtilities.getWindowAncestor(this);
        if (score > parent.getHighScore()) {
            parent.setHighScore(score);
        }
    }

    public GameManager getGameManager() {
        return GAME;
    }

    public void updateMove() {
        if (movingLeft) GAME.getBasket().moveLeft();
        if (movingRight) GAME.getBasket().moveRight();
    }

    public void resetMove() {
        movingLeft = false;
        movingRight = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
            resumeButton.setVisible(paused);
            repaint();
            return;
        }

        if (GAME.isGameOver() || paused) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (GAME.isGameOver() || paused) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
