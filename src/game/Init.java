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
        remove(MAIN_MENU); // Remove main menu from frame
        gamePanelSP = new GamePanel(); // Create game panel
        add(gamePanelSP); // Add game panel to frame
        gamePanelSP.setFocusable(true);
        gamePanelSP.requestFocusInWindow();
        revalidate(); // Refresh layout
        gameLoopSP(); // Start update loop
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
