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
    private SPGameManager game;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean paused = false;

    private JButton restartGameButton;
    private JButton returnToMenuButton;
    private JButton resumeButton;

    public GamePanel() {
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        game = new SPGameManager();

        // Restart Game Button
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
            game.start();
            restartGameButton.setVisible(false);
            returnToMenuButton.setVisible(false);
            resumeButton.setVisible(false);
            paused = false;
            this.requestFocusInWindow();
        });
        this.add(restartGameButton);

        // Return to Menu Button
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

        // Resume Game Button
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
            paused = false;
            resumeButton.setVisible(false);
            this.requestFocusInWindow();
        });
        this.add(resumeButton);
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
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Arial", Font.BOLD, 36));
            graphics.drawString("PAUSED", Constants.FRAME_WIDTH / 2 - 80, Constants.FRAME_HEIGHT / 2);
        }
    }

    public void drawBackground(Graphics2D graphics) {
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    public void update() {
        if (paused || game.getIsGameOver()) {
            if (game.getIsGameOver()) {
                resetMove();
                restartGameButton.setVisible(true);
                returnToMenuButton.setVisible(true);
                resumeButton.setVisible(false);
                repaint();

                if (game.getScore() > ((Init) SwingUtilities.getWindowAncestor(this)).getHighScore()) {
                    ((Init) SwingUtilities.getWindowAncestor(this)).setHighScore(game.getScore());
                }
            }
            return;
        }

        game.update();
        this.repaint();
        updateMove();
    }

    public void updateMove() {
        if (movingLeft) game.getBasket().moveLeft();
        if (movingRight) game.getBasket().moveRight();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
            resumeButton.setVisible(paused);
            repaint();
            return;
        }

        if (game.getIsGameOver() || paused) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (game.getIsGameOver() || paused) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void resetMove() {
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