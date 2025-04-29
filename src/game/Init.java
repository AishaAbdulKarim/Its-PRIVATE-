package game;

import gameConstants.Constants;
import javax.swing.*;

public class Init extends JFrame {
    // Main menu panel instance
    private final MainMenu MAIN_MENU;

    // Panels for game modes
    private MultiPlayerPanel gamePanelMP;
    private GamePanel gamePanelSP;

    // Stores the highest score achieved
    private int highScore = 0;

    private String playerName = "Player One"; // Default if user cancels

    // Constructor 
    public Init() {
        setTitle("Egg Catcher"); // Set window title
        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 
        setResizable(false); 

        // Create and display the main menu
        MAIN_MENU = new MainMenu(this);
        add(MAIN_MENU);

        setVisible(true); // Show the window
    }

    // Launches the single-player game mode
    public void showSPGame() {
        playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player One";
        }
        remove(MAIN_MENU);
        gamePanelSP = new GamePanel(playerName); // pass name to GamePanel
        add(gamePanelSP);
        gamePanelSP.setFocusable(true);
        gamePanelSP.requestFocusInWindow();
        revalidate();
        gameLoopSP();
    }

    // Game loop for single player - updates the game at a fixed interval
    private void gameLoopSP() {
        Thread gameThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(Constants.REFRESH_RATE); // Control game speed
                    gamePanelSP.update(); // Update game logic
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.setDaemon(true); 
        gameThread.start();
    }

    // Launches the multiplayer game mode
    public void showMPGame() {
        playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player One");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player One";
        }

        remove(MAIN_MENU); // Remove main menu
        gamePanelMP = new MultiPlayerPanel(); // Create multiplayer panel
        add(gamePanelMP);
        gamePanelMP.setFocusable(true);
        gamePanelMP.requestFocusInWindow();
        revalidate(); // Refresh layout
        gameLoopMP(); // Start update loop
    }

    // Game loop for multiplayer - similar to single player
    private void gameLoopMP() {
        Thread gameThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(Constants.REFRESH_RATE);
                    gamePanelMP.update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.setDaemon(true);
        gameThread.start();
    }

    // Getter for high score
    public int getHighScore() {
        return highScore;
    }

    // Setter for high score
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    // Returns to the main menu from any game mode
    public void returnToMainMenu() {
        getContentPane().removeAll(); // Remove all current panels
        add(MAIN_MENU);               // Add back the main menu
        MAIN_MENU.requestFocusInWindow();
        revalidate(); // Refresh layout
        repaint(); // Redraw screen
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Init()); 
     }
}
