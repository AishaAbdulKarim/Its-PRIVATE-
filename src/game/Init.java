package game;

import gameConstants.Constants;
import javax.swing.*;

public class Init extends JFrame {
    private MainMenu mainMenu;
    private MultiPlayerPanel gamePanelMP;
    private GamePanel gamePanelSP;

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

    public void showMPGame() {
        remove(mainMenu);
        gamePanelMP = new MultiPlayerPanel();
        add(gamePanelMP);
        gamePanelMP.setFocusable(true);
        gamePanelMP.requestFocusInWindow();
        revalidate();
        gameLoopMP();
    }

    public void showSPGame() {
        remove(mainMenu);
        gamePanelSP = new GamePanel();
        add(gamePanelSP);
        gamePanelSP.setFocusable(true);
        gamePanelSP.requestFocusInWindow();
        revalidate();
        gameLoopSP();
    }

    private void gameLoopSP() {
        long startTime = System.currentTimeMillis();
        while(true){
            long elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime > Constants.REFRESH_RATE){
                
                gamePanelSP.update();
                startTime = System.currentTimeMillis();                         // This function must remain at the end of this statement
            }
        }   
    }

    private void gameLoopMP() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(Constants.REFRESH_RATE);
                    gamePanelMP.update();
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
