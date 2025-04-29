package game;

import gameConstants.Constants;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameManager {

    private Basket basket; // The basket controlled by the player
    private EggSpawner eggSpawner; // Spawns eggs in the game
    private int score = 0; // Player's score
    private int lives = 3; // Number of lives the player has
    private boolean isGameOver = false; // Flag indicating whether the game is over
    private BufferedImage heartImage; // Image of a heart to represent lives

    private int currentPlayer = 1; // Tracks which player is playing (1 or 2)
    private int player1Score = 0; // Score for player 1
    private int player2Score = 0; // Score for player 2
    private boolean isMultiplayer = true; // Flag to check if multiplayer is enabled
    private boolean waitingForPlayer2Start = false; // Flag to check if it's waiting for Player 2 to start
    private Sound eggCatch; // Audio for basket catching egg
    private Sound lostLife; // Audio for egg reaching bottom of screen
    private int lastScoreCheckpoint = 0; // To track difficulty upgrades


    // Constructor, initializes objects when the game starts
    public GameManager() {}

    // Starts a new game, resets all values
    public void start() {
        basket = new Basket("Player One", Constants.BASKET_X, Constants.BASKET_Y, Constants.BASKET_WIDTH, Constants.BASKET_HEIGHT, "basket_01.png");
        eggSpawner = new EggSpawner(); // Creates the egg spawner
        score = 0; // Resets score
        lives = 3; // Resets lives
        isGameOver = false; // Resets the game over flag
        waitingForPlayer2Start = false; // Resets the flag for Player 2 start
        currentPlayer = 1; // Starts with Player 1

        // Tries to load the heart image, which is used for lives
        try {
            heartImage = ImageIO.read(new File("src/images/redHeart.png"));
        } catch (IOException e) {
            System.err.println("Could not load heart image redHeart.png");
            e.printStackTrace();
        }
        eggCatch = new Sound("one.wav");
        lostLife = new Sound("lostLife.wav");
    }

    // Updates the game state, including egg spawning, basket movement, and collision detection
    public void update() {
        if (isGameOver) return; // If the game is over, stop updating the game

        eggSpawner.update(); // Update the state of the eggs
        basket.update(); // Update the state of the basket
        updateCollision(); // Check for collisions between the basket and the eggs

        if (lives <= 0) { // Check if lives have reached zero
            if (isMultiplayer && currentPlayer == 1 && !waitingForPlayer2Start) {
                player1Score = score; // Save Player 1's score
                waitingForPlayer2Start = true; // Set the flag to wait for Player 2
                isGameOver = true; // End the game
                return;
            }
            if (!waitingForPlayer2Start && currentPlayer == 2) {
                player2Score = score; // Save Player 2's score
                isGameOver = true; // End the game
            }
        }
    }

    // Starts Player 2's turn, resetting necessary states
    public void startPlayer2() {
        waitingForPlayer2Start = false; // Stop waiting for Player 2 to start
        isGameOver = false; // Set game over flag to false
        resetForNextPlayer(); // Reset for the next player
        currentPlayer = 2; // Set current player to Player 2
        System.out.println("Starting Player 2...");
    }

    // Resets values for the next player's turn
    private void resetForNextPlayer() {
        lives = 3; // Reset lives
        score = 0; // Reset score
        eggSpawner.getEggList().clear(); // Clear the list of eggs
        basket.setX(Constants.BASKET_X); // Reset basket position
    }

    // Draws all game objects on the screen, including the basket, eggs, and HUD
    public void drawSprites(Graphics2D graphics, JPanel panel) {
        // Draw the basket and eggs
        graphics.drawImage(basket.getImage(), basket.getX(), basket.getY(), basket.getWidth(), basket.getHeight(), panel);

        for (Egg e : eggSpawner.getEggList()) {
            graphics.drawImage(e.getEggImage(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), panel);
        }

        // Draw the player's current status (Player 1 or Player 2)
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.drawString("Player " + currentPlayer, 20, 55);
        graphics.drawString("Score: " + score, 20, 30);

        // Handle displaying lose state (end game or waiting for next player)
        loseState(graphics, panel);
    }

    // Updates the collision checks for eggs falling and basket interaction
    public void updateCollision() {
        for (int i = 0; i < eggSpawner.getEggList().size(); i++) {
            Egg egg = eggSpawner.getEggList().get(i);
            egg.update(); // Update egg's position

            // Check if egg collided with basket
            if (checkCollision(egg, basket)) {
                score += 10; // Increase score
                eggSpawner.getEggList().remove(i); // Remove egg from list
                i--; // Adjust index since list size has changed
                eggCatch.play();
            } else if (egg.getY() > Constants.FRAME_HEIGHT) { // Egg fell out of the screen
                lives--; // Decrease lives
                eggSpawner.getEggList().remove(i); // Remove egg from list
                i--; // Adjust index since list size has changed
                lostLife.play();
            }
        }
    }

    // Checks if an egg has collided with the basket
    private boolean checkCollision(Egg egg, Basket basket) {
        return egg.getX() < basket.getX() + basket.getWidth() &&
               egg.getX() + egg.getWidth() > basket.getX() &&
               egg.getY() + egg.getHeight() > basket.getY() &&
               egg.getY() < basket.getY() + basket.getHeight();
    }

    
    public void loseState(Graphics2D g, JPanel p) {
        if (waitingForPlayer2Start) { // Display message when Player 1 finishes
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            String message = "Player 1 Finished! Score: " + player1Score;
            int x = (Constants.FRAME_WIDTH - g.getFontMetrics().stringWidth(message)) / 2;
            int y = Constants.FRAME_HEIGHT / 2;
            g.drawString(message, x, y);
            return;
        }

        // Skip drawing duplicate game over message during Player 2 end – handled in GamePanel
        if (isGameOver && currentPlayer == 2) {
            return;
        }

        // Display the player's current lives with heart icons
        if (!isGameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            int paddingRight = 20;
            int paddingTop = 20;
            int spacing = 10;

            // If heart image is available, draw it for each remaining life
            if (heartImage != null) {
                for (int i = 0; i < lives; i++) {
                    int x = Constants.FRAME_WIDTH - paddingRight - ((i + 1) * Constants.HEART_SIZE) - (i * spacing);
                    int y = paddingTop;
                    g.drawImage(heartImage, x, y, Constants.HEART_SIZE, Constants.HEART_SIZE, p);
                }
            } else {
                // Display lives count as text if heart image isn't available
                g.drawString("❤️ x " + lives, Constants.FRAME_WIDTH - 100, paddingTop + 20);
            }
        }
    }

    // Getter methods for various game state variables
    public Basket getBasket() { return basket; }
    public int getScore() { return score; }
    public boolean isGameOver() { return isGameOver; }
    public boolean isWaitingForPlayer2() { return waitingForPlayer2Start; }
    public int getCurrentPlayer() { return currentPlayer; }
    public int getPlayer1Score() { return player1Score; }
    public int getPlayer2Score() { return player2Score; }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }


}
