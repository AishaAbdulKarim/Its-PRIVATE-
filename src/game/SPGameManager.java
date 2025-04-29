package game;

import gameConstants.Constants;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SPGameManager {

    private Basket basket;
    private EggSpawner eggSpawner;
    private int score = 0;  // Track the player's score
    private int lives = 3;  // Number of lives the player has
    private boolean isGameOver = false;  // Flag indicating whether the game is over
    private BufferedImage heartImage;  // Image of a heart to represent lives
    private Sound eggCatch;  // Audio for basket catching egg
    private Sound lostLife;  // Audio for egg reaching bottom of screen
    private int lastScoreCheckpoint = 0;
    
    private ArrayList<Egg> heartList = new ArrayList<>();  // List to store hearts
    private int lastHeartSpawnScore = 0;  // Track the last score at which a heart was spawned

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SPGameManager() {
        start();
    }

    // Runs once on startup. Instantiate game objects here
    @SuppressWarnings("CallToPrintStackTrace")
    public void start() {
        basket = new Basket("Player One", Constants.BASKET_X, Constants.BASKET_Y, Constants.BASKET_WIDTH, Constants.BASKET_HEIGHT, "basket_01.png");
        eggSpawner = new EggSpawner();
        score = 0;  // Resets score
        lives = 3;  // Resets lives
        isGameOver = false;  // Resets the game over flag
        lastScoreCheckpoint = 0;  // Resets checkpoint
        lastHeartSpawnScore = 0;  // Initialize the last heart spawn score

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

    public void drawSprites(Graphics2D graphics, JPanel panel) {
        // Draws basket
        graphics.drawImage(basket.getImage(), basket.getX(), basket.getY(), basket.getWidth(), basket.getHeight(), panel);

        // Draw Eggs
        for (Egg e : eggSpawner.eggList) {
            graphics.drawImage(e.getEggImage(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), panel);
        }

        // Draw Hearts
        for (Egg heart : heartList) {
            graphics.drawImage(heart.getEggImage(), heart.getX(), heart.getY(), heart.getWidth(), heart.getHeight(), panel);
        }

        // Display Score on Screen
        graphics.setColor(java.awt.Color.BLACK);
        graphics.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        graphics.drawString("Score: " + score, 20, 30);

        // Display Lives with hearts
        loseState(graphics, panel);
    }

    public void update() {
        eggSpawner.update();
        basket.update();
        updateCollision();

        // Update heart positions
        for (Egg heart : heartList) {
            heart.update();  // Move heart down
        }

        // Spawn hearts at specific score intervals (every 100 or 150 points)
        if (score - lastHeartSpawnScore >= 200) {  // Spawn hearts every 100 points
            spawnHeart();
            lastHeartSpawnScore = score;  // Update last heart spawn score
        }

        if (score - lastScoreCheckpoint >= 150) {
            lastScoreCheckpoint += 150;  // Move checkpoint forward by 150 points

            // Gradually increase spawn rate for eggs
            int newSpawnRate = eggSpawner.getSpawnRate() + 1;
            eggSpawner.setSpawnRate(Math.min(newSpawnRate, 25));  // Cap spawnRate at 25%

            // Every 2 difficulty jumps, slightly increase egg speed
            if ((score / 150) % 2 == 0) {
                for (Egg egg : eggSpawner.getEggList()) {
                    egg.setSpeed(egg.getSpeed() + 1);
                }
            }

            System.out.println("Difficulty increased: spawnRate = " + eggSpawner.getSpawnRate());
        }

        if (lives <= 0) {
            setGameOver(true);
        }
    }

    // Updates the collision checks for eggs and hearts falling and basket interaction
    public void updateCollision() {
        for (int i = 0; i < eggSpawner.getEggList().size(); i++) {
            Egg egg = eggSpawner.getEggList().get(i);
            egg.update();  // Update egg's position

            // Check if egg collided with basket
            if (checkCollision(egg, basket)) {
                score += 10;  // Increase score
                eggSpawner.getEggList().remove(i);  // Remove egg from list
                i--;  // Adjust index since list size has changed
                eggCatch.play();
            } else if (egg.getY() > Constants.FRAME_HEIGHT) {  // Egg fell out of the screen
                lives--;  // Decrease lives
                eggSpawner.getEggList().remove(i);  // Remove egg from list
                i--;  // Adjust index since list size has changed
                lostLife.play();
            }
        }

        // Check collisions with hearts
        for (int i = 0; i < heartList.size(); i++) {
            Egg heart = heartList.get(i);

            // Check if heart collided with basket
            if (checkCollision(heart, basket)) {
                lives++;  // Increase lives
                heartList.remove(i);  // Remove heart from list
                i--;  // Adjust index since list size has changed
            } else if (heart.getY() > Constants.FRAME_HEIGHT) {  // Heart fell out of the screen
                heartList.remove(i);  // Remove heart from list
                i--;  // Adjust index since list size has changed
            }
        }
    }

    // Detects if the basket catches an egg or heart
    private boolean checkCollision(Egg egg, Basket basket) {
        return egg.getX() < basket.getX() + basket.getWidth() &&
                egg.getX() + 40 > basket.getX() &&  // 40 = egg/heart width
                egg.getY() + 50 > basket.getY() &&  // 50 = egg/heart height
                egg.getY() < basket.getY() + basket.getHeight();
    }

    // Spawn a new heart
    private void spawnHeart() {
        int x = (int) (Math.random() * (Constants.FRAME_WIDTH - 40));  // Random position
        int y = -40;  // Start above the screen
        int speed = 1 + (int) (Math.random() * 2);  // Random speed for variety (slower than eggs)
        Egg heart = new Egg(x, y, 30, 30, "redHeart.png");  // Create a new Egg object as a heart
        heartList.add(heart);  // Add the heart to the list
    }

    // Display the player's current lives with heart icons
    public void loseState(Graphics2D g, JPanel p) {
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

    public Basket getBasket() {
        return basket;
    }

    public int getScore() {
        return score;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public EggSpawner getEggSpawner() {
        return eggSpawner;
    }

    public void setEggSpawner(EggSpawner eggSpawner) {
        this.eggSpawner = eggSpawner;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public BufferedImage getHeartImage() {
        return heartImage;
    }

    public void setHeartImage(BufferedImage heartImage) {
        this.heartImage = heartImage;
    }
}
