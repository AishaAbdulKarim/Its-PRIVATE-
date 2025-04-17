package game;

import gameConstants.Constants;
import javax.swing.*;

public class Init extends JFrame {
    private final MainMenu MAIN_MENU;
    private MultiPlayerPanel gamePanelMP;
    private GamePanel gamePanelSP;

    private int highScore = 0; 

    public Init() {
        setTitle("Egg Catcher");
        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        MAIN_MENU = new MainMenu(this);
        add(MAIN_MENU);
        setVisible(true);
    }

    public void showSPGame() {
        remove(MAIN_MENU);
        gamePanelSP = new GamePanel();
        add(gamePanelSP);
        gamePanelSP.setFocusable(true);
        gamePanelSP.requestFocusInWindow();
        revalidate();
        gameLoopSP();
    }

    private void gameLoopSP() {
        Thread gameThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(Constants.REFRESH_RATE);
                    gamePanelSP.update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.setDaemon(true);
        gameThread.start();
    }

    public void showMPGame() {
        remove(MAIN_MENU);
        gamePanelMP = new MultiPlayerPanel();
        add(gamePanelMP);
        gamePanelMP.setFocusable(true);
        gamePanelMP.requestFocusInWindow();
        revalidate();
        gameLoopMP();
    }

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

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
    public void returnToMainMenu() {
        getContentPane().removeAll(); // Remove everything
        add(MAIN_MENU);               // Add main menu back
        MAIN_MENU.requestFocusInWindow();
        revalidate();
        repaint();
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Init());
    }
}
