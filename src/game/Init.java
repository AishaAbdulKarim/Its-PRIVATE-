package game;

import javax.swing.*;
import gameConstants.Constants;

public class Init extends JFrame {
    private MainMenu mainMenu;
    private GamePanel gamePanel;

    public Init() {
        setTitle("Egg Catcher");
        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainMenu = new MainMenu(this);
        add(mainMenu);
        setVisible(true);
    }

    public void showGame() {
        remove(mainMenu);
        gamePanel = new GamePanel();
        add(gamePanel);
        revalidate();
        gameLoop();
    }

    private void gameLoop() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(Constants.REFRESH_RATE);
                    gamePanel.update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Init());
    }
}
