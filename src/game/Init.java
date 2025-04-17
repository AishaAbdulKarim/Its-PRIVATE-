package game;

import gameConstants.Constants;
import javax.swing.*;

public class Init extends JFrame {
    private final MainMenu MAIN_MENU;
    private MultiPlayerPanel gamePanelMP;
    private GamePanel gamePanelSP;

    // Initiates Main Menu
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

    // Transitions to single player panel
    public void showSPGame() {
        remove(MAIN_MENU);
        gamePanelSP = new GamePanel();
        add(gamePanelSP);
        gamePanelSP.setFocusable(true);
        gamePanelSP.requestFocusInWindow();
        revalidate();
        gameLoopSP();
    }
    // single player loop
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

    // Transitions to multiplayer panel
    public void showMPGame() {
        remove(MAIN_MENU);
        gamePanelMP = new MultiPlayerPanel();
        add(gamePanelMP);
        gamePanelMP.setFocusable(true);
        gamePanelMP.requestFocusInWindow();
        revalidate();
        gameLoopMP();
    }
    // multiplayer loop
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Init());
    }
}
